package com.example.iotconn.activitys;

import android.content.Intent;
import android.support.annotation.NonNull;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Device> devicesList;
    private FirebaseUtils firebaseUtils;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseUtils = new FirebaseUtils();
        devicesList = new ArrayList<Device>();

        listView = findViewById(R.id.listView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                openDeviceActivity(devicesList.get(position));
//                String valorTexto = devicesList.get(position).getName();
//                Toast.makeText(getBaseContext(), valorTexto,Toast.LENGTH_LONG ).show();
            }
        });

        loadDevicesList();
        loadListView();

    }

    private void loadListView() {
        DeviceListAdapter adapter = new DeviceListAdapter(getBaseContext(),
                R.layout.template_listview_device,
                devicesList);
        listView.setAdapter(adapter);
    }

    private void loadDevicesList(){
//        devicesList.add(new Device("Lampada sala", "Ativo", "aaaaa", "asdas"));
        Query query = firebaseUtils.getMDatabase().child(firebaseUtils.getMAuth().getUid()).child("devices");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                devicesList = new ArrayList<Device>();
                for (DataSnapshot objSnapShot : dataSnapshot.getChildren()) {
                    Device d = objSnapShot.getValue(Device.class);
                    devicesList.add(d);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void refreshDevices(View v) {
        loadDevicesList();
        loadListView();
    }

    public void openDeviceActivity(Device d){
        Intent i = new Intent(this, DeviceActivity.class);
        i.putExtra("selected_device", d);
        finish();
        startActivity(i);
    }

    public void createDevice(View v){
        Intent intent = new Intent(this, CreateDeviceActivity.class);
        finish();
        startActivity(intent);
    }

}
