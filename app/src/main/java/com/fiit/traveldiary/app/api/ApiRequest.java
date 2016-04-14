package com.fiit.traveldiary.app.api;

import android.content.Context;
import android.content.ContextWrapper;
import org.json.JSONObject;

/**
 * Created by jdubec on 13/04/16.
 */
public class ApiRequest extends ContextWrapper{

	private ApiMethod method;
	private String uri;
	private JSONObject content;

	public ApiRequest(Context context, ApiMethod method, String uri, JSONObject content) {
		super(context);
		this.method = method;
		this.uri = uri;
		this.content = content;
	}

	public ApiRequest(Context context, ApiMethod method, String uri) {
		super(context);
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
