package com.lovewuchin.app.poweroff.adapter;

import java.io.Serializable;

public class TimedItem implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id = -1;
	private int hour;
	private int minute;
	// 是否启用
	private boolean avaliable;
	// 启用日期
	private String note;

	public TimedItem() {

	}

	public TimedItem(int hour, int minute,  boolean avaliable,
			String note) {
		super();
		id = -1;
		this.hour = hour;
		this.minute = minute;
		this.avaliable = avaliable;
		this.note= note;
	}

	public TimedItem(int id, int hour, int minute,
			boolean avaliable, String note) {
		super();
		this.id = id;
		this.hour = hour;
		this.minute = minute;
		this.avaliable = avaliable;
		this.note= note;
	}
	public TimedItem(int id,int hour,int minute){
		super();
		this.id=id;
		this.hour=hour;
		this.minute=minute;		
	}
	public TimedItem(int hour,int minute){
		super();
		this.hour=hour;
		this.minute=minute;
	}

	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getMinute() {
		return minute;
	}

	public void setMinute(int minute) {
		this.minute = minute;
	}


	public boolean isAvaliable() {
		return avaliable;
	}

	public void setAvaliable(boolean avaliable) {
		this.avaliable = avaliable;
	}

	public void setAvaliable(int avaliable) {
		if (avaliable == 1) {
			setAvaliable(true);
		} else {
			setAvaliable(false);
		}

	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
	

}
