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

    /**
     * Init period of bandwidth.
     * Please use following format of bandwidth string,
     * 12:00am-02:23pm=100|02:23pm-11:00pm=100|11:00pm-12:00am=
     * - time format - i.e. 08:30am;
     * - begin time (12:00am) equals end time (12:00am);
     * - bandwidth you can set after symbol '=' and bandwidth uses kb/s;
     * - you can add any count of time periods - please use separator '|'
     * - if bandwidth unlimited than use empty value.
     *
     * @param bandwidthPeriods is periods of bandwidth.
     */
    public void initBandwidthPeriods(String bandwidthPeriods) {
        BandwidthManager.getInstance().init(bandwidthPeriods);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(super.toString());
        builder.append(", avgBandwidth=").append(getAvgBandwidth());
        builder.append(", countClientStreams=").append(ClientManager.getInstance().getClients());
        return builder.toString();
    }
}