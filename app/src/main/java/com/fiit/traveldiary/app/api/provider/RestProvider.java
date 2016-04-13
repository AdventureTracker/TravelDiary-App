package com.fiit.traveldiary.app.api.provider;

import com.fiit.traveldiary.app.App;
import com.fiit.traveldiary.app.api.ApiRequest;
import com.fiit.traveldiary.app.api.ApiResponse;
import com.fiit.traveldiary.app.exceptions.InternalException;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by jdubec on 13/04/16.
 */
public class RestProvider implements ApiProvider {

	private HttpURLConnection connection;

	// TODO: move config values to configuration object or manifest
	private static final String API_LOCATION = "https://api.jakubove.zbytocnosti.sk";
	private static final int API_VERSION = 1;

	private static final String DEVICE_HEADER = "X-TravelDiary-Device";
	private static final String TOKEN_HEADER = "X-TravelDiary-Token";

	@Override
	public ApiResponse execute(ApiRequest request) throws InternalException {

		URL url;

		try {

			// Creating URL object based on ApiRequest
			url = new URL(String.format("%s/v%d/%s", API_LOCATION, API_VERSION, request.getUri()));

			// Opening HttpURLConnection
			this.connection = (HttpURLConnection) url.openConnection();

			// Setting method
			connection.setRequestMethod(request.getMethod().toString());

			// Preparing headers
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setRequestProperty(DEVICE_HEADER, App.getInstance().getPreferences().getString("DEVICE_UUID", ""));
			connection.setRequestProperty(TOKEN_HEADER, App.getInstance().getPreferences().getString("USER_TOKEN", ""));
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setUseCaches(false);


			// If request method has body
			if (request.getMethod().hasBody()) {

				OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
				writer.write(request.getContent().toString());
				writer.flush();

			}

		} catch (MalformedURLException e) {
			throw new InternalException("Invalid request!", e);
		} catch (IOException e) {
			throw new InternalException("Unable to create REST request", e);
		}

		BufferedReader reader;
		JSONObject responseObject;

		try {
			reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			responseObject = new JSONObject(reader.toString());
		} catch (IOException e) {
			throw new InternalException("Some kind of shitty IO exception", e);
		} catch (JSONException e) {
			throw new InternalException("Invalid JSON string!", e);
		}

		try {
			connection.disconnect();
			return new ApiResponse(connection.getResponseCode(), responseObject);
		} catch (IOException e) {
			throw new InternalException("I have no fucking idea :(", e);
		}
	}

}
