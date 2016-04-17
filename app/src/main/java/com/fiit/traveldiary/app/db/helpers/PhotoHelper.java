package com.fiit.traveldiary.app.db.helpers;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.util.Log;
import com.fiit.traveldiary.app.db.TravelDiaryContract;
import com.fiit.traveldiary.app.db.provider.SQLiteProvider;
import com.fiit.traveldiary.app.exceptions.RecordNotFoundException;
import com.fiit.traveldiary.app.models.Photo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jakub Dubec on 17/04/16.
 */
public abstract class PhotoHelper {

	public static long persist(Photo model) {
		SQLiteDatabase db = SQLiteProvider.getInstance().getWritableDatabase();

		ContentValues contentValues = new ContentValues();

		contentValues.put(TravelDiaryContract.PhotoEntry.COLUMN_ID_RECORD, model.getIdRecord());
		contentValues.put(TravelDiaryContract.PhotoEntry.COLUMN_FILENAME, model.getFilename());
		contentValues.put(TravelDiaryContract.PhotoEntry.COLUMN_UUID, model.getUuid());
		contentValues.put(TravelDiaryContract.PhotoEntry.COLUMN_CREATED_AT, model.getCreatedAtAsString("yyyy-MM-dd'T'HH:mm:ssZ"));

		boolean exists = true;

		try {
			model.setId(getOne(String.format("WHERE %s = '%s'", TravelDiaryContract.PhotoEntry.COLUMN_UUID, model.getUuid())).getId());
		}
		catch (RecordNotFoundException e) {
			exists = false;
		}

		if (exists) {
			String selection = TravelDiaryContract.PhotoEntry.COLUMN_ID_PHOTO + " LIKE ?";
			String[] selectionArgs = { String.valueOf(model.getId()) };
			db.update(TravelDiaryContract.PhotoEntry.TABLE_NAME, contentValues, selection, selectionArgs);
		}
		else {
			long primaryKey;
			primaryKey = db.insert(TravelDiaryContract.PhotoEntry.TABLE_NAME, null, contentValues);
			model.setId(primaryKey);
		}

		return model.getId();
	}

	public static boolean remove(Photo model) {
		SQLiteDatabase db = SQLiteProvider.getInstance().getWritableDatabase();

		String selection = TravelDiaryContract.PhotoEntry.COLUMN_ID_PHOTO + " LIKE ?";
		String[] selectionArgs = { String.valueOf(model.getId()) };

		return db.delete(TravelDiaryContract.PhotoEntry.TABLE_NAME, selection, selectionArgs) != 0;
	}

	public static List<Photo> getAll(String filter) throws RecordNotFoundException {
		List<Photo> photos = new ArrayList<Photo>();
		SQLiteDatabase db = SQLiteProvider.getInstance().getReadableDatabase();

		String sql = String.format("SELECT * FROM %s %s LIMIT 1;", TravelDiaryContract.PhotoEntry.TABLE_NAME, filter);
		Log.e(SQLiteProvider.LOG, sql);

		Cursor c = db.rawQuery(sql, null);

		if (c.moveToFirst()) {
			do {
				Photo photo = new Photo();
				photo.setId(c.getLong(c.getColumnIndex(TravelDiaryContract.PhotoEntry.COLUMN_ID_PHOTO)));
				photo.setIdRecord(c.getLong(c.getColumnIndex(TravelDiaryContract.PhotoEntry.COLUMN_ID_RECORD)));
				photo.setFilename(c.getString(c.getColumnIndex(TravelDiaryContract.PhotoEntry.COLUMN_FILENAME)));
				photo.setUuid(c.getString(c.getColumnIndex(TravelDiaryContract.PhotoEntry.COLUMN_UUID)));
				photo.setCreatedAtFromString(c.getString(c.getColumnIndex(TravelDiaryContract.PhotoEntry.COLUMN_CREATED_AT)), "yyyy-MM-dd'T'HH:mm:ss'Z'");

				photos.add(photo);
			} while (c.moveToNext());
		}

		c.close();
		return photos;
	}

	@NonNull
	public static Photo getOne(String filter) throws RecordNotFoundException {
		SQLiteDatabase db = SQLiteProvider.getInstance().getReadableDatabase();

		String sql = String.format("SELECT * FROM %s %s LIMIT 1;", TravelDiaryContract.PhotoEntry.TABLE_NAME, filter);
		Log.e(SQLiteProvider.LOG, sql);

		Cursor c = db.rawQuery(sql, null);

		if (!c.moveToFirst())
			throw new RecordNotFoundException();

		Photo photo = new Photo();
		photo.setId(c.getLong(c.getColumnIndex(TravelDiaryContract.PhotoEntry.COLUMN_ID_PHOTO)));
		photo.setIdRecord(c.getLong(c.getColumnIndex(TravelDiaryContract.PhotoEntry.COLUMN_ID_RECORD)));
		photo.setFilename(c.getString(c.getColumnIndex(TravelDiaryContract.PhotoEntry.COLUMN_FILENAME)));
		photo.setUuid(c.getString(c.getColumnIndex(TravelDiaryContract.PhotoEntry.COLUMN_UUID)));
		photo.setCreatedAtFromString(c.getString(c.getColumnIndex(TravelDiaryContract.PhotoEntry.COLUMN_CREATED_AT)), "yyyy-MM-dd'T'HH:mm:ss'Z'");

		c.close();

		return photo;
	}
}
