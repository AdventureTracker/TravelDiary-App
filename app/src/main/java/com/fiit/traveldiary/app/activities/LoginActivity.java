package com.fiit.traveldiary.app.activities;

/**
 * Created by Barbora on 14.4.2016.
 */

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.EditText;
import com.fiit.traveldiary.app.R;

public class LoginActivity extends Activity implements View.OnClickListener {


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

	}

}
