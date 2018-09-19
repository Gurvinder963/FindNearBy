package com.findnearby;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.findnearby.fragment.SearchFragment;
import com.findnearby.response.PlacesResponse;
import com.findnearby.util.Utils;

import com.google.android.gms.location.LocationRequest;


public class MainActivity extends AppCompatActivity {






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        SearchFragment fragment = SearchFragment.newInstance();
        Utils.replaceFragmentWithoutAnimation(getSupportFragmentManager(), fragment, SearchFragment.class.getSimpleName(), true, R.id.fragmentContainer);


    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)

    {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (getCurrentFragmentName(SearchFragment.class.getSimpleName())) {
            Fragment fragment = getSupportFragmentManager().findFragmentByTag(SearchFragment.class.getSimpleName());
            if (fragment != null) {
                fragment.onRequestPermissionsResult(requestCode, permissions, grantResults);
            }
        }


    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (getCurrentFragmentName(SearchFragment.class.getSimpleName())) {
            Fragment fragment = getSupportFragmentManager().findFragmentByTag(SearchFragment.class.getSimpleName());
            if (fragment != null) {
                fragment.onActivityResult(requestCode, resultCode, data);

            }
        }


    }
    @Override
    public void onBackPressed() {

            int count = getSupportFragmentManager().getBackStackEntryCount();
            if (count == 1)
            {
                finish();
            } else {
                super.onBackPressed();

            }
        }
    private boolean getCurrentFragmentName(String fragmentName) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        String fragmentTag = fragmentManager.getBackStackEntryAt(fragmentManager.getBackStackEntryCount() - 1).getName();
        return fragmentTag.equalsIgnoreCase(fragmentName);
    }

}
