package com.fiit.traveldiary.app.api;

/**
 * Created by Jakub Dubec on 13/04/16.
 */
public enum ApiMethod {

	POST_METHOD("POST"),
	GET_METHOD("GET"),
	PUT_METHOD("PUT"),
	DELETE_METHOD("DELETE"),
	PATCH_METHOD("PATCH");

	private final String name;

	ApiMethod(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return this.name;
	}

	public boolean hasBody() {
		switch (this) {
			case POST_METHOD:
			case PUT_METHOD:
			case PATCH_METHOD:
				return true;
			case GET_METHOD:
			case DELETE_METHOD:
			default:
				return false;
		}
	}
}
