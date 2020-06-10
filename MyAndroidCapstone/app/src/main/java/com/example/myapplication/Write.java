package com.example.myapplication;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Write extends Activity {
    String id;
    TextView tv_title;
    TextView tv_context;
    CheckBox checkBox;
    String result;
    long now;
    Date mDate;
    SimpleDateFormat simpleDateFormat;
    String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);
        Intent intent = getIntent();
        id=intent.getStringExtra("id");
        Log.d("글쓰기 페이지 인텐트 값 체크",intent.getExtras().getString("id"));

    }
    public void clickSave(View view){
        String[] word = {"필터링","테스트","최시창","볼드모트","윤구형"};
        tv_title = findViewById(R.id.tv_title);
        tv_context = findViewById(R.id.tv_context);
        String title = tv_title.getText().toString();
        String context = tv_context.getText().toString();
        now = System.currentTimeMillis();
        mDate = new Date(now);
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        date = simpleDateFormat.format(mDate);
        String filetercheck="";
        String filtertest="";
        WriteConn task = new WriteConn();

        try {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            for(int i=0;i<word.length;i++){
                if (context.contains(word[i])){
                    filetercheck += "'"+word[i]+"'"+ " ";
                    filtertest ="1";
                }
            }
            if(filtertest.equals("1")){
                alert.setMessage("금지된 단어 ["+ filetercheck +"] 를 입력하셨습니다");
                alert.show();
            }else {
                result = task.execute(id, date, title, context, "writepost").get();
                Log.d("글쓰기 result 출력 테스트: ", result);


                if (result.equals("Save success!")) {
                    alert.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            if (result.equals("Save success!")) {
                                finish();

                            }
                        }
                    });
                    alert.setMessage(result);
                    alert.show();
                } else {
                    alert.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();

                        }
                    });
                    alert.setMessage(result);
                    alert.show();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
