package com.example.android.quakereport;

/**
 * Created by Nabil on 23-Mar-18.
 */

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;


public class EarthquakeAdapter extends ArrayAdapter<EarthquakeDetails> {
    Context mContext;
    Integer mLayoutID;
    ArrayList<EarthquakeDetails> Earthquakes;

    public EarthquakeAdapter(Context context,int resource, ArrayList<EarthquakeDetails> earthquakes) {
        super(context, resource, earthquakes);
        this.mContext = context;
        this.mLayoutID = resource;
        this.Earthquakes = earthquakes;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row=convertView;

        PlaceHolder  placeHolder=null;

        if(row==null)
        {
            LayoutInflater inflater=LayoutInflater.from(mContext);
            row=inflater.inflate(mLayoutID,parent,false);
            placeHolder=new PlaceHolder();
            placeHolder.magIDView=(TextView)row.findViewById(R.id.magID);
            placeHolder.place1View=(TextView)row.findViewById(R.id.place1);
            placeHolder.place2View=(TextView)row.findViewById(R.id.place2);
            placeHolder.DateView=(TextView)row.findViewById(R.id.date);
            placeHolder.TimeView=(TextView)row.findViewById(R.id.time);
            row.setTag(placeHolder);
        }
        else{
            placeHolder=(PlaceHolder) row.getTag();
        }

        EarthquakeDetails earthquake=Earthquakes.get(position);
       // Integer rowPostion=position;
        placeHolder.magIDView.setText(String.valueOf(earthquake.formatMag( earthquake.magnitude)));
        if(!earthquake.place.contains("of")){
            placeHolder.place1View.setText("Near the");
            placeHolder.place2View.setText(earthquake.place);

        }
        else{
            placeHolder.place1View.setText(earthquake.place.substring(0,earthquake.place.indexOf('f')+1));
            placeHolder.place2View.setText(earthquake.place.substring(earthquake.place.indexOf('f')+1,earthquake.place.length()-1));
        }
        Date dateObject=new Date(earthquake.dateandtimeinmilli);
        placeHolder.DateView.setText(earthquake.formatDate(dateObject));
        placeHolder.TimeView.setText(earthquake.formatTime(dateObject));
       // int res=mContext.getResources().getIdentifier(earthquake,"drawable",mContext.getPackageName());
      //  placeHolder.imageView.setImageResource(res);

        // Set the proper background color on the magnitude circle.
        // Fetch the background from the TextView, which is a GradientDrawable.
        GradientDrawable magnitudeCircle = (GradientDrawable)  placeHolder.magIDView.getBackground();
        // Get the appropriate background color based on the current earthquake magnitude
        int magnitudeColor = getMagnitudeColor(earthquake.getMagnitude());
        // Set the color on the magnitude circle
        magnitudeCircle.setColor(magnitudeColor);
        return row;
    }
    private int getMagnitudeColor(double magnitude){
        int magID;
        int magnitudeFloor = (int) Math.floor(magnitude);
        switch (magnitudeFloor){
            case 0:
            case 1:
                magID=R.color.magnitude1;
                break;
            case 2:
                magID=R.color.magnitude2;
                break;
            case 3:
                magID=R.color.magnitude3;
                break;
            case 4:
                magID=R.color.magnitude4;
                break;
            case 5:
                magID=R.color.magnitude5;
                break;
            case 6:
                magID=R.color.magnitude6;
                break;
            case 7:
                magID=R.color.magnitude7;
                break;
            case 8:
                magID=R.color.magnitude8;
                break;
            case 9:
                magID=R.color.magnitude9;
                break;
            default:
                magID=R.color.magnitude10plus;
        }
        return ContextCompat.getColor(getContext(), magID);
    }
    private static class PlaceHolder{
        TextView magIDView;
        TextView DateView;
        TextView place1View;
        TextView place2View;
        TextView TimeView;

    };
    @Override
    public Filter getFilter() {
        return super.getFilter();
    }

}
