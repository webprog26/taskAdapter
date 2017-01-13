package com.example.webprog26.taskadapter.activities;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.webprog26.taskadapter.R;
import com.example.webprog26.taskadapter.fragments.AppsListFragment;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity_TAG";
    private static final String APPS_LIST_FRAGMENT = "apps_list_fragment";

    private AppsListFragment mAppsListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getSupportFragmentManager();

        mAppsListFragment = (AppsListFragment) fragmentManager.findFragmentByTag(APPS_LIST_FRAGMENT);

        if(null == mAppsListFragment){
            mAppsListFragment = new AppsListFragment();
            fragmentManager.beginTransaction().add(R.id.activity_main, mAppsListFragment, APPS_LIST_FRAGMENT).commit();
        }
    }
}
