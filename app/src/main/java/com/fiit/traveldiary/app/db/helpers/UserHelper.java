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

	public static long persist(User model) {

		SQLiteDatabase db = SQLiteProvider.getInstance().getWritableDatabase();

		ContentValues contentValues = new ContentValues();

		contentValues.put(TravelDiaryContract.UserEntry.COLUMN_NAME, model.getName());
		contentValues.put(TravelDiaryContract.UserEntry.COLUMN_EMAIL, model.getEmail());
		contentValues.put(TravelDiaryContract.UserEntry.COLUMN_UUID, model.getUuid());

		boolean exists = true;

		try {
			model.setId(getOne(String.format("WHERE %s = '%s'", TravelDiaryContract.UserEntry.COLUMN_UUID, model.getUuid())).getId());
		}
		catch (RecordNotFoundException e) {
			exists = false;
		}

		if (exists) {
			String selection = TravelDiaryContract.UserEntry.COLUMN_ID_USER + " LIKE ?";
			String[] selectionArgs = { String.valueOf(model.getId()) };
			db.update(TravelDiaryContract.UserEntry.TABLE_NAME, contentValues, selection, selectionArgs);
		}
		else {
			long primaryKey;
			primaryKey = db.insert(TravelDiaryContract.UserEntry.TABLE_NAME, null, contentValues);
			model.setId(primaryKey);
		}

		return model.getId();
	}

	public static boolean remove(User model) {
		SQLiteDatabase db = SQLiteProvider.getInstance().getWritableDatabase();

		String selection = TravelDiaryContract.UserEntry.COLUMN_ID_USER + " LIKE ?";
		String[] selectionArgs = { String.valueOf(model.getId()) };

		return db.delete(TravelDiaryContract.UserEntry.TABLE_NAME, selection, selectionArgs) != 0;
	}

	public static List<User> getAll(String filter) {
		List<User> users = new ArrayList<User>();
		SQLiteDatabase db = SQLiteProvider.getInstance().getReadableDatabase();

		String sql = String.format("SELECT * FROM %s %s LIMIT 1;", TravelDiaryContract.UserEntry.TABLE_NAME, filter);
		Log.e(SQLiteProvider.LOG, sql);

		Cursor c = db.rawQuery(sql, null);

		if (c.moveToFirst()) {
			User user = new User();
			user.setId(c.getInt(c.getColumnIndex(TravelDiaryContract.UserEntry.COLUMN_ID_USER)));
			user.setEmail(c.getString(c.getColumnIndex(TravelDiaryContract.UserEntry.COLUMN_EMAIL)));
			user.setName(c.getString(c.getColumnIndex(TravelDiaryContract.UserEntry.COLUMN_NAME)));
			user.setUuid(c.getString(c.getColumnIndex(TravelDiaryContract.UserEntry.COLUMN_UUID)));

			users.add(user);
		}

		c.close();

		return users;

	}

	@NonNull
	public static User getOne(String filter) throws RecordNotFoundException {

		SQLiteDatabase db = SQLiteProvider.getInstance().getReadableDatabase();

		String sql = String.format("SELECT * FROM %s %s LIMIT 1;", TravelDiaryContract.UserEntry.TABLE_NAME, filter);

		Cursor c = db.rawQuery(sql, null);

		if (c == null)
			throw new Resources.NotFoundException();

		c.moveToFirst();

		User user = new User();
		user.setId(c.getInt(c.getColumnIndex(TravelDiaryContract.UserEntry.COLUMN_ID_USER)));
		user.setEmail(c.getString(c.getColumnIndex(TravelDiaryContract.UserEntry.COLUMN_EMAIL)));
		user.setName(c.getString(c.getColumnIndex(TravelDiaryContract.UserEntry.COLUMN_NAME)));
		user.setUuid(c.getString(c.getColumnIndex(TravelDiaryContract.UserEntry.COLUMN_UUID)));

		c.close();

		return user;
	}
}
