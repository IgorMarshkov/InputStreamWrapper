package com.itechart.core;

import com.itechart.core.model.Bandwidth;
import com.itechart.core.util.AppConfig;
import com.itechart.core.util.BandwidthUtil;
import org.joda.time.LocalTime;

import java.util.List;

public class BandwidthManager {
    private static BandwidthManager instance = new BandwidthManager();

    private List<Bandwidth> bandwidths;
    private Bandwidth activeBandwidth;
    private double avgBandwidth;

    private BandwidthManager() {
        String bandwidthPeriods = AppConfig.getInstance().getBandwidthPeriods();
        bandwidths = BandwidthUtil.parsePeriod(bandwidthPeriods);
        activeBandwidth = bandwidths.get(0);
        recalculateAvgBandwidth(1);
    }

    public static BandwidthManager getInstance() {
        return instance;
    }

    public void recalculateAvgBandwidth(int countClients) {
        if (countClients == 0) {
            avgBandwidth = activeBandwidth.getBandwidth();
        } else {
            avgBandwidth = activeBandwidth.getBandwidth() / countClients;
        }
    }

    public Bandwidth getActiveBandwidth() {
        return activeBandwidth;
    }

    public double getAvgBandwidth() {
        LocalTime currentTime = new LocalTime();
        if (currentTime.isAfter(activeBandwidth.getToTime())) {
            setActiveBandwidth(currentTime);
        }

        return avgBandwidth;
    }

    private void setActiveBandwidth(LocalTime currentTime) {
        for (int i = 0; i < bandwidths.size(); i++) {
            Bandwidth bandwidth = bandwidths.get(i);
            long to = bandwidth.getToTime().toDateTimeToday().getMillis();
            if (i == (bandwidths.size() - 1)) {
                to -= 1;
            }

            if (currentTime.isEqual(bandwidth.getFromTime()) ||
                    (currentTime.isAfter(bandwidth.getFromTime()) && currentTime.isBefore(new LocalTime(to)))) {
                bandwidth.setToTime(new LocalTime(to));
                activeBandwidth = bandwidth;
                recalculateAvgBandwidth(ClientManager.getInstance().getClients());
            }
        }
    }
}