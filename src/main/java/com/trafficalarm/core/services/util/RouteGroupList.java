package com.trafficalarm.core.services.util;

import java.util.ArrayList;
import java.util.List;

import com.trafficalarm.core.model.entities.RouteGroup;

/**
 * Created by webyildirim on 7/1/14.
 */
public class RouteGroupList {

    private List<RouteGroup> routeGroups = new ArrayList<RouteGroup>();

    public RouteGroupList(List<RouteGroup> resultList) {
        this.routeGroups = resultList;
    }

    public List<RouteGroup> getBlogs() {
        return routeGroups;
    }

    public void setBlogs(List<RouteGroup> blogs) {
        this.routeGroups = blogs;
    }
}
