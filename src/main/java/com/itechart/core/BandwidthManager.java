package com.itechart.core;

import com.itechart.core.concurrent.BandwidthThread;
import com.itechart.core.model.Bandwidth;
import com.itechart.core.util.AppConfig;
import com.itechart.core.util.BandwidthUtil;

import java.util.List;

public class BandwidthManager {
    private static BandwidthManager instance = new BandwidthManager();

    private List<Bandwidth> bandwidths;
    private Bandwidth activeBandwidth;
    private double avgBandwidth;

    private BandwidthManager() {
        String bandwidthPeriods = AppConfig.getInstance().getBandwidthPeriods();
        this.bandwidths = BandwidthUtil.parsePeriod(bandwidthPeriods);
        this.activeBandwidth = bandwidths.get(0);
        recalculateAvgBandwidth(1);

        new BandwidthThread(bandwidths).start();
    }

    public static BandwidthManager getInstance() {
        return instance;
    }

    public void setActiveBandwidth(Bandwidth bandwidth) {
        this.activeBandwidth = bandwidth;
        recalculateAvgBandwidth(ClientManager.getInstance().getClients());
    }

    public void recalculateAvgBandwidth(int countClients) {
        if (countClients == 0) {
            this.avgBandwidth = activeBandwidth.getBandwidth();
        } else {
            this.avgBandwidth = activeBandwidth.getBandwidth() / countClients;
        }
    }

    public double getAvgBandwidth() {
        return avgBandwidth;
    }
}