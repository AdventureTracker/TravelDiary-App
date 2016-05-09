package com.fiit.traveldiary.app.activities;

/**
 * Created by Barbora on 14.4.2016.
 */

import android.content.ClipData;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Base64;
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
import com.fiit.traveldiary.app.db.helpers.*;
import com.fiit.traveldiary.app.exceptions.RecordNotFoundException;
import com.fiit.traveldiary.app.fragments.DatePickerFragment;
import com.fiit.traveldiary.app.helpers.Base64Helper;
import com.fiit.traveldiary.app.helpers.NetworkActivityManager;
import com.fiit.traveldiary.app.models.*;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class RecordDataActivity extends AppCompatActivity implements View.OnClickListener, AsyncTaskReceiver {

	private Spinner recordTypeSpinner;
	private EditText recordDay;
	private Trip trip;
	private Button saveButton;
	private Location location;
	private List<Uri> photoList;
	private Toolbar toolbar;

	private static final int SELECT_SINGLE_PICTURE = 101;
	private static final int SELECT_MULTIPLE_PICTURE = 201;


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

		this.photoList = new ArrayList<Uri>();
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
			case R.id.selectPhoto:
				this.selectPhotos(view);
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

		for (Uri photoUri : this.photoList) {

			Photo photo;

			try {
				photo = PhotoHelper.getOne(String.format("WHERE %s = '%s'", TravelDiaryContract.PhotoEntry.COLUMN_DATA, photoUri.toString()));
			}
			catch (RecordNotFoundException e) {
				photo = new Photo();
				photo.setUuid(UUID.randomUUID().toString());
				photo.setFilename(photoUri.toString());
				photo.setCreatedAt(new Date());
				Bitmap bitmap = null;
				try {
					bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), photoUri);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				photo.setData(Base64Helper.bitmapToBase64(bitmap));
			}

			if (!record.getPhotos().contains(photo)) {
				record.addPhoto(photo);
			}
		}


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

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (resultCode == RESULT_OK) {

			if (requestCode == SELECT_MULTIPLE_PICTURE) {

				if (data.getData() != null) {

					this.photoList.add(data.getData());

				}
				else if (data.getClipData() != null) {

					ClipData clipData = data.getClipData();

					for (int i = 0; i < clipData.getItemCount(); i++) {

						ClipData.Item item = clipData.getItemAt(i);
						this.photoList.add(item.getUri());

					}

				}
			}
		}

//		for (Uri anUriList : this.photoList) {
//			try {
//				Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), anUriList);
//				System.out.println(Base64Helper.bitmapToBase64(bitmap));
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}

		super.onActivityResult(requestCode, resultCode, data);

	}

	private void viewTrip() {
		Intent intent = new Intent(this, RecordListActivity.class);
		intent.putExtra("idTrip", this.trip.getId());
		startActivity(intent);
		finish();
	}

	public void selectPhotos(View view) {

		Log.w("Photo", "OnClick");

		Intent intent = new Intent();
		intent.setType("image/*");
		intent.setAction(Intent.ACTION_GET_CONTENT);
		intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);

		startActivityForResult(Intent.createChooser(intent, "Select Photos"), SELECT_MULTIPLE_PICTURE);

	}
}
