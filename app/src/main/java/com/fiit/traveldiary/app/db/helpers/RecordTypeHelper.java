package com.fiit.traveldiary.app.db.helpers;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.fiit.traveldiary.app.db.TravelDiaryContract;
import com.fiit.traveldiary.app.db.provider.SQLiteProvider;
import com.fiit.traveldiary.app.exceptions.RecordNotFoundException;
import com.fiit.traveldiary.app.models.RecordType;

/**
 * Created by Jakub Dubec on 17/04/16.
 */
public abstract class RecordTypeHelper {

	public static RecordType get(String code) throws RecordNotFoundException {

		SQLiteDatabase db = SQLiteProvider.getInstance().getReadableDatabase();

		String sql = String.format("SELECT * FROM %s WHERE %s = '%s' LIMIT 1;", TravelDiaryContract.RecordTypeEntry.TABLE_NAME, TravelDiaryContract.RecordTypeEntry.COLUMN_CODE, code);
		Log.e(SQLiteProvider.LOG, sql);

		Cursor c = db.rawQuery(sql, null);

		if (!c.moveToFirst())
			throw new RecordNotFoundException();

		RecordType recordType = new RecordType(
				c.getLong(c.getColumnIndex(TravelDiaryContract.RecordTypeEntry.COLUMN_ID_RECORD_TYPE)),
				c.getString(c.getColumnIndex(TravelDiaryContract.RecordTypeEntry.COLUMN_CODE)),
				c.getString(c.getColumnIndex(TravelDiaryContract.RecordTypeEntry.COLUMN_DESCRIPTION))
		);

		c.close();

		return recordType;
	}

	public static RecordType get(long id) throws RecordNotFoundException {

		SQLiteDatabase db = SQLiteProvider.getInstance().getReadableDatabase();

		String sql = String.format("SELECT * FROM %s WHERE %s = %d LIMIT 1;", TravelDiaryContract.RecordTypeEntry.TABLE_NAME, TravelDiaryContract.RecordTypeEntry.COLUMN_ID_RECORD_TYPE, id);
		Log.e(SQLiteProvider.LOG, sql);

		Cursor c = db.rawQuery(sql, null);

		if (!c.moveToFirst())
			throw new RecordNotFoundException();

		RecordType recordType = new RecordType(
				c.getLong(c.getColumnIndex(TravelDiaryContract.RecordTypeEntry.COLUMN_ID_RECORD_TYPE)),
				c.getString(c.getColumnIndex(TravelDiaryContract.RecordTypeEntry.COLUMN_CODE)),
				c.getString(c.getColumnIndex(TravelDiaryContract.RecordTypeEntry.COLUMN_DESCRIPTION))
		);

		c.close();

		return recordType;
	}

}
