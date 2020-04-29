package com.example.iotconn.activitys;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.iotconn.R;
import com.example.iotconn.models.Device;
import com.example.iotconn.utils.FirebaseUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class CreateDeviceActivity extends AppCompatActivity {
    private FirebaseUtils firebaseUtils;
    private Device device;
    private EditText evName;
    private EditText evAddress;
    private EditText evPort;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_device);
        firebaseUtils = new FirebaseUtils();
        Intent i = getIntent();

        evName = (EditText) findViewById(R.id.device_name);
        evAddress = (EditText) findViewById(R.id.actionValue);
        evPort = (EditText) findViewById(R.id.actionName);

        if(i.getBooleanExtra("newDevice", true)){
            device = new Device();
        } else{
            device = (Device) i.getSerializableExtra("object_device");
            evName.setText(device.getName());
            evAddress.setText(device.getAddress());
            evPort.setText(device.getPort());
        }

    }

    public void saveDevice(View v){
        device.setName(evName.getText().toString());
        device.setAddress(evAddress.getText().toString());
        device.setPort(evPort.getText().toString());

        firebaseUtils.getMDatabase().child(firebaseUtils.getUserUID()).child("devices").child(device.getId()).setValue(device)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getBaseContext(), "Success!",Toast.LENGTH_LONG ).show();
                        backToDevices();
                    }
                });
    }

    public void backToDevices(){
        Intent intent = new Intent(this, MainActivity.class);
        finish();
        startActivity(intent);
    }
}
