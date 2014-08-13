package com.itechart.core;

public class ClientManager implements Client {
    private static ClientManager instance = null;

    private int clients;

    public synchronized static ClientManager getInstance() {
        return instance == null ? new ClientManager() : instance;
    }

    @Override
    public boolean add() {
        clients++;
        BandwidthManager.getInstance().recalculateAvgBandwidth(clients);
        return true;
    }

    @Override
    public boolean remove() {
        clients--;
        BandwidthManager.getInstance().recalculateAvgBandwidth(clients);
        return true;
    }

    public int getClients() {
        return clients;
    }
}
