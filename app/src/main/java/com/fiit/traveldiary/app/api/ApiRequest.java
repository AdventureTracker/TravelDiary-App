package com.fiit.traveldiary.app.api;

import android.content.Context;
import android.content.ContextWrapper;
import android.util.Log;
import com.fiit.traveldiary.app.api.provider.ApiProvider;
import com.fiit.traveldiary.app.api.provider.RestProvider;
import com.fiit.traveldiary.app.api.provider.WebsocketProvider;
import org.json.JSONObject;

import java.util.Collection;
import java.util.Collections;
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
		this.provider = WebsocketProvider.class;
		this.headers = Collections.emptyMap();
	}

	public ApiRequest(Context context, RequestType requestType, String[] params, JSONObject content)  {
		super(context);
		this.method = requestType.getApiMethod();
		this.requestType = requestType;
		this.urlParams = params;
		this.content = content;
		this.provider = WebsocketProvider.class;
		this.headers = Collections.emptyMap();
	}

	public ApiRequest(Context context, RequestType requestType, String[] params)  {
		super(context);
		this.method = requestType.getApiMethod();
		this.requestType = requestType;
		this.urlParams = params;
		this.provider = WebsocketProvider.class;
		this.headers = Collections.emptyMap();
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

	public ApiRequest setProvider(Class provider) {
		this.provider = provider;
		return this;
	}

	public Map<String, String> getHeaders() {
		return headers;
	}
}
