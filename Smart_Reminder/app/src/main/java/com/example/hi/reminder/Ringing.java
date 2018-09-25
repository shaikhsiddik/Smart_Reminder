package com.example.hi.reminder;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class Ringing extends AppCompatActivity {

    RadioGroup rg;
    RadioButton r1,r2,r3;
    int tag=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ringing);

        rg= (RadioGroup) findViewById(R.id.rgb);
        r1= (RadioButton) findViewById(R.id.ring);
        r2= (RadioButton) findViewById(R.id.vib);
        r3= (RadioButton) findViewById(R.id.both);

        SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(this);
        int check    = app_preferences.getInt("settingstag", 1);

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
                    case R.id.ring:
                        tag=1;
                        break;
                    case R.id.vib:
                        tag=2;
                        break;
                    case R.id.both:
                        tag=3;
                        break;
                }

            }
        });

    }

    public void savebeh(View v)
    {

        SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = app_preferences.edit();
        editor.putInt("settingstag",tag);
        editor.commit();
        Toast.makeText(Ringing.this,"Ringing Behaviour Set",Toast.LENGTH_LONG).show();
        Intent i=new Intent(Ringing.this,MainActivity.class);
        startActivity(i);
        finish();
    }
}
