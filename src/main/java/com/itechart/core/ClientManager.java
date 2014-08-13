package com.itechart.core;

public class ClientManager implements Client {
    private static ClientManager instance = new ClientManager();
    private int clients;
    public static ClientManager getInstance() {
        return instance;
    }

    @Override
    public void add() {
        BandwidthManager.getInstance().recalculateAvgBandwidth(++clients);
    }

    @Override
    public void remove() {
        BandwidthManager.getInstance().recalculateAvgBandwidth(--clients);
    }

    public int getClients() {
        return clients;
    }
}