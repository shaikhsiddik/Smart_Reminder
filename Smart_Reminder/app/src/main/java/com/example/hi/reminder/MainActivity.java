package com.example.hi.reminder;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    SearchView searchView;
    MenuItem searchMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
      // DBAdapter db=new DBAdapter(this);

        All all=new All();
        FragmentManager manager=getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.mainscreen,all,all.getTag()).commit();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            Intent i=new Intent(MainActivity.this,AddNoti.class);
                startActivity(i);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        final SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.searchbtn).getActionView();
        searchMenuItem = menu.findItem(R.id.searchbtn);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(new ComponentName(this, SearchPage.class)));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                if (searchView.isShown()) {
                    searchMenuItem.collapseActionView();
                    searchView.setQuery("", false);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

            return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent i=new Intent(MainActivity.this,Ringing.class);
            startActivity(i);
        }

        if (id == R.id.about) {
            Intent i=new Intent(MainActivity.this,About.class);
            startActivity(i);
        }

        if (id == R.id.alarm) {
            Intent i=new Intent(this,AlarmTone.class);
            startActivity(i);
        }

        if (id == R.id.change) {
            Intent i=new Intent(this,Change.class);
            startActivity(i);
        }

        if (id == R.id.exit) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.all) {
            All all=new All();
            FragmentManager manager=getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.mainscreen,all,all.getTag()).commit();
        }
        else if (id == R.id.anniversary) {
            Anniversary anniversary=new Anniversary();
            FragmentManager manager=getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.mainscreen,anniversary,anniversary.getTag()).commit();
        } else if (id == R.id.birthday) {


           Birthday birthday=new Birthday();
            FragmentManager manager=getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.mainscreen,birthday,birthday.getTag()).commit();

        } else if (id == R.id.meeting) {


            Meeting meeting=new Meeting();
            FragmentManager manager=getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.mainscreen,meeting,meeting.getTag()).commit();

        } else if (id == R.id.call) {

            Call call=new Call();
            FragmentManager manager=getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.mainscreen,call,call.getTag()).commit();

        } else if (id == R.id.alarm) {

           Alarm alarm=new Alarm();
            FragmentManager manager=getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.mainscreen,alarm,alarm.getTag()).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
