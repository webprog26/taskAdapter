package com.example.webprog26.taskadapter.fragments;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.webprog26.taskadapter.R;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

/**
 * Created by webprog26 on 13.01.2017.
 */

public class AppsListFragment extends Fragment {

    private static final String TAG = "AppsListFragment";
    private Subscription mAppsListSubscription;
    private ProgressBar mPbLoadingStatus;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_apps_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPbLoadingStatus = (ProgressBar) view.findViewById(R.id.pbLoadingStatus);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final PackageManager packageManager = getActivity().getPackageManager();

        PublishSubject<PackageManager> resolveInfoPublishSubject = PublishSubject.create();
        mAppsListSubscription = resolveInfoPublishSubject
                .observeOn(Schedulers.io()).map(new Func1<PackageManager, List<ResolveInfo>>() {
                    @Override
                    public List<ResolveInfo> call(PackageManager packageManager) {
                        return getLauncherActivitiesList(packageManager);
                    }
                }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<List<ResolveInfo>>() {
                    @Override
                    public void onCompleted() {
                        if(mPbLoadingStatus.getVisibility() == View.VISIBLE){
                            mPbLoadingStatus.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(List<ResolveInfo> resolveInfos) {
                        for(ResolveInfo resolveInfo: resolveInfos){
                            Log.i(TAG, resolveInfo.loadLabel(packageManager).toString());
                        }
                    }
                });

        resolveInfoPublishSubject.onNext(packageManager);
        resolveInfoPublishSubject.onCompleted();


    }

    private List<ResolveInfo> getLauncherActivitiesList(final PackageManager packageManager){
        Intent getAppsIntent = new Intent(Intent.ACTION_MAIN);
        getAppsIntent.addCategory(Intent.CATEGORY_LAUNCHER);

        List<ResolveInfo> activities = packageManager.queryIntentActivities(getAppsIntent, 0);

        Collections.sort(activities, new Comparator<ResolveInfo>() {
            @Override
            public int compare(ResolveInfo a, ResolveInfo b) {
                return String.CASE_INSENSITIVE_ORDER
                        .compare(
                                a.loadLabel(packageManager).toString(),
                                b.loadLabel(packageManager).toString()
                        );
            }
        });

        return activities;
    }

    @Override
    public void onPause() {
        super.onPause();
        if(null != mAppsListSubscription && getActivity().isFinishing()){
            mAppsListSubscription.unsubscribe();
        }
    }
}
