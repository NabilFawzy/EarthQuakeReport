/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.quakereport;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Intent;
import android.content.Loader;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;


public class EarthquakeActivity extends AppCompatActivity implements LoaderCallbacks<ArrayList<EarthquakeDetails>> {
    EarthquakeAdapter adapter;
    public static final String LOG_TAG = EarthquakeActivity.class.getName();
ArrayList<EarthquakeDetails>Tearthquakes=new ArrayList<EarthquakeDetails>();
    private static final int EARTHQUAKE_LOADER_ID = 1;
    private  static String USGS_REQUEST_URL1 =
            "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&orderby=time&minmag=4&limit=20";
 private  static String USGS_REQUEST_URL2 =
         "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=6&limit=10";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);


       /* TextView output = (TextView) findViewById(R.id.txt1);

        String s="{}";


        String strJson="{\"Employee\" :[ {\"id\":\"01\",\"name\":\"Gopal Varma\",\"salary\":\"500000\"}, {\"id\":\"02\",\"name\":\"Sairamkrishna\",\"salary\":\"500000\"}, {\"id\":\"03\",\"name\":\"Sathish kallakuri\",\"salary\":\"600000\"} ]} ";
        String data = "";
        try {
            JSONObject jsonRootObject = new JSONObject(strJson);

            //Get the instance of JSONArray that contains JSONObjects
            JSONArray jsonArray = jsonRootObject.optJSONArray("Employee");

            //Iterate the jsonArray and print the info of JSONObjects
            for(int i=0; i < jsonArray.length(); i++){
                JSONObject JSONObject = jsonArray.getJSONObject(i);

                int id = Integer.parseInt(JSONObject.optString("id").toString());
                String name = JSONObject.optString("name").toString();
                float salary = Float.parseFloat(JSONObject.optString("salary").toString());

                data += "Node"+i+" : \n id= "+ id +" \n Name= "+ name +" \n Salary= "+ salary +" \n";
            }
            output.setText(data);
        } catch (JSONException e ) { e.printStackTrace();}
        */

        // Create a fake list of earthquake locations.
        //ArrayList<String> earthquakes = new ArrayList<>();
      /*  earthquakes.add("San Francisco");
        earthquakes.add("London");
        earthquakes.add("Tokyo");
        earthquakes.add("Mexico City");
        earthquakes.add("Moscow");
        earthquakes.add("Rio de Janeiro");
        earthquakes.add("Paris");

        // Find a reference to the {@link ListView} in the layout

        EarthquakeDetails earth;
       for(int i=0;i<earthquakes.size();i++){
           earth=new EarthquakeDetails();
           earth.place=earthquakes.get(i);
           earth.date="22/10/1995";
           earth.magnitude=i+1;
           Tearthquakes.add(earth);
       }
       */
        EarthquakeAsyncTask earthquakeAsyncTask=new EarthquakeAsyncTask();
        earthquakeAsyncTask.execute(USGS_REQUEST_URL1);
        // Create a new {@link ArrayAdapter} of earthquakes

        /** URL to query the USGS dataset for earthquake information */
        // Get a reference to the LoaderManager, in order to interact with loaders.
        adapter=new EarthquakeAdapter(getApplicationContext(),R.layout.row,new ArrayList<EarthquakeDetails>());
        LoaderManager loaderManager = getLoaderManager();

        // Initialize the loader. Pass in the int ID constant defined above and pass in null for
        // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
        // because this activity implements the LoaderCallbacks interface).
        loaderManager.initLoader(EARTHQUAKE_LOADER_ID, null, this);



    }

    @Override
    public Loader<ArrayList<EarthquakeDetails>> onCreateLoader(int id, Bundle args) {
        Log.e(LOG_TAG, "Problem parsing the earthquake JSON results");
        return new EarthquakeLoader(this, USGS_REQUEST_URL1);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<EarthquakeDetails>> loader, ArrayList<EarthquakeDetails> earthquakes) {
        Log.e(LOG_TAG, "Problem parsing the earthquake JSON results");
        // Clear the adapter of previous earthquake data
        adapter.clear();
        // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (earthquakes != null && !earthquakes.isEmpty()) {
            adapter.addAll(earthquakes);
        }
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<EarthquakeDetails>> loader) {
        adapter.clear();
    }

    private class EarthquakeAsyncTask extends AsyncTask<String,Void,ArrayList<EarthquakeDetails>> {

        @Override
        protected ArrayList<EarthquakeDetails> doInBackground(String... urls) {
            Log.e(LOG_TAG, "Problem parsing the earthquake JSON results");
            // Don't perform the request if there are no URLs, or the first URL is null.
            if (urls.length < 1 || urls[0] == null) {
                return null;
            }
            // Perform the HTTP request for earthquake data and process the response.
            ArrayList<EarthquakeDetails> earthquakes=QueryUtils.fetchEarthquakeData(urls[0]);
            return earthquakes;
        }


        @Override
        protected void onPostExecute(ArrayList<EarthquakeDetails> result) {
            // If there is no result, do nothing.
            if (result == null) {
                return;
            }
            updateUi(result);

        }
        private void updateUi(final ArrayList<EarthquakeDetails> earthquakes) {
            ListView earthquakeListView = (ListView) findViewById(R.id.list);
            final ArrayList<EarthquakeDetails>earth=new ArrayList<EarthquakeDetails>( earthquakes);
            EarthquakeAdapter adapter=new EarthquakeAdapter(getApplicationContext(),R.layout.row,earthquakes);

            // Set the adapter on the {@link ListView}
            // so the list can be populated in the user interface
            earthquakeListView.setAdapter(adapter);
            earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    EarthquakeDetails Quake=  earth.get(position);
                    String url= Quake.url;
                    Intent i=new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);

                }
            });
        }
    }
}