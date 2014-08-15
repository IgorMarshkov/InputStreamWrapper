package com.itechart.core;

/**
 * Class contains functionality of managing client streams.
 */
public class ClientManager {
    private static ClientManager instance = new ClientManager();

    private int clients;

    private ClientManager() {

    }

    public static ClientManager getInstance() {
        return instance;
    }

    /**
     * Add new client stream.
     */
    public void add() {
        BandwidthManager.getInstance().recalculateAvgBandwidth(++clients);
    }

    /**
     * Remove client stream.
     */
    public void remove() {
        if (clients > 0) {
            BandwidthManager.getInstance().recalculateAvgBandwidth(--clients);
        }
    }

    public int getClients() {
        return clients;
    }
}