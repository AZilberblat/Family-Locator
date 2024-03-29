package com.OA.mytracker;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;


public class MyServie extends Service {
     public static boolean IsRunning=false;
    DatabaseReference databaseReference;
    public  static Location location;
    TrackLocation trackLocation;
    private UserInformation uInfo = new UserInformation();
    private String numbeT;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        IsRunning=true;
        databaseReference= FirebaseDatabase.getInstance().getReference();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {

        GlobalInfo globalInfo= new GlobalInfo(this);
        globalInfo.LoadData();
               trackLocation = new TrackLocation();
            LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, trackLocation);
        for (Map.Entry m : GlobalInfo.MyTrackers.entrySet()) {

            numbeT = m.getKey().toString();
            Log.i("omer", numbeT);
        }
        databaseReference.child("Users").child(numbeT).
                child("Location").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    switch (ds.getKey()) {
                        case "LastOnlineDate":
                            uInfo.setLastOnlineDate(ds.getValue().toString());
                            break;
                        case "lag":
                            uInfo.setLat((long) 888555.0);
                            //uInfo.setLat((Long)ds.getValue());
                            break;
                        case "lon":
                            uInfo.setLon((Long) ds.getValue());
                            break;
                            default:
                            // code block
                    }
                    Double result=FlatEarthDist.distance(location.getLatitude(),uInfo.getLat(),location.getLongitude(),uInfo.getLon());
                    if(result>5)
                    {

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        databaseReference.child("Users").child(GlobalInfo.PhoneNumber).
                child("Updates").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (location==null)return;
                databaseReference.child("Users").
                        child(GlobalInfo.PhoneNumber).child("Location").child("lat")
                        .setValue( location.getLatitude());

                databaseReference.child("Users").
                        child(GlobalInfo.PhoneNumber).child("Location").child("lag")
                        .setValue( location.getLongitude());

                DateFormat df= new SimpleDateFormat("yyyy/MM/dd HH:MM:ss");
                Date date= new Date();
                databaseReference.child("Users").
                        child(GlobalInfo.PhoneNumber).child("Location").
                        child("LastOnlineDate")
                        .setValue(df.format(date).toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return START_NOT_STICKY;
    }

    public class TrackLocation implements LocationListener {


        public    boolean isRunning=false;
        public  TrackLocation(){
            isRunning=true;
            location=new Location("not defined");
            location.setLatitude(0);
            location.setLongitude(0);
        }
        @Override
        public void onLocationChanged(Location location) {
            MyServie.location=location;
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    }
}
