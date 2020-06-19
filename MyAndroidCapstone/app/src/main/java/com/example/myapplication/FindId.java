package com.example.myapplication;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class FindId extends AsyncTask<String, Void, String> {
    String sendMsg, receiveMsg;

    @Override
    protected String doInBackground(String... strings) {
        ;
        try{
            String str;
            String ip=MainActivity.ip;
            URL url = new URL("http://"+ip+":8080/mycap_team/index.jsp");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();//url커넥션 객체 생성
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded"); //요청 속성 설정
            conn.setRequestMethod("POST");//데이터를 POST 방식으로 전송합니다.
            OutputStreamWriter osw = new OutputStreamWriter(conn.getOutputStream());// 아웃풋스트림라이터 객체 생성

            sendMsg = "tel="+strings[0]+"&birth="+strings[1]+"&type="+strings[2];
            //보낼 정보. GET방식으로 작성. ex) "id=myid483&pwd=1234";
            //회원가입처럼 보낼 데이터가 여러 개일 경우 &로 구분하여 작성
            osw.write(sendMsg);//OutputStreamWriter 객체를 이용해 sendMsg값 저장
            osw.flush(); // 비워주는 효과


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

}