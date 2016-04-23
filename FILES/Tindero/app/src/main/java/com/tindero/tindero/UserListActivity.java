package com.tindero.tindero;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
<<<<<<< Updated upstream
import android.database.MatrixCursor;
=======
import android.graphics.Typeface;
>>>>>>> Stashed changes
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FilterQueryProvider;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class UserListActivity extends ListActivity {

    private UserDbAdapter dbHelper;
    private SimpleCursorAdapter dataAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        TextView tvTitle = (TextView) findViewById(R.id.tTitle) ;
        Typeface custom = Typeface.createFromAsset(getAssets(), "fonts/Montserrat-Bold.otf");
        tvTitle.setTypeface(custom);
        setListAdapter(dataAdapter);

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

        //Button back = (Button) findViewById(R.id.bListBack);
       /** back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
<<<<<<< Updated upstream
        });

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
=======
        });**/
>>>>>>> Stashed changes
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
        startActivity(intent);
    }
}
