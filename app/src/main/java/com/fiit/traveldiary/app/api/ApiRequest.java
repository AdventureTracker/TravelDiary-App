package com.fiit.traveldiary.app.api;

/**
 * Created by jdubec on 13/04/16.
 */
public class ApiRequest {

	enum ApiRequestTypeEnum {
		POST_REQUEST, GET_REQUEST, PUT_REQUEST, DELETE_REQUEST, PATCH_REQUEST
	}

	private ApiRequestTypeEnum request;
	private String uri;
	private String content;

	public ApiRequest(ApiRequestTypeEnum request, String uri, String content) {
		this.request = request;
		this.uri = uri;
		this.content = content;
	}

	public ApiRequest(ApiRequestTypeEnum request, String uri) {
		this.request = request;
		this.uri = uri;
	}

	public ApiRequestTypeEnum getRequest() {
		return request;
	}

	public String getUri() {
		return uri;
	}

	public String getContent() {
		return content;
	}
}
