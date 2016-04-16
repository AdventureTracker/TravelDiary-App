package com.fiit.traveldiary.app.db;

import android.provider.BaseColumns;

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
	}

	public static abstract class PrivacyEntry implements BaseColumns {
		public static final String TABLE_NAME = "privacy";

		public static final String COLUMN_ID_PRIVACY = "id_privacy";
		public static final String COLUMN_CODE = "prv_code";
		public static final String COLUMN_DESCRIPTION = "prv_description";
	}

	public static abstract class RecordTypeEntry implements BaseColumns {
		public static final String TABLE_NAME = "recordType";

		public static final String COLUMN_ID_RECORD_TYPE = "id_recordType";
		public static final String COLUMN_CODE = "ret_code";
		public static final String COLUMN_DESCRIPTION = "ret_description";
	}

	public static abstract class UserEntry implements BaseColumns {
		public static final String TABLE_NAME = "user";

		public static final String COLUMN_ID_USER = "id_user";
		public static final String COLUMN_FIRST_NAME = "usr_firstName";
		public static final String COLUMN_LAST_NAME = "usr_lastName";
		public static final String COLUMN_EMAIL = "usr_email";
		public static final String COLUMN_PASSWORD = "usr_password";
	}

	public static abstract class PhotoEntry implements BaseColumns {
		public static final String TABLE_NAME = "photo";

		public static final String COLUMN_ID_PHOTO = "id_photo";
		public static final String COLUMN_ID_RECORD = "id_record";
		public static final String COLUMN_UUID = "pht_uuid";
		public static final String COLUMN_FILENAME = "pht_filename";
		public static final String COLUMN_CREATED_AT = "pht_createdAt";
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
	}

	public static abstract class UserHaveTripEntry implements BaseColumns {
		public static final String TABLE_NAME = "user_have_trip";

		public static final String COLUMN_USER_ID = "id_user";
		public static final String COLUMN_TRIP_ID = "id_trip";
	}

}
