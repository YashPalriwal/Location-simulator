package com.palriwal.yash.service.waypoint;
import com.palriwal.yash.dto.Route;

import java.util.List;

public interface WayPointService {
    List<com.google.maps.model.LatLng> getWaypoints(Route route);
}
