package com.palriwal.yash.dto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LatLng {
    private Double latitude;
    private Double longitude;

    public String getLatLngAsString(){
        StringBuilder stringBuilder = new StringBuilder();
        if(Objects.isNull(latitude) || Objects.isNull(longitude))
            return null;
        stringBuilder.append(latitude.toString()).append(",").append(longitude.toString());
        return stringBuilder.toString();
    }
}
