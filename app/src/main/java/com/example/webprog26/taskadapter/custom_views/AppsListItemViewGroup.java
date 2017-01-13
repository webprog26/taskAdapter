package com.example.webprog26.taskadapter.custom_views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.webprog26.taskadapter.R;

/**
 * Created by webpr on 13.01.2017.
 */

public class AppsListItemViewGroup extends RelativeLayout {

    private View mRootView;
    private ImageView mIvAppIcon;
    private TextView mTvAppLabel;
    private TextView mTvAppCategory;
    private RadioButton mRbEducation;
    private RadioButton mRbForFun;
    private RadioButton mRbNeutral;

    public AppsListItemViewGroup(Context context) {
        super(context);
        init(context);
    }

    public AppsListItemViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context){
        mRootView = inflate(context, R.layout.apps_list_item, this);

        mIvAppIcon = (ImageView) mRootView.findViewById(R.id.ivAppIcon);
        mTvAppLabel = (TextView) mRootView.findViewById(R.id.tvAppLabel);
        mTvAppCategory = (TextView) mRootView.findViewById(R.id.tvAppCategory);

        mRbEducation = (RadioButton) mRootView.findViewById(R.id.rbEducation);
        mRbForFun = (RadioButton) mRootView.findViewById(R.id.rbForFun);
        mRbNeutral = (RadioButton) mRootView.findViewById(R.id.rbNeutral);
    }
}
