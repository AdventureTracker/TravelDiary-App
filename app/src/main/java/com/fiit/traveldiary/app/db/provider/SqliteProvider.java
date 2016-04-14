package com.fiit.traveldiary.app.db.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Jakub Dubec on 14/04/16.
 */
public class SQLiteProvider extends SQLiteOpenHelper {

	private static SQLiteProvider instance;

	public static final String LOG = "SQLiteProvider";

	public static final int DATABASE_VERSION = 1;
	public static final String DATABASE_NAME = "TravelDiaryDatabase";

	public static SQLiteProvider getInstance(Context context) {
		if (instance == null)
			instance = new SQLiteProvider(context);
		return instance;
	}

	private SQLiteProvider(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}
}
