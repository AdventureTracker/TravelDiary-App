package com.fiit.traveldiary.app.exceptions;

import android.util.Log;

/**
 * Created by jdubec on 13/04/16.
 */
public class InternalException extends BaseException {

	public InternalException(String detailMessage, Throwable throwable) {
		super(detailMessage, throwable);
		Log.w("TravelDiaryApp", this.getMessage());

		// TODO: some fucking logging shit

	}
}
