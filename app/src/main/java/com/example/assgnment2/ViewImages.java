package com.example.assgnment2;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class ViewImages extends AppCompatActivity {

    MyDB db = DownloadImages.database;
    public ImageListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_images);
        SQLiteDatabase data = db.getReadableDatabase();


        Cursor listCursor = data.rawQuery("SELECT * FROM NAME", null);

        ListView mListView = (ListView) findViewById(R.id.listView);

        adapter = new ImageListAdapter(this, R.layout.adapter_view_layout, listCursor, 0);
        mListView.setAdapter(adapter);


    }

    public void delete(View view) {
        Button b = (Button) view;
        // SQLiteDatabase del_db = db.getReadableDatabase();
        db.delete(b.getTag().toString());
        adapter.notifyDataSetChanged();

    }
}