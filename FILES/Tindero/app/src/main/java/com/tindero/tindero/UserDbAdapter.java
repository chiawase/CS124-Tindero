package com.tindero.tindero;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Maan on 4/19/2016.
 */
public class UserDbAdapter {
    protected static final String KEY_ROWID = "_id";
    protected static final String KEY_USERNAME = "username";
    protected static final String KEY_PASSWORD = "password";
    protected static final String KEY_USER_JSON = "user_json";
    protected static final String KEY_USER_TYPE = "user_type";

    private static DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "User Masterlist";
    private static final String SQLITE_TABLE = "Users";

    private final Context mCtx;

    private static final String DATABASE_CREATE =

            "CREATE TABLE if not exists "
                    + SQLITE_TABLE + " ("
                    + KEY_ROWID + " integer PRIMARY KEY autoincrement,"
                    + KEY_USERNAME + ","
                    + KEY_PASSWORD + ","
                    + KEY_USER_JSON + ");";

    private static class DatabaseHelper extends SQLiteOpenHelper {

        public static synchronized DatabaseHelper getInstance(Context ctx) {
            if (mDbHelper == null) {
                mDbHelper = new DatabaseHelper(ctx.getApplicationContext());
            }
            return mDbHelper;
        }

        private DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        // DATABASE CREATION
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DATABASE_CREATE);
        }

        // DATABASE CHANGE
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + SQLITE_TABLE);
            onCreate(db);
        }
    }

    // LIFE CYCLE
    public UserDbAdapter(Context ctx) {
        //ctx.deleteDatabase(DATABASE_NAME); //delete database
        this.mCtx = ctx;
    }

    public UserDbAdapter open() throws SQLException {
        mDbHelper = DatabaseHelper.getInstance(mCtx);
        mDb = mDbHelper.getWritableDatabase();

        return this;
    }

    public void close() {
        if (mDbHelper != null) {
            mDbHelper.close();
        }
    }

    public long addUser(String user, String pass, String user_json) {
        // INSERT
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_USERNAME, user);
        initialValues.put(KEY_PASSWORD, pass);
        initialValues.put(KEY_USER_JSON, user_json);

        // parameters
        // mDb.insert(table, nullColumnHack, values);

        return mDb.insert(SQLITE_TABLE, null, initialValues);
    }

    public long updateUser(String rowId, String user, String pass, String user_json) {
        // INSERT
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_USERNAME, user);
        initialValues.put(KEY_PASSWORD, pass);
        initialValues.put(KEY_USER_JSON, user_json);
        // String whereArgs[] = new String[1];
        //  whereArgs[0] = "" + rowId;

        return mDb.update(SQLITE_TABLE, initialValues, KEY_ROWID + "=" + rowId, null);
    }

    public boolean deleteAllUsers() {
        // DELETE
        // parameters
        // mDb.delete(table, whereClause, whereArgs)
        int doneDelete = 0;
        doneDelete = mDb.delete(SQLITE_TABLE, null, null);
        return doneDelete > 0;
    }

    public boolean deleteUser(String rowId) {

        return mDb.delete(SQLITE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
    }

    public Cursor fetchUserByName(String inputText) throws SQLException {
        Cursor mCursor = null;
        if (inputText == null || inputText.length() == 0) {
            throw new RuntimeException("Need a name");

        } else {
            mCursor = mDb.query(true,
                    SQLITE_TABLE,
                    new String[]{KEY_ROWID, KEY_USERNAME, KEY_PASSWORD, KEY_USER_JSON },
                    KEY_USERNAME + " = '" + inputText + "'",
                    null, null, null, null, null);
        }

        if (mCursor != null) {
            mCursor.moveToFirst();
        }

        return mCursor;
    }

    public Cursor fetchAllUsers() {
        // parameter descriptions
        // mDb.query(table, columns, selection, selectionArgs, groupBy, having, orderBy)
        Cursor mCursor = mDb.query(SQLITE_TABLE, new String[] { KEY_ROWID,
                KEY_USERNAME, KEY_PASSWORD, KEY_USER_JSON }, null, null, null, null, null);

        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public boolean checkPassword(String name, String pass) {
        String storedPass = fetchUserByName(name).getString(fetchUserByName(name).getColumnIndex(KEY_PASSWORD));

        return pass.equals(storedPass);
    }

    public boolean checkIfUserExists(String inputText)
    {
        Cursor mCursor = null;

        mCursor = mDb.query(true,
                SQLITE_TABLE,
                new String[] { KEY_ROWID,
                        KEY_USERNAME,
                        KEY_PASSWORD },
                KEY_USERNAME + " = '" + inputText+ "'",
                null, null, null, null, null);

        if(mCursor.moveToFirst()) {
            return true;
        } else return false;
    }

    public boolean isTableEmpty()
    {
        Cursor mCursor = mDb.query(SQLITE_TABLE, new String[] { KEY_ROWID,
                KEY_USERNAME, KEY_PASSWORD }, null, null, null, null, null);

        if (mCursor.moveToFirst()) {
            return false;
        } else return true;
    }
}