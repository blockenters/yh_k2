package com.blockent.memoapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.blockent.memoapp.R;
import com.blockent.memoapp.model.Memo;

import java.util.List;

public class MemoAdapter extends RecyclerView.Adapter<MemoAdapter.ViewHolder> {

    // 이 어댑터를 사용하는, 액티비티의 this
    Context context;
    List<Memo> memoList;

    public MemoAdapter(Context context, List<Memo> memoList) {
        this.context = context;
        this.memoList = memoList;
    }

    @NonNull
    @Override
    public MemoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.memo_row, parent, false);
        return new MemoAdapter.ViewHolder(view) ;
    }

    @Override
    public void onBindViewHolder(@NonNull MemoAdapter.ViewHolder holder, int position) {
        // 해당 행의 위치에 맞는, 메모데이터를 리스트에서 가져온다.
        Memo memo = memoList.get(position);

        // 메모 객체에 있는 타이틀과 내용을 화면에 셋팅해 준다.
        holder.txtTitle.setText(memo.title);
        holder.txtContent.setText(memo.content);

    }

    @Override
    public int getItemCount() {
        return memoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView txtTitle;
        TextView txtContent;
        ImageView imgDelete;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtContent = itemView.findViewById(R.id.txtContent);
            imgDelete = itemView.findViewById(R.id.imgDelete);
            cardView = itemView.findViewById(R.id.cardView);

            imgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // todo
                }
            });

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // todo
                }
            });

        }
    }

}
