package com.example.assgnment2;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class SearchImages extends AppCompatActivity {

    MyDB db = DownloadImages.database;
    public ImageListAdapter adapter;

    TextView id_view;
    ImageView img_view;
    TextView title_view;
    Button delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_images);
    }

    public void search(View view) {
        id_view = (TextView) view.findViewById(R.id.img_id);
        img_view = (ImageView) view.findViewById(R.id.img);
        title_view = (TextView)view.findViewById(R.id.img_title);
        delete = (Button)view.findViewById((R.id.del_img));

        ListView mSearchView = (ListView) findViewById(R.id.searchView);
        SQLiteDatabase db_2 = db.getReadableDatabase();

        EditText search_key = (EditText)findViewById(R.id.searchBar);

        String title = search_key.getText().toString();
        String query = "SELECT * FROM NAME WHERE TITLE = " + "'" + title + "'";

        Cursor cursor = db_2.rawQuery(query, null);

        adapter = new ImageListAdapter(this, R.layout.adapter_view_layout, cursor, 0);
        mSearchView.setAdapter(adapter);

    }

    public void delete(View view) {
        Button b = (Button) view;
        // SQLiteDatabase del_db = db.getReadableDatabase();
        db.delete(b.getTag().toString());
        adapter.notifyDataSetChanged();

    }
}
