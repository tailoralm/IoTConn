package com.example.iotconn.activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.iotconn.R;
import com.example.iotconn.models.Device;
import com.example.iotconn.utils.DeviceListAdapter;
import com.example.iotconn.utils.FirebaseUtils;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Device> devicesList;
    private FirebaseUtils firebaseUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseUtils = new FirebaseUtils();
        devicesList = firebaseUtils.getAllDevices();

        ListView listView = findViewById(R.id.listView);

        devicesList.add(new Device("Lampada sala", "Ativo", "aaaaa", "asdas"));

        DeviceListAdapter adapter = new DeviceListAdapter(this,
                R.layout.template_listview_device,
                devicesList);


        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String valorTexto = devicesList.get(position).getName();
                Toast.makeText(getBaseContext(), valorTexto,Toast.LENGTH_LONG ).show();
            }
        });
    }

    public void createDevice(View v){
        Intent intent = new Intent(this, CreateDeviceActivity.class);
        finish();
        startActivity(intent);
    }

}
