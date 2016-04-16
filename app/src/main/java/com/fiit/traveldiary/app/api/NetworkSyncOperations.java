package com.fiit.traveldiary.app.api;

import android.os.AsyncTask;
import android.util.Log;
import com.fiit.traveldiary.app.api.provider.RestProvider;
import com.fiit.traveldiary.app.exceptions.InternalException;

import java.util.ArrayList;
import java.util.List;

public class NetworkSyncOperations extends AsyncTask<ApiRequest, Integer, List<ApiResponse>> {

	@Override
	protected List<ApiResponse> doInBackground(ApiRequest... params) {

		int requestsCount = params.length;
		List<ApiResponse> responses = new ArrayList<ApiResponse>();

		for (int i = 0; i < requestsCount; i++) {
			ApiCall call;
			publishProgress((int) ((i / (float) requestsCount) * 100));
			try {
				call = new ApiCall(params[i], RestProvider.class);
			} catch (InternalException e) {
				return null;
			}

			responses.add(call.execute());

		}

		return responses;
	}

	protected void onPostExecute(List<ApiResponse> responses) {
		for (ApiResponse response : responses) {
			if (response != null)
				Log.w("API Response", response.getContent().toString());
			else
				Log.w("API Response", "Invalid API Call");

			if (response.getOriginalRequest().getMethod() == ApiMethod.GET_METHOD) {
				// TODO: create classes for DB persist
			}

		}
	}
}