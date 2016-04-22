package com.tindero.tindero;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.w3c.dom.Text;

public class prof extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prof);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("T I N D E R O");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Intent intent = getIntent();
        String username = intent.getStringExtra(UserDbAdapter.KEY_USERNAME);
        loadProfile(username);
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
        getMenuInflater().inflate(R.menu.prof, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_matches) {
            Intent intent = new Intent(prof.this, match.class);
            startActivity(intent);
        } else if (id == R.id.nav_view) {
            Intent intent = new Intent(prof.this, UserListActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void loadProfile(String username) {
        UserDbAdapter dbHelper = new UserDbAdapter(this);
        dbHelper.open();
        Cursor cursor = dbHelper.fetchUserByName(username);

        TextView tvProfileName = (TextView) findViewById(R.id.tvProfileName);
        TextView tvProfileUserType = (TextView) findViewById(R.id.tvProfileUserType);
        TextView tvProfileSkills = (TextView) findViewById(R.id.tvProfileSkills);
        TextView tvProfileSkillsList = (TextView) findViewById(R.id.tvProfileSkillsList);

        if(cursor.getString(cursor.getColumnIndexOrThrow(UserDbAdapter.KEY_USERTYPE)).equals("Employer")) {
            tvProfileSkills.setVisibility(View.GONE);
            tvProfileSkillsList.setVisibility(View.GONE);
        }

        tvProfileName.setText(cursor.getString(cursor.getColumnIndexOrThrow(UserDbAdapter.KEY_FULLNAME)));
        tvProfileUserType.setText(cursor.getString(cursor.getColumnIndexOrThrow(UserDbAdapter.KEY_USERTYPE)));
        tvProfileSkillsList.setText(cursor.getString(cursor.getColumnIndexOrThrow(UserDbAdapter.KEY_SKILLS)));
        cursor.close();
    }

    public void turnOffNotifications(View v) {
        //turn off notifications
    }

    public void editProfile(View v) {
        //edit profile
    }

    public void logOut(View v) {
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
        finish();
    }
}
