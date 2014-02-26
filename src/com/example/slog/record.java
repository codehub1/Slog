package com.example.slog;

import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

public class record {


	private String type;
	private String num;
	private int duration;
	private int iconID;
	private Date instant;
	
	
	public record(String type, String num, int duration, int iconID,
			Date instant) {
		super();
		this.type = type;
		this.num = num;
		this.duration = duration;
		this.iconID = iconID;
		this.instant = instant;
	}
	
	public String toJSON(){

	    JSONObject jsonObject= new JSONObject();
	    try {
	        jsonObject.put("type", getType());
	        jsonObject.put("num", getNum());
	        jsonObject.put("duration", getDuration());
	        jsonObject.put("iconID", getIconID());
	        jsonObject.put("instant", getInstant());

	        return jsonObject.toString();
	    } catch (JSONException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	        return "";
	    }

	}
	
	public String getType() {
		return type;
	}
	public String getNum() {
		return num;
	}
	public int getDuration() {
		return duration;
	}
	public int getIconID() {
		return iconID;
	}
	public Date getInstant() {
		return instant;
	}
}