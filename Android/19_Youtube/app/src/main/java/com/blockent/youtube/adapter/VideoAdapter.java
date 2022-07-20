package com.blockent.youtube.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.blockent.youtube.R;
import com.blockent.youtube.config.Config;
import com.blockent.youtube.model.Video;
import com.bumptech.glide.Glide;

import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {

    Context context;
    List<Video> videoList;

    public VideoAdapter(Context context, List<Video> videoList) {
        this.context = context;
        this.videoList = videoList;
    }

    @NonNull
    @Override
    public VideoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.video_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoAdapter.ViewHolder holder, int position) {
        Video video = videoList.get(position);
        holder.txtTitle.setText(video.title);
        holder.txtDescription.setText(video.description);

        Glide.with(context).load(video.imgUrl).into(holder.imgThumb);

    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView txtTitle;
        TextView txtDescription;
        ImageView imgThumb;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtDescription = itemView.findViewById(R.id.txtDescription);
            imgThumb = itemView.findViewById(R.id.imgThumb);
            cardView = itemView.findViewById(R.id.cardView);

            imgThumb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // todo

                }
            });

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    int index = getAdapterPosition();
                    Video video = videoList.get(index);

                    // videoId 를 URL로 만들어서 웹브라우저 실행시킨다.
                    String url = Config.PLAY_URL + video.videoId;
                    openWebPage(url);

                }
            });

        }
        void openWebPage(String url){
            Uri webPage = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, webPage);
            context.startActivity(intent);
        }
    }
}




