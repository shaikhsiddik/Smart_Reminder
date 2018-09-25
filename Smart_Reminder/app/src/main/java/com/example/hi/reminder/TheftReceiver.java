package com.example.hi.reminder;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Toast;

import java.util.Calendar;


public class TheftReceiver extends BroadcastReceiver {

    LocationManager lm;
    LocationListener locationListener;
    Context c;
    String id;
    TelephonyManager tm;
    String IMEI;
    @Override
    public void onReceive(Context context, Intent intent) {


        c = context;
        resetAlarm();
        tm = (TelephonyManager)
                context.getSystemService(Context.TELEPHONY_SERVICE);

        SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(context);
        id = app_preferences.getString("simid", tm.getSimSerialNumber());
        Toast.makeText(context, "SIM card ID: " + id,
                Toast.LENGTH_LONG).show();

        String simID = tm.getSimSerialNumber();
        Toast.makeText(context, "current SIM card ID: " + simID,
                Toast.LENGTH_LONG).show();

        if (simID.equals(id)) {

            Toast.makeText(c, "same sim is inserted",
                    Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(c, "diff. sim is inserted",
                    Toast.LENGTH_LONG).show();
             IMEI = tm.getDeviceId();
            if (IMEI != null)
                Toast.makeText(c, "IMEI number: " + IMEI,
                        Toast.LENGTH_LONG).show();
            else if (IMEI == null)



                Toast.makeText(c, "not found",
                        Toast.LENGTH_LONG).show();

            lm = (LocationManager)
                    c.getSystemService(Context.LOCATION_SERVICE);
            locationListener = new MyLocationListener();
            if (ActivityCompat.checkSelfPermission(c, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(c, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            lm.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    5000,
                    3,
                    locationListener);
        }

    }

    private class MyLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {

            if (location != null) {
                Toast.makeText(c,
                        "Location changed : Lat: " + location.getLatitude() +
                                " Lng: " + location.getLongitude(),
                        Toast.LENGTH_SHORT).show();
                SmsManager sms = SmsManager.getDefault();
                sms.sendTextMessage("8888545738", null, "check: " + location.getLatitude() + ", " + location.getLongitude()+", IMEI: "+IMEI,
                        null, null);
            }
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

            Toast.makeText(c, " Thanks! ", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onProviderDisabled(String s) {

            Toast.makeText(c, " Please Enable the Location to use this App", Toast.LENGTH_SHORT).show();

        }
    }


    private void resetAlarm() {
        SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(c);
        int pi      = app_preferences.getInt("pending", 500);
        DBAdapter db = new DBAdapter(c);
        db.open();
        Cursor cursor = db.getAllReminders();
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    int d=cursor.getInt(2);
                    int mnth=cursor.getInt(3);
                    int yr=cursor.getInt(4);
                    int hr=cursor.getInt(5);
                    int min=cursor.getInt(6);


                    Intent intent = new Intent( c, MyReceiver.class);
                    Bundle b=new Bundle();
                    b.putInt("day",d);
                    b.putInt("month",mnth);
                    b.putInt("year",yr);
                    b.putInt("hour",hr);
                    b.putInt("minute",min);

                    intent.putExtras(b);

                    PendingIntent pendingIntent = PendingIntent.getBroadcast(
                            c, pi, intent, PendingIntent.FLAG_UPDATE_CURRENT );

                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(1000);
                    calendar.set(Calendar.HOUR_OF_DAY,hr);
                    calendar.set(Calendar.MINUTE,min);
                    calendar.set(Calendar.YEAR,yr);
                    calendar.set(Calendar.MONTH,mnth);
                    calendar.set(Calendar.DAY_OF_MONTH,d);

                    AlarmManager alarmManager = (AlarmManager)c. getSystemService(c.ALARM_SERVICE);
                    alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);


                    SharedPreferences.Editor editor = app_preferences.edit();
                    editor.putInt("pending",pi+1);
                    editor.commit();

                } while (cursor.moveToNext());
            }
            db.close();
        }

        Toast.makeText(c,"alarm reset",Toast.LENGTH_LONG).show();

    }
}
