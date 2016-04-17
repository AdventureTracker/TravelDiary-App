package com.fiit.traveldiary.app.models;

/**
 * Created by jdubec on 13/04/16.
 */
public class Location {

	private float latitude;
	private float longitude;
	private int altitude;

	public Location() {
	}

	public Location(float latitude, float longitude, int altitude) {
		this.latitude = latitude;
		this.longitude = longitude;
		this.altitude = altitude;
	}

	public float getLatitude() {
		return latitude;
	}

	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}

	public float getLongitude() {
		return longitude;
	}

	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}

	public int getAltitude() {
		return altitude;
	}

	public void setAltitude(int altitude) {
		this.altitude = altitude;
	}

	public String toString() {
		return null;
	}

	public String toJSON() {
		return null;
	}

}
