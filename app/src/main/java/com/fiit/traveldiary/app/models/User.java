package com.fiit.traveldiary.app.models;

import com.fiit.traveldiary.app.exceptions.InvalidInputException;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

/**
 * Created by jdubec on 13/04/16.
 */
public class User extends Model {

	private long id;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private Date lastSeen;
	private Date createdAt;
	private Date updatedAt;

	public User(JSONObject object) throws InvalidInputException, JSONException {
		super(object);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getLastSeen() {
		return lastSeen;
	}

	public void setLastSeen(Date lastSeen) {
		this.lastSeen = lastSeen;
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
	public JSONObject toJSON(boolean detailed) {
		return null;
	}

	@Override
	public boolean parseJSON(JSONObject jsonObject) {
		return false;
	}

	public String toString() {
		return String.format("%s %s (%s)", this.firstName, this.lastName, this.email);
	}

}
