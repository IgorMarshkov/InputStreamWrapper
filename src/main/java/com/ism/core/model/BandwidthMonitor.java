package com.ism.core.model;

import com.ism.core.util.AppConfig;
import com.ism.core.util.BandwidthUtil;

import java.util.List;

public class BandwidthMonitor {
    private static BandwidthMonitor instance = null;

    private List<Bandwidth> bandwidths;

    private BandwidthMonitor() {
        String bandwidthPeriods = AppConfig.getInstance().getBandwidthPeriods();
        this.bandwidths = BandwidthUtil.parsePeriod(bandwidthPeriods);
    }

    public synchronized static BandwidthMonitor getInstance() {
        return instance == null ? new BandwidthMonitor() : instance;
    }

    // TODO add logic!
    // bytes/second
    public int getAvgBandwidth() {

        return 1000;
    }

}
