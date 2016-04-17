package com.fiit.traveldiary.app.db;

import org.json.JSONObject;

/**
 * Created by Jakub Dubec on 17/04/16.
 */
public abstract class DataPersister {

	public static boolean persistEnums(JSONObject jsonObject) {
		return false;
	}

	public static boolean persistTrips(JSONObject jsonObject) {
		return false;
	}

	public static boolean persistTrip(JSONObject jsonObject) {
		return false;
	}

	public static boolean persistRecord(JSONObject jsonObject) {
		return false;
	}

}
