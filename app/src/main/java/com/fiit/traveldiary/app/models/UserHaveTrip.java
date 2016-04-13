package com.fiit.traveldiary.app.models;

/**
 * Created by jdubec on 13/04/16.
 */
public class UserHaveTrip {

	private User user;
	private Trip trip;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Trip getTrip() {
		return trip;
	}

	public void setTrip(Trip trip) {
		this.trip = trip;
	}
}
