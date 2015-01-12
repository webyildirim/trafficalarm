package com.accmee.trafficalarm.core.models.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.accmee.structure.BaseEntity;

/**
 * Created by webyildirim on 6/28/14.
 */
@Entity
public class RouteDetail extends BaseEntity {
    @Id
    @GeneratedValue
    private Long id;

    private String coordinate;

	public RouteDetail() {
		entityName="RouteDetail";		
	}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

	public String getCoordinate() {
		return coordinate;
	}

	public void setCoordinate(String coordinate) {
		this.coordinate = coordinate;
	}

	@Override
	public String toString() {
		return coordinate;
	}
}
