package com.palriwal.yash.dto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.palriwal.yash.dto.Legs;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Route {
    private String summary;
    private List<Object> warnings;
    @JsonProperty("waypoint_order")
    private List<Object> waypointOrder;
    private List<Legs> legs;
    @JsonProperty("overview_polyline")
    private PolylineDTO overviewPolyline;
}
