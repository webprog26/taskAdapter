package com.example.webprog26.taskadapter.models;

import android.graphics.Bitmap;

/**
 * Created by webpr on 13.01.2017.
 */

public class AppsListItemModel {

    private long mAppId;
    private String mAppLabel;
    private Bitmap mAppIcon;
    private boolean isEducational;
    private boolean isNeutral;
    private boolean isForFun;

    public long getAppId() {
        return mAppId;
    }

    public String getAppLabel() {
        return mAppLabel;
    }

    public Bitmap getAppIcon() {
        return mAppIcon;
    }

    public boolean isEducational() {
        return isEducational;
    }

    public boolean isNeutral() {
        return isNeutral;
    }

    public boolean isForFun() {
        return isForFun;
    }

    public static Builder newBuilder(){
        return new AppsListItemModel(). new Builder();
    }

    public class Builder{

        public Builder setAppId(long appId){
            AppsListItemModel.this.mAppId = appId;
            return this;
        }

        public Builder setAppLabel(String appLabel){
            AppsListItemModel.this.mAppLabel = appLabel;
            return this;
        }

        public Builder setAppIcon(Bitmap appIcon){
            AppsListItemModel.this.mAppIcon = appIcon;
            return this;
        }

        public Builder setIsEducational(boolean isEducational){
            AppsListItemModel.this.isEducational = isEducational;
            return this;
        }

        public Builder setIsNeutral(boolean isNeutral){
            AppsListItemModel.this.isNeutral = isNeutral;
            return this;
        }

        public Builder setIsForFun(boolean isForFun){
            AppsListItemModel.this.isForFun = isForFun;
            return this;
        }

        public AppsListItemModel build(){
            return AppsListItemModel.this;
        }
    }
}
