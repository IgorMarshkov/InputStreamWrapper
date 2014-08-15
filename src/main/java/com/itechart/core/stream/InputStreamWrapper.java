package com.itechart.core.stream;

import com.itechart.core.BandwidthManager;
import com.itechart.core.ClientManager;

import java.io.InputStream;

public class InputStreamWrapper extends BandwidthInputStream {

    public InputStreamWrapper(InputStream inputStream) {
        super(inputStream);
    }

    @Override
    protected double getAvgBandwidth() {
        return BandwidthManager.getInstance().getAvgBandwidth();
    }

    @Override
    protected void addClient() {
        ClientManager.getInstance().add();
    }

    @Override
    protected void removeClient() {
        ClientManager.getInstance().remove();
    }
}
