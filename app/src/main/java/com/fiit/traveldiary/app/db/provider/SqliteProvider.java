package com.fiit.traveldiary.app.db.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.ParcelUuid;
import com.fiit.traveldiary.app.db.TravelDiaryContract;
import com.fiit.traveldiary.app.models.Trip;

/**
 * Created by Jakub Dubec on 14/04/16.
 */
public class SQLiteProvider extends SQLiteOpenHelper {

	private static SQLiteProvider instance;

	public static final String LOG = "SQLiteProvider";

	public static final int DATABASE_VERSION = 1;
	public static final String DATABASE_NAME = "TravelDiaryDatabase.db";

	public static SQLiteProvider getInstance(Context context) {
		if (instance == null)
			instance = new SQLiteProvider(context);
		return instance;
	}

	public static SQLiteProvider getInstance() {
		return instance;
	}

	private SQLiteProvider(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(TravelDiaryContract.StatusEntry.CREATE_SQL);
		db.execSQL(TravelDiaryContract.PrivacyEntry.CREATE_SQL);
		db.execSQL(TravelDiaryContract.RecordTypeEntry.CREATE_SQL);
		db.execSQL(TravelDiaryContract.UserEntry.CREATE_SQL);
		db.execSQL(TravelDiaryContract.TripEntry.CREATE_SQL);
		db.execSQL(TravelDiaryContract.RecordEntry.CREATE_SQL);
		db.execSQL(TravelDiaryContract.UserHaveTripEntry.CREATE_SQL);
		db.execSQL(TravelDiaryContract.PhotoEntry.CREATE_SQL);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(TravelDiaryContract.StatusEntry.REMOVE_SQL);
		db.execSQL(TravelDiaryContract.PrivacyEntry.REMOVE_SQL);
		db.execSQL(TravelDiaryContract.RecordTypeEntry.REMOVE_SQL);
		db.execSQL(TravelDiaryContract.UserEntry.REMOVE_SQL);
		db.execSQL(TravelDiaryContract.TripEntry.REMOVE_SQL);
		db.execSQL(TravelDiaryContract.RecordEntry.REMOVE_SQL);
		db.execSQL(TravelDiaryContract.UserHaveTripEntry.REMOVE_SQL);
		db.execSQL(TravelDiaryContract.PhotoEntry.REMOVE_SQL);
		onCreate(db);
	}

	@Override
	public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		onUpgrade(db, oldVersion, newVersion);
	}
}
