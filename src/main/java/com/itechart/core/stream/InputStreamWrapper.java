package com.itechart.core.stream;

import com.itechart.core.BandwidthManager;
import com.itechart.core.ClientManager;

import java.io.InputStream;

/**
 * InputStreamWrapper provides functionality to work with InputStream based on client streams and bandwidth.
 */
public class InputStreamWrapper extends BandwidthInputStream {

    /**
     * Load periods of bandwidth from file config.properties
     *
     * @param inputStream
     */
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

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(super.toString());
        builder.append(", avgBandwidth=").append(getAvgBandwidth());
        builder.append(", countClientStreams=").append(ClientManager.getInstance().getClients());
        return builder.toString();
    }
}