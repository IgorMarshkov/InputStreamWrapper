package com.itechart.core.stream;

import com.itechart.core.BandwidthManager;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class InputStreamWrapperTest extends Assert {

    @Test
    public void testInputStreamWrapper() throws IOException {
        String bandwidthPeriods = "12:00am-02:23pm=|02:23pm-11:00pm=|11:00pm-12:00am=";

        try (InputStreamWrapper isw = new InputStreamWrapper(this.getClass().getResourceAsStream("/test.txt"), bandwidthPeriods)) {
            double avgBandwidth = BandwidthManager.getInstance().getAvgBandwidth();
            while ((isw.read()) != -1) {
                boolean isOk = (isw.getBytesPerSec() - avgBandwidth) < (avgBandwidth / 1000);
                assertEquals(isOk, true);
            }
        }
    }
}
