package com.bliss.yang.daggerapp.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.jar.Attributes;

import javax.inject.Inject;

/**
 * @author YangTianFu
 * @date 2019/5/31  11:40
 * @description
 */
public class DBHelper extends SQLiteOpenHelper {
    private static final String TAG = "DBHelper";
    private static final String NAME = "dagger.db";
    private static final int VERSION = 1;

   @Inject
   public DBHelper(Context context){
       super(context, NAME,null,VERSION);
       Log.e(TAG, "DBHelper: " );
   }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.e(TAG, "onCreate: ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
