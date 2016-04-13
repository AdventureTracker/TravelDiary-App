package com.fiit.traveldiary.app.models;

import org.json.JSONObject;

/**
 * Created by jdubec on 13/04/16.
 */
public abstract class Model {

	public abstract JSONObject toJSON();
	public abstract boolean parseJSON(JSONObject jsonObject);

}
