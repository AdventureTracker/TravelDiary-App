package com.fiit.traveldiary.app.api.provider;

import android.provider.Settings;
import android.util.Log;
import com.fiit.traveldiary.app.api.ApiRequest;
import com.fiit.traveldiary.app.api.ApiResponse;
import com.fiit.traveldiary.app.exceptions.InternalException;
import com.fiit.traveldiary.app.helpers.NetworkActivityManager;
import com.securepreferences.SecurePreferences;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by jdubec on 13/04/16.
 */
public class RestProvider implements ApiProvider {

	private static final String API_LOCATION = "https://api.jakubove.zbytocnosti.sk";
	private static final int API_VERSION = 1;

	private static final String DEVICE_HEADER = "X-TravelDiary-Device";
	private static final String TOKEN_HEADER = "X-TravelDiary-Token";

	@Override
	public ApiResponse execute(ApiRequest request) throws InternalException {

		URL url;
		HttpsURLConnection connection;

		try {

			// Creating URL object based on ApiRequest
			url = new URL(String.format("%s/v%d/%s", API_LOCATION, API_VERSION, request.getUri()));

			Log.w("ApiCall", String.format("%s/v%d/%s", API_LOCATION, API_VERSION, request.getUri()));

			// Opening HttpURLConnection
			connection = (HttpsURLConnection) url.openConnection();

			// Hostname verifer
			connection.setHostnameVerifier(NetworkActivityManager.createHostnameVerifier());

			// Setting method
			connection.setRequestMethod(request.getMethod().toString());

			// Preparing headers
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setRequestProperty(DEVICE_HEADER, Settings.Secure.getString(request.getContentResolver(), Settings.Secure.ANDROID_ID));

			SecurePreferences preferences = new SecurePreferences(request.getBaseContext());

			if (preferences.getString("USER_TOKEN", null) != null)
				connection.setRequestProperty(TOKEN_HEADER, preferences.getString("USER_TOKEN", ""));

			connection.setDoInput(true);
			connection.setUseCaches(false);


			// If request method has body
			if (request.getMethod().hasBody()) {

				connection.setDoOutput(true);

				Log.w("RequestBody", request.getContent().toString());

				OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
				writer.write(request.getContent().toString());
				writer.flush();

			}

			connection.connect();

		} catch (MalformedURLException e) {
			throw new InternalException("Invalid request!", e);
		} catch (IOException e) {
			throw new InternalException("Unable to create REST request", e);
		}

		BufferedReader reader;
		JSONObject responseObject;

		try {

			if (connection.getResponseCode() >= 200 && connection.getResponseCode() < 400) {
				reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			}
			else {
				reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
			}

			StringBuilder stringBuilder = new StringBuilder();
			String line;

			while ((line = reader.readLine()) != null) {
				stringBuilder.append(line);
			}

			reader.close();

			if (request.getRequestType().isArrayExpected()) {
				responseObject = new JSONObject();
				responseObject.put("records", new JSONArray(stringBuilder.toString()));
			}
			else {
				responseObject = new JSONObject(stringBuilder.toString());
			}

		} catch (IOException e) {
			throw new InternalException("Some kind of shitty IO exception", e);
		} catch (JSONException e) {
			throw new InternalException("Invalid JSON string!", e);
		}

		try {
			connection.disconnect();
			return new ApiResponse(connection.getResponseCode(), responseObject, request);
		} catch (IOException e) {
			throw new InternalException("I have no fucking idea :(", e);
		}
	}

}
