package com.example.iotconn.models;

import com.example.iotconn.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

public class Device implements Serializable {

    private String id;
    private String name;
    private String status;
    private String hostname;
    private int port;
    private String username;
    private String password;
    private String topic;
    private ArrayList<Action> actions;
    private int image;

    public Device() {
        setHostname("");
        setPort(0);
        setTopic("");
        setUsername("");
        setPassword("");
        setName("");
        setStatus("");
        setActions(new ArrayList<Action>());
        setId(UUID.randomUUID().toString());
        setImage(R.drawable.device);
    }

    public Device(String id, String name, String hostname, int port, String topic, String username, String password, int image) {
        setHostname(hostname);
        setPort(port);
        setTopic(topic);
        setUsername(username);
        setPassword(password);
        setName(name);
        setStatus(status);
        setId(id);
        setImage(R.drawable.device);
    }

    public Device(String id, String name, String status, String hostname, int port, String topic, String username, String password) {
        setHostname(hostname);
        setPort(port);
        setTopic(topic);
        setUsername(username);
        setPassword(password);
        setName(name);
        setStatus(status);
        setId(id);
        setImage(R.drawable.device);
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public int getPort() {
        return port;
    }

    public String portAsString() {
        return String.valueOf(port);
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getName() { return name;  }

    public void setName(String name) {  this.name = name;  }

    public String getStatus() {  return status;   }

    public void setStatus(String status) {    this.status = status;   }

    public ArrayList<Action> getActions() {
        return actions;
    }

    public void addAction(Action action) {
        this.actions.add(action);
    }

    public void setActions(ArrayList<Action> actions) {
        this.actions = actions;
    }


    public int getImage() {   return image;  }

    public void setImage(int image) {  this.image = image;   }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
