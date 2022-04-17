package com.example.triviamania;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class customOptionList extends ArrayAdapter {
    private ArrayList<String> optionValue;
    private Activity context;
    public customOptionList(Activity context, ArrayList<String> optionValue) {
        super(context, R.layout.option_list_view, optionValue);
        this.context = context;
        this.optionValue = optionValue;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row=convertView;
        LayoutInflater inflater = context.getLayoutInflater();
        if(convertView==null)
            row = inflater.inflate(R.layout.option_list_view, null, true);
        TextView textViewCountry = row.findViewById(R.id.aOption);


        textViewCountry.setText(optionValue.get(position));
        return row;
    }
}
