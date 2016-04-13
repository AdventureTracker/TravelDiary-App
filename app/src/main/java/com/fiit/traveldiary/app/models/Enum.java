package com.fiit.traveldiary.app.models;

/**
 * Created by jdubec on 13/04/16.
 */
public class Enum extends Model {

	private long id;
	private String code;
	private String description;

	public long getId() {
		return id;
	}

	public String getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}

	@Override
	public String toJSON() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean parseJSON() {
		return false;
	}

	public String __toString() {
		return this.description;
	}

}
