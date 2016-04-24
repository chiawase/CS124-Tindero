package com.tindero.tindero;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Home extends AppCompatActivity {

    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    private UserDbAdapter dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        TextView tv = (TextView) findViewById(R.id.titleText);
        Typeface custom = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-Bold.otf");
        tv.setTypeface(custom);
        Typeface customR = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-Regular.otf");
        Button log = (Button) findViewById(R.id.logButton);
        Button sign = (Button) findViewById(R.id.signup);
        log.setTypeface(customR);
        sign.setTypeface(customR);

        dbHelper = new UserDbAdapter(this);
        dbHelper.open();

        prefs = getSharedPreferences("user_info", MODE_PRIVATE);
        editor = prefs.edit();
        editor.apply();

        String storedUser = prefs.getString(UserDbAdapter.KEY_USERNAME, "");
        String storedPass = prefs.getString(UserDbAdapter.KEY_PASSWORD, "");

        if(dbHelper.checkIfUserExists(storedUser))
        {
            if (prefs.contains(UserDbAdapter.KEY_USERNAME) && prefs.contains(UserDbAdapter.KEY_PASSWORD)) {
                EditText user = (EditText) findViewById(R.id.etUsername);
                user.setText(storedUser);
                EditText pass = (EditText) findViewById(R.id.etPassword);
                pass.setText(storedPass);
            }
        }

        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logIn();
            }
        });
        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
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

    public void printLol() {
        //TextView tv = (TextView) findViewById(R.id.textView4);
        //CharSequence lol = tv.getText();
        //System.out.println(lol);
    }

    private boolean isEditTextEmpty(EditText et) {
        return et.getText().toString().trim().length() == 0;
    }

    private void logIn() {
        EditText user = (EditText) findViewById(R.id.etUsername);
        EditText pass = (EditText) findViewById(R.id.etPassword);

        if(!isEditTextEmpty(user) && !isEditTextEmpty(pass)) {
            String u = user.getText().toString();
            String p = pass.getText().toString();

            if (!dbHelper.checkIfUserExists(u)) {
                Toast.makeText(getApplicationContext(), "User does not exist.", Toast.LENGTH_SHORT).show();
            } else {
                if (!dbHelper.checkPassword(u, p)) {
                    Toast.makeText(getApplicationContext(), "Incorrect password.", Toast.LENGTH_SHORT).show();
                } else {
                    savePrefs();
                    Intent intent = new Intent(Home.this, prof.class);
                    intent.putExtra(UserDbAdapter.KEY_USERNAME, u);
                    startActivity(intent);
                }
            }
        }
    }

    private void savePrefs() {
        EditText user = (EditText) findViewById(R.id.etUsername);
        String u = user.getText().toString();
        editor.putString(UserDbAdapter.KEY_USERNAME, u);

        EditText pass = (EditText) findViewById(R.id.etPassword);
        String p = pass.getText().toString();
        editor.putString(UserDbAdapter.KEY_PASSWORD, p);

        editor.commit();
    }

    public void signUp() {
        final Dialog selectType = new Dialog(this);
        selectType.setContentView(R.layout.signup_dialog);
        selectType.setTitle("SIGN UP AS");
        selectType.show();

        Button freelancer = (Button) selectType.findViewById(R.id.bFreelancer);
        freelancer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNext("Freelancer");
            }
        });

        Button employer = (Button) selectType.findViewById(R.id.bEmployer);
        employer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNext("Employer");
            }
        });
    }

    private void openNext(String type) {
        Intent intent = new Intent(this, SignUpActivity.class);
        intent.putExtra(UserDbAdapter.KEY_USERTYPE, type);
        startActivity(intent);
    }
}