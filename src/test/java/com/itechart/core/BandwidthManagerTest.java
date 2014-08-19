package com.itechart.core;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

public class BandwidthManagerTest extends Assert {

    @AfterClass
    public void clear() {
        ClientManager clientManager = ClientManager.getInstance();
        while (clientManager.getClients() > 0) {
            clientManager.remove();
        }
    }

    @Test
    public void testBandwidthManager() {
        BandwidthManager bandwidthManager = BandwidthManager.getInstance();
        assertNotEquals(bandwidthManager.getActiveBandwidth(), null);
        assertEquals(bandwidthManager.getAvgBandwidth(), bandwidthManager.getActiveBandwidth().getBandwidth());

        bandwidthManager.recalculateAvgBandwidth(0);
        assertEquals(bandwidthManager.getAvgBandwidth(), bandwidthManager.getActiveBandwidth().getBandwidth());

        ClientManager clientManager = ClientManager.getInstance();
        clientManager.add();
        clientManager.add();
        clientManager.add();
        assertEquals(bandwidthManager.getAvgBandwidth(), bandwidthManager.getActiveBandwidth().getBandwidth() / 3);

        bandwidthManager.recalculateAvgBandwidth(3);
        assertEquals(bandwidthManager.getAvgBandwidth(), bandwidthManager.getActiveBandwidth().getBandwidth() / 3);
    }
}
