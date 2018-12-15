package com.planning.nacleica.AuthActions;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class WorkerSpinnerAdapter extends ArrayAdapter<Worker> {

    private Context mContext;
    private List<Worker> mValues;

    public WorkerSpinnerAdapter(Context context,
                                 int textViewResourceId, List<Worker> objects) {
        super(context, textViewResourceId, objects);
        this.mContext = context;
        this.mValues = objects;
    }

    @Override
    public int getCount(){
        return mValues.size();
    }

    @Override
    public Worker getItem(int position){
        return mValues.get(position);
    }

    @Override
    public long getItemId(int position){
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //This is for the first item before dropdown or default state.
        TextView label = new TextView(mContext);
        label.setTextColor(Color.BLACK);
        label.setTextSize(18);
        label.setText(" " + mValues.get(position).getWorkerName());
        label.setHeight(50);
        label.setGravity(Gravity.LEFT | Gravity.CENTER );
        return label;
    }

    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        //This is when user click the spinner and list of item display
        // beneath it
        TextView label = new TextView(mContext);
        label.setTextColor(Color.BLACK);
        label.setTextSize(18);
        label.setText(" " + mValues.get(position).getWorkerName());
        label.setHeight(70);
        label.setGravity(Gravity.LEFT | Gravity.CENTER );

        return label;
    }
}
