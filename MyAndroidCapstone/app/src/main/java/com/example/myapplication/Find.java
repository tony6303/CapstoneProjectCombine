package com.example.myapplication;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class Find extends AppCompatActivity {
    SHA256 sha256;
    EditText inputtel1;
    EditText inputbirth1;

    EditText inputid1;
    EditText inputtel2;
    EditText inputbirth2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);
    }
    public void clickFindID(View v){
        String result;
        inputtel1 = findViewById(R.id.inputtel1);
        inputbirth1 = findViewById(R.id.inputbirth1);
        String inputtel = inputtel1.getText().toString();
        String inputbirth = inputbirth1.getText().toString();

        try{
            FindId task = new FindId();
            result = task.execute(inputtel,inputbirth,"findid").get();
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            alert.setMessage(result);
            alert.show();

        }catch(Exception e){
            Toast.makeText(this,"오류발생",Toast.LENGTH_LONG).show();
        }
    }
    public void clickFindPWD(View v){
        sha256 = new SHA256();
        final String result;
        inputtel2 = findViewById(R.id.inputtel2);
        inputbirth2 = findViewById(R.id.inputbirth2);
        inputid1 = findViewById(R.id.inputid1);
        String tel = inputtel2.getText().toString();
        String inputbirth = inputbirth2.getText().toString();
        final String inputid = inputid1.getText().toString();

        try{
            FindPwd task = new FindPwd();
            result = task.execute(inputid,tel,inputbirth,"findpwd").get();

            if(result.equals("ok")) {

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                LayoutInflater inflater = getLayoutInflater();
                View view = inflater.inflate(R.layout.dialog_changepwd, null);
                builder.setView(view);
                AlertDialog dialog = builder.create();
                dialog.show();
                Button changebtn = view.findViewById(R.id.changebtn);
                final EditText inputchangepwd = view.findViewById(R.id.inputchangepwd);
                final EditText inputchangepwd2 = view.findViewById(R.id.inputchangepwd2);

                changebtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(inputchangepwd.getText().toString().equals(inputchangepwd2.getText().toString())){
                            String result;
                            ChangePwd task = new ChangePwd();
                            try {
                                result = task.execute(inputid, sha256.sha256(inputchangepwd2.getText().toString()),"changepwd").get();
                                AlertDialog.Builder alert = new AlertDialog.Builder(Find.this);
                                alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        finish();
                                    }
                                });
                                alert.setMessage("Successful change!");
                                alert.show();
                            }catch(Exception e){
                                Toast.makeText(Find.this,"오류발생",Toast.LENGTH_LONG).show();
                            }
                        }else{
                            AlertDialog.Builder alert = new AlertDialog.Builder(Find.this);
                            alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            alert.setMessage("비밀번호가 일치하지 않습니다!");
                            alert.show();

                        }
                    }
                });


            }else{
                AlertDialog.Builder alert = new AlertDialog.Builder(this);
                alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alert.setMessage("테스트 메세지"+result);
                alert.show();
            }

        }catch(Exception e){
            Toast.makeText(this,"오류발생",Toast.LENGTH_LONG).show();
        }

    }
}
