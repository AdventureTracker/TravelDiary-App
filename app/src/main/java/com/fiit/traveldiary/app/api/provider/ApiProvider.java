package com.fiit.traveldiary.app.api.provider;

import com.fiit.traveldiary.app.api.ApiRequest;
import com.fiit.traveldiary.app.api.ApiResponse;
import com.fiit.traveldiary.app.exceptions.InternalException;

/**
 * Created by jdubec on 13/04/16.
 */
public interface ApiProvider {

	public ApiResponse execute(ApiRequest request) throws InternalException;

}
