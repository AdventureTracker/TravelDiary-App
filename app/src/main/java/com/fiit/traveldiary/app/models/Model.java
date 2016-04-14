package com.fiit.traveldiary.app.models;

import com.fiit.traveldiary.app.exceptions.InvalidInputException;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by jdubec on 13/04/16.
 */
public abstract class Model {

	public Model(JSONObject object) throws InvalidInputException, JSONException {
		this.parseJSON(object);
	}

	public Model() {
	}

	public abstract JSONObject toJSON(boolean detailed) throws JSONException;
	public abstract boolean parseJSON(JSONObject jsonObject) throws JSONException, InvalidInputException;

}
