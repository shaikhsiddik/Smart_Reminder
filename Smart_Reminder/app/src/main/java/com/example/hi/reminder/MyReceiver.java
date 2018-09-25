package com.example.hi.reminder;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;


public class MyReceiver extends BroadcastReceiver {

    int day, month, year, hour, minute,id;
    DBAdapter db;

    @Override
    public void onReceive(Context context, Intent intent) {

            Bundle b = intent.getExtras();
            day = b.getInt("day");
            month = b.getInt("month");
            year = b.getInt("year");
            hour = b.getInt("hour");
            minute = b.getInt("minute");

            db = new DBAdapter(context);
            db.open();
            Cursor c = db.getAllReminders();
            if (c != null) {
                if (c.moveToFirst()) {
                    do {
                        if (c.getInt(2) == day && c.getInt(3) == month && c.getInt(4) == year && c.getInt(5) == hour && c.getInt(6) == minute) {
                            int id = c.getInt(0);
                            Intent i = new Intent(context, ShowNotification.class);

                            Bundle ex = new Bundle();
                            ex.putInt("id", id);
                            i.putExtras(ex);
                            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(i);
                            break;
                        }
                    } while (c.moveToNext());
                }
                db.close();
            }


    }

}
