package com.example.assgnment2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button download;
    Button view;
    Button search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        download = (Button) findViewById(R.id.downloadImages);
        view = (Button) findViewById(R.id.viewImages);
        search = (Button) findViewById(R.id.searchImages);

        /**

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SearchImages.class));

            }
        }); **/



        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, DownloadImages.class));
            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ViewImages.class));
            }
        });
    }

    public void search_image(View view) {
        Intent i = new Intent(this, SearchImages.class);
        System.out.println("||||||||AHHHHHHHHH||||||||||||");
        startActivity(i);
    }

}
