package com.example.hi.reminder;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Change extends AppCompatActivity {

    EditText et;
    Button btngen,btnsave;
    String simID="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change);

        et= (EditText) findViewById(R.id.simet);
        btngen= (Button) findViewById(R.id.gen);
        btnsave= (Button) findViewById(R.id.savid);

        btngen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                TelephonyManager tm = (TelephonyManager)
                        getSystemService(Context.TELEPHONY_SERVICE);

                 simID = tm.getSimSerialNumber();
                if (simID != null)
                    Toast.makeText(Change.this, "SIM card ID: " + simID,
                            Toast.LENGTH_LONG).show();

                et.setText(simID);
            }
        });

        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String s=et.getText().toString();
                    SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(Change.this);
                    SharedPreferences.Editor editor = app_preferences.edit();
                    editor.putString("simid", s);
                    editor.commit();
                    Toast.makeText(Change.this,"saved",Toast.LENGTH_LONG).show();

                    Intent i=new Intent(Change.this,MainActivity.class);
                    startActivity(i);
                    finish();
                }catch (Exception e)
                {
                    Toast.makeText(Change.this,"First enter the sim id",Toast.LENGTH_LONG).show();

                }
            }
        });
    }
}
