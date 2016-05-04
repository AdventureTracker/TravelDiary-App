package com.fiit.traveldiary.app.db;

import android.util.Log;
import com.fiit.traveldiary.app.db.helpers.PrivacyHelper;
import com.fiit.traveldiary.app.db.helpers.RecordTypeHelper;
import com.fiit.traveldiary.app.db.helpers.StatusHelper;
import com.fiit.traveldiary.app.db.helpers.TripHelper;
import com.fiit.traveldiary.app.exceptions.InvalidInputException;
import com.fiit.traveldiary.app.models.Privacy;
import com.fiit.traveldiary.app.models.RecordType;
import com.fiit.traveldiary.app.models.Status;
import com.fiit.traveldiary.app.models.Trip;
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
		} catch (JSONException e) {
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
					Trip trip = new Trip(trips.getJSONObject(i));
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
			// TODO: persist records
		} catch (InvalidInputException e) {
			return false;
		} catch (JSONException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static boolean persistRecord(JSONObject jsonObject) {
		return false;
	}

}
