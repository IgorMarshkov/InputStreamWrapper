package com.itechart.core.util;

import com.itechart.core.model.Bandwidth;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.*;

public class BandwidthUtil {

    public static List<Bandwidth> parsePeriod(String str) {
        List<Bandwidth> list = new ArrayList<>();

        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("hh:mma");
        StringTokenizer st = new StringTokenizer(str, "|");
        while (st.hasMoreTokens()) {
            String periodStr = st.nextToken();
            int timeSepInd = periodStr.indexOf("-");
            int bandwidthSepInd = periodStr.indexOf("=");

            Bandwidth bandwidth = new Bandwidth();
            String fromTimeStr = periodStr.substring(0, timeSepInd);
            DateTime fromTime = dateTimeFormatter.parseDateTime(fromTimeStr);
            bandwidth.setFromTime(fromTime.toLocalTime());

            String toTimeStr = periodStr.substring(timeSepInd + 1, bandwidthSepInd);
            DateTime toTime = dateTimeFormatter.parseDateTime(toTimeStr);
            bandwidth.setToTime(toTime.toLocalTime());

            String bandwidthStr = periodStr.substring(bandwidthSepInd + 1);
            if (StringUtils.isEmpty(bandwidthStr)) {
                bandwidth.setBandwidth(Double.MAX_VALUE);
            } else {
                bandwidth.setBandwidth(Double.parseDouble(bandwidthStr) * 1000); // convert kb to bytes
            }

            list.add(bandwidth);
        }

        return list;
    }
}