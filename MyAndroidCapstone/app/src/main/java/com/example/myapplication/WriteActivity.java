package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class WriteActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Dae_MainActivity dae_mainActivity;

    private FirebaseFirestore mStore;

    private EditText mWriteTitleText;
    private EditText mWriteContentsText;
    private EditText mWriteNameText;
    private Button mWriteUploadButton; //플로팅액션 버튼 (파란연필)

    private String[] ArrayFirstArea = {"부산", "김해", "창원"}; //첫 번째 Spinner에 들어갈 배열
    private Spinner mFirstArea; //첫 번째 Spinner

    private String[] ArrayWorklist_busan = {"부산작물재배", "작물심기"}; //첫 번째 선택에 따라 들어갈 두 번째 Spinner 배열
    private String[] ArrayWorklist_kimhae = {"김해작물재배", "작물심기"};
    private String[] ArrayWorklist_chngwon = {"창원작물재배", "작물심기"};
    private Spinner mWorkList; //두 번째 Spinner

    private String[] ArrayWeek = {"주말", "평일"};  //세 번째 Spinner에 들어갈 배열
    private Spinner mWeekend; //세 번째 Spinner

    ArrayAdapter<String> arealist;
    ArrayAdapter<String> worklist;
    ArrayAdapter<String> weeklist;

    String pId, pTitle, pContents, pName, pArea, pWork, pDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write2);

        mStore  = FirebaseFirestore.getInstance();

        mWriteTitleText = findViewById(R.id.write_title_text);
        mWriteContentsText = findViewById(R.id.write_contents_text);
        mWriteNameText = findViewById(R.id.write_name_text);
        mWriteUploadButton = findViewById(R.id.write_upload_button);

        mFirstArea = (Spinner)findViewById(R.id.first_area);
        mFirstArea.setPrompt("지역 선택");

        arealist = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item, ArrayFirstArea);
        mFirstArea.setAdapter(arealist);
        //이게 있어야 override한 onItemSelect 함수를 쓸 수 있는것같습니다.
        mFirstArea.setOnItemSelectedListener(this);

        //FirstArea에 따라 바뀌게 하기위해 setAdapter함수는 아래에 override 해놓았습니다.
        mWorkList = (Spinner)findViewById(R.id.work_list);
        mWorkList.setPrompt("작업 선택");

        mWeekend = (Spinner)findViewById(R.id.weekend_or_weekday);
        mWeekend.setPrompt("날짜 선택");

        weeklist = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item, ArrayWeek);
        mWeekend.setAdapter(weeklist);

        final Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            //Update
            mWriteUploadButton.setText("수정");
            pId = bundle.getString("pId");
            pTitle = bundle.getString("pTitle");
            pContents = bundle.getString("pContents");
            pName = bundle.getString("pName");
            pArea = bundle.getString("pArea");
            pWork = bundle.getString("pWork");
            pDay = bundle.getString("pDay");

            //set data
            mWriteTitleText.setText(pTitle);
            mWriteContentsText.setText(pContents);
            mWriteNameText.setText(pName);
            mFirstArea.setSelection(arealist.getPosition(pArea));
            //mWorkList.setSelection(worklist.getPosition(pWork));
            // Spinner worklist를 잘 못찾는듯.  NullPointerException
            mWeekend.setSelection(weeklist.getPosition(pDay));

        }
        else{
            //new Data
            mWriteUploadButton.setText("글 작성");
        }

        //글 작성 버튼 이벤트함수
       mWriteUploadButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle1 = getIntent().getExtras();
                if (bundle != null){
                    //updating
                    //input data
                    String id = pId;
                    String title = mWriteTitleText.getText().toString();
                    String contents = mWriteContentsText.getText().toString();
                    String name = mWriteNameText.getText().toString();
                    String area = mFirstArea.getSelectedItem().toString();
                    String work = mWorkList.getSelectedItem().toString();
                    String day = mWeekend.getSelectedItem().toString();

                    updateData(id, title, contents, name, area, work, day);
                }
                else{
                    //adding new
                    //input data
                    String title = mWriteTitleText.getText().toString();
                    String contents = mWriteContentsText.getText().toString();
                    String name = mWriteNameText.getText().toString();
                    String area = mFirstArea.getSelectedItem().toString();
                    String work = mWorkList.getSelectedItem().toString();
                    String day = mWeekend.getSelectedItem().toString();

                    uploadData(title, contents, name, area, work, day);
                }

                /*
                mStore.collection("board").add(post)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(WriteActivity.this,"업로드성공",Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(WriteActivity.this,"업로드실패",Toast.LENGTH_SHORT).show();
                    }
                });
                */

            }
        });

    } //OnCreate 끝



    @Override
    //스피너가 선택되었을 때 동작하는 이벤트 함수
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        if(mFirstArea.getSelectedItem().toString().equals("부산")) { //지역 스피너가 부산이면 작업 스피너를 알맞게 setAdapt 한다. 이하동일
            worklist = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, ArrayWorklist_busan);
            mWorkList.setAdapter(worklist);
        } else if(mFirstArea.getSelectedItem().toString().equals("김해")){
            worklist = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, ArrayWorklist_kimhae);
            mWorkList.setAdapter(worklist);
        } else if(mFirstArea.getSelectedItem().toString().equals("창원")){
            worklist = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, ArrayWorklist_chngwon);
            mWorkList.setAdapter(worklist);
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    //새 글 작성
    private void uploadData(String title, String contents, String name, String area, String work, String day){
        String id = mStore.collection("board").document().getId();

        Map<String, Object> post = new HashMap<>();
        //파이어베이스 데이터베이스에 데이터를 put 합니다.
        post.put("id", id);

        //스피너 배열 속 아이템
        post.put("firstArea",mFirstArea.getSelectedItem().toString());
        post.put("worklist",mWorkList.getSelectedItem().toString());
        post.put("weekendorweekday",mWeekend.getSelectedItem().toString());

        //EditText 속 문자
        post.put("title", mWriteTitleText.getText().toString());
        post.put("contents", mWriteContentsText.getText().toString());
        post.put("name", mWriteNameText.getText().toString());

        //                         document인자로 id를 주면 됨, 인덱스찾는 단서
        mStore.collection("board").document(id).set(post)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(WriteActivity.this,"업로드성공",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(WriteActivity.this,"업로드실패",Toast.LENGTH_SHORT).show();
            }
        });
    }

    //글 수정
    private void updateData(String id, String title, String contents, String name, String area, String work, String day) {
        mStore.collection("board").document(id)
                .update("title", title, "contents", contents, "name", name, "firstArea" , area,"worklist", work, "weekendorweekday", day)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(WriteActivity.this,"수정 성공",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(WriteActivity.this,"수정 실패",Toast.LENGTH_SHORT).show();
                Log.d("TAG", e.getMessage());
            }
        });

    }
}
