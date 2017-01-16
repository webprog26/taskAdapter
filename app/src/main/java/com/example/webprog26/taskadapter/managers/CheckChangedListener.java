package com.example.webprog26.taskadapter.managers;

import android.util.Log;
import android.widget.RadioGroup;

import com.example.webprog26.taskadapter.R;
import com.example.webprog26.taskadapter.models.AppsListItemModel;

/**
 * Created by webpr on 16.01.2017.
 */

public class CheckChangedListener implements RadioGroup.OnCheckedChangeListener {

    private static final String TAG = "CheckChangedListener";

    private AppsListItemModel mAppsListItemModel;

    public CheckChangedListener(AppsListItemModel mAppsListItemModel) {
        this.mAppsListItemModel = mAppsListItemModel;
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId){
            case R.id.rbEducation:
                Log.i(TAG, mAppsListItemModel.getAppLabel() + " is now educational");
                mAppsListItemModel.setEducational(true);
                mAppsListItemModel.setForFun(false);
                mAppsListItemModel.setBlocked(false);
                break;
            case R.id.rbBlocked:
                Log.i(TAG, mAppsListItemModel.getAppLabel() + " is now blocked");
                mAppsListItemModel.setEducational(false);
                mAppsListItemModel.setForFun(false);
                mAppsListItemModel.setBlocked(true);
                break;
            case R.id.rbForFun:
                Log.i(TAG, mAppsListItemModel.getAppLabel() + " is now for fun");
                mAppsListItemModel.setEducational(false);
                mAppsListItemModel.setForFun(true);
                mAppsListItemModel.setBlocked(false);
                break;
        }
        Log.i(TAG, mAppsListItemModel.toString());
    }
}
