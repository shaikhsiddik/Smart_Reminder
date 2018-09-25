package com.example.hi.reminder;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddNoti extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    EditText details;
    TextView day,hour;
    Button btn;
    Spinner cat;
    String spin="";
    int hr,min,d,mnth,yr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_noti);

        cat= (Spinner) findViewById(R.id.category);
        day= (TextView) findViewById(R.id.date);
        //month= (EditText) findViewById(R.id.month);
        //year= (EditText) findViewById(R.id.year);
        hour= (TextView) findViewById(R.id.hour);
        //minute= (EditText) findViewById(R.id.minute);
        details= (EditText) findViewById(R.id.details);

       btn= (Button) findViewById(R.id.saveReminder);

        List<String> categories = new ArrayList<String>();
        categories.add("anniversary");
        categories.add("birthday");
        categories.add("meeting");
        categories.add("call");
        categories.add("alarm");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        cat.setAdapter(dataAdapter);
        cat.setOnItemSelectedListener(this);

        btn.setOnClickListener(this);
    }

    public void showDatePickerDialog(View v) {

        // Get Current Date
        final Calendar c = Calendar.getInstance();
       int mYear = c.get(Calendar.YEAR);
       int mMonth = c.get(Calendar.MONTH);
       int mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        day.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                        d=dayOfMonth;
                        mnth=monthOfYear;
                        yr=year;
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();

    }

    public void showTimePickerDialog(View v) {
        // Get Current Time
        final Calendar c = Calendar.getInstance();
        int x = c.get(Calendar.HOUR_OF_DAY);
        int y = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {

                        hour.setText(hourOfDay + ":" + minute);
                        hr=hourOfDay;
                        min=minute;
                    }
                }, x,y, false);
        timePickerDialog.show();
    }


    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.saveReminder)
        {
            String c="",de="";

            try {
                c = spin;
                de = details.getText().toString();


                DBAdapter db=new DBAdapter(this);
                db.open();
                if(db.insertReminder(c,d,mnth,yr,hr,min,de)>0)
                {
                    addNotification();
                    SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(this);
                    SharedPreferences.Editor editor = app_preferences.edit();
                    editor.putInt("isempty",1);
                    editor.commit();
                    Intent i=new Intent(AddNoti.this,MainActivity.class);
                    startActivity(i);
                    finish();
                }
                db.close();
            }catch (Exception e)
            {
                Toast.makeText(AddNoti.this,"Please enter all details",Toast.LENGTH_LONG).show();
            }


        }

    }

    private void addNotification() {

        SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(this);
        int pi      = app_preferences.getInt("pending", 1);

        Intent intent = new Intent(this, MyReceiver.class);
        Bundle b=new Bundle();
        b.putInt("day",d);
        b.putInt("month",mnth);
        b.putInt("year",yr);
        b.putInt("hour",hr);
        b.putInt("minute",min);

        intent.putExtras(b);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this.getApplicationContext(), pi, intent, PendingIntent.FLAG_UPDATE_CURRENT );

        SharedPreferences.Editor editor = app_preferences.edit();
        editor.putInt("pending",pi+1);
        editor.commit();

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(1000);
        calendar.set(Calendar.HOUR_OF_DAY,hr);
        calendar.set(Calendar.MINUTE,min);
        calendar.set(Calendar.YEAR,yr);
        calendar.set(Calendar.MONTH,mnth);
        calendar.set(Calendar.DAY_OF_MONTH,d);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

        Toast.makeText(AddNoti.this,"Reminder added..",Toast.LENGTH_LONG).show();

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {

        spin= parent.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        spin="birthday";
    }
}
