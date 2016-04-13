package com.fiit.traveldiary.app;

import android.app.Application;
import com.securepreferences.SecurePreferences;

/**
 * Created by Jakub Dubec on 13/04/16.
 */
public class App extends Application {

	private static App instance;
	private SecurePreferences preferences;

	private App() {
		super();
		instance = this;
		this.preferences = new SecurePreferences(this);
	}

	public static App getInstance() {
		return instance;
	}

	public SecurePreferences getPreferences() {
		return preferences;
	}
}
