package com.fiit.traveldiary.app.activities;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import com.fiit.traveldiary.app.R;
import com.fiit.traveldiary.app.api.ApiCall;
import com.fiit.traveldiary.app.api.ApiMethod;
import com.fiit.traveldiary.app.api.ApiRequest;
import com.fiit.traveldiary.app.api.ApiResponse;
import com.fiit.traveldiary.app.api.provider.ApiProvider;
import com.fiit.traveldiary.app.api.provider.RestProvider;
import com.fiit.traveldiary.app.exceptions.InternalException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		NetworkOperations networkOperations = new NetworkOperations();
		networkOperations.execute();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.action_settings) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	public class NetworkOperations extends AsyncTask<Void, Integer, ApiResponse> {

		@Override
		protected ApiResponse doInBackground(Void... params) {

			ApiCall call;

			try {
				call = new ApiCall(new ApiRequest(ApiMethod.GET_METHOD, "status"), RestProvider.class);
			} catch (InternalException e) {
				return null;
			}

			return call.execute();

		}

		protected void onPostExecute(ApiResponse response) {

			if (response != null)
				Log.w("API Response", response.getContent().toString());
			else
				Log.w("API Response", "Invalid API Call");
		}
	}

}
