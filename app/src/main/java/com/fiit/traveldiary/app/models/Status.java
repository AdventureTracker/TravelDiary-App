package com.fiit.traveldiary.app.models;

import org.json.JSONObject;

/**
 * Created by jdubec on 13/04/16.
 */
public class Status extends Enum {


	public Status(long id, String code, String description) {
		super(id, code, description);
	}

	public Status(String code) {
		super(code);
	}

	public Status(JSONObject jsonObject) {
		super(jsonObject);
	}
}
