package com.itechart.core;

public class ClientManager{
    private static ClientManager instance = new ClientManager();
    private int clients;
    public static ClientManager getInstance() {
        return instance;
    }

    public void add() {
        BandwidthManager.getInstance().recalculateAvgBandwidth(++clients);
    }

    public void remove() {
        if (clients > 0) {
            BandwidthManager.getInstance().recalculateAvgBandwidth(--clients);
        }
    }

    public int getClients() {
        return clients;
    }
}