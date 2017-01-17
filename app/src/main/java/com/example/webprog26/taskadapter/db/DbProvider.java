package com.example.webprog26.taskadapter.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.webprog26.taskadapter.models.AppsListItemModel;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by webpr on 16.01.2017.
 */

public class DbProvider {

    private WeakReference<Context> mContextWeakReference;
    private DbHelper mDbHelper;

    public DbProvider(Context context) {
        this.mContextWeakReference = new WeakReference<Context>(context);
        this.mDbHelper = new DbHelper(mContextWeakReference.get());
    }

    public int getAppsInDbCount(){
        int count = 0;

        Cursor cursor = mDbHelper.getReadableDatabase().query(DbHelper.TABLE_NAME, null, null, null, null, null, null);

        while(cursor.moveToNext()){
            count++;
        }

        return count;
    }

    public void writeToDb(List<AppsListItemModel> appsListItemModels){
        for(AppsListItemModel appsListItemModel: appsListItemModels){
            ContentValues contentValues = new ContentValues();
            contentValues.put(DbHelper.HASH_CODE, appsListItemModel.getAppLabel().hashCode());
            contentValues.put(DbHelper.IS_EDUCATIONAL, String.valueOf(appsListItemModel.isEducational()));
            contentValues.put(DbHelper.IS_FOR_FUN, String.valueOf(appsListItemModel.isForFun()));
            contentValues.put(DbHelper.IS_BLOCKED, String.valueOf(appsListItemModel.isBlocked()));
            contentValues.put(DbHelper.IS_NEUTRAL, String.valueOf(appsListItemModel.isNeutral()));

            mDbHelper.getWritableDatabase().insert(DbHelper.TABLE_NAME, null, contentValues);
        }
    }

    public AppsListItemModel getCategoriesValues(int hashCode){

        AppsListItemModel appsListItemModel = new AppsListItemModel();

        String selection = DbHelper.HASH_CODE + " = ?";
        String[] selectionArgs = new String[]{String.valueOf(hashCode)};

        Cursor cursor = mDbHelper.getReadableDatabase().query(
                DbHelper.TABLE_NAME,
                null,
                selection,
                selectionArgs,
                null,
                null,
                null);

        while(cursor.moveToNext()){
            appsListItemModel.setEducational(Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex(DbHelper.IS_EDUCATIONAL))));
            appsListItemModel.setForFun(Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex(DbHelper.IS_FOR_FUN))));
            appsListItemModel.setBlocked(Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex(DbHelper.IS_BLOCKED))));
            appsListItemModel.setNeutral(Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex(DbHelper.IS_NEUTRAL))));
        }

        return appsListItemModel;
    }

}
