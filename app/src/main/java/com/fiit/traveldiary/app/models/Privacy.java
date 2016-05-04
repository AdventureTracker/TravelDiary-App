package com.fiit.traveldiary.app.models;

import org.json.JSONObject;

/**
 * Created by jdubec on 13/04/16.
 */
public class Privacy extends Enum {


	public Privacy(long id, String code, String description) {
		super(id, code, description);
	}

	public Privacy(String code) {
		super(code);
	}

	public Privacy(JSONObject jsonObject) {
		super(jsonObject);
	}
}
