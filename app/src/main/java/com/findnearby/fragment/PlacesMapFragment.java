package com.findnearby.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.findnearby.R;
import com.findnearby.adapter.PlacesAdapter;
import com.findnearby.response.PlacesResponse;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


import java.util.ArrayList;

public class PlacesMapFragment extends Fragment implements OnMapReadyCallback {
    static ArrayList<PlacesResponse.CustomA> mResults;
    private RecyclerView recyclerView;
    private PlacesAdapter adapter;

    FrameLayout flMap;

    public static PlacesMapFragment newInstance(ArrayList<PlacesResponse.CustomA> results) {
        PlacesMapFragment fragment = new PlacesMapFragment();
        mResults = results;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.map, container, false);

        recyclerView =  v.findViewById(R.id.recyclerView);
        ImageView ivSwitch= v.findViewById(R.id.ivSwitch);

         flMap= v.findViewById(R.id.flMap);

        ivSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(recyclerView.getVisibility()==View.GONE){
                    recyclerView.setVisibility(View.VISIBLE);
                    flMap.setVisibility(View.GONE);
                }
                else {
                    recyclerView.setVisibility(View.GONE);
                    flMap.setVisibility(View.VISIBLE);
                }
            }
        });

        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);


        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);


        return v;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
       /* if (mapFragment == null) {
            mapFragment = SupportMapFragment.newInstance();
        }*/
        mapFragment.getMapAsync(this);


        adapter = new PlacesAdapter(getActivity(), mResults, "");
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        GoogleMap mMap = googleMap;

        //seattle coordinates

        for (int i = 0; i < mResults.size(); i++) {
            LatLng seattle = new LatLng(mResults.get(i).geometry.locationA.lat, mResults.get(i).geometry.locationA.lng);
            mMap.addMarker(new MarkerOptions().position(seattle).title(mResults.get(i).name));
        }
        // mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(mResults.get(mResults.size()-1).geometry.locationA.lat, mResults.get(mResults.size()-1).geometry.locationA.lng)));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mResults.get(mResults.size() - 1).geometry.locationA.lat, mResults.get(mResults.size() - 1).geometry.locationA.lng), 14.0f));

    }


}
