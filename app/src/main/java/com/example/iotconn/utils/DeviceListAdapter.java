package com.example.iotconn.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.iotconn.models.Device;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import com.example.iotconn.R;

public class DeviceListAdapter extends ArrayAdapter<Device> {
    private static  final String TAG = "DeviceListAdapter";
    private Context mContext;
    private  int mResource;

    public DeviceListAdapter ( Context context, int resource, ArrayList<Device> objects)  {
        super(context, resource, objects);
        mContext=context;
        mResource=resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView= inflater.inflate(mResource, parent, false);

        TextView tvName = (TextView) convertView.findViewById(R.id.tvName);
        TextView tvStatus = (TextView) convertView.findViewById(R.id.tvStatus);
        ImageView imgView = (ImageView) convertView.findViewById(R.id.imageView);

        tvName.setText(getItem(position).getName());
        tvStatus.setText(getItem(position).getStatus());
        imgView.setImageResource(getItem(position).getImage());
        return  convertView;
    }
}
