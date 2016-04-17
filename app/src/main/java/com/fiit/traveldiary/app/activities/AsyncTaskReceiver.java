package com.fiit.traveldiary.app.activities;

import com.fiit.traveldiary.app.api.ApiResponse;

import java.util.List;

/**
 * Created by Jakub Dubec on 17/04/16.
 */
public interface AsyncTaskReceiver {

	void processFinish(List<ApiResponse> apiResponses);

}
