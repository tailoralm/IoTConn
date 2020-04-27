package com.example.iotconn.activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.iotconn.R;
import com.example.iotconn.models.Device;

public class DeviceActivity extends AppCompatActivity {
    TextView tvDeviceName;
    TextView tvDeviceStatus;
    ImageView ivDeviceImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device);
        Intent i = getIntent();
        Device device = i.getParcelableExtra("selected_device");

        tvDeviceName = findViewById(R.id.tv_device_name);
        tvDeviceStatus = findViewById(R.id.tv_device_status);
//        ivDeviceImage - findViewById(R.id.iv_device_image);

        tvDeviceName.setText(device.getName());
        tvDeviceStatus.setText(device.getStatus());


    }

    private void loadData(){
    }
}
