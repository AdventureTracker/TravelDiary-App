package com.fiit.traveldiary.app.exceptions;

/**
 * Created by jdubec on 13/04/16.
 */
public class InternalException extends Exception {

	public InternalException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);

		// TODO: some fucking logging shit

	}
}
