package com.example.assgnment2;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ImageListAdapter extends ResourceCursorAdapter {

    public MyDB db = DownloadImages.database;
    public ImageListAdapter adapter;

    TextView id_view;
    ImageView img_view;
    TextView title_view;
    Button delete;


    public ImageListAdapter(Context context, int layout, Cursor cursor, int flags){
        super(context, layout,cursor, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.adapter_view_layout, parent, false);
    }

    @Override
    public void bindView(View view, Context context, final Cursor cursor) {
        id_view = (TextView) view.findViewById(R.id.img_id);
        img_view = (ImageView) view.findViewById(R.id.img);
        title_view = (TextView)view.findViewById(R.id.img_title);
        delete = (Button)view.findViewById((R.id.del_img));

        delete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                id_view.setText(cursor.getString(cursor.getColumnIndex("_id")));
                title_view.setText(cursor.getString(cursor.getColumnIndex("TITLE")));

                int img_index = cursor.getColumnIndex("IMAGE");
                byte[] img_blob = cursor.getBlob(img_index);
                Bitmap img_bit = BitmapFactory.decodeByteArray(img_blob, 0, img_blob.length);
                img_view.setImageBitmap(img_bit);

                String rm_id = id_view.getText().toString();
                // String rm_title = title_view.getText().toString();
                System.out.println("****************** CAN DO DELETE BUTTON ********************");
                db.delete(rm_id);
                delete.setTag(rm_id);
            }

        });

        String id = cursor.getString(cursor.getColumnIndexOrThrow("_id"));
        byte[] view1 = cursor.getBlob(cursor.getColumnIndexOrThrow("IMAGE"));
        String title = cursor.getString(cursor.getColumnIndexOrThrow("TITLE"));

        Bitmap bitmap = BitmapFactory.decodeByteArray(view1, 0 , view1.length);

        id_view.setText(id);
        img_view.setImageBitmap(bitmap);
        title_view.setText(title);



    }

}
