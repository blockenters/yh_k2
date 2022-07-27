package com.blockent.tab.adapter;

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


import com.blockent.tab.MainActivity;
import com.blockent.tab.R;
import com.blockent.tab.TestActivity;
import com.blockent.tab.config.Config;
import com.blockent.tab.model.Posting;
import com.bumptech.glide.Glide;

import java.util.List;


public class MyPostingAdapter extends RecyclerView.Adapter<MyPostingAdapter.ViewHolder> {

    Context context;
    List<Posting> postingList;

    public MyPostingAdapter(Context context, List<Posting> postingList) {
        this.context = context;
        this.postingList = postingList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_posting_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Posting posting = postingList.get(position);

        holder.txtContent.setText(posting.getContent());
        holder.txtCreatedAt.setText(posting.getCreatedAt());
        holder.txtLikeCnt.setText( "" + posting.getLikeCnt()  );

        Glide.with(context).load(Config.IMAGE_URL+posting.getImgUrl() )
                .placeholder(R.drawable.ic_default_photo)
                .into(  holder.imgPhoto  );

    }

    @Override
    public int getItemCount() {
        return postingList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        CardView cardView;
        ImageView imgPhoto;
        TextView txtContent;
        TextView txtCreatedAt;
        TextView txtLikeCnt;
        ImageView imgDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.cardView);
            imgPhoto = itemView.findViewById(R.id.imgPhoto);
            txtContent = itemView.findViewById(R.id.txtContent);
            txtCreatedAt = itemView.findViewById(R.id.txtCreatedAt);
            txtLikeCnt = itemView.findViewById(R.id.txtLikeCnt);
            imgDelete = itemView.findViewById(R.id.imgDelete);

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, TestActivity.class);
                    context.startActivity(intent);
                }
            });

        }
    }
}








