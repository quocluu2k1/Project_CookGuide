package com.example.cookguide.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cookguide.R;
import com.example.cookguide.interfaces.IClickItemSuggestionSearch;
import com.example.cookguide.models.HistorySearch;

import java.util.List;

public class SuggestionSearchAdapter extends RecyclerView.Adapter<SuggestionSearchAdapter.ViewHolder> {

    List<HistorySearch> historySearchList;
    Context context;
    IClickItemSuggestionSearch iClickItemSuggestionSearch;

    public SuggestionSearchAdapter(List<HistorySearch> historySearchList, Context context, IClickItemSuggestionSearch iClickItemSuggestionSearch) {
        this.historySearchList = historySearchList;
        this.context = context;
        this.iClickItemSuggestionSearch = iClickItemSuggestionSearch;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_suggestion_search,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textViewContentSuggestionSearch.setText(historySearchList.get(position).getContent());
        HistorySearch historySearch = historySearchList.get(position);
        holder.imageButtonSelectSuggestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iClickItemSuggestionSearch.onClickItem(historySearch);
            }
        });
    }

    @Override
    public int getItemCount() {
        return historySearchList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView textViewContentSuggestionSearch;
        ImageButton imageButtonSelectSuggestion;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewContentSuggestionSearch = itemView.findViewById(R.id.textViewContentSuggestionSearch);
            imageButtonSelectSuggestion = itemView.findViewById(R.id.imageButtonSelectSuggestion);
        }
    }
}
