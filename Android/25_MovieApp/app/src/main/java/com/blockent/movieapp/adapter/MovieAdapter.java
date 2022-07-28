package com.blockent.movieapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.blockent.movieapp.R;
import com.blockent.movieapp.ReviewListActivity;
import com.blockent.movieapp.model.Movie;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    Context context;
    List<Movie> movieList;

    public MovieAdapter(Context context, List<Movie> movieList) {
        this.context = context;
        this.movieList = movieList;
    }

    @NonNull
    @Override
    public MovieAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapter.ViewHolder holder, int position) {
        Movie movie = movieList.get(position);

        holder.txtTitle.setText(movie.getTitle());
        holder.txtCnt.setText("리뷰갯수("+movie.getCnt()+")");
        holder.txtAvg.setText("별점평균 "+movie.getAvg());
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        TextView txtTitle;
        TextView txtCnt;
        TextView txtAvg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.cardView);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtCnt = itemView.findViewById(R.id.txtCnt);
            txtAvg = itemView.findViewById(R.id.txtAvg);

            txtCnt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // 1. 어떤 영화를 선택했는지 정보를 가져온다.
                    int index = getAdapterPosition();
                    Movie movie = movieList.get(index);

                    // 2. 리뷰를 보여주는 액티비티에, 위의 영화정보를 넘겨준다.
                    Intent intent = new Intent(context, ReviewListActivity.class);
                    intent.putExtra("movie",movie);

                    context.startActivity(intent);

                }
            });
            txtAvg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // 위의 함수 다 만들고 나서 만들면 된다.
                }
            });

        }
    }
}









