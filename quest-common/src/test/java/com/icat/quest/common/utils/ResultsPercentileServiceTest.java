package com.icat.quest.common.utils;

import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

public class ResultsPercentileServiceTest {

    @Test
    public void getPercentileTest(){
        Map<String, Object> percentileMap = ResultPercentileService.getPercentile("Quantitative",775f);
        Assert.assertNotEquals("Unexpected Percentile", 0.0f, percentileMap.get("percentile"));

        Map<String, Object> percentileMap1 = ResultPercentileService.getPercentile("Quantitative",550f);
        Assert.assertNotEquals("Unexpected Percentile", 0.0f, percentileMap1.get("percentile"));

        Map<String, Object> percentileMap2 = ResultPercentileService.getPercentile("Quantitative",520f);
        Assert.assertNotEquals("Unexpected Percentile", 0.0f, percentileMap2.get("percentile"));

    }
}
