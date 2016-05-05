package com.fiit.traveldiary.app.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by jdubec on 13/04/16.
 */
public class Location {

	private double latitude;
	private double longitude;
	private int altitude;

	public Location() {
	}

	public Location(double latitude, double longitude, int altitude) {
		this.latitude = latitude;
		this.longitude = longitude;
		this.altitude = altitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public int getAltitude() {
		return altitude;
	}

	public void setAltitude(int altitude) {
		this.altitude = altitude;
	}

	public String toString() {
		return String.format("Latitude: %f | Longitude: %f | Altitude: %d", this.getLatitude(), this.getLongitude(), this.getAltitude());
	}

	public JSONObject toJSON() throws JSONException {
		JSONObject jsonObject = new JSONObject();

		jsonObject.put("latitude", this.getLatitude());
		jsonObject.put("longitude", this.getLongitude());
		jsonObject.put("altitude", this.getAltitude());

		return jsonObject;
	}

	public boolean parseJSON(JSONObject jsonObject) {

		try {
			this.setLatitude(jsonObject.getDouble("latitude"));
			this.setLongitude(jsonObject.getDouble("longitude"));
			this.setAltitude(jsonObject.getInt("altitude"));
		} catch (JSONException e) {
			return false;
		}

		return true;
	}

}
