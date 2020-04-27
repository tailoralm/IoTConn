package com.example.iotconn.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.iotconn.R;

import java.io.Serializable;
import java.util.UUID;

public class Device implements Serializable {

    private String id;
    private String name;
    private String status;
    private String address;
    private String port;
    private int image;

    public Device() {
        setAddress("");
        setPort("");
        setName("");
        setStatus("");
        setId(UUID.randomUUID().toString());
        image = R.drawable.device;
    }

    public Device(String id, String name, String address, String port) {
        setAddress(address);
        setPort(port);
        setName(name);
        setId(id);
        image = R.drawable.device;
    }

    public Device(String id, String name, String status, String address, String port) {
        setAddress(address);
        setPort(port);
        setName(name);
        setStatus(status);
        setId(id);
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
