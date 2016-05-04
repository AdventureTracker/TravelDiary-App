package com.fiit.traveldiary.app.activities;

/**
 * Created by Barbora on 14.4.2016.
 */

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ListView;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import com.fiit.traveldiary.app.R;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;
import com.fiit.traveldiary.app.adapters.RecordAdapter;
import com.fiit.traveldiary.app.api.ApiRequest;
import com.fiit.traveldiary.app.api.ApiResponse;
import com.fiit.traveldiary.app.api.NetworkSyncOperations;
import com.fiit.traveldiary.app.api.RequestType;
import com.fiit.traveldiary.app.db.SyncStatus;
import com.fiit.traveldiary.app.db.TravelDiaryContract;
import com.fiit.traveldiary.app.db.helpers.RecordHelper;
import com.fiit.traveldiary.app.db.helpers.TripHelper;
import com.fiit.traveldiary.app.exceptions.RecordNotFoundException;
import com.fiit.traveldiary.app.helpers.NetworkActivityManager;
import com.fiit.traveldiary.app.models.Record;
import com.fiit.traveldiary.app.models.Trip;

import java.util.ArrayList;
import java.util.List;


public class RecordListActivity extends AppCompatActivity implements View.OnClickListener, AsyncTaskReceiver {

	private ImageView imageView;
	private TextView textView;
	private ListView dayList;
	private FrameLayout frameLayout;
	Toolbar toolbar;

	Trip trip;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.records_list);

		toolbar = (Toolbar) findViewById(R.id.toolbar);

		// toolbar.setTitle(R.string.toolbarTitle);
		setSupportActionBar(toolbar);

		toolbar.setNavigationIcon(R.drawable.sipka_back);

		imageView = (ImageView) findViewById(R.id.imageView);
		textView = (TextView) findViewById(R.id.textView);
		dayList = (ListView) findViewById(R.id.dayList);
		frameLayout = (FrameLayout) findViewById(R.id.frameLayout);
		findViewById(R.id.circleButton).setOnClickListener(this);

		Bundle extras = this.getIntent().getExtras();
		if (extras != null) {
			long idTrip = extras.getLong("idTrip", 0);
			try {
				this.trip = TripHelper.getOne(String.format("WHERE %s = %d", TravelDiaryContract.TripEntry.COLUMN_ID_TRIP, idTrip));
			} catch (RecordNotFoundException e) {
				finish();
			}
		}
		else {
			finish();
		}

		if (NetworkActivityManager.hasActiveInternetConnection(this.getBaseContext())) {
			NetworkSyncOperations networkSyncOperations = new NetworkSyncOperations();
			networkSyncOperations.setDelegate(this);
			networkSyncOperations.execute(
					new ApiRequest(this.getBaseContext(), RequestType.TRIP, new String[]{this.trip.getUuid()})
			);
		}
		else {
			this.loadRecords();
		}

	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.circleButton:
				createRecord(0);
				break;
		}
	}

	private void loadRecords() {
		ArrayList<Record> records = (ArrayList<Record>) RecordHelper.getAll(String.format("WHERE %s = %d AND %s != '%s'", TravelDiaryContract.RecordEntry.COLUMN_ID_TRIP, this.trip.getId(), TravelDiaryContract.RecordEntry.COLUMN_SYNC, SyncStatus.REMOVED));
		RecordAdapter adapter = new RecordAdapter(this, records);
		this.dayList.setAdapter(adapter);
	}


	@Override
	public void processFinish(List<ApiResponse> apiResponses) {

		this.loadRecords();

//		for (ApiResponse response : apiResponses) {
//			if (response.getOriginalRequest().getRequestType().isPersistRequest()) {
//				this.loadRecords();
//			}
//		}

	}

	public void createRecord(long idRecord) {
		Intent intent = new Intent(this, RecordDataActivity.class);
		intent.putExtra("idTrip", idRecord);
		startActivity(intent);
	}
}
