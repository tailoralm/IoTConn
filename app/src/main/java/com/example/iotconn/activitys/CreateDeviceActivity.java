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
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class CreateDeviceActivity extends AppCompatActivity {
    private FirebaseUtils firebaseUtils;
    private Device device;
    private EditText evName;
    private EditText evHost;
    private EditText evPort;
    private EditText evTopic;
    private EditText evUsername;
    private EditText evPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_device);
        firebaseUtils = new FirebaseUtils();
        Intent i = getIntent();

        evName = (EditText) findViewById(R.id.device_name);
        evHost = (EditText) findViewById(R.id.device_hostname);
        evPort = (EditText) findViewById(R.id.device_port);
        evTopic = (EditText) findViewById(R.id.device_topic);
        evUsername = (EditText) findViewById(R.id.device_username);
        evPassword = (EditText) findViewById(R.id.device_password);

        if(i.getBooleanExtra("newDevice", true)){
            device = new Device();
        } else{
            device = (Device) i.getSerializableExtra("object_device");
            evName.setText(device.getName());
            evHost.setText(device.getHostname());
            evPort.setText(device.portAsString());
            evTopic.setText(device.getTopic());
            evUsername.setText(device.getUsername());
            evPassword.setText(device.getPassword());
        }

    }

    public void saveDevice(View v){
        device.setName(evName.getText().toString());
        device.setHostname(evHost.getText().toString());
        device.setPort(Integer.valueOf(evPort.getText().toString()));
        device.setTopic(evTopic.getText().toString());
        device.setUsername(evUsername.getText().toString());
        device.setPassword(evPassword.getText().toString());

        firebaseUtils.saveDevice(this, device);
        Intent intent = new Intent(this, MainActivity.class);
        finish();
        startActivity(intent);

    }
}
