package com.example.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MapSearchActivity extends AppCompatActivity {
    EditText mapsite;
    Button mapsearch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapsearch);
        mapsite = findViewById(R.id.searchmapsitetext);

        mapsearch = findViewById(R.id.searchmapbtn2);
        mapsearch.setOnClickListener(btnListener1);
    }

    View.OnClickListener btnListener1 = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.searchmapbtn2 :
                    try {
                    String mapSite = mapsite.getText().toString();
                        Intent intent = new Intent (Intent.ACTION_VIEW,Uri.parse("https://www.google.com/maps/place/" +mapSite));
                        startActivity(intent);
                    }catch (Exception e) {e.getMessage();}
                    break;
            }
        }
    };
}
