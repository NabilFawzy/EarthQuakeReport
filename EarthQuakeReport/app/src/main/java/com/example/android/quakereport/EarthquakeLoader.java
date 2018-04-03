package com.example.android.quakereport;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Nabil on 25-Mar-18.
 */
public class EarthquakeLoader extends AsyncTaskLoader<ArrayList<EarthquakeDetails>> {
    /** Tag for log messages */
    private static final String LOG_TAG = EarthquakeLoader.class.getName();

    /** Query URL */
    private String mUrl;
    public EarthquakeLoader(Context context, String url) {
        super(context);
        // TODO: Finish implementing this constructor
        mUrl=url;
    }
    @Override
    public ArrayList<EarthquakeDetails> loadInBackground() {
        Log.e(LOG_TAG, "Problem parsing the earthquake JSON results");
        // Don't perform the request if there are no URLs, or the first URL is null.
        if (mUrl== null) {
            return null;
        }
        // Perform the HTTP request for earthquake data and process the response.
        ArrayList<EarthquakeDetails> earthquakes=QueryUtils.fetchEarthquakeData(mUrl);
        return earthquakes;
    }

    @Override
    protected void onStartLoading() {
       forceLoad();
    }

}
