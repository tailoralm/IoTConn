package com.example.iotconn.activitys;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.iotconn.R;
import com.example.iotconn.models.Action;
import com.example.iotconn.models.Device;
import com.example.iotconn.utils.ActionListAdapter;
import com.example.iotconn.utils.FirebaseUtils;
import com.example.iotconn.utils.MQTTConnector;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class DeviceActivity extends AppCompatActivity {
    private TextView tvDeviceName;
    private TextView tvDeviceStatus;
    private TextView tvDeviceHost;
    private TextView tvDeviceTopic;
    private ImageView ivDeviceImage;
    private ListView lvActions;

    MQTTConnector mqttConnector;
    private FirebaseUtils fbUtils;
    private Device device;
    private boolean subscribed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device);
        Intent i = getIntent();
        device = (Device) i.getSerializableExtra("selected_device");

        fbUtils = new FirebaseUtils();
        tvDeviceName = findViewById(R.id.tv_device_name);
        tvDeviceStatus = findViewById(R.id.tv_device_status);
        tvDeviceHost = findViewById(R.id.tv_device_host);
        tvDeviceTopic = findViewById(R.id.tv_device_topic);
        ivDeviceImage = findViewById(R.id.iv_device_image);
        lvActions = findViewById(R.id.listActions);

        getMqttConnector();

        lvActions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                doAction(device.getActions().get(position).getValue());

            }
        });

        refreshInfos();
    }

    public void doAction(String value){
        getMqttConnector().doAction(value);
        subscribeGetStatus();
    }

    public void editDevice(View v){
        Intent i = new Intent(this, CreateDeviceActivity.class);
        i.putExtra("newDevice", false);
        i.putExtra("object_device", device);
        startActivity(i);
    }

    public void createAction(View v) {
        Intent i = new Intent(this, CreateActionActivity.class);
        i.putExtra("device", device);
        startActivity(i);
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
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
        subscribeGetStatus();
    }

    private void refreshInfos() {
        tvDeviceName.setText(getString(R.string.prompt_device_name) + ": " + device.getName());
        tvDeviceStatus.setText(getString(R.string.prompt_device_status) + ": " + device.getStatus());
        tvDeviceHost.setText(getString(R.string.prompt_device_host) + ": " + device.getHostname() + ":" + device.portAsString());
        tvDeviceTopic.setText(getString(R.string.prompt_device_topic) + ": " + device.getTopic());
        ivDeviceImage.setImageResource(device.getImage());
        loadListActionView();
    }

    private void loadListActionView() {
        ActionListAdapter adapter = new ActionListAdapter(this,
                R.layout.template_listview_action,
                device.getActions());
        lvActions.setAdapter(adapter);
    }

    public void deleteDevice(View v) {
        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.prompt_device_delete))
                .setMessage(getString(R.string.prompt_really_delete))
                .setPositiveButton(getString(R.string.action_yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deleteThisDevice();
                        Intent intent = new Intent(DeviceActivity.this, MainActivity.class);
                        finish();
                        startActivity(intent);
                    }
                })
                .setNegativeButton(getString(R.string.action_no), null).show();
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
                Toast.makeText(getBaseContext(),  getString(R.string.show_error_action), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void subscribeGetStatus() {
        if (getMqttConnector().isConnected() && !subscribed) {
            try {
                getMqttConnector().getClient().subscribe("status_" + device.getTopic(), 0);
                getMqttConnector().getClient().setCallback(new MqttCallback() {
                    @Override
                    public void connectionLost(Throwable cause) {}
                    @Override
                    public void messageArrived(String topic, final MqttMessage message) throws Exception {
                        fbUtils.getMDatabase().child(fbUtils.getUserUID()).child("devices").child(device.getId()).child("status").setValue(message.toString())
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        device.setStatus(message.toString());
                                        tvDeviceStatus.setText(getString(R.string.prompt_device_status) + ": " + device.getStatus());
                                        subscribed = true;
                                    }
                                });
                    }
                    @Override
                    public void deliveryComplete(IMqttDeliveryToken token) {}
                });
            } catch (MqttException e) {
                e.printStackTrace();
            }
        }
    }

    private MQTTConnector getMqttConnector(){
        if(mqttConnector == null || !mqttConnector.isConnected()){
            mqttConnector = new MQTTConnector(device.getHostname(), device.portAsString(), device.getUsername(), device.getPassword(), device.getTopic(), this);
            subscribed = false;

            IMqttToken token = mqttConnector.connect();
            if(token != null){
                token.setActionCallback(new IMqttActionListener() {
                    @Override
                    public void onSuccess(IMqttToken asyncActionToken) {
                        subscribeGetStatus();
                    }
                    @Override
                    public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                        exception.printStackTrace();
                    }
                });
            }


        }
        return mqttConnector;
    }


}
