package com.fiit.traveldiary.app.api;

import org.json.JSONObject;

/**
 * Created by jdubec on 13/04/16.
 */
public class ApiRequest {

	private ApiMethod method;
	private String uri;
	private JSONObject content;

	public ApiRequest(ApiMethod method, String uri, JSONObject content) {
		this.method = method;
		this.uri = uri;
		this.content = content;
	}

	public ApiRequest(ApiMethod method, String uri) {
		this.method = method;
		this.uri = uri;
	}

	public ApiMethod getMethod() {
		return method;
	}

	public String getUri() {
		return uri;
	}

	public JSONObject getContent() {
		return content;
	}
}
