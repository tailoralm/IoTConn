package com.example.iotconn.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.iotconn.R;
import com.example.iotconn.models.Action;
import com.example.iotconn.models.Device;
import com.example.iotconn.utils.FirebaseUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class CreateActionActivity extends AppCompatActivity {

    private Device device;
    private FirebaseUtils fbUtils;
    private Action action;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_action);
        fbUtils = new FirebaseUtils();
        action = new Action();

        Intent i = getIntent();
        device = (Device) i.getSerializableExtra("device");

    }

    public void saveButton(View v){
        EditText actionName = (EditText) findViewById(R.id.actionName);
        EditText actionValue = (EditText) findViewById(R.id.actionValue);
        action.setName(actionName.getText().toString());
        action.setValue(actionValue.getText().toString());
        device.addAction(action);

        fbUtils.getMDatabase().child(fbUtils.getUserUID()).child("devices").child(device.getId()).setValue(device)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getBaseContext(),  getString(R.string.show_success),Toast.LENGTH_LONG ).show();
                        Intent i = new Intent(getBaseContext(), DeviceActivity.class);
                        startActivity(i);
                    }
                });
    }
}
