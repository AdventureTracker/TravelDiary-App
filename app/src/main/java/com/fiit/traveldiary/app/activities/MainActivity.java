package com.fiit.traveldiary.app.activities;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import com.fiit.traveldiary.app.R;
import com.fiit.traveldiary.app.api.*;
import com.fiit.traveldiary.app.db.provider.SQLiteProvider;
import com.fiit.traveldiary.app.helpers.NetworkActivityManager;
import com.securepreferences.SecurePreferences;
import java.util.List;

/**
 * Tato aktivita bude sluzit na stiahnute ENUMs a sync, overenie prihlasenia a podobne
 */
public class MainActivity extends AppCompatActivity implements AsyncTaskReceiver{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.record_data);

//		List<RecordType> recordTypeList = new ArrayList<RecordType>();
//
//		recordTypeList.add(new RecordType(46, "HITCHHIKING_START", "Hitchhiking start destination"));
//		recordTypeList.add(new RecordType(42, "CAMPING", "Camping stuff"));
//
//		Spinner spinner = (Spinner) findViewById(R.id.recordType);
//
//		ArrayAdapter<RecordType> recordTypeArrayAdapter = new ArrayAdapter<RecordType>(this, android.R.layout.simple_spinner_item, recordTypeList);
//		recordTypeArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//		spinner.setAdapter(recordTypeArrayAdapter);

		SQLiteProvider.getInstance(this.getBaseContext()).getReadableDatabase();

		SecurePreferences preferences = new SecurePreferences(this.getBaseContext());

		if (preferences.getString("USER_TOKEN", "NOT_LOGGED_IN").equals("NOT_LOGGED_IN")) {
			Intent intent = new Intent(this, LoginActivity.class);
			startActivity(intent);
		}
		else {

			if (NetworkActivityManager.hasActiveInternetConnection(this.getBaseContext())) {
				NetworkSyncOperations networkSyncOperations = new NetworkSyncOperations();
				networkSyncOperations.setDelegate(this);
				networkSyncOperations.execute(
						new ApiRequest(this.getBaseContext(), RequestType.ENUMS, new String[]{}),
						new ApiRequest(this.getBaseContext(), RequestType.TRIP_LIST, new String[]{}),
						new ApiRequest(this.getBaseContext(), RequestType.TRIP, new String[]{"b429b294-ac24-423f-bb5a-a90998dd7612"}),
						new ApiRequest(this.getBaseContext(), RequestType.TRIP_RECORD, new String[]{"b429b294-ac24-423f-bb5a-a90998dd7612", "bf8730b8-5423-48dc-a57c-23ea7bdb809a"})
				);
				// Tu sprav sync
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
	}
}
