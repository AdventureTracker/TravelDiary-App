package com.fiit.traveldiary.app.db;

import android.provider.BaseColumns;
import com.fiit.traveldiary.app.db.provider.DatabaseConstants;
import com.fiit.traveldiary.app.models.RecordType;

import java.util.UUID;

/**
 * Created by Jakub Dubec on 16/04/16.
 */
public final class TravelDiaryContract {

	public TravelDiaryContract() {
	}

	public static abstract class StatusEntry implements BaseColumns {
		public static final String TABLE_NAME = "status";

		public static final String COLUMN_ID_STATUS = "id_status";
		public static final String COLUMN_CODE = "sta_code";
		public static final String COLUMN_DESCRIPTION = "sta_description";

		public static final String CREATE_SQL =
				"CREATE TABLE " + TABLE_NAME + " (" +
					COLUMN_ID_STATUS + DatabaseConstants.SQLITE_INTEGER_TYPE + " PRIMARY KEY " + DatabaseConstants.COMMA_SEP +
					COLUMN_CODE + DatabaseConstants.SQLITE_TEXT_TYPE + DatabaseConstants.COMMA_SEP +
					COLUMN_DESCRIPTION + DatabaseConstants.SQLITE_TEXT_TYPE + ")";

		public static final String REMOVE_SQL = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";
	}

	public static abstract class PrivacyEntry implements BaseColumns {
		public static final String TABLE_NAME = "privacy";

		public static final String COLUMN_ID_PRIVACY = "id_privacy";
		public static final String COLUMN_CODE = "prv_code";
		public static final String COLUMN_DESCRIPTION = "prv_description";

		public static final String CREATE_SQL =
				"CREATE TABLE " + TABLE_NAME + " (" +
					COLUMN_ID_PRIVACY + DatabaseConstants.SQLITE_INTEGER_TYPE + " PRIMARY KEY " + DatabaseConstants.COMMA_SEP +
					COLUMN_CODE + DatabaseConstants.SQLITE_TEXT_TYPE + DatabaseConstants.COMMA_SEP +
					COLUMN_DESCRIPTION + DatabaseConstants.SQLITE_TEXT_TYPE + ")";

		public static final String REMOVE_SQL = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";
	}

	public static abstract class RecordTypeEntry implements BaseColumns {
		public static final String TABLE_NAME = "recordType";

		public static final String COLUMN_ID_RECORD_TYPE = "id_recordType";
		public static final String COLUMN_CODE = "ret_code";
		public static final String COLUMN_DESCRIPTION = "ret_description";

		public static final String CREATE_SQL =
				"CREATE TABLE " + TABLE_NAME + " (" +
					COLUMN_ID_RECORD_TYPE + DatabaseConstants.SQLITE_INTEGER_TYPE + " PRIMARY KEY " + DatabaseConstants.COMMA_SEP +
					COLUMN_CODE + DatabaseConstants.SQLITE_TEXT_TYPE + DatabaseConstants.COMMA_SEP +
					COLUMN_DESCRIPTION + DatabaseConstants.SQLITE_TEXT_TYPE + ")";

		public static final String REMOVE_SQL = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";
	}

	public static abstract class UserEntry implements BaseColumns {
		public static final String TABLE_NAME = "user";

		public static final String COLUMN_ID_USER = "id_user";
		public static final String COLUMN_NAME = "usr_name";
		public static final String COLUMN_EMAIL = "usr_email";
		public static final String COLUMN_UUID = "usr_uuid";

		public static final String CREATE_SQL =
				"CREATE TABLE " + TABLE_NAME + " (" +
					COLUMN_ID_USER + DatabaseConstants.SQLITE_INTEGER_TYPE + " PRIMARY KEY" + DatabaseConstants.COMMA_SEP +
					COLUMN_NAME + DatabaseConstants.SQLITE_TEXT_TYPE + DatabaseConstants.COMMA_SEP +
					COLUMN_EMAIL + DatabaseConstants.SQLITE_TEXT_TYPE + DatabaseConstants.COMMA_SEP +
					COLUMN_UUID + DatabaseConstants.SQLITE_TEXT_TYPE + ");";

		public static final String REMOVE_SQL = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";
	}

	public static abstract class TripEntry implements BaseColumns {
		public static final String TABLE_NAME = "trip";

		public static final String COLUMN_ID_TRIP = "id_trip";
		public static final String COLUMN_ID_STATUS = "id_status";
		public static final String COLUMN_ID_PRIVACY = "id_privacy";
		public static final String COLUMN_UUID = "trp_uuid";
		public static final String COLUMN_NAME = "trp_name";
		public static final String COLUMN_DESTINATION = "trp_destination";
		public static final String COLUMN_DESCRIPTION = "trp_description";
		public static final String COLUMN_START_DATE = "trp_startDate";
		public static final String COLUMN_ESTIMATED_ARRIVAL_DATE = "trp_estimatedArrivalDate";
		public static final String COLUMN_CREATED_AT = "trp_createdAt";
		public static final String COLUMN_UPDATED_AT = "trp_updatedAt";

