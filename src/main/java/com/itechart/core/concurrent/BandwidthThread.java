package com.itechart.core.concurrent;

import com.itechart.core.BandwidthManager;
import com.itechart.core.model.Bandwidth;
import com.itechart.core.util.AppConfig;

import java.util.Date;
import java.util.List;

public class BandwidthThread extends Thread {

    private List<Bandwidth> bandwidths;

    public BandwidthThread(List<Bandwidth> bandwidths) {
        this.bandwidths = bandwidths;
    }

    @Override
    public void run() {
        while (true) {
            try {
                setupActiveBandwidth();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void setupActiveBandwidth() throws InterruptedException {
        Date currentTime = new Date();
        for (Bandwidth item : bandwidths) {
            if (currentTime.equals(item.getFromTime())
                    || (currentTime.after(item.getFromTime()) && currentTime.before(item.getToTime()))) {
                BandwidthManager.getInstance().setActiveBandwidth(item);

                long period = item.getToTime().getTime() - item.getFromTime().getTime();
                long periodWo5percent = (long) (period * 0.95); // We get period without 5%
                Thread.sleep(periodWo5percent);

                // transitioning between time periods should work seamlessly
                int step = AppConfig.getInstance().getBandwidthDownStep();
                long tail = period - periodWo5percent;
                while ((tail -= step) > 0) {
                    Bandwidth bandwidthDown = item;
                    int bandwidthDownVal = (int) (bandwidthDown.getBandwidth() - (item.getBandwidth() * 0.1));
                    bandwidthDown.setBandwidth(bandwidthDownVal);
                    BandwidthManager.getInstance().setActiveBandwidth(bandwidthDown);
                    Thread.sleep(step);
                }

                setupSleepTime(item);
            }
        }
    }

    private void setupSleepTime(Bandwidth item) throws InterruptedException {
        long period = item.getToTime().getTime() - item.getFromTime().getTime();

        Thread.sleep(period);
    }
}
