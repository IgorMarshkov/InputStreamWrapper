package com.itechart.core.concurrent;

import com.itechart.core.BandwidthManager;
import com.itechart.core.ClientManager;
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
        while (ClientManager.getInstance().getClients() > 0) {
            try {
                setupActiveBandwidth();
            } catch (InterruptedException e) {
                LOGGER.error(e.getMessage());
            }
        }
    }

    private void setupActiveBandwidth() throws InterruptedException {
        for (int i = 0; i < bandwidths.size(); i++) {
            Bandwidth bandwidth = bandwidths.get(i);
            long to = bandwidth.getToTime().toDateTimeToday().getMillis();
            if (i == (bandwidths.size() - 1)) {
                to -= 1;
            }

            LocalTime currentTime = new LocalTime();
            if (currentTime.isEqual(bandwidth.getFromTime()) ||
                    (currentTime.isAfter(bandwidth.getFromTime()) && currentTime.isBefore(new LocalTime(to)))) {
                setupUpdatedBandwidth(bandwidth);
            }
        }
    }

    private void setupUpdatedBandwidth(Bandwidth item) throws InterruptedException {
       // BandwidthManager.getInstance().setActiveBandwidth(item);
        long period = item.getToTime().toDateTimeToday().getMillis() - new LocalTime().toDateTimeToday().getMillis();
        BandwidthThread.sleep(period);
    }


    //Ну почему же нельзя. В программе, которая создала поток можно вызвать myThreat.interrupt(), а в потоке ловить InterruptedException
}