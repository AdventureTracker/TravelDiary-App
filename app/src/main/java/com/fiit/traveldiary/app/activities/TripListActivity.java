package com.fiit.traveldiary.app.activities;

/**
 * Created by Barbora on 14.4.2016.
 */

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ListView;
import com.fiit.traveldiary.app.R;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import com.fiit.traveldiary.app.adapters.TripAdapter;
import com.fiit.traveldiary.app.api.ApiRequest;
import com.fiit.traveldiary.app.api.ApiResponse;
import com.fiit.traveldiary.app.api.NetworkSyncOperations;
import com.fiit.traveldiary.app.api.RequestType;
import com.fiit.traveldiary.app.db.SyncStatus;
import com.fiit.traveldiary.app.db.TravelDiaryContract;
import com.fiit.traveldiary.app.db.helpers.TripHelper;
import com.fiit.traveldiary.app.helpers.NetworkActivityManager;
import com.fiit.traveldiary.app.models.Trip;

import java.util.ArrayList;
import java.util.List;


public class TripListActivity extends AppCompatActivity implements View.OnClickListener, AsyncTaskReceiver {

	private FrameLayout frameLayout;
	private ListView listView;
	Toolbar toolbar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.trips_list);

		toolbar = (Toolbar) findViewById(R.id.toolbar);

		// toolbar.setTitle(R.string.toolbarTitle);
		setSupportActionBar(toolbar);

		toolbar.setNavigationIcon(R.drawable.sipka_back);

		frameLayout = (FrameLayout) findViewById(R.id.frameLayout);
		findViewById(R.id.circleButton).setOnClickListener(this);
		listView = (ListView) findViewById(R.id.listView);

		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Trip trip = (Trip) parent.getItemAtPosition(position);
				createTrip(trip.getId());
			}
		});

		registerForContextMenu(this.listView);

		if (NetworkActivityManager.hasActiveInternetConnection(this.getBaseContext())) {
			NetworkSyncOperations networkSyncOperations = new NetworkSyncOperations();
			networkSyncOperations.setDelegate(this);
			networkSyncOperations.execute(
					new ApiRequest(this.getBaseContext(), RequestType.TRIP_LIST, new String[]{})
			);
		}
		else {
			this.loadTrips();
		}
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.circleButton:
				createTrip(0);
				break;
		}
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		getMenuInflater().inflate(R.menu.context_menu, menu);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

		Trip trip = (Trip) this.listView.getItemAtPosition(info.position);

		switch (item.getItemId()) {
			case R.id.cnt_menu_edit:
				this.createTrip(trip.getId());
				break;
			case R.id.cnt_menu_remove:
				this.removeTrip(trip);
				break;
		}

		return true;
	}

	@Override
	public void processFinish(List<ApiResponse> apiResponses) {
		this.loadTrips();
	}

	private void loadTrips() {
		ArrayList<Trip> trips = (ArrayList<Trip>) TripHelper.getAll(String.format("WHERE %s != '%s'", TravelDiaryContract.TripEntry.COLUMN_SYNC, SyncStatus.REMOVED));
		TripAdapter adapter = new TripAdapter(this, trips);
		listView.setAdapter(adapter);
	}

	private void createTrip(long idTrip) {
		Intent intent = new Intent(this, TripDataActivity.class);
		intent.putExtra("idTrip", idTrip);
		startActivity(intent);
	}

	private void removeTrip(Trip trip) {

		trip.setSyncStatus(SyncStatus.REMOVED);
		TripHelper.persist(trip);

		if (NetworkActivityManager.hasActiveInternetConnection(this.getBaseContext())) {
			NetworkSyncOperations networkSyncOperations = new NetworkSyncOperations();
			networkSyncOperations.setDelegate(this);
			networkSyncOperations.execute(
					new ApiRequest(this.getBaseContext(), RequestType.DELETE_TRIP, new String[]{trip.getUuid()}),
					new ApiRequest(this.getBaseContext(), RequestType.TRIP_LIST, new String[]{})
			);
			TripHelper.remove(trip);
		}

	}
}
