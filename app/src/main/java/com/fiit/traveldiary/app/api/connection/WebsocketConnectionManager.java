package com.fiit.traveldiary.app.api.connection;

import android.util.Log;
import com.fiit.traveldiary.app.api.ApiRequest;
import com.fiit.traveldiary.app.api.RequestType;
import com.fiit.traveldiary.app.db.DataPersister;
import io.socket.client.IO;
import io.socket.client.Manager;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import io.socket.engineio.client.Transport;
import org.json.JSONException;
import org.json.JSONObject;
import java.net.URISyntaxException;
import java.util.*;

/**
 * Created by Jakub Dubec on 11/05/16.
 */
public class WebsocketConnectionManager {

	private static final String WEBSOCKET_SERVER = "https://traveldiary-jakubdubec.rhcloud.com";

	private static WebsocketConnectionManager instance = null;

	private Socket socket;
	private Stack<JSONObject> requestStack;

	private WebsocketConnectionManager() throws URISyntaxException {
		requestStack = new Stack<JSONObject>();

		IO.Options opts = new IO.Options();

		opts.forceNew = true;
		opts.secure = true;
		opts.reconnection = true;
//		opts.transports = new String[]{"websocket"};
//		opts.timeout = 5000;

		Log.w("Socket.io", "Websocket Connection Manager Constructor");

		this.socket = IO.socket(String.format("%s", WEBSOCKET_SERVER), opts);

		this.socket.on("welcome", new Emitter.Listener() {
			@Override
			public void call(Object... args) {
				JSONObject jsonObject = (JSONObject) args[0];
				Log.w("Socket.io", jsonObject.toString());
			}
		});

		this.socket.on("pong", new Emitter.Listener() {
			@Override
			public void call(Object... args) {
				Log.w("Socket.io", "Pong");
			}
		});

		this.socket.on("receive", new Emitter.Listener() {
			@Override
			public void call(Object... args) {

				Log.w("Socket.io", "Receiving REST");

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

		this.socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
			@Override
			public void call(Object... args) {
				JSONObject jsonObject = new JSONObject();
				try {
					jsonObject.put("message", "Ping");
				} catch (JSONException e) {
					e.printStackTrace();
				}
				socket.emit("ping", jsonObject);

				for (JSONObject aRequestStack : requestStack) {
					socket.emit("rest", aRequestStack);
				}
				requestStack.clear();



			}
		});

		this.socket.on(Socket.EVENT_RECONNECT, new Emitter.Listener() {
			@Override
			public void call(Object... args) {
				JSONObject jsonObject = new JSONObject();
				try {
					jsonObject.put("message", "Ping");
				} catch (JSONException e) {
					e.printStackTrace();
				}
				socket.emit("ping", jsonObject);
			}
		});

		this.socket.on(Socket.EVENT_ERROR, new Emitter.Listener() {
			@Override
			public void call(Object... args) {
				Log.w("Socket.io", "Error");
				Log.e("Socket.io", args[0].toString());
			}
		});

		this.socket.connect();
	}

	public static WebsocketConnectionManager getInstance() throws URISyntaxException {
		if (instance == null)
			instance = new WebsocketConnectionManager();
		return instance;
	}

	public Stack<JSONObject> getRequestStack() {
		return requestStack;
	}

	public void reconnect() {
		if (this.socket.connected())
			this.socket.disconnect();
		this.socket.connect();
	}

	public Socket getSocket() {
		return socket;
	}
}
