package com.fiit.traveldiary.app.models;

import com.fiit.traveldiary.app.exceptions.InvalidInputException;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by jdubec on 13/04/16.
 */
public class User extends Model {

	private long id;
	private String name;
	private String email;
	private String uuid;

	public User(JSONObject object) throws InvalidInputException, JSONException {
		super(object);
	}

	public User(long id, String name, String email, String uuid) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.uuid = uuid;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	@Override
	public JSONObject toJSON(boolean detailed) {
		JSONObject object = new JSONObject();
		try {
			object.put("id", this.getUuid());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return object;
	}

	@Override
	public boolean parseJSON(JSONObject jsonObject) {
		try {
			this.setUuid(jsonObject.getString("id"));
			this.setName(jsonObject.getString("name"));
			this.setEmail(jsonObject.getString("email"));
		}
		catch (JSONException e) {
			return false;
		}
		return true;
	}

	public String toString() {
		return String.format("%s (%s)", this.name, this.email);
	}

}
