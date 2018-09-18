
package com.findnearby.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.findnearby.R;
import com.findnearby.response.PlacesResponse;

import java.util.ArrayList;


public class PlacesAdapter extends RecyclerView.Adapter<PlacesAdapter.MyViewHolder> {

    private ArrayList<PlacesResponse.CustomA> storeModels;
    private Context context;
    private String current_address;




    public PlacesAdapter(Context context, ArrayList<PlacesResponse.CustomA> storeModels, String current_address) {

        this.context = context;
        this.storeModels = storeModels;
        this.current_address = current_address;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_listitem, parent, false);

        return new MyViewHolder(itemView, viewType);

    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.res_name.setText(storeModels.get(position).name);


        holder.res_address.setText(storeModels.get(position).vicinity);


    }

    @Override
    public int getItemCount() {

        return storeModels.size();

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView res_name;

        TextView res_address;


        public MyViewHolder(final View itemView, final int viewType) {
            super(itemView);


            this.res_name = (TextView) itemView.findViewById(R.id.name);

            this.res_address = (TextView) itemView.findViewById(R.id.address);


        }


    }
}

