package com.fiit.traveldiary.app.models;

/**
 * Created by jdubec on 13/04/16.
 */
public abstract class Model {

	public abstract String toJSON();
	public abstract boolean parseJSON();

}
