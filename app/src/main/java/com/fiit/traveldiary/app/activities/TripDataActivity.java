package com.fiit.traveldiary.app.activities;

/**
 * Created by Barbora on 14.4.2016.
 */
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import com.fiit.traveldiary.app.R;
import com.fiit.traveldiary.app.api.ApiRequest;
import com.fiit.traveldiary.app.api.ApiResponse;
import com.fiit.traveldiary.app.api.NetworkSyncOperations;
import com.fiit.traveldiary.app.api.RequestType;
import com.fiit.traveldiary.app.db.SyncStatus;
import com.fiit.traveldiary.app.db.TravelDiaryContract;
import com.fiit.traveldiary.app.db.helpers.PrivacyHelper;
import com.fiit.traveldiary.app.db.helpers.StatusHelper;
import com.fiit.traveldiary.app.db.helpers.TripHelper;
import com.fiit.traveldiary.app.db.helpers.UserHelper;
import com.fiit.traveldiary.app.exceptions.RecordNotFoundException;
import com.fiit.traveldiary.app.fragments.DatePickerFragment;
import com.fiit.traveldiary.app.helpers.NetworkActivityManager;
import com.fiit.traveldiary.app.models.Privacy;
import com.fiit.traveldiary.app.models.Status;
import com.fiit.traveldiary.app.models.Trip;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class TripDataActivity extends AppCompatActivity implements View.OnClickListener, AsyncTaskReceiver {

	Toolbar toolbar;
	private Trip trip;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.trip_data);

		toolbar = (Toolbar) findViewById(R.id.toolbar);
		EditText description = (EditText) findViewById(R.id.tripDataDescription);

		// toolbar.setTitle(R.string.toolbarTitle);
		setSupportActionBar(toolbar);

		toolbar.setNavigationIcon(R.drawable.sipka_back);

		// Status adapter
		ArrayAdapter<Status> statusArrayAdapter = new ArrayAdapter<Status>(this, android.R.layout.simple_spinner_item, StatusHelper.getAll());
		statusArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		getStatusSpinner().setAdapter(statusArrayAdapter);

		// Privacy helper
		ArrayAdapter<Privacy> privacyArrayAdapter = new ArrayAdapter<Privacy>(this, android.R.layout.simple_spinner_item, PrivacyHelper.getAll());
		privacyArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		getPrivacySpinner().setAdapter(privacyArrayAdapter);

		Bundle extras = this.getIntent().getExtras();
		if (extras != null) {
			long idTrip = extras.getLong("idTrip", 0);
			if (idTrip > 0) {
				try {
					this.trip = TripHelper.getOne(String.format("WHERE %s = %d", TravelDiaryContract.TripEntry.COLUMN_ID_TRIP, idTrip));
				} catch (RecordNotFoundException ignored) {}
			}
			else {
				this.trip = new Trip();
			}
		}


		if (this.trip.getUuid() != null && NetworkActivityManager.hasActiveInternetConnection(this.getBaseContext())) {
			NetworkSyncOperations networkSyncOperations = new NetworkSyncOperations();
			networkSyncOperations.setDelegate(this);
			networkSyncOperations.execute(
					new ApiRequest(this.getBaseContext(), RequestType.TRIP, new String[]{trip.getUuid()})
			);
		}

		description.setOnTouchListener(new View.OnTouchListener() {

			public boolean onTouch(View view, MotionEvent event) {
				// TODO Auto-generated method stub
				if (view.getId() ==R.id.description) {
					view.getParent().requestDisallowInterceptTouchEvent(true);
					switch (event.getAction()&MotionEvent.ACTION_MASK){
						case MotionEvent.ACTION_UP:
							view.getParent().requestDisallowInterceptTouchEvent(false);
							break;
					}
				}
				return false;
			}
		});

		this.setListeners();
	}

	private EditText getName(){
		return (EditText) findViewById(R.id.name);
	}

	private EditText getDestination(){
		return (EditText) findViewById(R.id.destination);
	}

	private EditText getStartDate(){
		return (EditText) findViewById(R.id.startDate);
	}

	private EditText getEstimatedArrival(){
		return (EditText) findViewById(R.id.estimatedArrival);
	}

	private EditText getDecsription(){
		return (EditText) findViewById(R.id.tripDataDescription);
	}

	private Spinner getStatusSpinner() { return (Spinner) findViewById(R.id.tripStatusSpinner); }

	private Spinner getPrivacySpinner() { return (Spinner) findViewById(R.id.privacySpinner); }

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.save:
				this.processForm(view);
				break;
		}
	}

	private void setListeners() {

		// StartDatePicker
		this.getStartDate().setInputType(InputType.TYPE_NULL);
		this.getStartDate().setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				DatePickerFragment fragment = new DatePickerFragment();
				fragment.setEditText(getStartDate());
				fragment.show(getFragmentManager(), "datePicker");
			}
		});
		this.getStartDate().setOnFocusChangeListener(new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					DatePickerFragment fragment = new DatePickerFragment();
					fragment.setEditText(getStartDate());
					fragment.show(getFragmentManager(), "datePicker");
				}
			}
		});

		// EstimatedArrivalDatePicker
		this.getEstimatedArrival().setInputType(InputType.TYPE_NULL);
		this.getEstimatedArrival().setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				DatePickerFragment fragment = new DatePickerFragment();
				fragment.setEditText(getEstimatedArrival());
				fragment.show(getFragmentManager(), "datePicker");
			}
		});
		this.getEstimatedArrival().setOnFocusChangeListener(new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					DatePickerFragment fragment = new DatePickerFragment();
					fragment.setEditText(getEstimatedArrival());
					fragment.show(getFragmentManager(), "datePicker");
				}
			}
		});



	}

	private void prefillForm(long idTrip) {
		try {
			this.trip = TripHelper.getOne(String.format("WHERE %s = %d", TravelDiaryContract.TripEntry.COLUMN_ID_TRIP, idTrip));

			this.getName().setText(this.trip.getName());
			this.getDestination().setText(this.trip.getDestination());
			this.getDecsription().setText(this.trip.getDescription());
			this.getStartDate().setText(this.trip.getStartDateAsString("yyyy-MM-dd"));
			this.getEstimatedArrival().setText(this.trip.getEstimatedArrivalAsString("yyyy-MM-dd"));

			for (int i = 0; i < getPrivacySpinner().getCount(); i++) {
				if (((ArrayAdapter<Privacy>) getPrivacySpinner().getAdapter()).getItem(i).getCode().equals(this.trip.getPrivacy().getCode())) {
					getPrivacySpinner().setSelection(i);
					break;
				}
			}

			for (int i = 0; i < getStatusSpinner().getCount(); i++) {
				if (((ArrayAdapter<Status>) getStatusSpinner().getAdapter()).getItem(i).getCode().equals(this.trip.getStatus().getCode())) {
					getStatusSpinner().setSelection(i);
					break;
				}
			}


		} catch (RecordNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void processFinish(List<ApiResponse> apiResponses) {
		for (ApiResponse response : apiResponses) {

			if (response.getOriginalRequest().getRequestType().equals(RequestType.TRIP)) {
				Bundle extras = this.getIntent().getExtras();
				long idTrip = extras.getLong("idTrip", 0);
				prefillForm(idTrip);
			}
			else if (response.getOriginalRequest().getRequestType().isExecutiveRequest()) {
				Log.w("ApiResponse", response.getContent().toString());
				this.listTrips();
			}

		}
	}

	public void processForm(View view) {

		if (this.trip.getUuid() == null) {
			this.trip.setUuid(UUID.randomUUID().toString());
			this.trip.setSyncStatus(SyncStatus.CREATED);
			this.trip.setCreatedAt(new Date());
		}
		else {
			this.trip.setSyncStatus(SyncStatus.UPDATED);
		}

		this.trip.setName(this.getName().getText().toString());
		this.trip.setDescription(this.getDecsription().getText().toString());
		this.trip.setDestination(this.getDestination().getText().toString());
		this.trip.setStartDateFromString(this.getStartDate().getText().toString(), "yyyy-MM-dd");
		this.trip.setEstimatedArrivalFromString(this.getEstimatedArrival().getText().toString(), "yyyy-MM-dd");
		this.trip.setUpdatedAt(new Date());
		this.trip.setStatus((Status) this.getStatusSpinner().getSelectedItem());
		this.trip.setPrivacy((Privacy) this.getPrivacySpinner().getSelectedItem());

		// FIXME: hardcoded UUID, zabudol som zapamatat UUID prihlaseneho pouzivatela... smutne...
		try {
			this.trip.addUser(
					UserHelper.get("6205fea4-0380-4a19-802b-2217cc2949ff")
			);
		} catch (RecordNotFoundException e) {
			e.printStackTrace();
		}

		TripHelper.persist(trip);

		if (NetworkActivityManager.hasActiveInternetConnection(this.getBaseContext())) {

			NetworkSyncOperations networkSyncOperations = new NetworkSyncOperations();
			networkSyncOperations.setDelegate(this);

			ApiRequest apiRequest;
			JSONObject jsonObject;
			try {
				jsonObject = trip.toJSON(true);
			} catch (JSONException e) {
				e.printStackTrace();
				return;
			}

			if (this.trip.getSyncStatus().equals(SyncStatus.CREATED)) {
				apiRequest = new ApiRequest(this.getBaseContext(), RequestType.CREATE_TRIP, new String[]{}, jsonObject);
			}
			else {
				apiRequest = new ApiRequest(this.getBaseContext(), RequestType.UPDATE_TRIP, new String[]{trip.getUuid()}, jsonObject);
			}

			networkSyncOperations.execute(apiRequest);

		}
		else {
			this.listTrips();
			Log.w("Network", "Yo man! No connection");
		}
	}

	private void listTrips() {
		Intent intent = new Intent(this, TripListActivity.class);
		startActivity(intent);
		finish();
	}
}
