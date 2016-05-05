package com.fiit.traveldiary.app.db.helpers;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.fiit.traveldiary.app.db.TravelDiaryContract;
import com.fiit.traveldiary.app.db.provider.SQLiteProvider;
import com.fiit.traveldiary.app.exceptions.RecordNotFoundException;
import com.fiit.traveldiary.app.models.RecordType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jakub Dubec on 17/04/16.
 */
public abstract class RecordTypeHelper {

	public static long save(RecordType recordType) {

		SQLiteDatabase db = SQLiteProvider.getInstance().getWritableDatabase();

		ContentValues contentValues = new ContentValues();

		contentValues.put(TravelDiaryContract.RecordTypeEntry.COLUMN_CODE, recordType.getCode());
		contentValues.put(TravelDiaryContract.RecordTypeEntry.COLUMN_DESCRIPTION, recordType.getDescription());

		recordType.setId(db.insert(TravelDiaryContract.RecordTypeEntry.TABLE_NAME, null, contentValues)); //insert a rovno nasetuj ID

		return recordType.getId();

	}

	public static List<RecordType> getAll() {

		SQLiteDatabase db = SQLiteProvider.getInstance().getReadableDatabase();

		String sql = String.format("SELECT * FROM %s", TravelDiaryContract.RecordTypeEntry.TABLE_NAME);

		Cursor c = db.rawQuery(sql, null);

		List<RecordType> recordTypeList = new ArrayList<RecordType>();

		if (c.moveToFirst()) {

			do {
				RecordType recordType = new RecordType(
						c.getLong(c.getColumnIndex(TravelDiaryContract.RecordTypeEntry.COLUMN_ID_RECORD_TYPE)),
						c.getString(c.getColumnIndex(TravelDiaryContract.RecordTypeEntry.COLUMN_CODE)),
						c.getString(c.getColumnIndex(TravelDiaryContract.RecordTypeEntry.COLUMN_DESCRIPTION))
				);

				recordTypeList.add(recordType);
			} while (c.moveToNext());
		}

		c.close();

		return recordTypeList;

	}

	public static RecordType get(String code) throws RecordNotFoundException {

		SQLiteDatabase db = SQLiteProvider.getInstance().getReadableDatabase();

		String sql = String.format("SELECT * FROM %s WHERE %s = '%s' LIMIT 1;", TravelDiaryContract.RecordTypeEntry.TABLE_NAME, TravelDiaryContract.RecordTypeEntry.COLUMN_CODE, code);

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

	public static boolean removeAll() {
		SQLiteDatabase db = SQLiteProvider.getInstance().getWritableDatabase();
		return db.delete(TravelDiaryContract.RecordTypeEntry.TABLE_NAME, null, null) != 0;
	}

}
