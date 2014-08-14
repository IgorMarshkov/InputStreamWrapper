package com.itechart.core;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

public class ClientManagerTest extends Assert {

    @AfterClass
    public void clear() {
        ClientManager clientManager = ClientManager.getInstance();
        while (clientManager.getClients() > 0) {
            clientManager.remove();
        }
    }

    @Test
    public void testClientManager() {
        ClientManager clientManager = ClientManager.getInstance();
        BandwidthManager bandwidthManager = BandwidthManager.getInstance();

        // test add clients
        clientManager.add();
        clientManager.add();
        clientManager.add();
        assertEquals(clientManager.getClients(), 3);
        double expectedAvgBandwidth = bandwidthManager.getActiveBandwidth().getBandwidth() / clientManager.getClients();
        assertEquals(bandwidthManager.getAvgBandwidth(), expectedAvgBandwidth);


        // test remove clients
        clientManager.remove();
        assertEquals(clientManager.getClients(), 2);
        expectedAvgBandwidth = bandwidthManager.getActiveBandwidth().getBandwidth() / clientManager.getClients();
        assertEquals(bandwidthManager.getAvgBandwidth(), expectedAvgBandwidth);
    }
}
