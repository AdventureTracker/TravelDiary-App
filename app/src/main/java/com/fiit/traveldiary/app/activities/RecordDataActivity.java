package com.fiit.traveldiary.app.activities;

/**
 * Created by Barbora on 14.4.2016.
 */

import android.content.Intent;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import com.fiit.traveldiary.app.R;
import com.fiit.traveldiary.app.api.ApiRequest;
import com.fiit.traveldiary.app.api.ApiResponse;
import com.fiit.traveldiary.app.api.NetworkSyncOperations;
import com.fiit.traveldiary.app.api.RequestType;
import com.fiit.traveldiary.app.db.SyncStatus;
import com.fiit.traveldiary.app.db.TravelDiaryContract;
import com.fiit.traveldiary.app.db.helpers.RecordHelper;
import com.fiit.traveldiary.app.db.helpers.RecordTypeHelper;
import com.fiit.traveldiary.app.db.helpers.TripHelper;
import com.fiit.traveldiary.app.db.helpers.UserHelper;
import com.fiit.traveldiary.app.exceptions.RecordNotFoundException;
import com.fiit.traveldiary.app.fragments.DatePickerFragment;
import com.fiit.traveldiary.app.helpers.NetworkActivityManager;
import com.fiit.traveldiary.app.models.Location;
import com.fiit.traveldiary.app.models.Record;
import com.fiit.traveldiary.app.models.RecordType;
import com.fiit.traveldiary.app.models.Trip;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class RecordDataActivity extends AppCompatActivity implements View.OnClickListener, AsyncTaskReceiver {

	private Spinner recordTypeSpinner;
	private EditText recordDay;
	private Trip trip;
	private Button saveButton;
	private Location location;
	Toolbar toolbar;

	private final LocationListener mLocationListener = new LocationListener() {
		@Override
		public void onLocationChanged(final android.location.Location myCurrentLocation) {
			location = new Location(
					myCurrentLocation.getLatitude(),
					myCurrentLocation.getLongitude(),
					(int) myCurrentLocation.getAltitude()
			);
			Log.w("Location", location.toString());
			saveButton.setEnabled(true);

		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {

		}

		@Override
		public void onProviderEnabled(String provider) {

		}

		@Override
		public void onProviderDisabled(String provider) {

		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.record_data);

		toolbar = (Toolbar) findViewById(R.id.toolbar);

		// toolbar.setTitle(R.string.toolbarTitle);
		setSupportActionBar(toolbar);

		toolbar.setNavigationIcon(R.drawable.sipka_back);

//		findViewById(R.id.selectPhoto).setOnClickListener(this);
		this.recordTypeSpinner = (Spinner) findViewById(R.id.recordType);
		this.recordDay = (EditText) findViewById(R.id.recordDataDay);
		this.saveButton = (Button) findViewById(R.id.recordDataSave);
		this.saveButton.setEnabled(false);

		// RecordType adapter
		ArrayAdapter<RecordType> recordTypeArrayAdapter = new ArrayAdapter<RecordType>(this, android.R.layout.simple_spinner_item, RecordTypeHelper.getAll());
		recordTypeArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		this.recordTypeSpinner.setAdapter(recordTypeArrayAdapter);

		// RecordDayPicker
		this.recordDay.setInputType(InputType.TYPE_NULL);
		this.recordDay.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				DatePickerFragment fragment = new DatePickerFragment();
				fragment.setEditText(recordDay);
				fragment.show(getFragmentManager(), "datePicker");
			}
		});
		this.recordDay.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					DatePickerFragment fragment = new DatePickerFragment();
					fragment.setEditText(recordDay);
					fragment.show(getFragmentManager(), "datePicker");
				}
			}
		});

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

		// Request Location
		LocationManager mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
		mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 60000, 10, mLocationListener);
	}

	private EditText getDate(){
		return (EditText) findViewById(R.id.recordDataDay);
	}

	private EditText getDescription(){
		return (EditText) findViewById(R.id.description);
	}
	@Override
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.recordDataSave:
				this.processForm(view);
				break;
		}
	}

	public void processForm(View view) {

		Record record = new Record();
		record.setSyncStatus(SyncStatus.CREATED);
		record.setCreatedAt(new Date());
		record.setUpdatedAt(new Date());
		record.setUuid(UUID.randomUUID().toString());
		record.setTrip(this.trip);
		record.setDescription(this.getDescription().getText().toString());
		record.setDayFromString(this.getDate().getText().toString(), "yyyy-MM-dd");
		record.setLocation(this.location);
		record.setRecordType((RecordType) this.recordTypeSpinner.getSelectedItem());

		// FIXME: hardcoded UUID, zabudol som zapamatat UUID prihlaseneho pouzivatela... smutne...
		try {
			record.setUser(UserHelper.get("6205fea4-0380-4a19-802b-2217cc2949ff"));
		} catch (RecordNotFoundException e) {
			e.printStackTrace();
		}

		RecordHelper.persist(record);

		if (NetworkActivityManager.hasActiveInternetConnection(this.getBaseContext())) {

			NetworkSyncOperations networkSyncOperations = new NetworkSyncOperations();
			networkSyncOperations.setDelegate(this);

			ApiRequest apiRequest;
			JSONObject jsonObject;
			try {
				jsonObject = record.toJSON(true);
			} catch (JSONException e) {
				e.printStackTrace();
				return;
			}

			if (record.getSyncStatus().equals(SyncStatus.CREATED)) {
				apiRequest = new ApiRequest(this.getBaseContext(), RequestType.CREATE_TRIP_RECORD, new String[]{trip.getUuid()}, jsonObject);
			}
			else {
				apiRequest = new ApiRequest(this.getBaseContext(), RequestType.UPDATE_TRIP_RECORD, new String[]{trip.getUuid(), record.getUuid()}, jsonObject);
			}

			networkSyncOperations.execute(apiRequest);

		}
		else {
			this.viewTrip();
			Log.w("Network", "Yo man! No connection");
		}

	}

	@Override
	public void processFinish(List<ApiResponse> apiResponses) {
		for (ApiResponse response : apiResponses) {
			if (response.getOriginalRequest().getRequestType().isExecutiveRequest()) {
				Log.w("ApiResponse", response.getContent().toString());
				this.viewTrip();
			}

		}
	}

	private void viewTrip() {
		Intent intent = new Intent(this, RecordListActivity.class);
		intent.putExtra("idTrip", this.trip.getId());
		startActivity(intent);
		finish();
	}
}
