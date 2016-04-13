package com.fiit.traveldiary.app.api;

/**
 * Created by jdubec on 13/04/16.
 */
public class ApiResponse {

	private int status;
	private String content;

	public ApiResponse(int status, String content) {
		this.status = status;
		this.content = content;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
