package com.example.assgnment2;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.service.voice.VoiceInteractionService;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;



public class MyDB extends SQLiteOpenHelper {
    Context ctx;
    static String TABLE_NAME = "NAME";
    static int VERSION = 1;
    SQLiteDatabase db;

     public MyDB(@Nullable Context context) {
        super(context, TABLE_NAME, null, VERSION);
        ctx = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
         db.execSQL("CREATE TABLE " + TABLE_NAME + " ( _id INTEGER PRIMARY KEY, TITLE STRING, IMAGE BLOB NOT NULL)");
         // Toast.makeText(ctx, "TABLE " + TABLE_NAME + " " + VERSION + " IS CREATED", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
         if (oldVersion == VERSION){
             db = getWritableDatabase();
             db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
             onCreate(db);
             VERSION = newVersion;

             Toast.makeText(ctx, "TABLE " + TABLE_NAME + " " + oldVersion + " IS DROPPED", Toast.LENGTH_LONG).show();
         }
    }

    void insert(String title, byte[] download_url){
        ContentValues cv = new ContentValues();
        cv.put("TITLE", title);
        cv.put("IMAGE", download_url);

        db = getWritableDatabase();
        db.insert(TABLE_NAME, null, cv);
        //Toast.makeText(ctx, "download url " + download_url + " ADDED TO DB", Toast.LENGTH_LONG).show();

    }
    public void delete(String id) {

         db = getWritableDatabase();
         String whereClause = "_id=?";
         String[] whereArgs = new String[]{id};


         db.delete(TABLE_NAME, whereClause, whereArgs);
         // System.out.println("**************** SUCCESSFULLY DELETED COLUMN *****************         " + isDelete + "         ************");
    }
}
