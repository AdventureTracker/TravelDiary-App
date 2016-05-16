package com.fiit.traveldiary.app.activities;

/**
 * Created by Barbora on 14.4.2016.
 */

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import com.fiit.traveldiary.app.R;
import com.fiit.traveldiary.app.api.*;
import com.fiit.traveldiary.app.api.provider.RestProvider;
import com.securepreferences.SecurePreferences;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class LoginActivity extends Activity implements View.OnClickListener, AsyncTaskReceiver {


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		findViewById(R.id.logInButton).setOnClickListener(this);
	}

	private EditText getUsername(){
		return (EditText) findViewById(R.id.username);
	}

	private EditText getPassword(){
		return (EditText) findViewById(R.id.password);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.logInButton:
				onLoginButtonClick(view);
				break;
		}
	}

	private void onLoginButtonClick(View view) {

		JSONObject jsonObject = new JSONObject();

		try {
			jsonObject.put("email", this.getUsername().getText().toString());
			jsonObject.put("password", this.getPassword().getText().toString());
		} catch (JSONException e) {
			e.printStackTrace();
			Log.w("LoginActivity", "Auth JSON error");
			return;
		}

		Log.w("LoginActivity", jsonObject.toString());

		NetworkSyncOperations networkSyncOperations = new NetworkSyncOperations();
		networkSyncOperations.setDelegate(this);
		networkSyncOperations.execute((new ApiRequest(this.getBaseContext(), RequestType.LOGIN, new String[]{}, jsonObject)).setProvider(RestProvider.class));

	}

	@Override
	public void processFinish(List<ApiResponse> apiResponses) {

		ApiResponse response = apiResponses.get(0);

		if (response.getOriginalRequest().getProvider() != RestProvider.class)
			return;

		Log.w("LoginActivity", response.getContent().toString());

		if (response.getStatus() == 201) {
			SecurePreferences preferences = new SecurePreferences(this.getBaseContext());
			try {
				SecurePreferences.Editor editor = preferences.edit();
				editor.putString("USER_TOKEN", response.getContent().getString("token"));
				editor.commit();
			} catch (JSONException e) {
				e.printStackTrace();
				Log.w("LoginActivity", "Invalid response!");
				return;
			}

			this.openMainActivity();

		}
	}

	private void openMainActivity() {
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
		finish();
	}
}
