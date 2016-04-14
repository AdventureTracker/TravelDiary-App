package com.fiit.traveldiary.app.models;

import com.fiit.traveldiary.app.exceptions.InvalidInputException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by jdubec on 13/04/16.
 */
public class Trip extends Model {

	private long id;
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

	private List<Record> records;
	private List<User> users;

	public Trip(JSONObject object) throws InvalidInputException, JSONException {
		super(object);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Privacy getPrivacy() {
		return privacy;
	}

	public void setPrivacy(Privacy privacy) {
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

	public Date getEstimatedArrival() {
		return estimatedArrival;
	}

	public void setEstimatedArrival(Date estimatedArrival) {
		this.estimatedArrival = estimatedArrival;
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

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	@Override
	public JSONObject toJSON(boolean detailed) throws JSONException {
		JSONObject tripItem = new JSONObject();

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US); //Y-m-d
		DateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.US); // ISO 8601

		tripItem.put("id", this.getUuid());
		tripItem.put("name", this.getName());
		tripItem.put("destination", this.getDestination());
		tripItem.put("status", this.getStatus());
		tripItem.put("privacy", this.getPrivacy());
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
		this.setStatus(new Status(jsonObject.getString("status")));
		this.setPrivacy(new Privacy(jsonObject.getString("privacy")));

		// Not required
		if (jsonObject.has("description"))
			this.setDescription(jsonObject.getString("description"));

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US); //Y-m-d
		DateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.US); // ISO 8601
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
			throw new InvalidInputException("Unable to parse date time!", e);
		}

		// Not required
		if (jsonObject.has("records")) {
			JSONArray recordsArray = jsonObject.getJSONArray("records");

			for (int i = 0; i <= recordsArray.length(); i++) {
				this.records.add(new Record(recordsArray.getJSONObject(i)));
			}
		}

		// Not required
		if (jsonObject.has("users")) {

			JSONArray usersArray = jsonObject.getJSONArray("users");

			for (int i = 0; i <= usersArray.length(); i++) {
				this.users.add(new User(usersArray.getJSONObject(i)));
			}
		}

		return true;
	}
}
