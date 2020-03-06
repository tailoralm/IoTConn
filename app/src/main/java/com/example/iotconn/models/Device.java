package com.example.iotconn.models;

import com.example.iotconn.R;

public class Device {

    private String name;
    private String status;
    private String address;
    private String port;
    private int image;

    public Device() {
        image = R.drawable.device;
    }

    public Device(String name, String address, String port) {
        setAddress(address);
        setPort(port);
        setName(name);
        image = R.drawable.device;
    }

    public Device(String name, String status, String address, String port) {
        setAddress(address);
        setPort(port);
        setName(name);
        setStatus(status);
        image = R.drawable.device;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getName() { return name;  }

    public void setName(String name) {  this.name = name;  }

    public String getStatus() {  return status;   }

    public void setStatus(String status) {    this.status = status;   }

    public int getImage() {   return image;  }

    public void setImage(int image) {  this.image = image;   }
}
