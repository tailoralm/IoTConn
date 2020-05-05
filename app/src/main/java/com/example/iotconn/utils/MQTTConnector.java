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
    FirebaseUtils fbUtils;
    MqttConnectOptions options;
    String topic;

    public MQTTConnector(String mqttHost, String port, String username, String password, String topic, final Context context){
        fbUtils = new FirebaseUtils();
        String clientId = MqttClient.generateClientId();
        client = new MqttAndroidClient(context, mqttHost + ":" + port, clientId);
        this.topic = topic;
        options = new MqttConnectOptions();

        if(!username.isEmpty() && !password.isEmpty()){
            options.setUserName(username);
            options.setPassword(password.toCharArray());
        }
    }

    public IMqttToken connect(){
        try {
            return client.connect(options);
        } catch (MqttException e) {
            e.printStackTrace();
        }
        return null;
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

    public MqttAndroidClient getClient(){
        return client;
    }

    public boolean isConnected(){
        return client.isConnected();
    }


}
