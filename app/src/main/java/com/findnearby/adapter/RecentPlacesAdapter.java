package com.findnearby.adapter;


import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.findnearby.R;
import com.findnearby.database.SearchItem;

import java.util.ArrayList;


public class RecentPlacesAdapter extends RecyclerView.Adapter<RecentPlacesAdapter.ViewHolder> {
    private final ArrayList<SearchItem> mItemList;
    private Activity activity;


    public RecentPlacesAdapter(Activity activity, ArrayList<SearchItem> itemList) {
        this.activity = activity;
        this.mItemList = itemList;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_search_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.tvSearchItem.setText(mItemList.get(position).getName());

    }


    @Override
    public int getItemCount() {
        return mItemList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {


        private final TextView tvSearchItem;

        ViewHolder(View view) {
            super(view);
            tvSearchItem = view.findViewById(R.id.tvSearchItem);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
    }
}

