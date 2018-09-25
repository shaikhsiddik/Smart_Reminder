package com.example.hi.reminder;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ShowDetail extends AppCompatActivity {

    int pos;
    int id;
    ArrayList<Reminder> list;
    Cursor c;
    DBAdapter db;
    TextView tvdate,tvtime,tvdetails;
    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_detail);

        tvdate= (TextView) findViewById(R.id.tvdate);
        tvtime= (TextView) findViewById(R.id.tvtime);
        tvdetails= (TextView) findViewById(R.id.tvdetails);
        img= (ImageView) findViewById(R.id.image);

        db=new DBAdapter(this);
        list=new ArrayList<Reminder>();
        Bundle b=getIntent().getExtras();
        pos=b.getInt("pos");
        list=b.getParcelableArrayList("list");

        Reminder re=list.get(pos);
        id=re.getId();

        display();
    }

    private void display() {
        db.open();
        c= db.getReminderId(id);
        Toast.makeText(this,"id="+id,Toast.LENGTH_LONG).show();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.insidemenu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int i = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (i == R.id.update) {

            Intent intent = new Intent( this, MyReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(
                    this, id, intent, PendingIntent.FLAG_UPDATE_CURRENT );
            AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
            alarmManager.cancel(pendingIntent);

            Intent d=new Intent(this,UpdateNoti.class);
            Bundle b=new Bundle();
            b.putInt("id",id);
            d.putExtras(b);
            startActivity(d);
        }
        if(i==R.id.delete)
        {
            db.open();
            if(db.deleteReminder(id))
                Toast.makeText(this,"Notification Deleted",Toast.LENGTH_LONG).show();
            db.close();
            Intent d=new Intent(this,MainActivity.class);
            startActivity(d);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

}
