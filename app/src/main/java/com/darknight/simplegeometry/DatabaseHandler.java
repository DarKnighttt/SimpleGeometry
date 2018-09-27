package com.darknight.simplegeometry;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper implements IDatabaseHandler {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "levelsDB";
    private static final String TABLE_LEVELS = "levels";
    private static final String LEVEL_ID = "id";
    private static final String LEVEL_ANSWER = "answer";
    private static final String LEVEL_STATUS = "status";
    private final String TAG = "mySQLite";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_LEVELS_TABLE = "CREATE TABLE " + TABLE_LEVELS + "("
                + LEVEL_ID + " INTEGER PRIMARY KEY," + LEVEL_ANSWER + " INTEGER,"
                + LEVEL_STATUS + " INTEGER" + ")";
        db.execSQL(CREATE_LEVELS_TABLE);
        int[] answers = {2, 3, 3, 4, 4, 5, 5, 7, 8, 8, 6, 7, 12, 13, 10, 15, 16, 21, 32, 35};
        for (int i = 0; i < 20; i++) {
            String fillDB = "INSERT INTO " + TABLE_LEVELS + "(" + LEVEL_ANSWER + ","
                    + LEVEL_STATUS + ") VALUES (" + answers[i] + "," + 0 + ")";
            db.execSQL(fillDB);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LEVELS);
        onCreate(db);
    }

    @Override
    public void addLevel(Level lvl) {
        Log.d(TAG, "addLevel " + lvl.toString());
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(LEVEL_ANSWER, lvl.getAnswer());
        values.put(LEVEL_STATUS, lvl.getStatus());

        db.insert(TABLE_LEVELS, null, values);
        db.close();
    }

    @Override
    public Level getLevel(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_LEVELS, new String[]{LEVEL_ID,
                        LEVEL_ANSWER, LEVEL_STATUS}, LEVEL_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        Level level = new Level(Integer.parseInt(cursor.getString(0)),
                Integer.parseInt(cursor.getString(1)),
                Integer.parseInt(cursor.getString(2)));

        db.close();
        Log.d(TAG, "getLevel(" + id + ") " + level.toString());
        return level;
    }

    @Override
    public List<Level> getAllLevels() {
        List<Level> levelList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_LEVELS;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Level lvl = new Level();
                lvl.setId(Integer.parseInt(cursor.getString(0)));
                lvl.setAnswer(Integer.parseInt(cursor.getString(1)));
                lvl.setStatus(Integer.parseInt(cursor.getString(2)));
                levelList.add(lvl);
            } while (cursor.moveToNext());
        }
        db.close();
        Log.d(TAG, "getAllLevels() " + levelList.toString());
        return levelList;
    }

    @Override
    public void updateLevel(Level lvl) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(LEVEL_ANSWER, lvl.getAnswer());
        values.put(LEVEL_STATUS, 1);
        db.update(TABLE_LEVELS, values, LEVEL_ID + " = ?",
                new String[]{String.valueOf(lvl.getId())});
        db.close();
    }

    @Override
    public void clearProgress() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE " + TABLE_LEVELS + " SET " + LEVEL_STATUS + " = " + 0);
        db.close();
    }

}
