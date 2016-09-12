package com.ibm.aix;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by joe on 8/23/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ForwardPath {
    String route;
    ForwardRouteInfo routeInfo;

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public ForwardRouteInfo getRouteInfo() {
        return routeInfo;
    }

    public void setRouteInfo(ForwardRouteInfo routeInfo) {
        this.routeInfo = routeInfo;
    }
}
