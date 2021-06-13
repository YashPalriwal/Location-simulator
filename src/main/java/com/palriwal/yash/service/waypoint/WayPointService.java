package com.palriwal.yash.service.waypoint;
import com.palriwal.yash.dto.Route;

import java.util.List;

/**
 *      ORDER OF ACCURACY WITH DIFFERENT WAYPOINT CALCULATION STRATEGIES
 *
 *      StepPolyline >  OverviewPolyline  >> StepPoints
 */


public interface WayPointService {
    List<com.google.maps.model.LatLng> getWaypoints(Route route);
}
