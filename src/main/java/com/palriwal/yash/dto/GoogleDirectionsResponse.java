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
public class GoogleDirectionsResponse {
    @JsonProperty("geocoded_waypoints")
    private List<Object> geocodedWaypoints;
    @JsonProperty("routes")
    private List<Route> routes;
    @JsonProperty("status")
    private String status;
}
