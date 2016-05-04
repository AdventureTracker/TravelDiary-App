package com.fiit.traveldiary.app.db.helpers;

import android.content.ContentValues;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.util.Log;
import com.fiit.traveldiary.app.db.TravelDiaryContract;
import com.fiit.traveldiary.app.db.provider.SQLiteProvider;
import com.fiit.traveldiary.app.exceptions.RecordNotFoundException;
import com.fiit.traveldiary.app.models.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jakub Dubec on 17/04/16.
 */
public abstract class UserHelper {

	public static long save(User model) {
		SQLiteDatabase db = SQLiteProvider.getInstance().getWritableDatabase();

		ContentValues contentValues = new ContentValues();

		contentValues.put(TravelDiaryContract.UserEntry.COLUMN_NAME, model.getName());
		contentValues.put(TravelDiaryContract.UserEntry.COLUMN_EMAIL, model.getEmail());
		contentValues.put(TravelDiaryContract.UserEntry.COLUMN_UUID, model.getUuid());

		model.setId(db.insert(TravelDiaryContract.UserEntry.TABLE_NAME, null, contentValues));

		return model.getId();
	}

	public static User get(String uuid) throws RecordNotFoundException {

		SQLiteDatabase db = SQLiteProvider.getInstance().getReadableDatabase();

		String sql = String.format("SELECT * FROM %s WHERE %s = '%s' LIMIT 1;", TravelDiaryContract.UserEntry.TABLE_NAME, TravelDiaryContract.UserEntry.COLUMN_UUID, uuid);
		Log.e(SQLiteProvider.LOG, sql);

		Cursor c = db.rawQuery(sql, null);

		if (!c.moveToFirst())
			throw new RecordNotFoundException();

		User user = new User(
				c.getLong(c.getColumnIndex(TravelDiaryContract.UserEntry.COLUMN_ID_USER)),
				c.getString(c.getColumnIndex(TravelDiaryContract.UserEntry.COLUMN_NAME)),
				c.getString(c.getColumnIndex(TravelDiaryContract.UserEntry.COLUMN_EMAIL)),
				c.getString(c.getColumnIndex(TravelDiaryContract.UserEntry.COLUMN_UUID))
		);

		Log.w("User", String.valueOf(user.getId()));

		c.close();

		return user;
	}

	public static User get(long id) throws RecordNotFoundException {

		SQLiteDatabase db = SQLiteProvider.getInstance().getReadableDatabase();

		String sql = String.format("SELECT * FROM %s WHERE %s = %d LIMIT 1;", TravelDiaryContract.UserEntry.TABLE_NAME, TravelDiaryContract.UserEntry.COLUMN_ID_USER, id);
		Log.e(SQLiteProvider.LOG, sql);

		Cursor c = db.rawQuery(sql, null);

		if (!c.moveToFirst())
			throw new RecordNotFoundException();

		User user = new User(
				c.getLong(c.getColumnIndex(TravelDiaryContract.UserEntry.COLUMN_ID_USER)),
				c.getString(c.getColumnIndex(TravelDiaryContract.UserEntry.COLUMN_NAME)),
				c.getString(c.getColumnIndex(TravelDiaryContract.UserEntry.COLUMN_EMAIL)),
				c.getString(c.getColumnIndex(TravelDiaryContract.UserEntry.COLUMN_UUID))
		);

		c.close();

		return user;
	}

	public static boolean removeAll() {
		SQLiteDatabase db = SQLiteProvider.getInstance().getWritableDatabase();
		return db.delete(TravelDiaryContract.UserEntry.TABLE_NAME, null, null) != 0;
	}
}
