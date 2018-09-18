package com.findnearby.util;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.view.inputmethod.InputMethodManager;

public class Utils {


    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity
                .getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (null != activity.getCurrentFocus()) {
            inputMethodManager.hideSoftInputFromWindow(activity
                    .getCurrentFocus().getWindowToken(), 0);
        }
    }
    public static void replaceFragmentWithoutAnimation(android.support.v4.app.FragmentManager fragmentManager, Fragment fragment, String tag, boolean addTobackStack, int container) {

        // transaction
        android.support.v4.app.FragmentTransaction ft = fragmentManager.beginTransaction();
        if (addTobackStack) {
            ft.addToBackStack(tag);
        }

        ft.replace(container, fragment, tag);
        ft.commit();
    }
}
