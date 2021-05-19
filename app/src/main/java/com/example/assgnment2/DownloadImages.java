package com.example.assgnment2;

import androidx.appcompat.app.AppCompatActivity;


import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import java.io.BufferedInputStream;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.MalformedURLException;


public class DownloadImages extends AppCompatActivity {

    AsyncTask mMyTask;

    public static MyDB database;
    EditText download_url, image_title;
    Button download_button;

    String[] input = new String[2];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_images);

        database = new MyDB(this);
        download_button = findViewById(R.id.downloadButton);
        download_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view){
                image_title = findViewById(R.id.imageTitle);
                download_url = findViewById(R.id.downloadURL);

                String title = image_title.getText().toString();
                String link = download_url.getText().toString();

                input[0] = title;
                input[1] = link;

                // ----- fix adding title and link to db
                // ----- already have adapter view and list view set up

                System.out.println(input);


                // System.out.println("////////////////////////////input works /////////////////////");

                mMyTask = new DownloadUrlTask(DownloadImages.this).execute(input);
                // Toast.makeText(DownloadImages.this, "creating table!!!", Toast.LENGTH_SHORT).show();
            }
        });



    }


     private class DownloadUrlTask extends AsyncTask<String, Void, Boolean> {
        Context ctx;
        HttpURLConnection connection = null;

        public DownloadUrlTask(Context ctx){
            this.ctx = ctx;
        }

        @Override
        protected Boolean doInBackground(String... input) {
            System.out.println(" *************************** " + input[0] + " :::CHECKING INPUT[0]");
            System.out.println(" *************************** " + input[1] + " :::CHECKING INPUT[1]");
            String title = input[0];
            URL url = stringToURL(input[1]);


            try {
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                System.out.println("CONNECTION SUCCESS");

                InputStream inputStream = connection.getInputStream();

                BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);

                Bitmap bmp = BitmapFactory.decodeStream(bufferedInputStream);

                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.PNG, 0, outputStream);

                byte[] img_byte = outputStream.toByteArray();

                database.insert(title, img_byte);

                return true;

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                connection.disconnect();

                System.out.println("CONNECTION DISCONNECTED");
            }
            return false;
        }

        protected void onPostExecute(Boolean result){
            ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

            try{
                NetworkInfo info = connectivityManager.getActiveNetworkInfo();
                Boolean connect = info.isConnected();

                if (result == true && connect == true){
                    Toast.makeText(DownloadImages.this, "DATA SAVED", Toast.LENGTH_SHORT).show();
                    finish();

                } else if (result == false){
                    if (connect == false ){
                        Toast.makeText(DownloadImages.this, "UNABLE TO CONNECT TO INTERNET", Toast.LENGTH_SHORT).show();
                    }
                    Toast.makeText(DownloadImages.this, "INVALID URL", Toast.LENGTH_SHORT).show();
                    Toast.makeText(DownloadImages.this, "UNABLE TO SAVE DATA", Toast.LENGTH_SHORT).show();
                }

            } catch (Exception e){
                Toast.makeText(DownloadImages.this, "UNABLE TO CONNECT TO INTERNET", Toast.LENGTH_SHORT).show();
            }



        }


        protected URL stringToURL(String urlString){
            try{
                URL url = new URL(urlString);
                return url;
            } catch (MalformedURLException e){
                e.printStackTrace();
            }
            return null;

        }
    }

}
