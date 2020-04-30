package com.example.iotconn.utils;

import android.content.Context;
import android.widget.Toast;

import com.example.iotconn.R;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;

public class MQTTConnector {

    MqttAndroidClient client;
    String topic;

    public MQTTConnector(String mqttHost, String port, String username, String password, String topic, final Context context){
        String clientId = MqttClient.generateClientId();
        client = new MqttAndroidClient(context, mqttHost + ":" + port, clientId);
        this.topic = topic;
        MqttConnectOptions options = new MqttConnectOptions();
        if(!username.isEmpty() && !password.isEmpty()){
            options.setUserName(username);
            options.setPassword(password.toCharArray());
        }

        try {
            IMqttToken token = client.connect(options);
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Toast.makeText(context, context.getString(R.string.show_connected),Toast.LENGTH_LONG).show();
                }
                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Toast.makeText(context, context.getString(R.string.show_not_connected),Toast.LENGTH_LONG).show();
                    exception.printStackTrace();
                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void doAction(String message){
        if(isConnected()){
            try {
                client.publish(topic, message.getBytes(),0,false);
            } catch (MqttException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isConnected(){
        return client.isConnected();
    }


}
