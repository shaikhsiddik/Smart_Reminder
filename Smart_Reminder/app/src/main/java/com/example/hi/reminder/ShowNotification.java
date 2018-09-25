package com.example.hi.reminder;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ShowNotification extends AppCompatActivity {

    Cursor c;
    DBAdapter db;
    TextView tvdate,tvtime,tvdetails;
    ImageView img;
    int id;
    MediaPlayer mp;
    int check=0;
    Vibrator vibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_notification);

        tvdate= (TextView) findViewById(R.id.tvdate);
        tvtime= (TextView) findViewById(R.id.tvtime);
        tvdetails= (TextView) findViewById(R.id.tvdetails);
        img= (ImageView) findViewById(R.id.image);

        db=new DBAdapter(this);
        Bundle b=getIntent().getExtras();
        id=b.getInt("id");
        display();

        SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(this);
        int tag     = app_preferences.getInt("alarmtag", 1);
         check   =app_preferences.getInt("settingstag", 1);

        if(check==1) {
            if (tag == 1)
                mp = MediaPlayer.create(this, R.raw.spd);
            else if (tag == 2)
                mp = MediaPlayer.create(this, R.raw.sam);
            else if (tag == 3)
                mp = MediaPlayer.create(this, R.raw.axe);

            mp.start();
        }
        else if(check==2)
        {
             vibrator=(Vibrator)ShowNotification.this.getSystemService(Context.VIBRATOR_SERVICE);
            vibrator.vibrate(8000);
        }

        else if(check==3)
        {
             vibrator=(Vibrator)ShowNotification.this.getSystemService(Context.VIBRATOR_SERVICE);
            vibrator.vibrate(8000);
            if (tag == 1)
                mp = MediaPlayer.create(this, R.raw.spd);
            else if (tag == 2)
                mp = MediaPlayer.create(this, R.raw.sam);
            else if (tag == 3)
                mp = MediaPlayer.create(this, R.raw.axe);

            mp.start();
        }
    }

    private void display() {
        db.open();
        c= db.getReminderId(id);
        if(c.moveToFirst())
        {

            String s=c.getInt(2)+"/"+c.getInt(3)+"/"+c.getInt(4);

            tvdate.setText(s);
            tvtime.setText(c.getInt(5)+":"+c.getInt(6));
            tvdetails.setText(c.getString(7));
            String name=c.getString(1);

            if(name.equalsIgnoreCase("birthday"))
                img.setImageResource(R.drawable.birth);
            else if(name.equalsIgnoreCase("anniversary"))
                img.setImageResource(R.drawable.ann2);
            else if(name.equalsIgnoreCase("meeting"))
                img.setImageResource(R.drawable.meet);
            else if(name.equalsIgnoreCase("alarm"))
                img.setImageResource(R.drawable.alarm);
            else if(name.equalsIgnoreCase("call"))
                img.setImageResource(R.drawable.call2);

            else
                img.setImageResource(R.drawable.books);
        }
        db.close();
    }

    public void StopNoti(View v)
    {
       if(check==1)
            mp.stop();
        else if(check==2)
           vibrator.cancel();
        else if(check==3)
       {
           vibrator.cancel();
           mp.stop();
       }
        db.open();
        if(db.deleteReminder(id))
            Toast.makeText(this,"Notification Deleted",Toast.LENGTH_LONG).show();
        db.close();
        Intent i=new Intent(this,MainActivity.class);
        startActivity(i);
        finish();
    }
}
