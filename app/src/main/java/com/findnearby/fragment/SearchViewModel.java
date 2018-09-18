package com.findnearby.fragment;

import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.view.View;
import android.widget.Toast;


import com.findnearby.AppConstants;
import com.findnearby.MyApplication;
import com.findnearby.repo.PlacesRepositry;
import com.findnearby.response.PlacesResponse;
import com.findnearby.util.SingleLiveEvent;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class SearchViewModel extends ViewModel {

    @Inject
    PlacesRepositry dashboardRepository;


    private final Context mContext;

    private long radius = 3 * 1000;
    private static final int MY_PERMISION_CODE = 10;

    public SingleLiveEvent<Object> getOnSearchBtnClick() {
        return onSearchBtnClick;
    }

    SingleLiveEvent<Object> onSearchBtnClick = new SingleLiveEvent<>();

    public SingleLiveEvent<ArrayList<PlacesResponse.CustomA>> getSearchListEvent() {
        return searchListEvent;
    }

    SingleLiveEvent<ArrayList<PlacesResponse.CustomA>> searchListEvent = new SingleLiveEvent<>();

    public SearchViewModel(Context context){

        mContext = context.getApplicationContext();
        MyApplication.getApp().appComponent.inject(this);


    }

    public void fetchPlaces(String location,String searchKeyword) {

        dashboardRepository.findPlaces(location, radius, searchKeyword, AppConstants.GOOGLE_PLACE_API_KEY).subscribe(new Observer<PlacesResponse.Root>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(PlacesResponse.Root value) {
                PlacesResponse.Root root = value;




                    switch (root.status) {
                        case "OK":
                            searchListEvent.setValue(root.customA);

                            break;
                        case "ZERO_RESULTS":
                            Toast.makeText(mContext, "No matches found near you", Toast.LENGTH_SHORT).show();

                            break;
                        case "OVER_QUERY_LIMIT":
                            Toast.makeText(mContext, "You have reached the Daily Quota of Requests", Toast.LENGTH_SHORT).show();

                            break;
                        default:
                            Toast.makeText(mContext, "Error", Toast.LENGTH_SHORT).show();

                            break;
                    }
                }


            @Override
            public void onError(Throwable e) {
                Toast.makeText(mContext, "Error  found.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onComplete() {

            }
        });



    }
    public void onSearchBtnClick(View view){

        onSearchBtnClick.call();
    }


}
