package com.tindero.tindero;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CheckBox;
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
        TextView tv = (TextView) findViewById(R.id.titleText);
        Typeface custom = Typeface.createFromAsset(getAssets(), "fonts/vscript.ttf");
        //tv.setTypeface(custom);
        setContentView(R.layout.activity_home);
        Button log = (Button) findViewById(R.id.logButton);
        Button sign = (Button) findViewById(R.id.signup);

        dbHelper = new UserDbAdapter(this);
        dbHelper.open();
        //dbHelper.deleteAllUsers();
        //dbHelper.seed();

        prefs = getSharedPreferences("user_info", MODE_PRIVATE);
        editor = prefs.edit();
        editor.apply();

        boolean rememberMe = prefs.getBoolean("rememberMe", false);
        String storedUser = prefs.getString("username", "");
        String storedPass = prefs.getString("password", "");

        if(rememberMe && dbHelper.checkIfUserExists(storedUser))
        {
            if (prefs.contains("username") && prefs.contains("password")) {
                EditText user = (EditText) findViewById(R.id.etUsername);
                user.setText(storedUser);
                EditText pass = (EditText) findViewById(R.id.etPassword);
                pass.setText(storedPass);

                CheckBox cbRememberMe = (CheckBox)findViewById(R.id.cbRememberMe);
                cbRememberMe.setChecked(true);
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

            CheckBox cbRememberMe = (CheckBox) findViewById(R.id.cbRememberMe);
            boolean rememberMe = cbRememberMe.isChecked();

            Cursor mCursor = dbHelper.fetchUserByName(u);

            if (mCursor.getCount() == 0) {
                Toast.makeText(getApplicationContext(), "User does not exist.", Toast.LENGTH_SHORT).show();
            } else {
                if (!dbHelper.checkPassword(u, p)) {
                    clearPrefs();
                    Toast.makeText(getApplicationContext(), "Incorrect password.", Toast.LENGTH_SHORT).show();
                } else {
                    if (rememberMe) {
                        savePrefs();
                    } else {
                        clearPrefs();
                    }

                    Intent intent = new Intent(Home.this, prof.class);
                    startActivity(intent);
                }
            }
        }
    }

    private void savePrefs() {
        EditText user = (EditText) findViewById(R.id.etUsername);
        String u = user.getText().toString();
        editor.putString("username", u);

        EditText pass = (EditText) findViewById(R.id.etPassword);
        String p = pass.getText().toString();
        editor.putString("password", p);

        editor.putBoolean("rememberMe", true);

        editor.commit();
    }

    private void clearPrefs() {
        editor.putBoolean("rememberMe", false);
        //editor.remove("username");
        //editor.remove("password");
        //editor.clear();
        editor.commit();
    }

    public void signUp()
    {
        final Dialog register = new Dialog(this);
        register.setContentView(R.layout.signup_dialog);
        register.setTitle("Sign Up");
        register.setCancelable(false);
        register.getWindow().setLayout(400, 600);
        register.show();

        Button cancel = (Button) register.findViewById(R.id.bCancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(register.getContext());
                builder.setMessage("Are you sure?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                register.dismiss();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        Button ok = (Button) register.findViewById(R.id.bOK);
        ok.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                final EditText regUser = (EditText) register.findViewById(R.id.etRegUser);
                final EditText regPass = (EditText) register.findViewById(R.id.etRegPass);
                String newUsername = regUser.getText().toString();
                String newPassword = regPass.getText().toString();

                if (newUsername.length() == 0 || newPassword.length() == 0) {
                    Toast.makeText(getApplicationContext(), "Field(s) must not be empty", Toast.LENGTH_SHORT).show();
                }

                else {
                    if (dbHelper.checkIfUserExists(newUsername)) {
                        Toast.makeText(getApplicationContext(), "User already exists.", Toast.LENGTH_SHORT).show();
                    }

                    else {
                        dbHelper.addUser(newUsername, newPassword);

                        Toast.makeText(getApplicationContext(), "Registration successful.", Toast.LENGTH_SHORT).show();
                        register.dismiss();
                    }
                }
            }
        });
    }
}