package com.fiit.traveldiary.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import com.fiit.traveldiary.app.R;
import com.fiit.traveldiary.app.api.ApiRequest;
import com.fiit.traveldiary.app.api.ApiResponse;
import com.fiit.traveldiary.app.api.NetworkSyncOperations;
import com.fiit.traveldiary.app.api.RequestType;
import com.fiit.traveldiary.app.api.connection.WebsocketConnectionManager;
import com.fiit.traveldiary.app.api.provider.WebsocketProvider;
import com.fiit.traveldiary.app.db.provider.SQLiteProvider;
import com.fiit.traveldiary.app.helpers.NetworkActivityManager;
import com.securepreferences.SecurePreferences;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.List;

/**
 * Tato aktivita bude sluzit na stiahnute ENUMs a sync, overenie prihlasenia a podobne
 */
public class MainActivity extends AppCompatActivity implements AsyncTaskReceiver{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);

		JSONObject test = new JSONObject();

		NetworkSyncOperations networkSyncOperations = new NetworkSyncOperations();
		networkSyncOperations.execute(
				(new ApiRequest(this.getBaseContext(), RequestType.ENUMS, new String[]{})).setProvider(WebsocketProvider.class)
		);

//		SQLiteProvider.getInstance(this.getBaseContext()).getReadableDatabase();
//
//		SecurePreferences preferences = new SecurePreferences(this.getBaseContext());
//
//		if (preferences.getString("USER_TOKEN", "NOT_LOGGED_IN").equals("NOT_LOGGED_IN")) {
//			Intent intent = new Intent(this, LoginActivity.class);
//			startActivity(intent);
//		}
//		else {
//
//			if (NetworkActivityManager.hasActiveInternetConnection(this.getBaseContext())) {
//				NetworkSyncOperations networkSyncOperations = new NetworkSyncOperations();
//				networkSyncOperations.setDelegate(this);
//				networkSyncOperations.execute(
//						new ApiRequest(this.getBaseContext(), RequestType.ENUMS, new String[]{})
//				);
//				// Tu sprav sync
//			}
//			else {
//				this.startTripListActivity();
//			}
//
//		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		int id = item.getItemId();

		if (id == R.id.action_settings) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}


	@Override
	public void processFinish(List<ApiResponse> apiResponses) {
		this.startTripListActivity();
	}

	private void startTripListActivity() {
		Intent intent = new Intent(this, TripListActivity.class);
		startActivity(intent);
		finish();
	}
}
