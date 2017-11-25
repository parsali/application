package com.burs.parsa.myapplication;


import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.burs.parsa.myapplication.module.news;

public class information extends AppCompatActivity {
    ActionBar ab;
    TextView title;
    TextView description;
    TextView date;
    String link;
    ImageView imageView;
    MySingleton volleySingleton;
    ImageLoader imageLoader;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        Toolbar toolbar = (Toolbar) findViewById(R.id.myBestoolbar);
        setSupportActionBar(toolbar);
        ab = getSupportActionBar();
        ab.setDisplayShowTitleEnabled(false);
        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowHomeEnabled(true);
        title= (TextView)findViewById(R.id.titleInfo);
        description= (TextView)findViewById(R.id.description);
        date= (TextView)findViewById(R.id.dateInfo);
        imageView = (ImageView)findViewById(R.id.backdrop);
        Bundle extras = getIntent().getExtras();
        title.setText(extras.getString("title"));
        description.setText(extras.getString("description"));
        date.setText(extras.getString("date"));
        link = extras.getString("imageUrl");
        volleySingleton = MySingleton.getInstance(this);
        imageLoader = volleySingleton.getImageLoader();
        imageLoader.get(link, new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                imageView.setImageBitmap(response.getBitmap());

            }

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }
}
