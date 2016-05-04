package com.fiit.traveldiary.app.db.helpers;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.util.Log;
import com.fiit.traveldiary.app.db.SyncStatus;
import com.fiit.traveldiary.app.db.TravelDiaryContract;
import com.fiit.traveldiary.app.db.provider.SQLiteProvider;
import com.fiit.traveldiary.app.exceptions.RecordNotFoundException;
import com.fiit.traveldiary.app.models.Record;
import com.fiit.traveldiary.app.models.Trip;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jakub Dubec on 17/04/16.
 */
public abstract class TripHelper {
	public static long persist(Trip model) {
		SQLiteDatabase db = SQLiteProvider.getInstance().getWritableDatabase();

		ContentValues contentValues = new ContentValues();
		contentValues.put(TravelDiaryContract.TripEntry.COLUMN_ID_PRIVACY, model.getIdStatus());
		contentValues.put(TravelDiaryContract.TripEntry.COLUMN_ID_STATUS, model.getIdStatus());
		contentValues.put(TravelDiaryContract.TripEntry.COLUMN_UUID, model.getUuid());
		contentValues.put(TravelDiaryContract.TripEntry.COLUMN_DESCRIPTION, model.getDescription());
		contentValues.put(TravelDiaryContract.TripEntry.COLUMN_NAME, model.getName());
		contentValues.put(TravelDiaryContract.TripEntry.COLUMN_DESTINATION, model.getDestination());
		contentValues.put(TravelDiaryContract.TripEntry.COLUMN_START_DATE, model.getStartDateAsString("yyyy-MM-dd'T'HH:mm:ssZ"));
		contentValues.put(TravelDiaryContract.TripEntry.COLUMN_ESTIMATED_ARRIVAL_DATE, model.getEstimatedArrivalAsString("yyyy-MM-dd'T'HH:mm:ssZ"));
		contentValues.put(TravelDiaryContract.TripEntry.COLUMN_UPDATED_AT, model.getUpdatedAtAsString("yyyy-MM-dd'T'HH:mm:ssZ"));
		contentValues.put(TravelDiaryContract.TripEntry.COLUMN_CREATED_AT, model.getCreatedAtAsString("yyyy-MM-dd'T'HH:mm:ssZ"));
		contentValues.put(TravelDiaryContract.TripEntry.COLUMN_SYNC, model.getSyncStatus().toString());

		boolean exists = true;

		try {
			model.setId(getOne(String.format("WHERE %s = '%s'", TravelDiaryContract.TripEntry.COLUMN_UUID, model.getUuid())).getId());
		}
		catch (RecordNotFoundException e) {
			exists = false;
		}

		if (exists) {
			String selection = TravelDiaryContract.TripEntry.COLUMN_ID_TRIP + " LIKE ?";
			String[] selectionArgs = { String.valueOf(model.getId()) };
			db.update(TravelDiaryContract.TripEntry.TABLE_NAME, contentValues, selection, selectionArgs);
		}
		else {
			long primaryKey;
			primaryKey = db.insert(TravelDiaryContract.TripEntry.TABLE_NAME, null, contentValues);
			model.setId(primaryKey);
		}

		if (model.getRecords() != null) {
			for (Record record : model.getRecords()) {
				record.setIdTrip(model.getId());
				record.setSyncStatus(SyncStatus.SYNCED);
				RecordHelper.persist(record);
			}
		}

		return model.getId();
	}

	public static boolean remove(Trip model) {
		SQLiteDatabase db = SQLiteProvider.getInstance().getWritableDatabase();

		String selection = TravelDiaryContract.TripEntry.COLUMN_ID_TRIP + " LIKE ?";
		String[] selectionArgs = { String.valueOf(model.getId()) };

		return db.delete(TravelDiaryContract.TripEntry.TABLE_NAME, selection, selectionArgs) != 0;
	}

