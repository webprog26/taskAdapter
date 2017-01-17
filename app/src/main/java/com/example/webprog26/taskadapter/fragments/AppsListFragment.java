package com.example.webprog26.taskadapter.fragments;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.webprog26.taskadapter.R;
import com.example.webprog26.taskadapter.adapters.AppsListAdapter;
import com.example.webprog26.taskadapter.db.DbProvider;
import com.example.webprog26.taskadapter.managers.DrawableToBitmapConverter;
import com.example.webprog26.taskadapter.models.AppsListItemModel;

import java.util.ArrayList;
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
    private RecyclerView mAppsRecyclerView;

    private DbProvider mDbProvider;

    private LinearLayout mAppsCategoriesCountContainer;
    private TextView mTvTotalAppsCount;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_apps_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mDbProvider = new DbProvider(getActivity().getApplicationContext());
        mTvTotalAppsCount = (TextView) view.findViewById(R.id.tvTotalAppsCount);
        mAppsCategoriesCountContainer = (LinearLayout) view.findViewById(R.id.appsCountLayout);

        Log.i(TAG, "count " + mDbProvider.getAppsInDbCount());

        mPbLoadingStatus = (ProgressBar) view.findViewById(R.id.pbLoadingStatus);

        mAppsRecyclerView = (RecyclerView) view.findViewById(R.id.appsRecyclerView);
        mAppsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAppsRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAppsRecyclerView.setHasFixedSize(true);
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
                }).map(new Func1<List<ResolveInfo>, List<AppsListItemModel>>() {
                    @Override
                    public List<AppsListItemModel> call(List<ResolveInfo> resolveInfos) {
                        List<AppsListItemModel> appsListItemModels = new ArrayList<AppsListItemModel>();
                        for(ResolveInfo resolveInfo: resolveInfos){
                            String appLabel = resolveInfo.loadLabel(packageManager).toString();

                            AppsListItemModel model = new AppsListItemModel();
                            model.setAppId(appLabel.hashCode());
                            model.setAppLabel(appLabel);
                            model.setAppIcon(DrawableToBitmapConverter.drawableToBitmap(resolveInfo.loadIcon(packageManager)));
                            //Todo should add reading user choice from database for boolean fields
                            model.setEducational(false);
                            model.setBlocked(false);
                            model.setForFun(false);
                            model.setNeutral(true);
                            appsListItemModels.add(model);
                        }

                        if(mDbProvider.getAppsInDbCount() == 0){
                            mDbProvider.writeToDb(appsListItemModels);
                        }
                        return appsListItemModels;
                    }
                }).map(new Func1<List<AppsListItemModel>, List<AppsListItemModel>>() {
                    @Override
                    public List<AppsListItemModel> call(List<AppsListItemModel> appsListItemModels) {
                        for(AppsListItemModel appsListItemModel: appsListItemModels){
                                AppsListItemModel appsListItemModelFromDb = mDbProvider.getCategoriesValues(appsListItemModel.getAppLabel().hashCode());
                                appsListItemModel.setEducational(appsListItemModelFromDb.isEducational());
                                appsListItemModel.setForFun(appsListItemModelFromDb.isForFun());
                                appsListItemModel.setBlocked(appsListItemModelFromDb.isForFun());
                                appsListItemModel.setNeutral(appsListItemModelFromDb.isNeutral());
                        }
                        return appsListItemModels;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<List<AppsListItemModel>>() {
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
                    public void onNext(List<AppsListItemModel> appsListItemModels) {
                        for(AppsListItemModel appsListItemModel: appsListItemModels){
                            Log.i(TAG, appsListItemModel.toString());
                        }
                        AppsListAdapter adapter = new AppsListAdapter(appsListItemModels, getActivity());

                        int appsCount = adapter.getItemCount();
                        mTvTotalAppsCount.setText(appsCount + " applications");

                        if(mAppsCategoriesCountContainer.getVisibility() == View.GONE){
                            mAppsCategoriesCountContainer.setVisibility(View.VISIBLE);
                        }

                        mAppsRecyclerView.setItemViewCacheSize(appsCount);
                        mAppsRecyclerView.setAdapter(adapter);
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
