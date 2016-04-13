package com.fiit.traveldiary.app.api;

import org.json.JSONObject;

/**
 * Created by jdubec on 13/04/16.
 */
public class ApiResponse {

	private int status;
	private JSONObject content;

	public ApiResponse(int status, JSONObject content) {
		this.status = status;
		this.content = content;
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
}