	public static List<Trip> getAll(String filter) {
		List<Trip> trips = new ArrayList<Trip>();
		SQLiteDatabase db = SQLiteProvider.getInstance().getReadableDatabase();

		String sql = String.format("SELECT * FROM %s %s LIMIT 1;", TravelDiaryContract.TripEntry.TABLE_NAME, filter);
		Log.e(SQLiteProvider.LOG, sql);

		Cursor c = db.rawQuery(sql, null);

		if (c.moveToFirst()) {
			Trip trip = new Trip();

			trip.setId(c.getLong(c.getColumnIndex(TravelDiaryContract.TripEntry.COLUMN_ID_TRIP)));
			trip.setIdPrivacy(c.getLong(c.getColumnIndex(TravelDiaryContract.TripEntry.COLUMN_ID_PRIVACY)));
			trip.setIdStatus(c.getLong(c.getColumnIndex(TravelDiaryContract.TripEntry.COLUMN_ID_STATUS)));
			trip.setUuid(c.getString(c.getColumnIndex(TravelDiaryContract.TripEntry.COLUMN_UUID)));
			trip.setName(c.getString(c.getColumnIndex(TravelDiaryContract.TripEntry.COLUMN_NAME)));
			trip.setDescription(c.getString(c.getColumnIndex(TravelDiaryContract.TripEntry.COLUMN_DESCRIPTION)));
			trip.setDestination(c.getString(c.getColumnIndex(TravelDiaryContract.TripEntry.COLUMN_DESTINATION)));
			trip.setStartDateFromString(c.getString(c.getColumnIndex(TravelDiaryContract.TripEntry.COLUMN_START_DATE)), "yyyy-MM-dd'T'HH:mm:ssZ");
			trip.setEstimatedArrivalFromString(c.getString(c.getColumnIndex(TravelDiaryContract.TripEntry.COLUMN_ESTIMATED_ARRIVAL_DATE)), "yyyy-MM-dd'T'HH:mm:ssZ");
			trip.setCreatedAtFromString(c.getString(c.getColumnIndex(TravelDiaryContract.TripEntry.COLUMN_CREATED_AT)), "yyyy-MM-dd'T'HH:mm:ssZ");
			trip.setUpdatedAtFromString(c.getString(c.getColumnIndex(TravelDiaryContract.TripEntry.COLUMN_UPDATED_AT)), "yyyy-MM-dd'T'HH:mm:ssZ");
			trip.setSyncStatus(SyncStatus.parseString(c.getString(c.getColumnIndex(TravelDiaryContract.TripEntry.COLUMN_SYNC))));

			trips.add(trip);
		}

		c.close();

		return trips;
	}

	@NonNull
	public static Trip getOne(String filter) throws RecordNotFoundException {
		SQLiteDatabase db = SQLiteProvider.getInstance().getReadableDatabase();

		String sql = String.format("SELECT * FROM %s %s LIMIT 1;", TravelDiaryContract.TripEntry.TABLE_NAME, filter);
		Log.e(SQLiteProvider.LOG, sql);

		Cursor c = db.rawQuery(sql, null);

		if (!c.moveToFirst()) {
			c.close();
			throw new RecordNotFoundException();
		}

		Trip trip = new Trip();

		trip.setId(c.getLong(c.getColumnIndex(TravelDiaryContract.TripEntry.COLUMN_ID_TRIP)));
		trip.setIdPrivacy(c.getLong(c.getColumnIndex(TravelDiaryContract.TripEntry.COLUMN_ID_PRIVACY)));
		trip.setIdStatus(c.getLong(c.getColumnIndex(TravelDiaryContract.TripEntry.COLUMN_ID_STATUS)));
		trip.setUuid(c.getString(c.getColumnIndex(TravelDiaryContract.TripEntry.COLUMN_UUID)));
		trip.setName(c.getString(c.getColumnIndex(TravelDiaryContract.TripEntry.COLUMN_NAME)));
		trip.setDescription(c.getString(c.getColumnIndex(TravelDiaryContract.TripEntry.COLUMN_DESCRIPTION)));
		trip.setDestination(c.getString(c.getColumnIndex(TravelDiaryContract.TripEntry.COLUMN_DESTINATION)));
		trip.setStartDateFromString(c.getString(c.getColumnIndex(TravelDiaryContract.TripEntry.COLUMN_START_DATE)), "yyyy-MM-dd'T'HH:mm:ssZ");
		trip.setEstimatedArrivalFromString(c.getString(c.getColumnIndex(TravelDiaryContract.TripEntry.COLUMN_ESTIMATED_ARRIVAL_DATE)), "yyyy-MM-dd'T'HH:mm:ssZ");
		trip.setCreatedAtFromString(c.getString(c.getColumnIndex(TravelDiaryContract.TripEntry.COLUMN_CREATED_AT)), "yyyy-MM-dd'T'HH:mm:ssZ");
		trip.setUpdatedAtFromString(c.getString(c.getColumnIndex(TravelDiaryContract.TripEntry.COLUMN_UPDATED_AT)), "yyyy-MM-dd'T'HH:mm:ssZ");
		trip.setSyncStatus(SyncStatus.parseString(c.getString(c.getColumnIndex(TravelDiaryContract.TripEntry.COLUMN_SYNC))));

		c.close();

		return trip;
	}
}
