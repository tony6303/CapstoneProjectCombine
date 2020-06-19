package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class Join extends Activity {
    static EditText name;
    EditText id, pwd, tel, birth;
    Button joinBtn;
    SHA256 sha256;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);
        id= (EditText)findViewById(R.id.inputid);
        pwd= (EditText)findViewById(R.id.inputpwd);
        name = (EditText)findViewById(R.id.inputname);
        tel = (EditText)findViewById(R.id.inputtel);
        birth = (EditText)findViewById(R.id.inputbirth);
        joinBtn = (Button)findViewById(R.id.joinbtn2);

        joinBtn.setOnClickListener(btnListener);
    }

    class CustomTask extends AsyncTask<String, Void, String> {
        String sendMsg, receiveMsg;

        @Override
        protected String doInBackground(String... strings) {

            try{
                String str;
                String ip=MainActivity.ip;
                String inputurl=MainActivity.inputurl;
                URL url = new URL("http://"+ip+inputurl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();//url커넥션 객체 생성
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded"); //요청 속성 설정
                conn.setRequestMethod("POST");//데이터를 POST 방식으로 전송합니다.
                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());// 아웃풋스트림라이터 객체 생성
                sendMsg = "id="+strings[0]+"&pwd="+strings[1]+"&name="+strings[2]+"&tel="+strings[3]+"&birth="+strings[4]+"&type="+strings[5];
                //보낼 정보. GET방식으로 작성. ex) "id=myid483&pwd=1234";
                //회원가입처럼 보낼 데이터가 여러 개일 경우 &로 구분하여 작성
                osw.write(sendMsg);//OutputStreamWriter 객체를 이용해 sendMsg값 저장
                osw.flush(); // 비워주는 효과
                Log.d("확인22","조인부분 try들어옴");
                //jsp와 통신이 정상적으로 되었을 때 할 코드들입니다.
                if(conn.getResponseCode() == conn.HTTP_OK) {
                    InputStreamReader tmp = new InputStreamReader(conn.getInputStream(), "UTF-8");
                    BufferedReader reader = new BufferedReader(tmp);
                    StringBuffer buffer = new StringBuffer();
                    //jsp에서 보낸 값을 받음
                    while ((str = reader.readLine()) != null) {
                        buffer.append(str); // 스트링버퍼 객체에 str 추가
                    }
                    receiveMsg = buffer.toString();
                    return receiveMsg;
                } else {
                    Log.i("통신 결과", conn.getResponseCode()+"에러");
                    return "웹서버와 통신실패";
                    // 통신이 실패했을 때 실패한 이유를 알기 위한 로그
                }

            }catch (Exception e){
                e.printStackTrace();
                Log.d("에러2"," 조인 캐치문 들옴");
            }
            return receiveMsg; //스트링 버퍼에 저장된 값 리턴 jsp로 부터 받은 값임
        }

    }




    View.OnClickListener btnListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            sha256 = new SHA256();
            switch (view.getId()){
                case R.id.joinbtn2 :
                    String joinid = id.getText().toString();
                    String joinpwd = sha256.sha256(pwd.getText().toString());
                    String joinname=name.getText().toString();
                    String jointel=tel.getText().toString();
                    String joinbirth=birth.getText().toString();

                    try {
                        String result  = new CustomTask().execute(joinid,joinpwd,joinname,jointel,joinbirth,"join").get();
                        if(result.equals("id")) {
                            Toast.makeText(Join.this,"이미 존재하는 아이디입니다.",Toast.LENGTH_SHORT).show();

                        } else if(result.equals("ok")) {
                            Toast.makeText(Join.this,"회원가입을 축하합니다.",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Join.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }catch (Exception e) {e.getMessage();}
                    break;
            }


        }
    };
}
