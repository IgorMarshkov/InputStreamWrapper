package com.ism.core.util;

import com.ism.core.model.Bandwidth;
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
            bandwidth.setFromTime(fromTime.toDate());

            String toTimeStr = periodStr.substring(timeSepInd + 1, bandwidthSepInd);
            DateTime toTime = dateTimeFormatter.parseDateTime(toTimeStr);
            bandwidth.setToTime(toTime.toDate());

            String bandwidthStr = periodStr.substring(bandwidthSepInd + 1);
            int bandwidthVal = StringUtils.isEmpty(bandwidthStr) ? Integer.MAX_VALUE : Integer.parseInt(bandwidthStr);
            bandwidth.setBandwidth(bandwidthVal * 1000); // convert kb to bytes

            list.add(bandwidth);
        }

        return list;
    }
}
