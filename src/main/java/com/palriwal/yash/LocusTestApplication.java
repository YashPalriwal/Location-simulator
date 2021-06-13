package com.palriwal.yash;

import com.palriwal.yash.aggregator.FlagResponseAggregator;
import com.palriwal.yash.utils.InputUtils;
import com.palriwal.yash.utils.ResponseUtils;
import lombok.extern.slf4j.Slf4j;
import java.lang.String;

@Slf4j
public class LocusTestApplication{
    public static void main(String[] args){
        FlagResponseAggregator flagResponseAggregator = new FlagResponseAggregator();
        ResponseUtils.printPretty(flagResponseAggregator.getFlagCoordinateResponse(InputUtils.getInputFromConsole()));
    }









}
