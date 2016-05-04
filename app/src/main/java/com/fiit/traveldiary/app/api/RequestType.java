package com.fiit.traveldiary.app.api;

import android.support.annotation.NonNull;

/**
 * Created by Jakub Dubec on 26/04/16.
 */
public enum RequestType {

	LOGIN("LOGIN"),
	TRIP("TRIP"),
	TRIP_LIST("TRIP_LIST"),
	CREATE_TRIP("CREATE_TRIP"),
	UPDATE_TRIP("UPDATE_TRIP"),
	DELETE_TRIP("DELETE_TRIP"),
	TRIP_RECORD("TRIP_RECORD"),
	TRIP_RECORD_LIST("TRIP_RECORD_LIST"),
	CREATE_TRIP_RECORD("CREATE_TRIP_RECORD"),
	UPDATE_TRIP_RECORD("UPDATE_TRIP_RECORD"),
	DELETE_TRIP_RECORD("DELETE_TRIP_RECORD"),
	ENUMS("ENUMS"),
	STATUS("STATUS");

	private final String name;

	RequestType(String name) {
		this.name = name;
	}

	@NonNull
	public String getUrl() {
		switch (this) {
			case LOGIN:
				return "token";
			case TRIP:
			case UPDATE_TRIP:
			case DELETE_TRIP:
				return "trip/%s";
			case TRIP_LIST:
				return "trips";
			case CREATE_TRIP:
				return "trip";
			case TRIP_RECORD:
			case UPDATE_TRIP_RECORD:
			case DELETE_TRIP_RECORD:
				return "trip/%s/record/%s";
			case TRIP_RECORD_LIST:
				return "trip/%s/records";
			case CREATE_TRIP_RECORD:
				return "trip/%s/record";
			case ENUMS:
				return "enums";
			default:
				return "status";
		}
	}

	@NonNull
	public ApiMethod getApiMethod() {
		switch (this) {
			case LOGIN:
			case CREATE_TRIP:
			case CREATE_TRIP_RECORD:
				return ApiMethod.POST_METHOD;
			case TRIP:
			case TRIP_LIST:
			case TRIP_RECORD:
			case TRIP_RECORD_LIST:
			case ENUMS:
			case STATUS:
				return ApiMethod.GET_METHOD;
			case UPDATE_TRIP:
			case UPDATE_TRIP_RECORD:
				return ApiMethod.PUT_METHOD;
			case DELETE_TRIP:
			case DELETE_TRIP_RECORD:
				return ApiMethod.DELETE_METHOD;
			default:
				return ApiMethod.GET_METHOD;
		}
	}

	public boolean isArrayExpected() {
		switch (this) {
			case TRIP_LIST:
			case TRIP_RECORD_LIST:
				return true;
			default:
				return false;
		}
	}

	public boolean isPersistRequest() {

		switch (this) {
			case TRIP:
			case TRIP_LIST:
			case TRIP_RECORD:
			case TRIP_RECORD_LIST:
			case ENUMS:
				return true;
			default:
				return false;
		}

	}
}
