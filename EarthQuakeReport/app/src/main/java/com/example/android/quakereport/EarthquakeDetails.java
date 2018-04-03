package com.example.android.quakereport;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Nabil on 23-Mar-18.
 */
public class EarthquakeDetails {
    public double magnitude;
    public String place;
    public long dateandtimeinmilli;
    public String url;
    EarthquakeDetails( double magnitude,String place,long date,String url){
        this.magnitude=magnitude;
        this.dateandtimeinmilli=date;
        this.place=place;
        this.url=url;

    }
    public double getMagnitude(){return  magnitude;};
    /**
     * Return the formatted date string (i.e. "Mar 3, 1984") from a Date object.
     */
    public String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
        return dateFormat.format(dateObject);
    }

    /**
     * Return the formatted date string (i.e. "4:30 PM") from a Date object.
     */
    public String formatTime(Date dateObject) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateObject);
    }

    public String formatMag(double mag) {
        DecimalFormat formatter = new DecimalFormat("0.0");
        String output = formatter.format(mag);
        return  output;
    }
}
