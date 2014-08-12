package com.ism.core.util;

import com.ism.core.model.Bandwidth;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class BandwidthUtil {

    // TODO add parser!!!
    public static List<Bandwidth> parsePeriod() {
        List<Bandwidth> list = new ArrayList<>();

        Bandwidth bandwidth = new Bandwidth();
        Calendar calendar = Calendar.getInstance();
        bandwidth.setFromTime(calendar.getTime());
        calendar.add(Calendar.HOUR, 10);
        bandwidth.setToTime(calendar.getTime());
        bandwidth.setBandwidth(1000);
        list.add(bandwidth);

        return list;
    }
}
