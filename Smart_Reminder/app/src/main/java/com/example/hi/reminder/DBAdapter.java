package com.example.hi.reminder;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by HI on 07-Aug-16.
 */
public class DBAdapter {

    static final String KEY_ROWID = "_id";
    static final String KEY_NAME = "name";
    static final String KEY_HOUR = "hour";
    static final String KEY_MINUTE = "minute";
    static final String KEY_DETAILS = "details";
    static final String KEY_YEAR = "year";
    static final String KEY_DAY = "day";
    static final String KEY_MONTH = "month";
    static final String TAG = "DBAdapter";
    static final String DATABASE_NAME = "MyDB";
    static final String DATABASE_TABLE = "Reminder";
    static final int DATABASE_VERSION = 1;
    static final String DATABASE_CREATE =
            "create table Reminder (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "name text not null,day integer not null,month integer not null,year integer not null, hour integer not null, "
                    + "minute integer not null,details text not null);";
    Context context;
    DatabaseHelper DBHelper;
    SQLiteDatabase db;

    public DBAdapter(Context ctx)
    {
        this.context = ctx;
       DBHelper = new DatabaseHelper(context);
    }

    private class DatabaseHelper extends SQLiteOpenHelper {

        public DatabaseHelper(Context context) {
            super(context,DATABASE_NAME, null, DATABASE_VERSION);

        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {

            try {
                sqLiteDatabase.execSQL(DATABASE_CREATE);

            } catch (SQLException e) {
                e.printStackTrace();

            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Reminder");
            onCreate(sqLiteDatabase);
        }
    }

    //---opens the database---
    public DBAdapter open() throws SQLException
    {
     //  DBHelper = new DatabaseHelper(context);
        db = DBHelper.getWritableDatabase();
        return this;
    }
    //---closes the database---
    public void close()
    {
        DBHelper.close();
    }

    //---insert a reminder into the database---
    public long insertReminder(String name,int day,int mnth,int year, int hr,int min,String detail)
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_NAME, name);
        initialValues.put(KEY_DAY, day);
        initialValues.put(KEY_MONTH, mnth);
        initialValues.put(KEY_YEAR,year);
        initialValues.put(KEY_HOUR, hr);
        initialValues.put(KEY_MINUTE, min);
        initialValues.put(KEY_DETAILS, detail);
        return db.insert(DATABASE_TABLE, null, initialValues);
    }
    //---deletes a particular reminder---
    public boolean deleteReminder(long rowId)
    {
        return db.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
    }

    //---retrieves all the reminders---
    public Cursor getAllReminders()
    {
        return db.query(DATABASE_TABLE, new String[] {KEY_ROWID,KEY_NAME,KEY_DAY,KEY_MONTH,KEY_YEAR,
                KEY_HOUR,KEY_MINUTE,KEY_DETAILS}, null, null, null, null, null);
    }
    //---retrieves a particular reminder---
    public Cursor getReminderId(long rowId) throws SQLException
    {
        Cursor mCursor =
                db.query(true, DATABASE_TABLE,  new String[] {KEY_ROWID,KEY_NAME,KEY_DAY,KEY_MONTH,KEY_YEAR,
                                KEY_HOUR,KEY_MINUTE,KEY_DETAILS}, KEY_ROWID + "=" + rowId, null,
                        null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public Cursor getReminderName(String category) throws SQLException
    {
        Cursor mCursor =
                db.query(true, DATABASE_TABLE,  new String[] {KEY_ROWID,KEY_NAME,KEY_DAY,KEY_MONTH,KEY_YEAR,
                                KEY_HOUR,KEY_MINUTE,KEY_DETAILS}, KEY_NAME + "='" + category+"'", null,
                        null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    //---updates a contact---
    public boolean updateContact(long rowId,String name,int day,int mnth,int year, int hr,int min,String detail)
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_NAME, name);
        initialValues.put(KEY_DAY, day);
        initialValues.put(KEY_MONTH, mnth);
        initialValues.put(KEY_YEAR,year);
        initialValues.put(KEY_HOUR, hr);
        initialValues.put(KEY_MINUTE, min);
        initialValues.put(KEY_DETAILS, detail);
        return db.update(
                DATABASE_TABLE, initialValues, KEY_ROWID + "=" + rowId, null) > 0;
    }
}
