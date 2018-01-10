package com.picsou.tivp;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Trip {

    private int id;
    private int reason;

    private long out_date;
    private int out_station;
    private int out_bikestand;

    private long in_date;
    private int in_station;
    private int in_bikestand;

    private boolean activeUser;

    public Trip(int id, int reason, long out_date, int out_station, int out_bikestand, long in_date, int in_station, int in_bikestand) {
        this.id = id;
        this.reason = reason;
        this.out_date = out_date;
        this.out_station = out_station;
        this.out_bikestand = out_bikestand;
        this.in_date = in_date;
        this.in_station = in_station;
        this.in_bikestand = in_bikestand;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getReason() {
        return reason;
    }

    public void setReason(int reason) {
        this.reason = reason;
    }

    public long getOut_date() {
        return out_date;
    }

    public void setOut_date(long out_date) {
        this.out_date = out_date;
    }

    public int getOut_station() {
        return out_station;
    }

    public void setOut_station(int out_station) {
        this.out_station = out_station;
    }

    public int getOut_bikestand() {
        return out_bikestand;
    }

    public void setOut_bikestand(int out_bikestand) {
        this.out_bikestand = out_bikestand;
    }

    public long getIn_date() {
        return in_date;
    }

    public void setIn_date(long in_date) {
        this.in_date = in_date;
    }

    public int getIn_station() {
        return in_station;
    }

    public void setIn_station(int in_station) {
        this.in_station = in_station;
    }

    public int getIn_bikestand() {
        return in_bikestand;
    }

    public void setIn_bikestand(int in_bikestand) {
        this.in_bikestand = in_bikestand;
    }

    public boolean isActiveUser() {
        return activeUser;
    }

    public void setActiveUser(boolean activeUser) {
        this.activeUser = activeUser;
    }

    public void display(SimpleDateFormat dateFormat) {

        long duration = (in_date - out_date) / 1000;

        long hours = duration / 3600;
        long minutes = (duration % 3600) / 60;
        long seconds = (duration % 3600) % 60;

        System.out.println(
                "Trip n°" + id + ", " +
                        "start at " + dateFormat.format(out_date) + " and last " + hours + " hours, " + minutes + " minutes and " + seconds + " seconds. " +
                        "arrives at " + dateFormat.format(in_date) +
                        " From station " + out_station + " (" + out_bikestand + ") to " + in_station + " (" + in_bikestand + ")"
        );
    }



}
