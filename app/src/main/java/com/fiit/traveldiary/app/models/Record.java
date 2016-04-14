package com.fiit.traveldiary.app.models;

import org.json.JSONObject;

import java.util.Date;
import java.util.List;

/**
 * Created by jdubec on 13/04/16.
 */
public class Record extends Model {

	private long id;
	private Trip trip;
	private RecordType recordType;
	private User user;
	private String uuid;
	private Date day;
	private String description;
	private Location location;
	private Date createdAt;
	private Date updatedAt;

	private List<Photo> photos;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public void addPhoto(Photo photo) {
		if (!this.photos.contains(photo))
			this.photos.add(photo);
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

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	@Override
	public JSONObject toJSON() {
		return null;
	}

	@Override
	public boolean parseJSON(JSONObject jsonObject) {
		return false;
	}

	public String toString() {
		return null;
	}
}
