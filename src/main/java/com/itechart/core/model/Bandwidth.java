package com.itechart.core.model;

import java.util.Date;

public class Bandwidth {
    private Date fromTime;
    private Date toTime;
    private double bandwidth; // bytes/second

    public Bandwidth() {

    }

    public Bandwidth(Date fromTime, Date toTime, int bandwidth) {
        this.fromTime = fromTime;
        this.toTime = toTime;
        this.bandwidth = bandwidth;
    }

    public Date getFromTime() {
        return fromTime;
    }

    public void setFromTime(Date fromTime) {
        this.fromTime = fromTime;
    }

    public Date getToTime() {
        return toTime;
    }

    public void setToTime(Date toTime) {
        this.toTime = toTime;
    }

    public double getBandwidth() {
        return bandwidth;
    }

    public void setBandwidth(double bandwidth) {
        this.bandwidth = bandwidth;
    }
}
