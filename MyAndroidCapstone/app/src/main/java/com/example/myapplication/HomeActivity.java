package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class HomeActivity extends Activity {

    String id,resultcount;
    ArrayList resultTitleList;
    //String pwd=intent.getExtras().getString("pwd");
    Button WriterBtn1;
    Button MapBtn1;
    Button ReportBtn1;
    Button MapSearch1;
    Button Thread1;
    ListView listview = null ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Intent intent = getIntent();

        //Log.d("1차 홈액티비티 테스트",id);
        WriterBtn1 = (Button)findViewById(R.id.writerbtn1);
        WriterBtn1.setOnClickListener(btnListener1);
        MapBtn1 = (Button)findViewById(R.id.mapbtn);
        MapBtn1.setOnClickListener(btnListener2);
        ReportBtn1 = (Button)findViewById(R.id.reportbtn);
        ReportBtn1.setOnClickListener(btnListener3);
        MapSearch1 = (Button)findViewById((R.id.mapsearchbtn));
        MapSearch1.setOnClickListener(btnListener4);
        Thread1 = (Button)findViewById(R.id.writethreadbtn);
        Thread1.setOnClickListener(btnListener5);

        HomeConn task = new HomeConn();
        // LinearLayout linearLayout = (LinearLayout) findViewById(R.id.homelistView);
        //Button button= new AppCompatButton(this);
        //button.setText("코드로 만든 버튼");

        try{
            resultcount=task.execute("count").get();
            //resultTitleList = task.execute("gettitle").get();
            // 반복 시작
            //
            /*
            for ( int i = 0; i < Integer.parseInt(resultcount); i++) {
                Button button = new AppCompatButton(this);
                button.setText("게시글"); // 여기에 타이틀 넣어야함 .. 공부중
                linearLayout.addView(button);
            }*/
            //
            // 반복 끝
        }catch(Exception e){
            e.getMessage();
        }
        //Log.d("글쓰기 페이지 인텐트 값 체크",intent.getExtras().getString("id"));

    }



    View.OnClickListener btnListener1 = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.writerbtn1 :
                    try {
                        Intent intent = new Intent(HomeActivity.this, Write.class);
                        //Log.d("2차 홈액티비티 테스트",id);
                        startActivity(intent);
                        //finish();

                    }catch (Exception e) {e.getMessage();}
                    break;
            }
        }
    };

    View.OnClickListener btnListener2 = new View.OnClickListener() {



        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.mapbtn :
                    try {
                        Intent intent = new Intent(HomeActivity.this, MapActivity.class);
                        startActivity(intent);
                        startActivity(intent);
                    }catch (Exception e) {e.getMessage();}
                    break;
            }
        }
    };

    View.OnClickListener btnListener3 = new View.OnClickListener() {



        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.reportbtn :
                    try {
                        Intent intent = new Intent(HomeActivity.this, ReportActivity.class);
                        startActivity(intent);
                    }catch (Exception e) {e.getMessage();}
                    break;
            }
        }
    };

    View.OnClickListener btnListener4 = new View.OnClickListener() {



        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.mapsearchbtn :
                    try {
                        Intent intent = new Intent(HomeActivity.this, MapSearchActivity.class);
                        startActivity(intent);
                    }catch (Exception e) {e.getMessage();}
                    break;
            }
        }
    };

    View.OnClickListener btnListener5 = new View.OnClickListener() {



        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.writethreadbtn :
                    try {
                        Intent intent = new Intent(HomeActivity.this, Dae_MainActivity.class);
                        startActivity(intent);

                    }catch (Exception e) {e.getMessage();}
                    break;
            }
        }
    };

}
