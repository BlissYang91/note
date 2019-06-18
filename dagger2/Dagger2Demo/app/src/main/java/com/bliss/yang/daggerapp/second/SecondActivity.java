package com.bliss.yang.daggerapp.second;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.bliss.yang.daggerapp.R;
import com.bliss.yang.daggerapp.app.APP;
import com.bliss.yang.daggerapp.db.DBManager;
import com.bliss.yang.daggerapp.di.module.SecondModule;
import com.bliss.yang.daggerapp.single.SingletonObj;

import javax.inject.Inject;

public class SecondActivity extends AppCompatActivity {
    @Inject
    DBManager dbManager;
    @Inject
    DBManager dbManager1;
    @Inject
    SingletonObj mainSingleton;
    @Inject
    SingletonObj mainSingleton1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        ((APP)getApplication()).getAppComponent()
                .plus(new SecondModule())
                .inject(this);

        if (dbManager==dbManager1) {
            Log.e(SecondActivity.class.getSimpleName(), "onCreate: dbmanager-singleton->"+dbManager.hashCode());
        }else{
            Log.e(SecondActivity.class.getSimpleName(), "onCreate: dbmanager:"+dbManager.hashCode());
            Log.e(SecondActivity.class.getSimpleName(), "onCreate: dbmanager1:"+dbManager1.hashCode());
        }

        if (mainSingleton==mainSingleton1){
            Log.e(SecondActivity.class.getSimpleName(), "onCreate: main-singleton>"+mainSingleton.hashCode());
        }else{
            Log.e(SecondActivity.class.getSimpleName(), "onCreate: main:"+mainSingleton.hashCode());
            Log.e(SecondActivity.class.getSimpleName(), "onCreate: main1:"+mainSingleton1.hashCode());
        }
    }
}
/**
 * com.bliss.yang.daggerapp E/DBHelper: DBHelper:
 * com.bliss.yang.daggerapp E/MainActivity: onCreate: appdb-->262316602
 * com.bliss.yang.daggerapp E/MainActivity: onCreate: dbmanager-sintleton->262316602
 * com.bliss.yang.daggerapp E/MainActivity: onCreate: main-singleton->143191787
 * com.bliss.yang.daggerapp E/SecondActivity: onCreate: dbmanager-singleton->262316602
 * com.bliss.yang.daggerapp E/SecondActivity: onCreate: main-singleton>18356307
 */
