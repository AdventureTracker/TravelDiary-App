package com.fiit.traveldiary.app.api;

import android.annotation.TargetApi;
import android.os.Build;
import com.fiit.traveldiary.app.api.provider.ApiProvider;
import com.fiit.traveldiary.app.exceptions.InternalException;

/**
 * Created by jdubec on 13/04/16.
 */
public class ApiCall {

	private ApiRequest request;
	private ApiProvider provider;

	public ApiCall(ApiRequest request, Class<ApiProvider> provider) throws InternalException {
		this.request = request;

		try {
			this.provider = provider.newInstance();
		} catch (InstantiationException e) {
			throw new InternalException("Unable to create ApiProvider instance!", e);
		} catch (IllegalAccessException e) {
			throw new InternalException("Unable to create ApiProvider instance!", e);
		}

	}

	public ApiResponse execute() {
		try {
			return this.provider.execute(request);
		} catch (InternalException e) {
			e.printStackTrace();
		}
		return null;
	}
}
