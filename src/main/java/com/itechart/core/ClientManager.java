package com.itechart.core;

public class ClientManager implements Client {
    private static ClientManager instance = null;

    private int clients;

    public synchronized static ClientManager getInstance() {
        return instance == null ? new ClientManager() : instance;
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