package com.tindero.tindero;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class UserListActivity extends ListActivity {

    private UserDbAdapter dbHelper;
    private SimpleCursorAdapter dataAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        setListAdapter(dataAdapter);

        dbHelper = new UserDbAdapter(this);
        dbHelper.open();

        Cursor cursor = dbHelper.fetchAllUsers();

        dataAdapter = new SimpleCursorAdapter(this,
                R.layout.row,
                cursor,
                new String[] { UserDbAdapter.KEY_FULLNAME, UserDbAdapter.KEY_USERTYPE, UserDbAdapter.KEY_SKILLS, },
                new int[]    { R.id.listFullName, R.id.listUserType, R.id.listDescription },
                0 );

        ListView lv = getListView();
        lv.setAdapter(dataAdapter);
        registerForContextMenu(lv);

        Button back = (Button) findViewById(R.id.bListBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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

        Intent intent = new Intent(UserListActivity.this, prof.class);
        intent.putExtra(UserDbAdapter.KEY_USERNAME, username);
        startActivity(intent);
    }
}
