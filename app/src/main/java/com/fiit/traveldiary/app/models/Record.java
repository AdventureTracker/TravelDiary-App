package com.fiit.traveldiary.app.models;

import com.fiit.traveldiary.app.db.SyncStatus;
import com.fiit.traveldiary.app.db.helpers.RecordTypeHelper;
import com.fiit.traveldiary.app.db.helpers.UserHelper;
import com.fiit.traveldiary.app.exceptions.InvalidInputException;
import com.fiit.traveldiary.app.exceptions.RecordNotFoundException;
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
public class Record extends Model {


	private long id;
	private long idTrip;
	private long idRecordType;
	private long idUser;
	private Trip trip;
	private RecordType recordType;
	private User user;
	private String uuid;
	private Date day;
	private String description;
	private Location location;
	private Date createdAt;
	private Date updatedAt;
	private SyncStatus syncStatus;

	private List<Photo> photos;

	public Record() {
		this.photos = new ArrayList<Photo>();
	}

	public Record(JSONObject object) throws InvalidInputException, JSONException {
		super(object);
		this.location = new Location();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getIdTrip() {
		return idTrip;
	}

	public void setIdTrip(long idTrip) {
		this.idTrip = idTrip;
	}

	public long getIdRecordType() {
		return idRecordType;
	}

	public void setIdRecordType(long idRecordType) {
		this.idRecordType = idRecordType;
	}

	public long getIdUser() {
		return idUser;
	}

	public void setIdUser(long idUser) {
		this.idUser = idUser;
	}

	public Trip getTrip() {
		return trip;
	}

	public void setTrip(Trip trip) {
		this.trip = trip;
	}

	public RecordType getRecordType() {
		return recordType;
	}

	public void setRecordType(RecordType recordType) {
		this.recordType = recordType;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Date getDay() {
		return day;
	}

	public void setDay(Date day) {
		this.day = day;
	}

	public boolean setDayFromString(String date, String format) {
		DateFormat dateFormat = new SimpleDateFormat(format, Locale.US);
		try {
			this.setDay(dateFormat.parse(date));
		} catch (ParseException e) {
			return false;
		}
		return true;
	}

	public String getDayAsString(String format) {
		DateFormat dateFormat = new SimpleDateFormat(format, Locale.US);
		return dateFormat.format(this.day);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Location getLocation() {
		if (this.location == null)
			this.location = new Location();

		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public void addPhoto(Photo photo) {
		if (!this.photos.contains(photo))
			this.photos.add(photo);
	}

	public SyncStatus getSyncStatus() {
		return syncStatus;
	}

	public void setSyncStatus(SyncStatus syncStatus) {
		this.syncStatus = syncStatus;
	}

	public boolean removePhoto(Photo photo) {
		return this.photos.contains(photo) && this.photos.remove(photo);
	}

	public boolean removePhoto(String uuid) {
		for (Photo photo : this.photos) {
			if (photo.getUuid().equals(uuid))
				return this.photos.remove(photo);
		}
		return false;
	}

	public List<Photo> getPhotos() {
		return photos;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public boolean setCreatedAtFromString(String date, String format) {

		if (date == null)
			return false;

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

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public boolean setUpdatedAtFromString(String date, String format) {

		if (date == null)
			return false;

		DateFormat dateFormat = new SimpleDateFormat(format, Locale.US);
		try {
			this.setUpdatedAt(dateFormat.parse(date));
		} catch (ParseException e) {
			return false;
		}
		return true;
	}

	public String getUpdatedAtAsString(String format) {
		DateFormat dateFormat = new SimpleDateFormat(format, Locale.US);
		return dateFormat.format(this.updatedAt);
	}

	@Override
	public JSONObject toJSON(boolean detailed) {
		return null;
	}

	@Override
	public boolean parseJSON(JSONObject jsonObject) {
		try {
			this.setUuid(jsonObject.getString("id"));
			this.setRecordType(RecordTypeHelper.get(jsonObject.getString("type")));
			this.setDayFromString(jsonObject.getString("day"), "yyyy-MM-dd");

			if (jsonObject.has("description"))
				this.setDescription(jsonObject.getString("description"));

			if (jsonObject.has("coordinates")) {
				JSONObject coordinates = jsonObject.getJSONObject("coordinates");
				this.setLocation(new Location(
						coordinates.getDouble("latitude"),
						coordinates.getDouble("longitude"),
						coordinates.getInt("altitude")
				));
			}

			if (jsonObject.has("author")) {
				JSONObject author = jsonObject.getJSONObject("author");
				this.setUser(UserHelper.get(author.getString("id")));
			}

			if (jsonObject.has("photos")) {
				// TODO: fucking photos
			}

		}
		catch (JSONException e) {
			e.printStackTrace();
			return false;
		} catch (RecordNotFoundException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public String toString() {
		return null;
	}
}
