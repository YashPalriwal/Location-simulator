package com.palriwal.yash.service.waypoint;
import com.palriwal.yash.dto.Route;

import java.util.List;

public interface waypointService {
    List<com.google.maps.model.LatLng> getWaypoints(Route route);
}
