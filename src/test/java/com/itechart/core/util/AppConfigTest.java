package com.itechart.core.util;

import org.testng.Assert;
import org.testng.annotations.Test;

public class AppConfigTest extends Assert {

    @Test
    public void testParsePeriod() {
        AppConfig config = AppConfig.getInstance();
        assertNotEquals(config.getBandwidthPeriods(), "");
    }
}
