package com.example.iotconn.activitys;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.iotconn.R;
import com.example.iotconn.models.Device;
import com.example.iotconn.utils.FirebaseUtils;

public class CreateDeviceActivity extends AppCompatActivity {
    FirebaseUtils firebaseUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_device);
        firebaseUtils = new FirebaseUtils();
    }

    public void saveDevice(View v){
        EditText evName = (EditText) v.findViewById(R.id.device_name);
        EditText evAddress = (EditText) v.findViewById(R.id.device_address);
        EditText evPort = (EditText) v.findViewById(R.id.device_port);

        Device device = new Device(evName.getText().toString(), evAddress.getText().toString(), evPort.getText().toString());

        if(firebaseUtils.insertDevice(device))
            Toast.makeText(this, "Success!", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "NOT Success!", Toast.LENGTH_SHORT).show();

    }
}
