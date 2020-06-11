package com.example.myapplication;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MainViewHolder extends RecyclerView.ViewHolder {
    TextView mTitleTextView;
    TextView mContentsTextView;
    TextView mNameTextView;
    TextView mAreaTextView;
    TextView mWorkTextView;
    TextView mDayTextView;

    View mView;

    public MainViewHolder(@NonNull View itemView) {
        super(itemView);

        mView = itemView;
        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mCliCkListener.onItemlongClick(v, getAdapterPosition());
                return true;
            }
        });

        mTitleTextView = itemView.findViewById(R.id.item_title_text);
        mContentsTextView = itemView.findViewById(R.id.item_contents_text);
        mNameTextView = itemView.findViewById(R.id.item_name_text);
        mAreaTextView = itemView.findViewById(R.id.item_area_text);
        mWorkTextView = itemView.findViewById(R.id.item_work_text);
        mDayTextView = itemView.findViewById(R.id.item_day_text);

    }

    //인터페이스  https://www.youtube.com/watch?v=In52xAjSTsM 11:20 참고
    private MainViewHolder.ClickListener mCliCkListener;
    public interface ClickListener {
        void onItemlongClick(View view, int position);
    }
        public void setonClickListener(MainViewHolder.ClickListener clickListener){
            mCliCkListener = clickListener;
        }
    }

