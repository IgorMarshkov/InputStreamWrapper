package com.itechart.core.concurrent;

import com.itechart.core.BandwidthManager;
import com.itechart.core.model.Bandwidth;
import org.joda.time.Interval;
import org.joda.time.LocalTime;
import org.slf4j.LoggerFactory;

import java.util.List;

public class BandwidthThread extends Thread {
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(BandwidthThread.class);

    private List<Bandwidth> bandwidths;

    public BandwidthThread(List<Bandwidth> bandwidths) {
        this.bandwidths = bandwidths;
    }

    @Override
    public void run() {
        int index = 0;
        while (true) {
            try {
                index = setupActiveBandwidth(index);
            } catch (InterruptedException e) {
                LOGGER.error(e.getMessage());
            }
        }
    }

    private int setupActiveBandwidth(int index) throws InterruptedException {
        for (int i = 0; i < bandwidths.size(); i++) {
            if (index > 0) {
                index++;
                Bandwidth bandwidth = bandwidths.get(index);
                setupUpdatedBandwidth(bandwidth);
            } else {
                Bandwidth bandwidth = bandwidths.get(i);
                long from = bandwidth.getFromTime().toDateTimeToday().getMillis();
                long to = bandwidth.getToTime().toDateTimeToday().getMillis();
                Interval interval = new Interval(from, to);
                LocalTime localTime = new LocalTime();
                if (interval.contains(localTime.toDateTimeToday())) {
                    index = i;
                    setupUpdatedBandwidth(bandwidth);
                }
            }
        }
        return index;
    }

    private void setupUpdatedBandwidth(Bandwidth item) throws InterruptedException {
        BandwidthManager.getInstance().setActiveBandwidth(item);
        long period = item.getToTime().toDateTimeToday().getMillis() - item.getFromTime().toDateTimeToday().getMillis();
        Thread.sleep(period);
    }
}