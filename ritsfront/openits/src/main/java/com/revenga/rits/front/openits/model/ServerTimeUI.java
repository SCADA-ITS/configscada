package com.revenga.rits.front.openits.model;

import java.io.Serializable;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import lombok.Data;

@Data
public class ServerTimeUI implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1783450175806606694L;
	
	long epochMillis;
	long epochSecs;
	int year;
	int month;
	int day;
	int hour;
	int min;
	int sec;
	boolean serverTime;
	
	public ServerTimeUI() {
		
		setProps(Instant.now());
	}
	
	public ServerTimeUI(long epochMillis) {
		
		setProps(Instant.ofEpochMilli(epochMillis));
	}
	
	private void setProps(Instant instant) {
		
		ZonedDateTime zone = instant.atZone(ZoneOffset.UTC);
		
		this.epochMillis = instant.toEpochMilli();
		this.epochSecs = instant.getEpochSecond();
		this.year = zone.getYear();
		this.month = zone.getMonthValue();
		this.day = zone.getDayOfMonth();
		this.hour = zone.getHour();
		this.min = zone.getMinute();
		this.sec = zone.getSecond();
	}
}

