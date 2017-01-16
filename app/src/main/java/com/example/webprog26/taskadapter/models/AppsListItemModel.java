package com.example.webprog26.taskadapter.models;

import android.graphics.Bitmap;

/**
 * Created by webpr on 13.01.2017.
 */

public class AppsListItemModel {

    private long mAppId;
    private String mAppLabel;
    private Bitmap mAppIcon;
    private String mAppCategory;
    private boolean isEducational;
    private boolean isBlocked;
    private boolean isForFun;
    private boolean isNeutral;

    public long getAppId() {
        return mAppId;
    }

    public String getAppLabel() {
        return mAppLabel;
    }

    public Bitmap getAppIcon() {
        return mAppIcon;
    }

    public String getAppCategory() {
        return mAppCategory;
    }

    public boolean isEducational() {
        return isEducational;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public boolean isForFun() {
        return isForFun;
    }

    public boolean isNeutral() {
        return isNeutral;
    }

    public void setAppId(long mAppId) {
        this.mAppId = mAppId;
    }

    public void setAppLabel(String mAppLabel) {
        this.mAppLabel = mAppLabel;
    }

    public void setAppIcon(Bitmap mAppIcon) {
        this.mAppIcon = mAppIcon;
    }

    public void setAppCategory(String mAppCategory) {
        this.mAppCategory = mAppCategory;
    }

    public void setEducational(boolean educational) {
        isEducational = educational;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }

    public void setForFun(boolean forFun) {
        isForFun = forFun;
    }

    public void setNeutral(boolean neutral) {
        isNeutral = neutral;
    }

    //    public static Builder newBuilder(){
//        return new AppsListItemModel(). new Builder();
//    }
//
//    public class Builder{
//
//        public Builder setAppId(long appId){
//            AppsListItemModel.this.mAppId = appId;
//            return this;
//        }
//
//        public Builder setAppLabel(String appLabel){
//            AppsListItemModel.this.mAppLabel = appLabel;
//            return this;
//        }
//
//        public Builder setAppIcon(Bitmap appIcon){
//            AppsListItemModel.this.mAppIcon = appIcon;
//            return this;
//        }
//
//        public Builder setAppCategory(String appCategory){
//            AppsListItemModel.this.mAppCategory = appCategory;
//            return this;
//        }
//
//        public Builder setIsEducational(boolean isEducational){
//            AppsListItemModel.this.isEducational = isEducational;
//            return this;
//        }
//
//        public Builder setIsNeutral(boolean isBlocked){
//            AppsListItemModel.this.isBlocked = isBlocked;
//            return this;
//        }
//
//        public Builder setIsForFun(boolean isForFun){
//            AppsListItemModel.this.isForFun = isForFun;
//            return this;
//        }
//
//        public AppsListItemModel build(){
//            return AppsListItemModel.this;
//        }
//    }

    @Override
    public String toString() {
        return "App " + getAppLabel() + " with id " + getAppId() + " with icon " + getAppIcon().toString() + "\n"
                + "educational " + isEducational + ", for fun " + isForFun + ", blocked " + isBlocked;
    }
}
