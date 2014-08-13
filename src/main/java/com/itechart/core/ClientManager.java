package com.itechart.core;

import com.itechart.core.concurrent.SingleThread;
import java.util.ArrayList;
import java.util.List;

public class ClientManager implements Client {
    private static ClientManager instance = null;

    private List<SingleThread> clients;

    private ClientManager() {
        this.clients = new ArrayList<>();
    }

    public synchronized static ClientManager getInstance() {
        return instance == null ? new ClientManager() : instance;
    }

    @Override
    public boolean add(SingleThread thread) {
        boolean isAdd = clients.add(thread);
        if (isAdd) {
            BandwidthManager.getInstance().recalculateAvgBandwidth(clients.size());
            return true;
        }
        return false;
    }

    @Override
    public boolean remove(SingleThread thread) {
        boolean isRemove = clients.remove(thread);
        if (isRemove) {
            BandwidthManager.getInstance().recalculateAvgBandwidth(clients.size());
            return true;
        }
        return false;
    }
}
