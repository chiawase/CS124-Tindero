package com.tindero.tindero;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

public class SignUpActivity extends AppCompatActivity {
    private UserDbAdapter dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dbHelper = new UserDbAdapter(this);
        dbHelper.open();

        Intent intent = getIntent();
        String type = intent.getStringExtra(UserDbAdapter.KEY_USERTYPE);
        TextView tv = (TextView) findViewById(R.id.tvUserType);
        tv.setText(type);

        if(type.equals("Employer")) {
            TextView tvSkills = (TextView) findViewById(R.id.tvSkills);
            CheckBox cbFeed = (CheckBox) findViewById(R.id.cbFeedPets);
            CheckBox cbHouse = (CheckBox) findViewById(R.id.cbHousekeeping);
            CheckBox cbLaundry = (CheckBox) findViewById(R.id.cbLaundry);
            CheckBox cbWater = (CheckBox) findViewById(R.id.cbWaterPlants);

            tvSkills.setVisibility(View.GONE);
            cbFeed.setVisibility(View.GONE);
            cbHouse.setVisibility(View.GONE);
            cbLaundry.setVisibility(View.GONE);
            cbWater.setVisibility(View.GONE);
        }

        Button finish = (Button) findViewById(R.id.bSignFinish);
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addUser();
            }
        });
        Button back = (Button) findViewById(R.id.bSignBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void addUser() {
        EditText signUser = (EditText) findViewById(R.id.etSignUsername);
        EditText signPass = (EditText) findViewById(R.id.etSignPassword);
        EditText signName = (EditText) findViewById(R.id.etSignFullName);
        EditText signContact = (EditText) findViewById(R.id.etSignContact);

        String newUsername = signUser.getText().toString();
        String newPassword = signPass.getText().toString();
        String newName = signName.getText().toString();
        String newContact = signContact.getText().toString();

        if (newUsername.length() == 0 || newPassword.length() == 0 || newName.length() == 0) {
            Toast.makeText(getApplicationContext(), "Field(s) must not be empty", Toast.LENGTH_SHORT).show();
        } else {
            if (dbHelper.checkIfUserExists(newUsername)) {
                Toast.makeText(getApplicationContext(), "User already exists.", Toast.LENGTH_SHORT).show();
            } else {
                TextView tv = (TextView) findViewById(R.id.tvUserType);
                String type = tv.getText().toString();

                CheckBox cbFeed = (CheckBox) findViewById(R.id.cbFeedPets);
                CheckBox cbHouse = (CheckBox) findViewById(R.id.cbHousekeeping);
                CheckBox cbLaundry = (CheckBox) findViewById(R.id.cbLaundry);
                CheckBox cbWater = (CheckBox) findViewById(R.id.cbWaterPlants);

                Gson gson = new Gson();
                String user_json = "";
                User user = null;
                String id = "" + dbHelper.getHighestId();

                if(type.equals("Employer")) {
                    user = new Employer(id, newUsername, newPassword, newName, type, newContact, "emailAddress", "");
                }

                else if(type.equals("Freelancer")) {
                    Skill skill = new Skill();
                    if (cbFeed.isChecked()) { skill = new FeedThePets(skill); }
                    if (cbHouse.isChecked()){ skill = new HouseKeeper(skill); }
                    if (cbLaundry.isChecked()){ skill = new Laundry(skill); }
                    if (cbWater.isChecked()){ skill = new WaterThePlants(skill); }
                    user = new Freelancer(id, newUsername, newPassword, newName, type, newContact, "emailAddress", skill.getDescription());
                }

                user_json = gson.toJson(user);
                dbHelper.addUser(newUsername, newPassword, newName, type, user.getDescription(), user_json, "");
                Toast.makeText(getApplicationContext(), "Successfully signed up.", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(SignUpActivity.this, CameraActivity.class);
                intent.putExtra(UserDbAdapter.KEY_USERNAME, newUsername);
                intent.putExtra(UserDbAdapter.KEY_ROWID, id);
                startActivity(intent);
            }
        }
    }
}