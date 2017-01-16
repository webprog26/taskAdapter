package com.example.webprog26.taskadapter.custom_views;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.AppCompatRadioButton;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.webprog26.taskadapter.R;

/**
 * Created by webpr on 13.01.2017.
 */

public class AppsListItemViewGroup extends RelativeLayout{

    private static final String TAG = "AppsListItemViewGroup";

    private View mRootView;
    private ImageView mIvAppIcon;
    private TextView mTvAppLabel;
    private TextView mTvAppCategory;
    private RadioGroup mRadioGroup;
    private AppCompatRadioButton mRbEducation;
    private AppCompatRadioButton mRbForFun;
    private AppCompatRadioButton mRbBlocked;

    public AppsListItemViewGroup(Context context) {
        super(context);
        init(context);
    }

    public AppsListItemViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context){
        mRootView = inflate(context, R.layout.apps_list_item_view, this);

        mIvAppIcon = (ImageView) mRootView.findViewById(R.id.ivAppIcon);
        mTvAppLabel = (TextView) mRootView.findViewById(R.id.tvAppLabel);
        mTvAppCategory = (TextView) mRootView.findViewById(R.id.tvAppCategory);

        mRadioGroup = (RadioGroup) mRootView.findViewById(R.id.radioGroup);

        mRbEducation = (AppCompatRadioButton) mRootView.findViewById(R.id.rbEducation);
        mRbForFun = (AppCompatRadioButton) mRootView.findViewById(R.id.rbForFun);
        mRbBlocked = (AppCompatRadioButton) mRootView.findViewById(R.id.rbBlocked);

    }

    public void setAppIcon(Bitmap appIcon){
        this.mIvAppIcon.setImageBitmap(appIcon);
    }

    public void setAppCategory(String appCategory){
        this.mTvAppCategory.setText(appCategory);
    }

    public void setAppLabel(String appLabel){
        this.mTvAppLabel.setText(appLabel);
    }

    public void setEducationState(boolean rbEducationState){
        this.mRbEducation.setChecked(rbEducationState);
    }

    public void setForFunState(boolean rbForFunState){
        this.mRbForFun.setChecked(rbForFunState);
    }

    public void setNeutralState(boolean rbNeutralState){
        this.mRbBlocked.setChecked(rbNeutralState);
    }

    public void setRadioGroupCheckedChangeListener(RadioGroup.OnCheckedChangeListener listener){
        this.mRadioGroup.setOnCheckedChangeListener(listener);
    }
}
