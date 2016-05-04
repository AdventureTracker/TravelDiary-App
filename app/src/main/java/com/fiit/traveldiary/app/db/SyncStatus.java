package com.fiit.traveldiary.app.db;

import java.util.InputMismatchException;

/**
 * Created by Jakub Dubec on 03/05/16.
 */
public enum SyncStatus {

	CREATED("CREATED"),
	REMOVED("REMOVED"),
	UPDATED("UPDATED"),
	SYNCED("SYNCED");

	private String value;

	SyncStatus(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return this.value;
	}

	public static SyncStatus parseString(String value) {
		if (value.equals("CREATED"))
			return SyncStatus.CREATED;
		else if (value.equals("REMOVED"))
			return SyncStatus.REMOVED;
		else if (value.equals("UPDATED"))
			return SyncStatus.UPDATED;
		else if (value.equals("SYNCED"))
			return SyncStatus.SYNCED;

		throw new InputMismatchException(String.format("Invalid input %s", value));
	}
}
