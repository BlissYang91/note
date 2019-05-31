package com.bliss.yang.daggerapp.main;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.bliss.yang.daggerapp.R;
import com.bliss.yang.daggerapp.app.APP;
import com.bliss.yang.daggerapp.db.DBManager;
import com.bliss.yang.daggerapp.di.module.MainModule;
import com.bliss.yang.daggerapp.second.SecondActivity;
import com.bliss.yang.daggerapp.single.SingletonObj;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {
    /**
     * 在acvitity中获取到 MainComponent 的实例 并注入
     */
    @Inject
    DBManager dbManager;

    @Inject DBManager dbManager1;

    @Inject
    SingletonObj mainSingleton;

    @Inject
    SingletonObj mainSingleton1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*依赖注入*/
        ((APP)getApplication()).getAppComponent()
                .plus(new MainModule())
                .inject(this);
        Log.e(MainActivity.class.getSimpleName(), "onCreate: appdb-->"+((APP)getApplication()).getAppComponent().getDBManager().hashCode());

        //查看 是否和 second的一致，是否是全局范围内单例
        if (dbManager==dbManager1) {
            Log.e(MainActivity.class.getSimpleName(), "onCreate: dbmanager-sintleton->"+dbManager.hashCode());
        }else{
            Log.e(MainActivity.class.getSimpleName(), "onCreate: dbmanager:"+dbManager.hashCode());
            Log.e(MainActivity.class.getSimpleName(), "onCreate: dbmanager1:"+dbManager1.hashCode());
        }
        //主要看 这个 和 second的是否一致，是否是activity范围内单例。
        if (mainSingleton==mainSingleton1){
            Log.e(MainActivity.class.getSimpleName(), "onCreate: main-singleton->"+mainSingleton.hashCode());
        }else{
            Log.e(MainActivity.class.getSimpleName(), "onCreate: main:"+mainSingleton.hashCode());
            Log.e(MainActivity.class.getSimpleName(), "onCreate: main1:"+mainSingleton1.hashCode());
        }

        startActivity(new Intent(this, SecondActivity.class));


    }

}
/**
 * com.bliss.yang.daggerapp E/DBHelper: DBHelper:
 * com.bliss.yang.daggerapp E/MainActivity: onCreate: appdb-->262316602
 * com.bliss.yang.daggerapp E/MainActivity: onCreate: dbmanager-sintleton->262316602
 * com.bliss.yang.daggerapp E/MainActivity: onCreate: main-singleton->143191787
 */