		public static final String CREATE_SQL =
				"CREATE TABLE " + TABLE_NAME + " (" +
						COLUMN_ID_TRIP + DatabaseConstants.SQLITE_INTEGER_TYPE + " PRIMARY KEY" + DatabaseConstants.COMMA_SEP +
						COLUMN_ID_STATUS + DatabaseConstants.SQLITE_INTEGER_TYPE + DatabaseConstants.COMMA_SEP +
						COLUMN_ID_PRIVACY + DatabaseConstants.SQLITE_INTEGER_TYPE + DatabaseConstants.COMMA_SEP +
						COLUMN_UUID + DatabaseConstants.SQLITE_TEXT_TYPE + DatabaseConstants.COMMA_SEP +
						COLUMN_NAME + DatabaseConstants.SQLITE_TEXT_TYPE + DatabaseConstants.COMMA_SEP +
						COLUMN_DESTINATION + DatabaseConstants.SQLITE_TEXT_TYPE + DatabaseConstants.COMMA_SEP +
						COLUMN_DESCRIPTION + DatabaseConstants.SQLITE_TEXT_TYPE + DatabaseConstants.COMMA_SEP +
						COLUMN_START_DATE + DatabaseConstants.SQLITE_DATETIME_TYPE + DatabaseConstants.COMMA_SEP +
						COLUMN_ESTIMATED_ARRIVAL_DATE + DatabaseConstants.SQLITE_DATETIME_TYPE + DatabaseConstants.COMMA_SEP +
						COLUMN_CREATED_AT + DatabaseConstants.SQLITE_TIMESTAMP_TYPE + DatabaseConstants.COMMA_SEP +
						COLUMN_UPDATED_AT + DatabaseConstants.SQLITE_TIMESTAMP_TYPE +
						"FOREIGN KEY(" + COLUMN_ID_STATUS + ") REFERENCES " + StatusEntry.TABLE_NAME + "(" + StatusEntry.COLUMN_ID_STATUS + ")" +
						"FOREIGN KEY(" + COLUMN_ID_PRIVACY + ") REFERENCES " + PrivacyEntry.TABLE_NAME + "(" + PrivacyEntry.COLUMN_ID_PRIVACY + ")" +
						");";

		public static final String REMOVE_SQL = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";
	}

	public static abstract class RecordEntry implements BaseColumns {
		public static final String TABLE_NAME = "record";

		public static final String COLUMN_ID_RECORD = "id_record";
		public static final String COLUMN_ID_RECORD_TYPE = "id_recordType";
		public static final String COLUMN_ID_TRIP = "id_trip";
		public static final String COLUMN_ID_USER = "id_user";
		public static final String COLUMN_UUID = "rec_uuid";
		public static final String COLUMN_DAY = "rec_day";
		public static final String COLUMN_DESCRIPTION = "rec_description";
		public static final String COLUMN_LATITUDE = "rec_latitude";
		public static final String COLUMN_LONGITUDE = "rec_longitude";
		public static final String COLUMN_ALTITUDE = "rec_altitude";
		public static final String COLUMN_CREATED_AT = "rec_createdAt";
		public static final String COLUMN_UPDATED_AT = "rec_updatedAt";

		public static final String CREATE_SQL =
				"CREATE TABLE " + TABLE_NAME + " (" +
						COLUMN_ID_RECORD + DatabaseConstants.SQLITE_INTEGER_TYPE + " PRIMARY KEY" + DatabaseConstants.COMMA_SEP +
						COLUMN_ID_RECORD_TYPE + DatabaseConstants.SQLITE_INTEGER_TYPE + DatabaseConstants.COMMA_SEP +
						COLUMN_ID_TRIP + DatabaseConstants.SQLITE_INTEGER_TYPE + DatabaseConstants.COMMA_SEP +
						COLUMN_ID_USER + DatabaseConstants.SQLITE_INTEGER_TYPE + DatabaseConstants.COMMA_SEP +
						COLUMN_UUID + DatabaseConstants.SQLITE_TEXT_TYPE + DatabaseConstants.COMMA_SEP +
						COLUMN_DAY + DatabaseConstants.SQLITE_DATETIME_TYPE + DatabaseConstants.COMMA_SEP +
						COLUMN_DESCRIPTION + DatabaseConstants.SQLITE_TEXT_TYPE + DatabaseConstants.COMMA_SEP +
						COLUMN_LATITUDE + DatabaseConstants.SQLITE_FLOAT_TYPE + DatabaseConstants.COMMA_SEP +
						COLUMN_LONGITUDE + DatabaseConstants.SQLITE_FLOAT_TYPE + DatabaseConstants.COMMA_SEP +
						COLUMN_ALTITUDE + DatabaseConstants.SQLITE_INTEGER_TYPE + DatabaseConstants.COMMA_SEP +
						COLUMN_CREATED_AT + DatabaseConstants.SQLITE_TIMESTAMP_TYPE + DatabaseConstants.COMMA_SEP +
						COLUMN_UPDATED_AT + DatabaseConstants.SQLITE_TIMESTAMP_TYPE +
						"FOREIGN KEY(" + COLUMN_ID_RECORD_TYPE + ") REFERENCES " + RecordTypeEntry.TABLE_NAME + "(" + RecordTypeEntry.COLUMN_ID_RECORD_TYPE + ")" +
						"FOREIGN KEY(" + COLUMN_ID_TRIP + ") REFERENCES " + TripEntry.TABLE_NAME + "(" + TripEntry.COLUMN_ID_TRIP + ")" +
						"FOREIGN KEY(" + COLUMN_ID_USER + ") REFERENCES " + UserEntry.TABLE_NAME + "(" + UserEntry.COLUMN_ID_USER + ")" +
						");";

