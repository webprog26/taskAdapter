package com.example.webprog26.taskadapter.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.webprog26.taskadapter.R;
import com.example.webprog26.taskadapter.custom_views.AppsListItemViewGroup;
import com.example.webprog26.taskadapter.models.AppsListItemModel;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by webpr on 13.01.2017.
 */

public class AppsListAdapter extends RecyclerView.Adapter<AppsListAdapter.AppsListViewholder> {

    private List<AppsListItemModel> mAppsListItemModels;
    private WeakReference<Context> mContextWeakReference;

    public AppsListAdapter(List<AppsListItemModel> mAppsListItemModels, Context context) {
        this.mAppsListItemModels = mAppsListItemModels;
        this.mContextWeakReference = new WeakReference<Context>(context);
    }

    @Override
    public AppsListViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContextWeakReference.get()).inflate(R.layout.apps_list_card, parent, false);
        return new AppsListViewholder(view);
    }

    @Override
    public void onBindViewHolder(AppsListViewholder holder, int position) {
        holder.bind(mAppsListItemModels.get(position));
    }

    @Override
    public int getItemCount() {
        return mAppsListItemModels.size();
    }

    public class AppsListViewholder extends RecyclerView.ViewHolder{

        private AppsListItemViewGroup mAppsListItemViewGroup;

        public AppsListViewholder(View itemView) {
            super(itemView);
            mAppsListItemViewGroup = (AppsListItemViewGroup) itemView.findViewById(R.id.appsListItemView);
        }

        public void bind(final AppsListItemModel itemModel){
            mAppsListItemViewGroup.setAppIcon(itemModel.getAppIcon());
            mAppsListItemViewGroup.setAppLabel(itemModel.getAppLabel());
            mAppsListItemViewGroup.setAppCategory(itemModel.getAppCategory());

            mAppsListItemViewGroup.setEducationState(itemModel.isEducational());
            mAppsListItemViewGroup.setForFunState(itemModel.isForFun());
            mAppsListItemViewGroup.setNeutralState(itemModel.isNeutral());
        }
    }
}
