package com.fiit.traveldiary.app.models;

import com.fiit.traveldiary.app.db.TravelDiaryContract;
import com.fiit.traveldiary.app.db.helpers.RecordHelper;
import com.fiit.traveldiary.app.exceptions.InvalidInputException;
import com.fiit.traveldiary.app.exceptions.RecordNotFoundException;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by jdubec on 13/04/16.
 */
public class Photo extends Model {

	private long id;
	private long idRecord;
	private String uuid;
	private String filename;
	private Record record;
	private Date createdAt;

	public Photo() {
	}

	public Photo(JSONObject object) throws InvalidInputException, JSONException {
		super(object);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getIdRecord() {
		return idRecord;
	}

	public void setIdRecord(long idRecord) throws RecordNotFoundException {
		this.idRecord = idRecord;
		this.record = RecordHelper.getOne(String.format("WHERE %s = %d", TravelDiaryContract.RecordEntry.COLUMN_ID_RECORD, idRecord));
	}

	public Record getRecord() {
		return record;
	}

	public void setRecord(Record record) {
		this.idRecord = record.getId();
		this.record = record;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public boolean setCreatedAtFromString(String date, String format) {
		DateFormat dateFormat = new SimpleDateFormat(format, Locale.US);
		try {
			this.setCreatedAt(dateFormat.parse(date));
		} catch (ParseException e) {
			return false;
		}
		return true;
	}

	public String getCreatedAtAsString(String format) {
		DateFormat dateFormat = new SimpleDateFormat(format, Locale.US);
		return dateFormat.format(this.createdAt);
	}

	@Override
	public JSONObject toJSON(boolean detailed) {
		return null;
	}

	@Override
	public boolean parseJSON(JSONObject jsonObject) {
		return false;
	}
}
