package com.example.iotconn.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.iotconn.R;
import com.example.iotconn.models.Action;

import java.util.ArrayList;

public class ActionListAdapter extends ArrayAdapter<Action> {
    private static  final String TAG = "ActionListAdapter";
    private Context mContext;
    private  int mResource;

    public ActionListAdapter ( Context context, int resource, ArrayList<Action> objects)  {
        super(context, resource, objects);
        mContext=context;
        mResource=resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView= inflater.inflate(mResource, parent, false);

//        Button btn = (Button) convertView.findViewById(R.id.actionBtn1);
        TextView tv = (TextView) convertView.findViewById(R.id.btnAction);

        tv.setText(" >  " + getItem(position).getName());
//        btn.setText(getItem(position).getName());

        return  convertView;
    }
}
