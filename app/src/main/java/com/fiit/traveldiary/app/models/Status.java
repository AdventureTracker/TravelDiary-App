package com.fiit.traveldiary.app.models;

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
}
