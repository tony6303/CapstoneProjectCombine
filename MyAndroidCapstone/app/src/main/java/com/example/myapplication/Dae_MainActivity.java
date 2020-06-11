package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

public class Dae_MainActivity extends AppCompatActivity {

    //파이어스토어
    private FirebaseFirestore mStore;

    private EditText mMainSearchAreaText;
    private String id;  //파이어베이스 primary key 설정용

    //리사이클러뷰 = 게시판 나오는 레이아웃
    private RecyclerView mMainRecyclerview;
    RecyclerView.LayoutManager layoutManager;

    //리사이클러뷰 어댑터
    private MainAdapter mAdapter;
    private List<Board> mBoardList;

    //내가만든 클래스의 생성자 사용
    //이미지 슬라이드용 (뷰페이저)
    MainImageAdapter mImageAdapter; //
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dae_activity_main);

        //파이어스토어 싱글턴패턴
        mStore = FirebaseFirestore.getInstance();

        mViewPager = findViewById(R.id.viewpager);
        mImageAdapter = new MainImageAdapter(this); //new 로 인스턴스 생성
        mViewPager.setAdapter(mImageAdapter);

        mMainRecyclerview = findViewById(R.id.main_recyclerview); //MainActivity RecyclerView
        mBoardList = new ArrayList<>();  //Board 객체를 담을 ArrayList
        layoutManager = new LinearLayoutManager(this);
        mMainRecyclerview.setLayoutManager(layoutManager);


        showData();


        mMainSearchAreaText = findViewById(R.id.search_main_area_text); //MainActivity EditText
        Button mMainSearchButton = findViewById(R.id.search_main_button); //MainActivity Button(검색)

        //검색 버튼을 누르면 발동하는 이벤트, 파이어베이스에서 title(Editext에 쓴 내용)으로 검색한다
        mMainSearchButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                //사용자 함수 하단에 정의
                searchData(mMainSearchAreaText.getText().toString());
            }
        });

        //초록색 햄버거버튼 함수 리스트 새로고침
        FloatingActionButton mMainGoDrawerButton = findViewById(R.id.godrawerbutton1);
        mMainGoDrawerButton.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                showData();
            }
        });

        //파란색 연필버튼 함수, WriteActivity 로 액티비티 이동
        FloatingActionButton mMainWriteButton = findViewById(R.id.writebutton1);
        mMainWriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Dae_MainActivity.this, WriteActivity.class));
            }
        });

        //청록색 사람모양버튼 함수, MainActivity로 액티비티이동 (로그인화면)
        FloatingActionButton mMainGoSiChangButton = findViewById(R.id.gosichangbutton);
        mMainGoSiChangButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Dae_MainActivity.this,MainActivity.class));
            }
        });
    }  // Oncreate 끝

    // 파이어베이스에서 실시간으로 데이터 읽어오기
    public void showData() {
        id = mStore.collection("board").document().getId();
        mStore.collection("board").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override

                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        mBoardList.clear();
                        for (DocumentSnapshot dc : task.getResult()){
                            Board data = new Board(dc.getString("id"),
                                    dc.getString("title"),
                                    dc.getString("contents"),
                                    dc.getString("name"),
                                    dc.getString("firstArea"),
                                    dc.getString("worklist"),
                                    dc.getString("weekendorweekday"));
                            /*
                            String id = (String)dc.getDocument().getData().get("id");
                            String title = (String)dc.getDocument().getData().get("title");
                            String contents = (String)dc.getDocument().getData().get("contents");
                            String name = (String)dc.getDocument().getData().get("name");
                            String area = (String) dc.getDocument().getData().get("firstArea");
                            String work = (String)dc.getDocument().getData().get("worklist");
                            String day = (String)dc.getDocument().getData().get("weekendorweekday");

                            Board data = new Board(id,title,contents,name,area,work,day); //생성자 호출
                             */

                            mBoardList.add(data);
                        }
                        mAdapter = new MainAdapter(Dae_MainActivity.this, mBoardList);
                        mMainRecyclerview.setAdapter(mAdapter);

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("TAG",e.getMessage());
            }
        });
/*
        mStore.collection("board")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        for (DocumentChange dc : queryDocumentSnapshots.getDocumentChanges()){
                            String id = (String)dc.getDocument().getData().get("id");
                            String title = (String)dc.getDocument().getData().get("title");
                            String contents = (String)dc.getDocument().getData().get("contents");
                            String name = (String)dc.getDocument().getData().get("name");
                            String area = (String) dc.getDocument().getData().get("firstArea");
                            String work = (String)dc.getDocument().getData().get("worklist");
                            String day = (String)dc.getDocument().getData().get("weekendorweekday");

                            Board data = new Board(id,title,contents,name,area,work,day); //생성자 호출

                            mBoardList.add(data);
                        }
                        mAdapter = new MainAdapter(Dae_MainActivity.this, mBoardList);
                        mMainRecyclerview.setAdapter(mAdapter);
                    }
                });

 */

    }

    //Query문을 사용하여 데이터를 검색하는 사용자 함수      https://url.kr/E97l3a 참고
    public void searchData(String s){
        //FireStore에서 Query Like문을 사용하고 싶었는데 어떻게 쓰는지 알 수가 없었습니다. 자료가 없네요..
        mStore.collection("board").whereEqualTo("firstArea",s) //검색하는 글자가 정확히 일치해야한다.
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() { //스냅샷 리스너가 아니라 컴플리트 리스너입니다. 차이는 모름
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        mBoardList.clear();  //원래 있었던 어댑터를 초기화시킵니다.
                        for(DocumentSnapshot dc : task.getResult()){
                            String id = dc.getString("id");
                            String title = dc.getString("title");
                            String contents = dc.getString("contents");
                            String name = dc.getString("name");
                            String area = dc.getString("firstArea");
                            String work = dc.getString("worklist");
                            String day = dc.getString("weekendorweekday");

                            Board data = new Board(id,title,contents,name,area,work,day);

                            mBoardList.add(data);
                        }
                        mAdapter = new MainAdapter(Dae_MainActivity.this, mBoardList);
                        mMainRecyclerview.setAdapter(mAdapter);
                        Toast.makeText(Dae_MainActivity.this,"검색완료",Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("TAG", e.getMessage());
                    }
                });
    }

    //글 삭제
    public void deleteData(int index){
        mStore.collection("board").document(mBoardList.get(index).getId())
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(Dae_MainActivity.this,"삭제 완료",Toast.LENGTH_SHORT).show();
                        showData();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Dae_MainActivity.this,"삭제 실패",Toast.LENGTH_SHORT).show();
                Log.d("TAG", e.getMessage());
            }
        });
        /*
        mStore.collection("board").document(mBoardList.get(index).getId())
                .delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(Dae_MainActivity.this,"삭제 완료",Toast.LENGTH_SHORT).show();
                        showData();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Dae_MainActivity.this,"삭제 실패",Toast.LENGTH_SHORT).show();
                Log.d("TAG", e.getMessage());
            }
        });

         */
    }

}
