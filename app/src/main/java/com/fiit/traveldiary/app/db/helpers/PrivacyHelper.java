package com.fiit.traveldiary.app.db.helpers;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.fiit.traveldiary.app.db.TravelDiaryContract;
import com.fiit.traveldiary.app.db.provider.SQLiteProvider;
import com.fiit.traveldiary.app.exceptions.RecordNotFoundException;
import com.fiit.traveldiary.app.models.Privacy;

/**
 * Created by Jakub Dubec on 17/04/16.
 */
public abstract class PrivacyHelper {

	public static long save(Privacy privacy) {

		SQLiteDatabase db = SQLiteProvider.getInstance().getWritableDatabase();

		ContentValues contentValues = new ContentValues();

		contentValues.put(TravelDiaryContract.PrivacyEntry.COLUMN_CODE, privacy.getCode());
		contentValues.put(TravelDiaryContract.PrivacyEntry.COLUMN_DESCRIPTION, privacy.getDescription());

		privacy.setId(db.insert(TravelDiaryContract.PrivacyEntry.TABLE_NAME, null, contentValues)); //insert a rovno nasetuj ID

		return privacy.getId();

	}

	public static Privacy get(String code) throws RecordNotFoundException {

		SQLiteDatabase db = SQLiteProvider.getInstance().getReadableDatabase();

		String sql = String.format("SELECT * FROM %s WHERE %s = '%s' LIMIT 1;", TravelDiaryContract.PrivacyEntry.TABLE_NAME, TravelDiaryContract.PrivacyEntry.COLUMN_CODE, code);

		Cursor c = db.rawQuery(sql, null);

		if (!c.moveToFirst())
			throw new RecordNotFoundException();

		Privacy privacy = new Privacy(
				c.getLong(c.getColumnIndex(TravelDiaryContract.PrivacyEntry.COLUMN_ID_PRIVACY)),
				c.getString(c.getColumnIndex(TravelDiaryContract.PrivacyEntry.COLUMN_CODE)),
				c.getString(c.getColumnIndex(TravelDiaryContract.PrivacyEntry.COLUMN_DESCRIPTION))
		);

		c.close();

		return privacy;
	}

	public static Privacy get(long id) throws RecordNotFoundException {

		SQLiteDatabase db = SQLiteProvider.getInstance().getReadableDatabase();

		String sql = String.format("SELECT * FROM %s WHERE %s = %d LIMIT 1;", TravelDiaryContract.PrivacyEntry.TABLE_NAME, TravelDiaryContract.PrivacyEntry.COLUMN_ID_PRIVACY, id);

		Cursor c = db.rawQuery(sql, null);

		if (!c.moveToFirst())
			throw new RecordNotFoundException();

		Privacy privacy = new Privacy(
				c.getLong(c.getColumnIndex(TravelDiaryContract.PrivacyEntry.COLUMN_ID_PRIVACY)),
				c.getString(c.getColumnIndex(TravelDiaryContract.PrivacyEntry.COLUMN_CODE)),
				c.getString(c.getColumnIndex(TravelDiaryContract.PrivacyEntry.COLUMN_DESCRIPTION))
		);

		c.close();

		return privacy;
	}

	public static boolean removeAll() {
		SQLiteDatabase db = SQLiteProvider.getInstance().getWritableDatabase();
		return db.delete(TravelDiaryContract.PrivacyEntry.TABLE_NAME, null, null) != 0;
	}

}
