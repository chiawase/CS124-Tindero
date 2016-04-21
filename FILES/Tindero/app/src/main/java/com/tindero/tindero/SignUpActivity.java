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
        String type = intent.getStringExtra("user_type");
        TextView tv = (TextView) findViewById(R.id.tvUserType);
        tv.setText(type);

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

        String newUsername = signUser.getText().toString();
        String newPassword = signPass.getText().toString();
        String newName = signName.getText().toString();

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

                Skill skill = new DefaultSkill();
                if (cbFeed.isChecked()) { skill = new FeedThePets(skill); }
                if (cbHouse.isChecked()){ skill = new HouseKeeper(skill); }
                if (cbLaundry.isChecked()){ skill = new Laundry(skill); }
                if (cbWater.isChecked()){ skill = new WaterThePlants(skill); }

                dbHelper.addUser(newUsername, newPassword, newName, type, ""+ skill.getDescription());
                Toast.makeText(getApplicationContext(), "Successfully signed up.", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(SignUpActivity.this, prof.class);
                startActivity(intent);
            }
        }
    }
}
