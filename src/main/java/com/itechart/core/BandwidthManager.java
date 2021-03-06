package com.itechart.core;

import com.itechart.core.model.Bandwidth;
import com.itechart.core.util.AppConfig;
import com.itechart.core.util.BandwidthUtil;
import org.joda.time.LocalTime;

import java.util.List;

/**
 * Class contains functionality of managing bandwidth.
 */
public class BandwidthManager {
    private static BandwidthManager instance = new BandwidthManager();

    private List<Bandwidth> bandwidths;
    private Bandwidth activeBandwidth;
    private double avgBandwidth;

    private BandwidthManager() {
        init(AppConfig.getInstance().getBandwidthPeriods());
    }

    public static BandwidthManager getInstance() {
        return instance;
    }

    /**
     * Init bandwidth by time.
     * By default use values from file config.properties
     * <p/>
     * Please use following format of bandwidth string,
     * 12:00am-02:23pm=100|02:23pm-11:00pm=100|11:00pm-12:00am=
     * - time format - i.e. 08:30am;
     * - begin time (12:00am) equals end time (12:00am);
     * - bandwidth you can set after symbol '=' and bandwidth uses kb/s;
     * - you can add any count of time periods - please use separator '|'
     * - if bandwidth unlimited than use empty value.
     *
     * @param bandwidthPeriods is bandwidth as string.
     */
    public synchronized void init(String bandwidthPeriods) {
        bandwidths = BandwidthUtil.parsePeriod(bandwidthPeriods);
        BandwidthUtil.fixLastBorder(bandwidths);
        setActiveBandwidth();
        recalculateAvgBandwidth(1);
    }

    /**
     * Recalculate average bandwidth by one client stream.
     *
     * @param countClients is count client streams.
     */
    public synchronized void recalculateAvgBandwidth(int countClients) {
        if (countClients == 0) {
            avgBandwidth = activeBandwidth.getBandwidth();
        } else {
            avgBandwidth = activeBandwidth.getBandwidth() / countClients;
        }
    }

    public Bandwidth getActiveBandwidth() {
        return activeBandwidth;
    }

    /**
     * Get average bandwidth by one client stream.
     *
     * @return average bandwidth by one client stream.
     */
    public synchronized double getAvgBandwidth() {
        if (isValidPeriod(activeBandwidth)) {
            setActiveBandwidth();
        }

        return avgBandwidth;
    }

    /**
     * Load active bandwidth period.
     * Method uses when we change bandwidth period.
     */
    private void setActiveBandwidth() {
        for (int i = 0; i < bandwidths.size(); i++) {
            Bandwidth bandwidth = bandwidths.get(i);
            if (isValidPeriod(bandwidth)) {
                activeBandwidth = bandwidth;
                recalculateAvgBandwidth(ClientManager.getInstance().getClients());
            }
        }
    }

    private boolean isValidPeriod(Bandwidth bandwidth) {
        LocalTime currentTime = new LocalTime();
        if ((currentTime.isAfter(bandwidth.getFromTime()) && currentTime.isBefore(bandwidth.getToTime())) ||
                currentTime.isEqual(bandwidth.getFromTime())) {
            return true;
        }
        return false;
    }
}