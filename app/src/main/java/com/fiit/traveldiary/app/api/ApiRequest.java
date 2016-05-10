package com.fiit.traveldiary.app.api;

import android.content.Context;
import android.content.ContextWrapper;
import com.fiit.traveldiary.app.api.provider.ApiProvider;
import com.fiit.traveldiary.app.api.provider.RestProvider;
import org.json.JSONObject;

import java.util.Map;

/**
 * Created by jdubec on 13/04/16.
 */
public class ApiRequest extends ContextWrapper{

	private ApiMethod method;
	private JSONObject content;
	private String[] urlParams;
	private RequestType requestType;
	private Class provider;
	private Map<String, String> headers;


	/**
	 * RestProvider is defailt communucation provider
	 * @param base Context
	 */
	public ApiRequest(Context base) {
		super(base);
		this.provider = RestProvider.class;
	}

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

	public Class getProvider() {
		return this.provider;
	}

	public void setProvider(Class provider) {
		this.provider = provider;
	}

	public Map<String, String> getHeaders() {
		return headers;
	}

	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}
}
