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

import com.blockent.posting.PostingListActivity;
import com.blockent.posting.R;
import com.blockent.posting.config.Config;
import com.blockent.posting.model.Posting;
import com.bumptech.glide.Glide;

import java.util.List;

public class FollowPostingAdapter extends RecyclerView.Adapter<FollowPostingAdapter.ViewHolder> {

    Context context;
    List<Posting> postingList;

    public FollowPostingAdapter(Context context, List<Posting> postingList) {
        this.context = context;
        this.postingList = postingList;
    }

    @NonNull
    @Override
    public FollowPostingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.posting_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FollowPostingAdapter.ViewHolder holder, int position) {
        Posting posting = postingList.get(position);
        holder.txtContent.setText(posting.getContent());
        holder.txtName.setText(posting.getName());
        holder.txtCreatedAt.setText(posting.getCreatedAt());
        holder.txtLikeCnt.setText(""+posting.getLikeCnt());

        if(posting.getIsLike() == 1){
            holder.imgLike.setImageResource(R.drawable.ic_thumb_up);
        }else{
            holder.imgLike.setImageResource(R.drawable.ic_thumb_up_off);
        }

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
        TextView txtContent;
        TextView txtName;
        TextView txtCreatedAt;
        TextView txtLikeCnt;
        ImageView imgPhoto;
        ImageView imgLike;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.cardView);
            txtContent = itemView.findViewById(R.id.txtContent);
            txtName = itemView.findViewById(R.id.txtName);
            txtCreatedAt = itemView.findViewById(R.id.txtCreatedAt);
            txtLikeCnt = itemView.findViewById(R.id.txtLikeCnt);
            imgPhoto = itemView.findViewById(R.id.imgPhoto);
            imgLike = itemView.findViewById(R.id.imgLike);

            imgLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // 메인액티비티에 함수 만들어 놓고
                    // 그 함수 호출하자.
                    int index = getAdapterPosition();
                    ((PostingListActivity)context).setLike(index);
                }
            });

        }
    }
}
