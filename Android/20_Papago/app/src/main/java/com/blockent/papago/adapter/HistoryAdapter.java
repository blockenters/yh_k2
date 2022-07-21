package com.blockent.papago.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.blockent.papago.R;
import com.blockent.papago.model.Papago;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    Context context;
    List<Papago> papagoList;

    public HistoryAdapter(Context context, List<Papago> papagoList) {
        this.context = context;
        this.papagoList = papagoList;
    }

    @NonNull
    @Override
    public HistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.history_row, parent, false);
        return new HistoryAdapter.ViewHolder(view) ;
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryAdapter.ViewHolder holder, int position) {
        Papago papago = papagoList.get(position);
        holder.txtText.setText(papago.text);
        holder.txtTranslatedText.setText(papago.translatedText);
    }

    @Override
    public int getItemCount() {
        return papagoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView txtText;
        TextView txtTranslatedText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtText = itemView.findViewById(R.id.txtText);
            txtTranslatedText = itemView.findViewById(R.id.txtTranslatedText);

        }
    }

}




