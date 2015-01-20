package com.trafficalarm.core.model.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.structure.BaseEntity;

/**
 * Created by webyildirim on 6/28/14.
 */
@Entity
public class RouteSchedule extends BaseEntity{
    @Id
    @GeneratedValue
    private Long id;

    private String cronExpression;
    private boolean monApplied, tueApplied, wedApplied, thuApplied, friApplied, satApplied, sunApplied;
    private String time;
    
    @ManyToOne
    private RouteGroup routeGroup;

	public RouteSchedule() {
		entityName="RouteSchedule";
	}

	public RouteGroup getRouteGroup() {
		return routeGroup;
	}

	public void setRouteGroup(RouteGroup routeGroup) {
		this.routeGroup = routeGroup;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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


	@Override
	public String toString() {
		return getCronExpression();
	}
}
