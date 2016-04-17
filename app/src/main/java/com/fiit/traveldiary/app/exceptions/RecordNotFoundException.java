package com.fiit.traveldiary.app.exceptions;

/**
 * Created by Jakub Dubec on 17/04/16.
 */
public class RecordNotFoundException extends BaseException {
	public RecordNotFoundException() {
		super("Record not found!", null);
	}
}
