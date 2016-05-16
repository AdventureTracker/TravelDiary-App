package com.fiit.traveldiary.app.api.provider;

import android.util.Log;
import com.fiit.traveldiary.app.api.ApiRequest;
import com.fiit.traveldiary.app.api.ApiResponse;
import com.fiit.traveldiary.app.api.RequestType;
import com.fiit.traveldiary.app.api.connection.WebsocketConnectionManager;
import com.securepreferences.SecurePreferences;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

/**
 * Created by Jakub Dubec on 13/04/16.
 */
public class WebsocketProvider implements ApiProvider {
	@Override
	public ApiResponse execute(ApiRequest request) {

		Log.w("WebSocketProvider", "Web socket provider execute");

		JSONObject data = new JSONObject();

		try {

			data.put("method", request.getMethod().toString());
			data.put("uri", request.getUri());
			data.put("requestType", request.getRequestType().toString());
			data.put("device", "6205fea4-0380-4a19-802b-2217cc2949ff");

			SecurePreferences preferences = new SecurePreferences(request.getBaseContext());
			if (preferences.getString("USER_TOKEN", null) != null)
				data.put("token", preferences.getString("USER_TOKEN", ""));
			else
				data.put("token", "NOT_AUTH");

			if (request.getMethod().hasBody()) {
				data.put("content", request.getContent().toString());
			}

		} catch (JSONException e) {
			e.printStackTrace();
			return new ApiResponse(500, null, request);
		}

		try {
			Log.w("socket.io", "Rest emit: " + data.toString());
			WebsocketConnectionManager.getInstance().getRequestStack().push(data);
			WebsocketConnectionManager.getInstance().reconnect();
//			WebsocketConnectionManager.getInstance().getSocket().emit("rest", data);
		} catch (URISyntaxException e) {
			e.printStackTrace();
			return new ApiResponse(500, null, request);
		}

		return new ApiResponse(200, null, request);
	}
}
