package com.fiit.traveldiary.app.db;

import android.util.Log;
import com.fiit.traveldiary.app.db.helpers.*;
import com.fiit.traveldiary.app.exceptions.InvalidInputException;
import com.fiit.traveldiary.app.exceptions.RecordNotFoundException;
import com.fiit.traveldiary.app.models.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Jakub Dubec on 17/04/16.
 */
public abstract class DataPersister {

	public static boolean persistEnums(JSONObject jsonObject) {

		try {
			// Status enum
			JSONArray statusArray = jsonObject.getJSONArray("statuses");
			StatusHelper.removeAll();
			for (int i = 0; i < statusArray.length(); i++) {
				StatusHelper.save(new Status(statusArray.getJSONObject(i)));
			}

			// RecordType enum
			JSONArray recordTypeArray = jsonObject.getJSONArray("record_types");
			RecordTypeHelper.removeAll();
			for (int i = 0; i < recordTypeArray.length(); i++) {
				RecordTypeHelper.save(new RecordType(recordTypeArray.getJSONObject(i)));
			}

			// PrivacyEnum
			JSONArray privacyArray = jsonObject.getJSONArray("privacy_levels");
			PrivacyHelper.removeAll();
			for (int i = 0; i < privacyArray.length(); i++) {
				PrivacyHelper.save(new Privacy(privacyArray.getJSONObject(i)));
			}

			// UsersEnum
			JSONArray userArray = jsonObject.getJSONArray("users");
			UserHelper.removeAll();
			for (int i = 0; i < userArray.length(); i++) {
				UserHelper.save(new User(userArray.getJSONObject(i)));
			}
		} catch (JSONException e) {
			e.printStackTrace();
			return false;
		} catch (InvalidInputException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	public static boolean persistTrips(JSONObject jsonObject) {

		try {
			JSONArray trips = jsonObject.getJSONArray("records");

			for (int i = 0; i < trips.length(); i++) {
				try {
					Trip trip;
					try {
						trip = TripHelper.getOne(String.format("WHERE %s = '%s'", TravelDiaryContract.TripEntry.COLUMN_UUID, trips.getJSONObject(i).getString("id")));
					}
					catch (RecordNotFoundException e) {
						trip = new Trip();
					}
					trip.parseJSON(trips.getJSONObject(i));
					trip.setSyncStatus(SyncStatus.SYNCED);
					TripHelper.persist(trip);
				} catch (InvalidInputException e) {
					Log.w("DataPersister", e.getMessage());
				}
			}

		}
		catch (JSONException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	public static boolean persistTrip(JSONObject jsonObject) {
		try {
			Trip trip = new Trip(jsonObject);
			trip.setSyncStatus(SyncStatus.SYNCED);
			TripHelper.persist(trip);
		} catch (InvalidInputException e) {
			return false;
		} catch (JSONException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static boolean persistRecord(JSONObject jsonObject) {
		try {
			Record record = new Record(jsonObject);
			record.setSyncStatus(SyncStatus.SYNCED);
			RecordHelper.persist(record);
		}
		catch (JSONException e) {
			e.printStackTrace();
			return false;
		} catch (InvalidInputException e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}

}
