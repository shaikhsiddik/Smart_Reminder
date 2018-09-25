package com.example.hi.reminder;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class AlarmTone extends AppCompatActivity {

    RadioGroup rg;
    static MediaPlayer mp;
    int tag=0;
    RadioButton r1,r2,r3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_tone);

        rg= (RadioGroup) findViewById(R.id.rg);
        mp=MediaPlayer.create(AlarmTone.this,R.raw.spd);
        r1= (RadioButton) findViewById(R.id.rspd);
        r2= (RadioButton) findViewById(R.id.rsam);
        r3= (RadioButton) findViewById(R.id.raxe);


        SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(this);
        int check    = app_preferences.getInt("alarmtag", 1);

        if(check==1)
            r1.setChecked(true);
        else if(check==2)
            r2.setChecked(true);
        else if(check==3)
            r3.setChecked(true);

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                switch(i)
                {
                    case  R.id.rspd:
                        if(mp.isPlaying())
                        {
                            mp.stop();
                        }
                        mp=MediaPlayer.create(AlarmTone.this,R.raw.spd);
                        mp.start();
                        tag=1;
                        break;

                    case  R.id.rsam:
                        if(mp.isPlaying())
                        {
                            mp.stop();
                        }
                        mp=MediaPlayer.create(AlarmTone.this,R.raw.sam);
                        mp.start();
                        tag=2;
                        break;

                    case  R.id.raxe:
                        if(mp.isPlaying())
                        {
                            mp.stop();
                        }
                        mp=MediaPlayer.create(AlarmTone.this,R.raw.axe);
                        mp.start();
                        tag=3;
                        break;
                }
            }
        });
    }

    public void save(View v)
    {
        if(mp.isPlaying())
            mp.stop();
        SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = app_preferences.edit();
        editor.putInt("alarmtag",tag);
        editor.commit();
        Toast.makeText(AlarmTone.this,"Tune Set",Toast.LENGTH_LONG).show();
        Intent i=new Intent(AlarmTone.this,MainActivity.class);
        startActivity(i);
        finish();
    }
}
