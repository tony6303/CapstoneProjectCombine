package com.example.myapplication;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    EditText userId, userPwd;
    Button loginBtn, joinBtn;
    SHA256 sha256;

    //학교 와이파이는 3306 포트가 막혀있다고합니다
    static final String ip ="192.168.0.19"; // cmd에서 ipconfig로 나온 자신의 ip를 넣어야함 클라우드에 tomcat 올리는중..
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("확인3","ㅎㅎ");
        userId = (EditText) findViewById(R.id.userId);
        userPwd = (EditText) findViewById(R.id.userPwd);
        loginBtn = (Button) findViewById(R.id.loginBtn);
        joinBtn = (Button) findViewById(R.id.joinBtn);
        loginBtn.setOnClickListener(btnListener);
        joinBtn.setOnClickListener(btnListener);


        /*
        data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String result;
                    CustomTask task = new CustomTask();
                    //execute의 매개값은
                    //sendMsg = "id="+strings[0]+"&pwd="+strings[1];
                    //doInBackround에서 사용된 문자열 배열에 필요한 값을 넣습니다.

                    result = task.execute("test483", "1234").get();
                    // 그리고 jsp로 부터 리턴값을 받아야할 때는
                    //String returns = task.execute("rain483","1234").get();
                    //처럼 사용하시면 되는데요. get()에서 에러가 발생할 수 있어서 try catch문으로
                    //감싸야에러가 나지 않습니다.
                    Log.i("테스트2","테스트");
                    Log.i("리턴 값",result);
                }catch (Exception e){
                    e.getStackTrace();
                }
            }
        });*/




    }
    public void clickFind(View v){    // login fragment의 아이디 찾기 클릭
        Intent it = new Intent(this,Find.class);
        startActivity(it);
    }


    class CustomTask extends AsyncTask<String, Void, String> {
        String sendMsg, receiveMsg;

        @Override
        protected String doInBackground(String... strings) {
            //Log.d("확인1","ㅎㅎ");
            try{
            String str;
                //String ip="172.17.83.56";
                //Url 주소 = ip:8080 뒤에 경로를 JSP가동중... 경로에 맞게 바꿔보세요
                URL url = new URL("http://"+ip+":8080/mycap_team_war_exploded/");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();//url커넥션 객체 생성
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded"); //요청 속성 설정
                conn.setRequestMethod("POST");//데이터를 POST 방식으로 전송합니다.
                OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());// 아웃풋스트림라이터 객체 생성
                sendMsg = "id="+strings[0]+"&pwd="+strings[1]+"&type="+strings[2];//보낼 정보. GET방식으로 작성. ex) "id=myid483&pwd=1234";
                //회원가입처럼 보낼 데이터가 여러 개일 경우 &로 구분하여 작성
                osw.write(sendMsg);//OutputStreamWriter 객체를 이용해 sendMsg값 저장
                osw.flush(); // 비워주는 효과
                //Log.d("확인2","ㅎㅎ");
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
            Log.d("에러","캐치문 들옴");
            }
            return receiveMsg; //스트링 버퍼에 저장된 값 리턴 jsp로 부터 받은 값임


        }

        @Override
        protected void onPostExecute(String s) {
            Log.d("PostExecute","act");
            super.onPostExecute(s);
        }
    }

    View.OnClickListener btnListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            sha256 = new SHA256();
            switch (view.getId()){
                case R.id.loginBtn : // 로그인 버튼 눌렀을 경우
                    String loginid = userId.getText().toString();
                    String loginpwd = sha256.sha256(userPwd.getText().toString());
                    try {
                        String result  = new CustomTask().execute(loginid,loginpwd,"login").get();
                        //Log.d("하이 로그인 아이디 테스트",loginid);
                        if(result.equals("true")) {
                            Toast.makeText(MainActivity.this,"로그인 성공",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                            intent.putExtra("id",loginid);
                           //intent.putExtra("pwd",loginpwd);
                            startActivity(intent);
                            finish();
                        } else if(result.equals("false")) {
                            Toast.makeText(MainActivity.this,"아이디 또는 비밀번호가 틀렸습니다",Toast.LENGTH_SHORT).show();
                            //userId.setText("");
                            //userPwd.setText("");
                        } else if(result.equals("noId")) {
                            Toast.makeText(MainActivity.this,"아이디 또는 비밀번호가 틀렸습니다",Toast.LENGTH_SHORT).show();
                            //userId.setText("");
                            //userPwd.setText("");
                        }
                    }catch (Exception e) {}
                    break;
                case R.id.joinBtn : // 회원가입
                    Intent intent = new Intent(MainActivity.this, Join.class);
                    startActivity(intent);
                    finish();
                    break;
                    /*
                    String joinid = userId.getText().toString();
                    String joinpwd = sha256.sha256(userPwd.getText().toString());
                    try {
                        String result  = new CustomTask().execute(joinid,joinpwd,"join").get();
                        if(result.equals("id")) {
                            Toast.makeText(MainActivity.this,"이미 존재하는 아이디입니다.",Toast.LENGTH_SHORT).show();
                            userId.setText("");
                            userPwd.setText("");
                        } else if(result.equals("ok")) {
                            userId.setText("");
                            userPwd.setText("");
                            Toast.makeText(MainActivity.this,"회원가입을 축하합니다.",Toast.LENGTH_SHORT).show();
                        }
                    }catch (Exception e) {}
                    break;
                    */
            }
        }
    };
}
