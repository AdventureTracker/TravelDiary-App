package com.fiit.traveldiary.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import com.fiit.traveldiary.app.R;
import com.fiit.traveldiary.app.api.ApiRequest;
import com.fiit.traveldiary.app.api.ApiResponse;
import com.fiit.traveldiary.app.api.NetworkSyncOperations;
import com.fiit.traveldiary.app.api.RequestType;
import com.fiit.traveldiary.app.api.connection.WebsocketConnectionManager;
import com.fiit.traveldiary.app.api.provider.WebsocketProvider;
import com.fiit.traveldiary.app.db.SyncStatus;
import com.fiit.traveldiary.app.db.TravelDiaryContract;
import com.fiit.traveldiary.app.db.helpers.TripHelper;
import com.fiit.traveldiary.app.db.provider.SQLiteProvider;
import com.fiit.traveldiary.app.helpers.NetworkActivityManager;
import com.fiit.traveldiary.app.models.Trip;
import com.securepreferences.SecurePreferences;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * Tato aktivita bude sluzit na stiahnute ENUMs a sync, overenie prihlasenia a podobne
 */
public class MainActivity extends AppCompatActivity implements AsyncTaskReceiver{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);

//		try {
//			WebsocketConnectionManager.getInstance().getSocket().emit("ping", "");
//		} catch (URISyntaxException e) {
//			e.printStackTrace();
//		}

		SQLiteProvider.getInstance(this.getBaseContext()).getReadableDatabase();

		SecurePreferences preferences = new SecurePreferences(this.getBaseContext());

		if (preferences.getString("USER_TOKEN", "NOT_LOGGED_IN").equals("NOT_LOGGED_IN")) {
			Intent intent = new Intent(this, LoginActivity.class);
			startActivity(intent);
		}
		else {

			List<Trip> createdTrips = TripHelper.getAll(String.format("WHERE %s = '%s'", TravelDiaryContract.TripEntry.COLUMN_SYNC, SyncStatus.CREATED));
			List<Trip> updatedTrips = TripHelper.getAll(String.format("WHERE %s = '%s'", TravelDiaryContract.TripEntry.COLUMN_SYNC, SyncStatus.UPDATED));
			List<Trip> removedTrips = TripHelper.getAll(String.format("WHERE %s = '%s'", TravelDiaryContract.TripEntry.COLUMN_SYNC, SyncStatus.REMOVED));

			List<ApiRequest> apiRequests = new ArrayList<ApiRequest>();
			apiRequests.add(new ApiRequest(this.getBaseContext(), RequestType.ENUMS, new String[]{}));
//			apiRequests.add((new ApiRequest(this.getBaseContext(), RequestType.ENUMS, new String[]{})).setProvider(WebsocketProvider.class));

//			for (Trip trip : createdTrips) {
//				try {
//					apiRequests.add(new ApiRequest(this.getBaseContext(), RequestType.CREATE_TRIP, new String[]{}, trip.toJSON(true)));
//				} catch (JSONException e) {
//					e.printStackTrace();
//				}
//			}
//
//			for (Trip trip : updatedTrips) {
//				try {
//					apiRequests.add(new ApiRequest(this.getBaseContext(), RequestType.UPDATE_TRIP, new String[]{trip.getUuid()}, trip.toJSON(true)));
//				} catch (JSONException e) {
//					e.printStackTrace();
//				}
//			}
//
//			for (Trip trip : removedTrips) {
//				apiRequests.add(new ApiRequest(this.getBaseContext(), RequestType.DELETE_TRIP, new String[]{trip.getUuid()}));
//			}

			if (NetworkActivityManager.hasActiveInternetConnection(this.getBaseContext())) {
				NetworkSyncOperations networkSyncOperations = new NetworkSyncOperations();
				networkSyncOperations.setDelegate(this);
				networkSyncOperations.execute((ApiRequest[]) apiRequests.toArray(new ApiRequest[apiRequests.size()]));
			}
			else {
				this.startTripListActivity();
			}

		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		int id = item.getItemId();

		if (id == R.id.action_settings) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}


	@Override
	public void processFinish(List<ApiResponse> apiResponses) {
		this.startTripListActivity();
	}

	private void startTripListActivity() {
		Intent intent = new Intent(this, TripListActivity.class);
		startActivity(intent);
		finish();
	}
}
