package com.example.ynaito.androidsamples;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    SqlContract mDbHelper;
    private String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDbHelper = new SqlContract(getBaseContext());
        SQLiteDatabase mDb = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        long data = System.currentTimeMillis();
        String title = "this is title";
        String subtitle = "this is subtitle";
        values.put(SqlContract.KEY_REGISTER_DATE, data);
        values.put(SqlContract.KEY_TITLE, title);
        values.put(SqlContract.KEY_SUBTITLE, subtitle);

        long newRowId = mDb.insert(SqlContract.TABLE_NAME, null, values);
        doSelect();
    }

    private void doSelect() {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

// Define a projection that specifies which columns from the database
// you will actually use after this query.
        String[] projection = {
                SqlContract.KEY_ID,
                SqlContract.KEY_TITLE,
                SqlContract.KEY_SUBTITLE
        };

// Filter results WHERE "title" = 'My Title'
        String selection = SqlContract.KEY_TITLE + " = ?";
        String[] selectionArgs = {"this is title"};

// How you want the results sorted in the resulting Cursor
        String sortOrder =
                SqlContract.KEY_TITLE + " DESC";

        Cursor c = db.query(
                SqlContract.TABLE_NAME,                     // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );
        c.moveToFirst();
        String Title = c.getString(
                c.getColumnIndexOrThrow(SqlContract.KEY_TITLE)
        );
        Log.d(TAG,"Title is " + Title);

    }
}
