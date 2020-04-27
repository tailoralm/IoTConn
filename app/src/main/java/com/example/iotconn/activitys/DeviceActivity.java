package com.example.iotconn.activitys;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.iotconn.R;
import com.example.iotconn.models.Device;
import com.example.iotconn.utils.FirebaseUtils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DeviceActivity extends AppCompatActivity {
    TextView tvDeviceName;
    TextView tvDeviceStatus;
    ImageView ivDeviceImage;
    FirebaseUtils fbUtils;
    Device device;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device);
        Intent i = getIntent();
        device = (Device) i.getSerializableExtra("selected_device");

        fbUtils = new FirebaseUtils();
        tvDeviceName = findViewById(R.id.tv_device_name);
        tvDeviceStatus = findViewById(R.id.tv_device_status);
        ivDeviceImage = findViewById(R.id.iv_device_image);

        refreshInfos();
    }

    public void editDevice(View v){
        Intent i = new Intent(this, CreateDeviceActivity.class);
        i.putExtra("newDevice", false);
        i.putExtra("object_device", device);
        startActivity(i);
    }

    public void createAction(View v) {
    }

    public void refreshInfos(View v) {
        Query query = fbUtils.getMDatabase().child(fbUtils.getUserUID()).child("devices").child(device.getId());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    device = dataSnapshot.getValue(Device.class);
                    refreshInfos();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void refreshInfos() {
        tvDeviceName.setText(device.getName());
        tvDeviceStatus.setText(device.getStatus());
        ivDeviceImage.setImageResource(device.getImage());
    }

    public void deleteDevice(View v) {
        new AlertDialog.Builder(this)
                .setTitle("Delete Device")
                .setMessage("Do you really want to delete this device?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deleteThisDevice();
                        Intent intent = new Intent(DeviceActivity.this, MainActivity.class);
                        finish();
                        startActivity(intent);
                    }
                })
                .setNegativeButton("No", null).show();
    }

    public void deleteThisDevice() {
        fbUtils.getMDatabase().child(fbUtils.getUserUID()).child("devices").child(device.getId()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    snapshot.getRef().removeValue();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getBaseContext(), "Error", Toast.LENGTH_LONG).show();
            }
        });
    }
}
