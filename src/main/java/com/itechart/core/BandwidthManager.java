package com.itechart.core;

import com.itechart.core.model.Bandwidth;
import com.itechart.core.util.AppConfig;
import com.itechart.core.util.BandwidthUtil;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class BandwidthManager {
    private static BandwidthManager instance = null;

    private List<Bandwidth> bandwidths;
    private Bandwidth activeBandwidth;
    private BigDecimal avgBandwidth;

    private BandwidthManager() {
        String bandwidthPeriods = AppConfig.getInstance().getBandwidthPeriods();
        this.bandwidths = BandwidthUtil.parsePeriod(bandwidthPeriods);
    }

    public synchronized static BandwidthManager getInstance() {
        return instance == null ? new BandwidthManager() : instance;
    }

    public void recalculateAvgBandwidth(int countClients) {
        BigDecimal activeBandwidthVal = new BigDecimal(activeBandwidth.getBandwidth());
        if (countClients == 0) {
            this.avgBandwidth = activeBandwidthVal;
        } else {
            BigDecimal count = new BigDecimal(countClients);
            this.avgBandwidth = activeBandwidthVal.divide(count, 3, RoundingMode.CEILING);
        }
    }

    public BigDecimal getAvgBandwidth() {
        return avgBandwidth;
    }
}
