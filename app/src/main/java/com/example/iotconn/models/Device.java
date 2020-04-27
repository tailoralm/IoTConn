package com.example.iotconn.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.iotconn.R;

public class Device implements Parcelable {

    private String name;
    private String status;
    private String address;
    private String port;
    private int image;
    private int mData;

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }

    public static final Parcelable.Creator<Device> CREATOR = new Parcelable.Creator<Device>() {
        public Device createFromParcel(Parcel in) {
            return new Device(in);
        }
        public Device[] newArray(int size) {
            return new Device[size];
        }
    };

    // example constructor that takes a Parcel and gives you an object populated with it's values
    private Device(Parcel in) {
        mData = in.readInt();
    }
}
