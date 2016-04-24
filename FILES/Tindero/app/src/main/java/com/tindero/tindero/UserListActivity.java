package com.tindero.tindero;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
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

public class UserListActivity extends ListActivity {

    private UserDbAdapter dbHelper;
    private SimpleCursorAdapter dataAdapter;
    String currentUser;

    @Override   
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        setListAdapter(dataAdapter);

        Intent intent = getIntent();
        currentUser = intent.getStringExtra("currentUser");

        dbHelper = new UserDbAdapter(this);
        dbHelper.open();

        Cursor cursor = dbHelper.fetchAllUsers();

        dataAdapter = new SimpleCursorAdapter(this,
                R.layout.row,
                cursor,
                new String[] { UserDbAdapter.KEY_FULLNAME, UserDbAdapter.KEY_USERTYPE , UserDbAdapter.KEY_DESCRIPTION },
                new int[]    { R.id.listFullName, R.id.listUserType, R.id.listDescription },
                0 );

        ListView lv = getListView();
        lv.setAdapter(dataAdapter);
        registerForContextMenu(lv);

        EditText myFilter = (EditText) findViewById(R.id.etFilter);
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
            case R.id.delete:
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

        Intent intent = new Intent(UserListActivity.this, match.class);
        intent.putExtra(UserDbAdapter.KEY_USERNAME, username);
        intent.putExtra("currentUser", currentUser);
        startActivity(intent);
    }
}
