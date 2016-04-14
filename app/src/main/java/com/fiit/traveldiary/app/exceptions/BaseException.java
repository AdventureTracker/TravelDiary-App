package com.fiit.traveldiary.app.exceptions;

import android.util.Log;

/**
 * Created by Jakub Dubec on 14/04/16.
 */
public class BaseException extends Exception {

	public BaseException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);

		// TODO: Database error logger

		Log.e("TravelDiaryErr", this.getMessage());


	}
}
