package com.fiit.traveldiary.app.api;

import android.os.AsyncTask;
import android.util.Log;
import com.fiit.traveldiary.app.activities.AsyncTaskReceiver;
import com.fiit.traveldiary.app.api.provider.RestProvider;
import com.fiit.traveldiary.app.db.DataPersister;
import com.fiit.traveldiary.app.exceptions.InternalException;

import java.util.ArrayList;
import java.util.List;

public class NetworkSyncOperations extends AsyncTask<ApiRequest, Integer, List<ApiResponse>> {

	private AsyncTaskReceiver delegate = null;

	public void setDelegate(AsyncTaskReceiver delegate) {
		this.delegate = delegate;
	}

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

		if (this.delegate != null)
			this.delegate.processFinish(responses);

		for (ApiResponse response : responses) {
			if (response == null) {
				Log.w("API Response", "Invalid API Call");
				continue;
			}

			if (response.getOriginalRequest().getRequestType().isPersistRequest()) {
				if (response.getOriginalRequest().getRequestType().equals(RequestType.ENUMS))
					DataPersister.persistEnums(response.getContent());
				else if (response.getOriginalRequest().getRequestType().equals(RequestType.TRIP_LIST))
					DataPersister.persistTrips(response.getContent());
				else if (response.getOriginalRequest().getRequestType().equals(RequestType.TRIP))
					DataPersister.persistTrip(response.getContent());
				else if (response.getOriginalRequest().getRequestType().equals(RequestType.TRIP_RECORD))
					DataPersister.persistRecord(response.getContent());
			}
		}
	}
}