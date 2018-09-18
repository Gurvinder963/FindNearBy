package com.findnearby.repo;

import com.findnearby.network.ApiServices;
import com.findnearby.response.PlacesResponse;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@Singleton
public class PlacesRepositry {
    private final ApiServices apiService;

    @Inject
    public PlacesRepositry(ApiServices apiService) {

        this.apiService = apiService;
    }



    public Observable<PlacesResponse.Root> findPlaces(String location, long radius, String searchKeyword, String googlePlaceApiKey) {
        return apiService.doPlaces(location,radius,searchKeyword,googlePlaceApiKey).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
