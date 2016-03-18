package com.fiit.traveldiary.app;

import android.content.Context;
import android.util.Log;
import org.apache.http.HttpRequest;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ApiWrapper {

	private static final String API_CALL = "https://traveldiary.jakubdubec.me/web/api/v1/trips";

	//X-TravelDiary-Device
	private static final String DEVICE_UUID = "669ea7a7-b600-4ed9-b85e-13aabd222775";

	public static JSONObject getJSON() {


		URL url;
		HttpURLConnection connection;
		BufferedReader reader;

		try {
			url = new URL(API_CALL);
		} catch (MalformedURLException e) {
			Log.w("TravelDiaryApp", e.getMessage());
			return null;
		}

		try {
			connection = (HttpURLConnection) url.openConnection();
		} catch (IOException e) {
			Log.w("TravelDiaryApp", e.getMessage());
			return null;
		}

		connection.addRequestProperty("X-TravelDiary-Device", DEVICE_UUID);
		connection.setDoInput(true);

		try {
			connection.getInputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			connection.disconnect();
		}

		try {
			reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		} catch (IOException e) {
			Log.w("TravelDiaryApp", e.getMessage());
			return null;
		}
		finally {
			connection.disconnect();
		}


		StringBuffer json = new StringBuffer(1024);
		String tmp="";

		try {
			while((tmp=reader.readLine())!=null)
				json.append(tmp).append("\n");
		} catch (IOException e) {
			Log.w("TravelDiaryApp", e.getMessage());
			return null;
		}
		finally {
			try {
				reader.close();
			} catch (IOException e) {
				Log.w("TravelDiaryApp", e.getMessage());
			}
		}

		JSONObject data;

		try {
			data = new JSONObject(json.toString());
		} catch (JSONException e) {
			Log.w("TravelDiaryApp", e.getMessage());
			return null;
		}

		return data;
	}

}
