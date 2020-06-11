package com.example.myapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainViewHolder> {

    Dae_MainActivity dae_mainActivity;
    private List<Board> mBoardList;

    public MainAdapter(Dae_MainActivity dae_mainActivity, List<Board> mBoardList) {
        this.dae_mainActivity = dae_mainActivity;
        this.mBoardList = mBoardList;
    }

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, final int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_main,parent,false);

        //MainViewHolder 클래스에 만든 인터페이스 사용
        MainViewHolder mainViewHolder = new MainViewHolder(itemView);
        mainViewHolder.setonClickListener(new MainViewHolder.ClickListener() {
            @Override
            public void onItemlongClick(View view, final int position) {
                AlertDialog.Builder builder = new AlertDialog.Builder(dae_mainActivity);
                String[] options = {"수정", "삭제"};
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(which ==0){
                            //수정
                            //get data
                            String id = mBoardList.get(position).getId();
                            String title = mBoardList.get(position).getTitle();
                            String contents = mBoardList.get(position).getContents();
                            String name = mBoardList.get(position).getName();
                            String area = mBoardList.get(position).getArea();
                            String work = mBoardList.get(position).getWork();
                            String day = mBoardList.get(position).getDay();

                            Intent intent = new Intent(dae_mainActivity, WriteActivity.class);
                            intent.putExtra("pId", id);
                            intent.putExtra("pTitle", title);
                            intent.putExtra("pContents", contents);
                            intent.putExtra("pName", name);
                            intent.putExtra("pArea", area);
                            intent.putExtra("pWork", work);
                            intent.putExtra("pDay", day);

                            dae_mainActivity.startActivity(intent);
                        }
                        if(which == 1){
                            //삭제
                            dae_mainActivity.deleteData(position);
                        }
                    }
                }).create().show();
            }
        });


        return mainViewHolder;
    }

    @Override
    public void onBindViewHolder(@ NonNull MainViewHolder holder, int position) {
        Board data = mBoardList.get(position);

        holder.mTitleTextView.setText(data.getTitle());
        holder.mContentsTextView.setText(data.getContents());
        holder.mNameTextView.setText(data.getName());
        holder.mAreaTextView.setText(data.getArea());
        holder.mWorkTextView.setText(data.getWork());
        holder.mDayTextView.setText(data.getDay()); //Board 클래스 미완성
    }

    @Override
    public int getItemCount() {  //보드 리스트 사이즈만큼
        return mBoardList.size();
    }
}
