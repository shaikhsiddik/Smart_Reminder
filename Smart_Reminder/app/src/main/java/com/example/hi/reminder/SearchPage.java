package com.example.hi.reminder;

import android.app.SearchManager;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class SearchPage extends AppCompatActivity {

    ArrayList<Reminder> list;
    String query = "";
    RecyclerView recycle;
    MyCustomAdapter adapter;
    DBAdapter db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        list = new ArrayList<Reminder>();
        db = new DBAdapter(this);
        Intent i = getIntent();

        if (Intent.ACTION_SEARCH.equals(i.getAction())) {
            query = i.getStringExtra(SearchManager.QUERY);
            query = query.toLowerCase();
            getList();
        }
        if (list.size() == 0) {
            setContentView(R.layout.nodata);
        }
        else
        {
            setContentView(R.layout.activity_search_page);

            recycle = (RecyclerView) findViewById(R.id.recycleview8);
            recycle.setLayoutManager(new LinearLayoutManager(SearchPage.this));

            adapter = new MyCustomAdapter(SearchPage.this, list);
            recycle.setAdapter(adapter);

        }
    }


    private void getList() {

        db.open();
        Cursor c = db.getAllReminders();
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    if(c.getString(1).contains(query)||Integer.toString(c.getInt(2)).contains(query)||Integer.toString(c.getInt(3)).contains(query)
                            ||Integer.toString(c.getInt(4)).contains(query)||Integer.toString(c.getInt(5)).contains(query)
                            ||Integer.toString(c.getInt(6)).contains(query)||c.getString(7).contains(query))
                    {
                        Reminder ob = new Reminder();
                        ob.setId(c.getInt(0));
                        ob.setName(c.getString(1));
                        ob.setDay(c.getInt(2));
                        ob.setMonth(c.getInt(3));
                        ob.setYear(c.getInt(4));
                        ob.setHour(c.getInt(5));
                        ob.setMinute(c.getInt(6));
                        ob.setDetails(c.getString(7));

                        list.add(ob);
                    }

                } while (c.moveToNext());
            }
            db.close();
        }
    }
}
