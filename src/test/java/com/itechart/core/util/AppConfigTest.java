package com.itechart.core.util;

import com.itechart.core.model.Bandwidth;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class AppConfigTest extends Assert {

    @Test
    public void testParsePeriod() {
        AppConfig config = AppConfig.getInstance();
        String periodsStr = config.getBandwidthPeriods();
        assertNotEquals(periodsStr, "");

        List<Bandwidth> bandwidths = BandwidthUtil.parsePeriod(periodsStr);
        assertEquals(bandwidths.get(0).getFromTime(), bandwidths.get(bandwidths.size() - 1).getToTime());
    }
}
