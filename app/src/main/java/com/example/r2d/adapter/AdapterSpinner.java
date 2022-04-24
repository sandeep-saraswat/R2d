package com.example.r2d.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.r2d.R;
import com.example.r2d.utils.Const;

import java.util.ArrayList;
import java.util.HashMap;


public class AdapterSpinner extends ArrayAdapter<String> {

    ArrayList<HashMap<String, String>> list;
    Context context;

    public AdapterSpinner(Context context, int txtViewResourceId,
                          ArrayList<HashMap<String, String>> list) {
        super(context, txtViewResourceId);
        this.context = context;
        this.list = list;

    }

    @Override
    public String getItem(int index) {
        return list.get(index).get(Const.KEY_NAME);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getDropDownView(int position, View cnvtView, ViewGroup prnt) {
        return getCustomViewDropdown(position, cnvtView, prnt);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        /*View mySpinner = inflater.inflate(R.layout.layout_spinner_dropdown, parent, false);*/
         TextView textView = (TextView) View.inflate(context, R.layout.layout_spinner_dropdown, null);

        textView.setTextColor(ContextCompat.getColor(context, R.color.black));

        HashMap<String, String> events = list.get(position);

        if(events.get(Const.KEY_NAME)!=null)
            textView.setText(events.get(Const.KEY_NAME));


        return textView;
    }

    public View getCustomViewDropdown(int position, View convertView, ViewGroup parent) {


        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        TextView textView = (TextView) View.inflate(context,R.layout.layout_spinner_dropdown_view, null);

       /* TextView textView = (TextView) mySpinner
                .findViewById(R.id.txtView);*/

        HashMap<String, String> events = list.get(position);

        if(events.get(Const.KEY_NAME)!=null)
            textView.setText(events.get(Const.KEY_NAME));

        return textView;
    }

    @Override
    public int getCount() {
        return list.size();
    }
}
