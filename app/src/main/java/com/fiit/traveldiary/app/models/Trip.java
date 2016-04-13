package com.fiit.traveldiary.app.models;

import java.util.Date;

/**
 * Created by jdubec on 13/04/16.
 */
public class Trip extends Model {

	private long id;
	private Status status;
	private Privacy privacy;
	private String uuid;
	private String name;
	private String destination;
	private String description;
	private Date startDate;
	private Date estimatedArrival;
	private Date createdAt;
	private Date updatedAt;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Privacy getPrivacy() {
		return privacy;
	}

	public void setPrivacy(Privacy privacy) {
		this.privacy = privacy;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEstimatedArrival() {
		return estimatedArrival;
	}

	public void setEstimatedArrival(Date estimatedArrival) {
		this.estimatedArrival = estimatedArrival;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	@Override
	public String toJSON() {
		return null;
	}

	@Override
	public boolean parseJSON() {
		return false;
	}
}
