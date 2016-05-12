package com.fiit.traveldiary.app.api.connection;

import android.util.Log;
import com.fiit.traveldiary.app.api.RequestType;
import com.fiit.traveldiary.app.db.DataPersister;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URISyntaxException;

/**
 * Created by Jakub Dubec on 11/05/16.
 */
public class WebsocketConnectionManager {

	private static final String WEBSOCKET_SERVER = "https://traveldiary-jakubdubec.rhcloud.com";

	private static WebsocketConnectionManager instance = null;

	private Socket socket;

	private WebsocketConnectionManager() throws URISyntaxException {
		IO.Options opts = new IO.Options();

		opts.reconnection = true;
		opts.forceNew = false;
		opts.secure = true;
		opts.timeout = 5000;

		this.socket = IO.socket(String.format("%s", WEBSOCKET_SERVER), opts);

		this.socket.on("welcome", new Emitter.Listener() {
			@Override
			public void call(Object... args) {
				JSONObject jsonObject = (JSONObject) args[0];
				Log.w("Socket.io", jsonObject.toString());
			}
		});

		this.socket.on("rest", new Emitter.Listener() {
			@Override
			public void call(Object... args) {

				JSONObject jsonObject = (JSONObject) args[0];
				JSONObject responseObject;
				Log.w("Socket.io", String.format("Received: %s", jsonObject.toString()));


				try {
					RequestType originalRequestType = RequestType.valueOf(jsonObject.getString("requestType"));

					if (jsonObject.getInt("status") >= 200 && jsonObject.getInt("status") < 400) {

						if (originalRequestType.isArrayExpected()) {
							responseObject = new JSONObject();
							responseObject.put("records", jsonObject.getJSONArray("content"));
						}
						else {
							responseObject = jsonObject.getJSONObject("content");
						}

						if (originalRequestType.isPersistRequest()) {
							if (originalRequestType.equals(RequestType.ENUMS))
								DataPersister.persistEnums(responseObject);
							else if (originalRequestType.equals(RequestType.TRIP_LIST))
								DataPersister.persistTrips(responseObject);
							else if (originalRequestType.equals(RequestType.TRIP))
								DataPersister.persistTrip(responseObject);
							else if (originalRequestType.equals(RequestType.TRIP_RECORD))
								DataPersister.persistRecord(responseObject);
						}

					}

				} catch (JSONException e) {
					e.printStackTrace();
				}

			}
		});

		this.socket.on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {
			@Override
			public void call(Object... args) {
				Log.w("Socket.io", "Going down with the sickness bitch!");
			}
		});

		this.socket.connect();
	}

	public static WebsocketConnectionManager getInstance() throws URISyntaxException {
		if (instance == null)
			instance = new WebsocketConnectionManager();
		return instance;
	}

	public Socket getSocket() {
		return socket;
	}
}
