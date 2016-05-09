package com.tindero.tindero;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.MergeCursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FilterQueryProvider;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;

public class MatchListActivity extends ListActivity {

    private UserDbAdapter dbHelper;
    private SimpleCursorAdapter dataAdapter;
    String currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_list);
        setListAdapter(dataAdapter);

        Intent intent = getIntent();
        currentUser = intent.getStringExtra("currentUser");

        dbHelper = new UserDbAdapter(this);
        dbHelper.open();
        User user = getUser(currentUser);
        Employer emp;
        Freelancer free;

        MergeCursor cursor = null;

        if (user.getUserType().equals("Employer")) {
            emp = (Employer) user;

            ArrayList<String> temp = emp.getObserverNames();
            for(String t: temp) {
                cursor = new MergeCursor (new Cursor [] {dbHelper.fetchUserByName(t)});
            }
        } else if (user.getUserType().equals("Freelancer")) {
            free = (Freelancer) user;

            ArrayList<String> temp = free.getObserverNames();
            for(String t: temp) {
                cursor = new MergeCursor (new Cursor [] {dbHelper.fetchUserByName(t)});
            }
        }

        dataAdapter = new SimpleCursorAdapter(this,
                R.layout.row,
                cursor,
                new String[]{UserDbAdapter.KEY_FULLNAME, UserDbAdapter.KEY_USERTYPE, UserDbAdapter.KEY_DESCRIPTION},
                new int[]{R.id.listFullName, R.id.listUserType, R.id.listDescription},
                0);

        ListView lv = getListView();
        lv.setAdapter(dataAdapter);
        registerForContextMenu(lv);

        EditText myFilter = (EditText) findViewById(R.id.etFilterSubscriber);

        myFilter.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                dataAdapter.getFilter().filter(s.toString());
            }
        });

        dataAdapter.setFilterQueryProvider(new FilterQueryProvider() {
            public Cursor runQuery(CharSequence constraint) {
                return dbHelper.filterUsersByName(constraint.toString());
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();

        // places the contents of the XML to the menu
        inflater.inflate(R.menu.menu_user_list, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item)
    {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item
                .getMenuInfo();
        final int position = info.position;

        switch (item.getItemId()) {
            case R.id.delete: // TODO change to remove observer
                ListView lv = getListView();
                Cursor cursor = (Cursor) lv.getItemAtPosition(position);
                final String del = cursor.getString(cursor.getColumnIndexOrThrow(UserDbAdapter.KEY_ROWID));

                dbHelper.deleteUser(del);
                dataAdapter.notifyDataSetChanged();
                dataAdapter.changeCursor(dbHelper.fetchAllUsers());
                Toast.makeText(getApplicationContext(), "User deleted.", Toast.LENGTH_SHORT).show();
                return true;

            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    protected void onListItemClick(ListView l, View v, final int position, long id) {
        super.onListItemClick(l, v, position, id);

        Cursor cursor = (Cursor)l.getAdapter().getItem(position);
        String username = cursor.getString(cursor.getColumnIndexOrThrow(UserDbAdapter.KEY_USERNAME));

        Intent intent = new Intent(MatchListActivity.this, prof.class);
        intent.putExtra(UserDbAdapter.KEY_USERNAME, username);
        intent.putExtra("currentUser", currentUser);
        startActivity(intent);
    }

    public User getUser(String username) {
        Cursor cursor = dbHelper.fetchUserByName(username);
        String j = cursor.getString(cursor.getColumnIndexOrThrow(UserDbAdapter.KEY_USER_JSON));

        JsonParser parser = new JsonParser();
        JsonObject o = parser.parse(j).getAsJsonObject();
        String type = o.get("userType").getAsString();

        Gson gson = new Gson();
        User user = null;

        cursor.close();

        if(type.equals("Employer")) {
            user = gson.fromJson(o, Employer.class);
        } else if (type.equals("Freelancer")) {
            user = gson.fromJson(o, Freelancer.class);
        }

        return user;
    }
}
