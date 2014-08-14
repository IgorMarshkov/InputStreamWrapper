package com.itechart.core;

import com.itechart.core.model.Bandwidth;
import org.joda.time.LocalTime;
import org.testng.Assert;
import org.testng.annotations.Test;

public class BandwidthManagerTest extends Assert {

    @Test
    public void testBandwidthManager() {
        BandwidthManager bandwidthManager = BandwidthManager.getInstance();
        assertNotEquals(bandwidthManager.getActiveBandwidth(), null);
        assertEquals(bandwidthManager.getAvgBandwidth(), bandwidthManager.getActiveBandwidth().getBandwidth());

        Bandwidth bandwidth = new Bandwidth();
        bandwidth.setFromTime(new LocalTime(1, 0, 0));
        bandwidth.setToTime(new LocalTime(10, 0, 0));
        bandwidth.setBandwidth(64000);
        bandwidthManager.setActiveBandwidth(bandwidth);
        assertEquals(bandwidthManager.getActiveBandwidth() , bandwidth);
        assertEquals(bandwidthManager.getAvgBandwidth(), bandwidth.getBandwidth() / 1);

        bandwidthManager.recalculateAvgBandwidth(3);
        assertEquals(bandwidthManager.getAvgBandwidth(), bandwidth.getBandwidth() / 3);
    }
}
