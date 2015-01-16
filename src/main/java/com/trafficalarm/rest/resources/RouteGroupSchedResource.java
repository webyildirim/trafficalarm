package com.trafficalarm.rest.resources;

import org.springframework.hateoas.ResourceSupport;

import com.trafficalarm.core.model.entities.RouteGroupSchedule;

/**
 * Created by webyildirim on 6/27/14.
 */
public class RouteGroupSchedResource extends ResourceSupport {

    private String cronExpression;
    private boolean monApplied, tueApplied, wedApplied, thuApplied, friApplied, satApplied, sunApplied;
    private String time;

    private Long rid;

    public Long getRid() {
        return rid;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    public String getCronExpression() {
		return cronExpression;
	}

	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}

	public boolean isMonApplied() {
		return monApplied;
	}

	public void setMonApplied(boolean monApplied) {
		this.monApplied = monApplied;
	}

	public boolean isTueApplied() {
		return tueApplied;
	}

	public void setTueApplied(boolean tueApplied) {
		this.tueApplied = tueApplied;
	}

	public boolean isWedApplied() {
		return wedApplied;
	}

	public void setWedApplied(boolean wedApplied) {
		this.wedApplied = wedApplied;
	}

	public boolean isThuApplied() {
		return thuApplied;
	}

	public void setThuApplied(boolean thuApplied) {
		this.thuApplied = thuApplied;
	}

	public boolean isFriApplied() {
		return friApplied;
	}

	public void setFriApplied(boolean friApplied) {
		this.friApplied = friApplied;
	}

	public boolean isSatApplied() {
		return satApplied;
	}

	public void setSatApplied(boolean satApplied) {
		this.satApplied = satApplied;
	}

	public boolean isSunApplied() {
		return sunApplied;
	}

	public void setSunApplied(boolean sunApplied) {
		this.sunApplied = sunApplied;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public RouteGroupSchedule toRouteGroupSchedule() {
    	RouteGroupSchedule entity = new RouteGroupSchedule();
        entity.setMonApplied(monApplied);
        entity.setTueApplied(tueApplied);
        entity.setWedApplied(wedApplied);
        entity.setThuApplied(thuApplied);
        entity.setFriApplied(friApplied);
        entity.setSatApplied(satApplied);
        entity.setSunApplied(sunApplied);
        entity.setTime(time);
        entity.setCronExpression(cronExpression);
        return entity;
    }
}
