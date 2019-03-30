package com.example.sistemas.pagocontado;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class SpinnerAdapter extends BaseAdapter {

    List<String> values;
    Context context;

    public SpinnerAdapter(Context context, List<String> values )
    {
        this.context = context;
        this.values = values;
    }

    @Override
    public int getCount() {
        return values.size();
    }

    @Override
    public Object getItem(int position) {
        return values.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater= LayoutInflater.from(context);
        convertView = inflater.inflate(android.R.layout.simple_spinner_dropdown_item, null);
        TextView txv = (TextView)convertView.findViewById(android.R.id.text1);
        txv.setPadding(20,20,20,20);
        //Color Fondo.
        txv.setBackgroundColor(Color.parseColor("#ffffff"));
        //Color Texto.
        txv.setTextColor(Color.parseColor("#000000"));
        txv.setText(values.get(position));

        return convertView;
    }
}


