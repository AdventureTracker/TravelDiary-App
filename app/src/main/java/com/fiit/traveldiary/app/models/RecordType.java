package com.fiit.traveldiary.app.models;

import org.json.JSONObject;

/**
 * Created by jdubec on 13/04/16.
 */
public class RecordType extends Enum {


	public RecordType(long id, String code, String description) {
		super(id, code, description);
	}

	public RecordType(JSONObject jsonObject) {
		super(jsonObject);
	}
}
