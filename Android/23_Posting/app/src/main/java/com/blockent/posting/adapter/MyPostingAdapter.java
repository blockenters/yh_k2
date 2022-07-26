package com.blockent.posting.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.blockent.posting.R;
import com.blockent.posting.config.Config;
import com.blockent.posting.model.Posting;
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
    public MyPostingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_posting_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyPostingAdapter.ViewHolder holder, int position) {
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


        }
    }
}








