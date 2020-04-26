package com.example.iotconn.activitys;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.iotconn.R;
import com.example.iotconn.models.Device;
import com.example.iotconn.utils.FirebaseUtils;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

public class CreateDeviceActivity extends AppCompatActivity {
    FirebaseUtils firebaseUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_device);
        firebaseUtils = new FirebaseUtils();
    }

    public void saveDevice(View v){
        EditText evName = (EditText) findViewById(R.id.device_name);
        EditText evAddress = (EditText) findViewById(R.id.device_address);
        EditText evPort = (EditText) findViewById(R.id.device_port);

        Device device = new Device(evName.getText().toString(), evAddress.getText().toString(), evPort.getText().toString());


        firebaseUtils.getMDatabase().child(firebaseUtils.getMAuth().getUid()).child("devices").child(device.getName()).setValue(device)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getBaseContext(), "Sucess!",Toast.LENGTH_LONG ).show();
                        backToDevices();
                    }
                });
    }

    public void backToDevices(View v){
        backToDevices();
    }

    public void backToDevices(){
        Intent intent = new Intent(CreateDeviceActivity.this, MainActivity.class);
        finish();
        startActivity(intent);
    }


    private void showErrorDialog(String message) {
        new AlertDialog.Builder(this)
                .setTitle("Oops")
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
