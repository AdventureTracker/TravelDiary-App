package com.fiit.traveldiary.app.api;

import org.json.JSONObject;

/**
 * Created by jdubec on 13/04/16.
 */
public class ApiResponse {

	private int status;
	private JSONObject content;
	private ApiRequest originalRequest;

	public ApiResponse(int status, JSONObject content, ApiRequest originalRequest) {
		this.status = status;
		this.content = content;
		this.originalRequest = originalRequest;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public JSONObject getContent() {
		return content;
	}

	public void setContent(JSONObject content) {
		this.content = content;
	}

	public ApiRequest getOriginalRequest() {
		return originalRequest;
	}
}
