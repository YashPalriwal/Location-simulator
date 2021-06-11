package com.palriwal.yash.dto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Steps {
    private DistanceDTO distance;
    private DurationDTO duration;
    @JsonProperty("start_location")
    private LatLng startLocation;
    @JsonProperty("end_location")
    private LatLng endLocation;
    @JsonProperty("html_instructions")
    private String htmlInstructions;
    @JsonProperty("travel_mode")
    private String travelMode;
}
