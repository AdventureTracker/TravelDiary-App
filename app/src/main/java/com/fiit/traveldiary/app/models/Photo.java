package com.fiit.traveldiary.app.models;

import org.json.JSONObject;

import java.util.Date;

/**
 * Created by jdubec on 13/04/16.
 */
public class Photo extends Model {

	private long id;
	private String uuid;
	private String filename;
	private Date createdAt;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	@Override
	public JSONObject toJSON() {
		return null;
	}

	@Override
	public boolean parseJSON(JSONObject jsonObject) {
		return false;
	}
}
