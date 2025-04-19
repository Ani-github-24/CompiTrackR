package com.example.compitrackr;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database version and name
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "ContestTracker.db";

    // Table name and column names
    private static final String TABLE_CONTESTS = "contests";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_PLATFORM = "platform";
    private static final String COLUMN_CONTEST_NAME = "contest_name";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_RANK = "rank";
    private static final String COLUMN_OLD_RATING = "old_rating";
    private static final String COLUMN_NEW_RATING = "new_rating";
    private static final String COLUMN_PROBLEMS_SOLVED = "problems_solved";

    // Create table SQL query
    private static final String CREATE_TABLE_CONTESTS =
            "CREATE TABLE " + TABLE_CONTESTS + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_PLATFORM + " TEXT, " +
                    COLUMN_CONTEST_NAME + " TEXT, " +
                    COLUMN_DATE + " TEXT, " +
                    COLUMN_RANK + " INTEGER, " +
                    COLUMN_OLD_RATING + " INTEGER, " +
                    COLUMN_NEW_RATING + " INTEGER, " +
                    COLUMN_PROBLEMS_SOLVED + " INTEGER);";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Execute the query to create the contests table
        db.execSQL(CREATE_TABLE_CONTESTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older tables if they exist and recreate them
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTESTS);
        onCreate(db);
    }

    // Method to insert a contest log into the database
    public void insertContestLog(ContestLog contestLog) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_PLATFORM, contestLog.getPlatform());
        values.put(COLUMN_CONTEST_NAME, contestLog.getContestName());
        values.put(COLUMN_DATE, contestLog.getDate().toString()); // Store date as String
        values.put(COLUMN_RANK, contestLog.getRank());
        values.put(COLUMN_OLD_RATING, contestLog.getOldRating());
        values.put(COLUMN_NEW_RATING, contestLog.getNewRating());
        values.put(COLUMN_PROBLEMS_SOLVED, contestLog.getProblemsSolvedCount());

        // Insert the new contest log
        db.insert(TABLE_CONTESTS, null, values);
        db.close();
    }

    // Method to get all contest logs from the database
    public List<ContestLog> getAllContestLogs() {
        List<ContestLog> logs = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Fix table name and column names in the query
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_CONTESTS, null);

        if (cursor.moveToFirst()) {
            do {
                String platform = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PLATFORM));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTEST_NAME));
                String date = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE)); // date as String
                int rank = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_RANK));
                int oldRating = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_OLD_RATING));
                int newRating = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_NEW_RATING));
                int problemsSolved = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_PROBLEMS_SOLVED));

                // Add to the list of contest logs
                logs.add(new ContestLog(platform, name, new Date(date), rank, oldRating, newRating, problemsSolved));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return logs;
    }
}