		public static final String REMOVE_SQL = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";
	}

	public static abstract class PhotoEntry implements BaseColumns {
		public static final String TABLE_NAME = "photo";

		public static final String COLUMN_ID_PHOTO = "id_photo";
		public static final String COLUMN_ID_RECORD = "id_record";
		public static final String COLUMN_UUID = "pht_uuid";
		public static final String COLUMN_FILENAME = "pht_filename";
		public static final String COLUMN_CREATED_AT = "pht_createdAt";

		public static final String CREATE_SQL =
				"CREATE TABLE " + TABLE_NAME + " (" +
						COLUMN_ID_PHOTO + DatabaseConstants.SQLITE_INTEGER_TYPE + " PRIMARY KEY" + DatabaseConstants.COMMA_SEP +
						COLUMN_ID_RECORD + DatabaseConstants.SQLITE_INTEGER_TYPE + DatabaseConstants.COMMA_SEP +
						COLUMN_UUID + DatabaseConstants.SQLITE_TEXT_TYPE + DatabaseConstants.COMMA_SEP +
						COLUMN_FILENAME + DatabaseConstants.SQLITE_TEXT_TYPE + DatabaseConstants.COMMA_SEP +
						COLUMN_CREATED_AT + DatabaseConstants.SQLITE_TIMESTAMP_TYPE +
						"FOREIGN KEY(" + COLUMN_ID_RECORD + ") REFERENCES " + RecordEntry.TABLE_NAME + "(" + RecordEntry.COLUMN_ID_RECORD + ")" +
						");";

		public static final String REMOVE_SQL = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";
	}

	public static abstract class UserHaveTripEntry implements BaseColumns {
		public static final String TABLE_NAME = "user_have_trip";

		public static final String COLUMN_ID_USER = "id_user";
		public static final String COLUMN_ID_TRIP = "id_trip";

		public static final String CREATE_SQL =
				"CREATE TABLE " + TABLE_NAME + " (" +
						COLUMN_ID_TRIP + DatabaseConstants.SQLITE_INTEGER_TYPE + DatabaseConstants.COMMA_SEP +
						COLUMN_ID_USER + DatabaseConstants.SQLITE_INTEGER_TYPE + DatabaseConstants.COMMA_SEP +
						"FOREIGN KEY(" + COLUMN_ID_TRIP + ") REFERENCES " + TripEntry.TABLE_NAME + "(" + TripEntry.COLUMN_ID_TRIP + ")" +
						"FOREIGN KEY(" + COLUMN_ID_USER + ") REFERENCES " + UserEntry.TABLE_NAME + "(" + UserEntry.COLUMN_ID_USER + ")" +
						");";
		public static final String REMOVE_SQL = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";
	}

	public static abstract class ActivityLogEntry implements BaseColumns {
		public static final String TABLE_NAME = "activity_log";

		public static final String COLUMN_ID_ACTIVITY = "id_activity";
		public static final String COLUMN_ENTITY = "act_entity";
		public static final String COLUMN_UUID = "act_uuid";
		public static final String COLUMN_CREATED_AT = "act_createdAt";

		public static final String CREATE_SQL =
				"CREATE TABLE " + TABLE_NAME + " (" +
						COLUMN_ID_ACTIVITY + DatabaseConstants.SQLITE_INTEGER_TYPE + " PRIMARY KEY" + DatabaseConstants.COMMA_SEP +
						COLUMN_ENTITY + DatabaseConstants.SQLITE_TEXT_TYPE + DatabaseConstants.COMMA_SEP +
						COLUMN_UUID + DatabaseConstants.SQLITE_TEXT_TYPE + DatabaseConstants.COMMA_SEP +
						COLUMN_CREATED_AT + DatabaseConstants.SQLITE_TIMESTAMP_TYPE + DatabaseConstants.COMMA_SEP +
						");";
		public static final String REMOVE_SQL = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";
	}

}
