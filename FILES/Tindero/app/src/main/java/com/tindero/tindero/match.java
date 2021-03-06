package com.tindero.tindero;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class match extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private UserDbAdapter dbHelper;
    Gson gson;
    String currentUser;
    String selectedUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("TINDERO");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        dbHelper = new UserDbAdapter(this);
        dbHelper.open();
        gson = new Gson();

        Intent intent = getIntent();
        selectedUser = intent.getStringExtra(UserDbAdapter.KEY_USERNAME);
        currentUser = intent.getStringExtra("currentUser");
        loadProfile(selectedUser);
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
        getMenuInflater().inflate(R.menu.match, menu);
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

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void loadProfile(String username) {

        TextView tvMatchName = (TextView) findViewById(R.id.tvMatchName);
        TextView tvMatchUserType = (TextView) findViewById(R.id.tvMatchUserType);
        TextView tvMatchDescription = (TextView) findViewById(R.id.tvMatchDescription);

        User user = getUser(username);

        tvMatchName.setText(user.getFullName());
        tvMatchUserType.setText(user.getUserType());
        tvMatchDescription.setText(user.getDescription());
    }

    public void dislike(View v) {
        finish();
    }

    public void like(View v) {
        User selected = getUser(selectedUser);
        User current = getUser(currentUser);
        Employer emp;
        Freelancer free;

        if (current.getUserType().equals("Employer")) {
            emp = (Employer) current;
            free =  (Freelancer) selected;
            //emp.showInterest(free);
            free.addObserverName(emp.getUsername());
            updateUser(free);
            Toast.makeText(getApplicationContext(), "You are now subscribed to " + free.getFullName(), Toast.LENGTH_SHORT).show();
        } else if (current.getUserType().equals("Freelancer")) {
            free = (Freelancer) current;
            emp = (Employer) selected;
            //free.apply(emp);
            emp.addObserverName(free.getUsername());
            updateUser(emp);
            Toast.makeText(getApplicationContext(), "You are now subscribed to " + emp.getFullName(), Toast.LENGTH_SHORT).show();
        }
    }

    public User getUser(String username) {
        Cursor cursor = dbHelper.fetchUserByName(username);
        String j = cursor.getString(cursor.getColumnIndexOrThrow(UserDbAdapter.KEY_USER_JSON));

        JsonParser parser = new JsonParser();
        JsonObject o = parser.parse(j).getAsJsonObject();
        String type = o.get("userType").getAsString();
        User user = null;

        if(type.equals("Employer")) {
            user = gson.fromJson(o, Employer.class);
        } else if (type.equals("Freelancer")) {
            user = gson.fromJson(o, Freelancer.class);
        }

        cursor.close();

        return user;
    }

    public void updateUser(User u) { //TODO fix problem serializing object's arraylist
        String user_json = gson.toJson(u);
        dbHelper.updateUser(u.getId(), u.getUsername(), u.getPassword(), u.getFullName(), u.getUserType(), u.getDescription(), user_json);
    }
}
