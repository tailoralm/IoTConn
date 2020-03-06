package com.example.iotconn.utils;

import android.support.annotation.NonNull;

import com.example.iotconn.models.Device;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class FirebaseUtils {
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    public FirebaseUtils(){
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public boolean insertDevice(Device device){
        final boolean[] varReturn = {false};
        mDatabase.child("devices").child(mAuth.getCurrentUser().getEmail()).setValue(device)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        varReturn[0] = true;
                    }
                });
        return varReturn[0];
    }

    public ArrayList<Device> getAllDevices(){
        return new ArrayList<Device>();
    }

    public Device getDevice(){
        return null;
    }

    public void deleteDevice(){

    }
}
