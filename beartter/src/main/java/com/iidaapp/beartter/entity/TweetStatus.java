package com.iidaapp.beartter.entity;

import java.util.Date;

import twitter4j.Place;
import twitter4j.Status;
import twitter4j.URLEntity;
import twitter4j.User;

public class TweetStatus {

	String text;
	String screenName;
	String name;
	String miniProfileImageURLHttps;
	Date createdAt;
	int favoriteCount;
	int retweetCount;
	long inReplyToUserId;
	long inReplyToStatusId;
	String inReplyToScreenName;
	Place place;
	Status retweetedStatus;
	URLEntity[] urlEntities;


	public TweetStatus(Status status) {

		User user = status.getUser();

		this.text = status.getText();
		this.screenName = user.getScreenName();
		this.name = user.getName();
		this.miniProfileImageURLHttps = user.getMiniProfileImageURLHttps();
		this.createdAt = status.getCreatedAt();
		this.favoriteCount = status.getFavoriteCount();
		this.retweetCount = status.getRetweetCount();
		this.inReplyToUserId = status.getInReplyToUserId();
		this.inReplyToStatusId = status.getInReplyToStatusId();
		this.inReplyToScreenName = status.getInReplyToScreenName();
		this.place = status.getPlace();
		this.retweetedStatus = status.getRetweetedStatus();
		this.urlEntities = status.getURLEntities();

	}


	public String getText() {
		return text;
	}


	public void setText(String text) {
		this.text = text;
	}


	public String getScreenName() {
		return screenName;
	}


	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getMiniProfileImageURLHttps() {
		return miniProfileImageURLHttps;
	}


	public void setMiniProfileImageURLHttps(String miniProfileImageURLHttps) {
		this.miniProfileImageURLHttps = miniProfileImageURLHttps;
	}


	public Date getCreatedAt() {
		return createdAt;
	}


	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}


	public int getFavoriteCount() {
		return favoriteCount;
	}


	public void setFavoriteCount(int favoriteCount) {
		this.favoriteCount = favoriteCount;
	}


	public int getRetweetCount() {
		return retweetCount;
	}


	public void setRetweetCount(int retweetCount) {
		this.retweetCount = retweetCount;
	}


	public long getInReplyToUserId() {
		return inReplyToUserId;
	}


	public void setInReplyToUserId(long inReplyToUserId) {
		this.inReplyToUserId = inReplyToUserId;
	}


	public long getInReplyToStatusId() {
		return inReplyToStatusId;
	}


	public void setInReplyToStatusId(long inReplyToStatusId) {
		this.inReplyToStatusId = inReplyToStatusId;
	}


	public String getInReplyToScreenName() {
		return inReplyToScreenName;
	}


	public void setInReplyToScreenName(String inReplyToScreenName) {
		this.inReplyToScreenName = inReplyToScreenName;
	}


	public Place getPlace() {
		return place;
	}


	public void setPlace(Place place) {
		this.place = place;
	}


	public Status getRetweetedStatus() {
		return retweetedStatus;
	}


	public void setRetweetedStatus(Status retweetedStatus) {
		this.retweetedStatus = retweetedStatus;
	}


	public URLEntity[] getUrlEntities() {
		return urlEntities;
	}


	public void setUrlEntities(URLEntity[] urlEntities) {
		this.urlEntities = urlEntities;
	}

}
