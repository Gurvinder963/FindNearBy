package com.findnearby.fragment;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;


import com.findnearby.R;
import com.findnearby.adapter.RecentPlacesAdapter;
import com.findnearby.database.AppDatabase;
import com.findnearby.database.SearchItem;
import com.findnearby.databinding.FragmentSearchBinding;
import com.findnearby.interfaces.OnLoactionFindListner;
import com.findnearby.response.PlacesResponse;
import com.findnearby.util.GetLocation;
import com.findnearby.util.Utils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class SearchFragment extends Fragment implements OnLoactionFindListner {
    private static final int REQUEST_CHECK_SETTINGS = 21213;
    private EditText etSearch;
    private long radius = 3 * 1000;

    ArrayList<PlacesResponse.CustomA> results;
    private static final int MY_PERMISION_CODE = 10;

    private SearchViewModel searchViewModel;

    FragmentSearchBinding mBinding;
    private RecentPlacesAdapter recentPlacesAdapter;
    RecyclerView recyclerView;
    ArrayList<SearchItem> arrListRecentSearches;

    public static SearchFragment newInstance() {
        SearchFragment fragment = new SearchFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        results = new ArrayList<>();
        arrListRecentSearches = new ArrayList<>();
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false);
        return mBinding.getRoot();


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        searchViewModel = new SearchViewModel(getContext());
        mBinding.setViewModel(searchViewModel);

        recyclerView = mBinding.recyclerView;
        recentPlacesAdapter = new RecentPlacesAdapter(getActivity(), arrListRecentSearches);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recentPlacesAdapter);

        getRecentSearchFromDB();
        setObserver();
    }

    private void getRecentSearchFromDB() {
        AppDatabase db = AppDatabase.getAppDatabase(getActivity());
        arrListRecentSearches.addAll(db.userDao().getAll());
        recentPlacesAdapter.notifyDataSetChanged();

    }

    private void setObserver() {

        searchViewModel.getOnSearchBtnClick().observe(this, new Observer<Object>() {
            @Override
            public void onChanged(@Nullable Object circles) {

                askForPermission();
            }
        });
        searchViewModel.getSearchListEvent().observe(this, new Observer<ArrayList<PlacesResponse.CustomA>>() {
            @Override
            public void onChanged(@Nullable ArrayList<PlacesResponse.CustomA> list) {
                results.clear();
                results.addAll(list);
                PlacesMapFragment fragment = PlacesMapFragment.newInstance(results);
                Utils.replaceFragmentWithoutAnimation(getFragmentManager(), fragment, PlacesMapFragment.class.getSimpleName(), true, R.id.fragmentContainer);


            }
        });

    }

    public void askForPermission() {
        if (ActivityCompat.checkSelfPermission(getActivity(), ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION},
                    MY_PERMISION_CODE);

        } else {
            getLocation();

        }
    }

    private void getLocation() {
        new GetLocation(getActivity(), this).build();
    }


    @Override
    public void onFind(String loc) {

        if (mBinding.etSearch.getText().toString().trim().length() > 0) {
            insertInDataBase(mBinding.etSearch.getText().toString());
            searchViewModel.fetchPlaces(loc, mBinding.etSearch.getText().toString());
        } else {
            Toast.makeText(getActivity(), "Please Enter search keyword", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)

    {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {

            case MY_PERMISION_CODE:

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getLocation();

                } else {

                    Toast.makeText(getActivity(), "Please allow permission to access the features", Toast.LENGTH_LONG).show();


                }
                break;

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        getLocation();
                        break;
                    case Activity.RESULT_CANCELED:
                        Toast.makeText(getActivity(), "You must turn on the location to be able to search the location automatically.", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
                break;
        }
    }

    public void insertInDataBase(String searchItem) {

        AppDatabase db = AppDatabase.getAppDatabase(getActivity());
        SearchItem item = new SearchItem();
        item.setName(searchItem);
        db.userDao().insertAll(item);

    }
}
