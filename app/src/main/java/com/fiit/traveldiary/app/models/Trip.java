package com.fiit.traveldiary.app.models;

import android.util.Log;
import com.fiit.traveldiary.app.db.SyncStatus;
import com.fiit.traveldiary.app.db.TravelDiaryContract;
import com.fiit.traveldiary.app.db.helpers.PrivacyHelper;
import com.fiit.traveldiary.app.db.helpers.RecordHelper;
import com.fiit.traveldiary.app.db.helpers.StatusHelper;
import com.fiit.traveldiary.app.exceptions.InvalidInputException;
import com.fiit.traveldiary.app.exceptions.RecordNotFoundException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by jdubec on 13/04/16.
 */
public class Trip extends Model {

	private long id;
	private long idStatus;
	private long idPrivacy;
	private Status status;
	private Privacy privacy;
	private String uuid;
	private String name;
	private String destination;
	private String description;
	private Date startDate;
	private Date estimatedArrival;
	private Date createdAt;
	private Date updatedAt;
	private SyncStatus syncStatus;

	private List<Record> records;
	private List<User> users;

	public Trip() {
		this.records = new ArrayList<Record>();
		this.users = new ArrayList<User>();
	}

	public Trip(JSONObject object) throws InvalidInputException, JSONException {
		super(object);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getIdStatus() {
		return idStatus;
	}

	public void setIdStatus(long idStatus) {
		this.idStatus = idStatus;
		try {
			this.status = StatusHelper.get(idStatus);
		} catch (RecordNotFoundException e) {
			e.printStackTrace();
		}
	}

	public long getIdPrivacy() {
		return idPrivacy;
	}

	public void setIdPrivacy(long idPrivacy) {
		this.idPrivacy = idPrivacy;
		try {
			this.privacy = PrivacyHelper.get(idPrivacy);
		} catch (RecordNotFoundException e) {
			e.printStackTrace();
		}
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.idStatus = status.getId();
		this.status = status;
	}

	public Privacy getPrivacy() {
		return privacy;
	}

	public void setPrivacy(Privacy privacy) {
		this.idPrivacy = privacy.getId();
		this.privacy = privacy;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public boolean setStartDateFromString(String date, String format) {
		DateFormat dateFormat = new SimpleDateFormat(format, Locale.US);
		try {
			this.setStartDate(dateFormat.parse(date));
		} catch (ParseException e) {
			return false;
		}
		return true;
	}

	public String getStartDateAsString(String format) {
		if (this.startDate == null)
			return "";

		DateFormat dateFormat = new SimpleDateFormat(format, Locale.US);
		return dateFormat.format(this.startDate);
	}

	public Date getEstimatedArrival() {
		return estimatedArrival;
	}

	public void setEstimatedArrival(Date estimatedArrival) {
		this.estimatedArrival = estimatedArrival;
	}

	public boolean setEstimatedArrivalFromString(String date, String format) {
		DateFormat dateFormat = new SimpleDateFormat(format, Locale.US);
		try {
			this.setEstimatedArrival(dateFormat.parse(date));
		} catch (ParseException e) {
			return false;
		}
		return true;
	}

	public String getEstimatedArrivalAsString(String format) {
		if (this.estimatedArrival == null)
			return "";

		DateFormat dateFormat = new SimpleDateFormat(format, Locale.US);
		return dateFormat.format(this.estimatedArrival);
	}

	public void addRecord(Record record) {
		this.records.add(record);
	}

	public boolean removeRecord(Record record) {
		return this.records.contains(record) && this.records.remove(record);
	}

	public List<Record> getRecords() {
		return this.records;
	}

	public void setRecords(List<Record> records) {
		this.records = records;
	}

	public void addUser(User user) {
		if (!this.users.contains(user))
			this.users.add(user);
	}

	public boolean removeUser(User user) {
		return this.users.contains(user) && this.users.remove(user);
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
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
		if (this.createdAt == null)
			return "";

		DateFormat dateFormat = new SimpleDateFormat(format, Locale.US);
		return dateFormat.format(this.createdAt);
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public boolean setUpdatedAtFromString(String date, String format) {
		DateFormat dateFormat = new SimpleDateFormat(format, Locale.US);
		try {
			this.setUpdatedAt(dateFormat.parse(date));
		} catch (ParseException e) {
			return false;
		}
		return true;
	}

	public String getUpdatedAtAsString(String format) {
		if (this.updatedAt == null)
			return "";

		DateFormat dateFormat = new SimpleDateFormat(format, Locale.US);
		return dateFormat.format(this.updatedAt);
	}

	public SyncStatus getSyncStatus() {
		return syncStatus;
	}

	public void setSyncStatus(SyncStatus syncStatus) {
		this.syncStatus = syncStatus;
	}

	@Override
	public JSONObject toJSON(boolean detailed) throws JSONException {
		JSONObject tripItem = new JSONObject();

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US); //Y-m-d
		DateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.US); // ISO 8601

		tripItem.put("id", this.getUuid());
		tripItem.put("name", this.getName());
		tripItem.put("destination", this.getDestination());
		tripItem.put("status", this.getStatus().getCode());
		tripItem.put("privacy", this.getPrivacy().getCode());
		tripItem.put("description", this.getDescription());
		tripItem.put("start_date", dateFormat.format(this.getStartDate()));
		tripItem.put("estimated_arrival_date", dateFormat.format(this.getEstimatedArrival()));
		tripItem.put("created_at", dateTimeFormat.format(this.getCreatedAt()));
		tripItem.put("updated_at", dateTimeFormat.format(this.getUpdatedAt()));

		if (detailed) {

			JSONArray recordsItems = new JSONArray();
			JSONArray userItems = new JSONArray();

			for (Record record : this.getRecords()) {
				recordsItems.put(record.toJSON(false));
			}

			for (User user : this.getUsers()) {
				userItems.put(user.toJSON(false));
			}

			tripItem.put("users", userItems);
			tripItem.put("records", recordsItems);
		}

		return tripItem;
	}

	@Override
	public boolean parseJSON(JSONObject jsonObject) throws JSONException, InvalidInputException {

		this.setUuid(jsonObject.getString("id"));
		this.setName(jsonObject.getString("name"));
		this.setDestination(jsonObject.getString("destination"));

		try {
			this.setStatus(StatusHelper.get(jsonObject.getString("status")));
			this.setPrivacy(PrivacyHelper.get(jsonObject.getString("privacy")));
		} catch (RecordNotFoundException e) {
			e.printStackTrace();
			return false;
		}

		// Not required
		if (jsonObject.has("description"))
			this.setDescription(jsonObject.getString("description"));

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US); //Y-m-d
		DateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.US); // ISO 8601
		try {

			// DateTime attributes are not required

			if (jsonObject.has("start_date"))
				this.setStartDate(dateFormat.parse(jsonObject.getString("start_date")));

			if (jsonObject.has("estimated_arrival_date"))
				this.setEstimatedArrival(dateFormat.parse(jsonObject.getString("estimated_arrival_date")));

			if (jsonObject.has("created_at"))
				this.setCreatedAt(dateTimeFormat.parse(jsonObject.getString("created_at")));

			if (jsonObject.has("updated_at"))
				this.setUpdatedAt(dateTimeFormat.parse(jsonObject.getString("updated_at")));

		} catch (ParseException e) {
			Log.w("DateTimeParser", e.getMessage());
			throw new InvalidInputException("Unable to parse date time!", e);
		}

		// Not required
		if (jsonObject.has("records")) {

			this.records = new ArrayList<Record>();

			JSONArray recordsArray = jsonObject.getJSONArray("records");

			for (int i = 0; i < recordsArray.length(); i++) {

				Record record;

				try {
					record = RecordHelper.getOne(String.format("WHERE %s = '%s'", TravelDiaryContract.RecordEntry.COLUMN_UUID, recordsArray.getJSONObject(i).getString("id")));
				}
				catch (RecordNotFoundException e) {
					record = new Record();
				}
				record.parseJSON(recordsArray.getJSONObject(i));
				this.records.add(record);
			}
		}

		// Not required
		if (jsonObject.has("users")) {

			this.users = new ArrayList<User>();

			JSONArray usersArray = jsonObject.getJSONArray("users");

			for (int i = 0; i < usersArray.length(); i++) {
				this.addUser(new User(usersArray.getJSONObject(i)));
			}
		}

		return true;
	}
}
