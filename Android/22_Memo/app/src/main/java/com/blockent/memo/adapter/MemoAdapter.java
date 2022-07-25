package com.blockent.memo.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.blockent.memo.EditActivity;
import com.blockent.memo.MainActivity;
import com.blockent.memo.R;
import com.blockent.memo.model.Memo;

import java.util.List;

public class MemoAdapter extends RecyclerView.Adapter<MemoAdapter.ViewHolder> {

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
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MemoAdapter.ViewHolder holder, int position) {
        Memo memo = memoList.get(position);

        holder.txtTitle.setText(memo.getTitle());
        holder.txtDate.setText( memo.getDate().replace("T", " ").substring(0, 15+1)  );
        holder.txtContent.setText(memo.getContent());

    }

    @Override
    public int getItemCount() {
        return memoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtTitle;
        TextView txtDate;
        TextView txtContent;

        CardView cardView;
        ImageView imgDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtDate = itemView.findViewById(R.id.txtDate);
            txtContent = itemView.findViewById(R.id.txtContent);
            cardView = itemView.findViewById(R.id.cardView);
            imgDelete = itemView.findViewById(R.id.imgDelete);

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int index = getAdapterPosition();
                    Memo memo = memoList.get(index);

                    Intent intent = new Intent(context, EditActivity.class);
                    intent.putExtra("memo", memo);
                    context.startActivity(intent);

                }
            });

            imgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    // 여기에 알러트다이얼로그 띄우고
                    // 알러트다이얼로그에서 YES 버튼 누르면,
                    // 네트워크로 API 호출하여, 해당 메모 삭제하고,
                    // 삭제하고 나면, 화면에 반영해줘야한다.

                    // 위의 일을! 메인액티비티에서하는 함수를 만들고,
                    // 여기서는 메인액티비티의 함수만 호출해 주자!

                    int index = getAdapterPosition();

                    ((MainActivity)context).deleteMemo(index);

                }
            });

        }
    }
}





