package com.fiit.traveldiary.app.api;

import android.content.Context;
import android.content.ContextWrapper;
import org.json.JSONObject;

/**
 * Created by jdubec on 13/04/16.
 */
public class ApiRequest extends ContextWrapper{

	private ApiMethod method;
	private JSONObject content;
	private String[] urlParams;
	private RequestType requestType;


	public ApiRequest(Context context, RequestType requestType, String[] params, JSONObject content)  {
		super(context);
		this.method = requestType.getApiMethod();
		this.requestType = requestType;
		this.urlParams = params;
		this.content = content;
	}

	public ApiRequest(Context context, RequestType requestType, String[] params)  {
		super(context);
		this.method = requestType.getApiMethod();
		this.requestType = requestType;
		this.urlParams = params;
	}

	public ApiMethod getMethod() {
		return method;
	}

	public String getUri() {
		return this.urlParams.length != 0 ? String.format(this.requestType.getUrl(), (Object[]) urlParams) : this.requestType.getUrl();
	}

	public RequestType getRequestType() {
		return requestType;
	}

	public JSONObject getContent() {
		return content;
	}
}
