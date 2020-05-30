package com.example.iotconn.utils;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.example.iotconn.R;
import com.example.iotconn.activitys.DeviceActivity;
import com.example.iotconn.activitys.MainActivity;
import com.example.iotconn.models.Device;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FirebaseUtils {
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    public FirebaseUtils(){
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    public FirebaseAuth getMAuth(){
        return mAuth;
    }

    public String getUserUID(){
        return mAuth.getUid();
    }

    public DatabaseReference getMDatabase(){
        return mDatabase;
    }

    public void saveDevice(final Context context, Device device){
        getMDatabase().child(getUserUID()).child("devices").child(device.getId()).setValue(device)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(context, context.getString(R.string.show_success),Toast.LENGTH_LONG ).show();
                    }
                }).addOnCanceledListener(new OnCanceledListener() {
                    @Override
                    public void onCanceled() {
                        Toast.makeText(context, context.getString(R.string.show_error),Toast.LENGTH_LONG ).show();
                    }
                });
    }

    public void deleteDevice(final Context context, Device device){
        getMDatabase().child(getUserUID()).child("devices").child(device.getId()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    snapshot.getRef().removeValue();
                    Intent intent = new Intent(context, MainActivity.class);
                    context.startActivity(intent);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(context,  context.getString(R.string.show_error_action), Toast.LENGTH_LONG).show();
            }
        });
    }
}
