package com.bliss.yang.daggerapp.third;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bliss.yang.daggerapp.R;
import com.bliss.yang.daggerapp.di.annotation.Age;
import com.bliss.yang.daggerapp.di.annotation.Height;
import com.bliss.yang.daggerapp.di.component.DaggerThirdComponent;

import javax.inject.Inject;
import javax.inject.Named;

import okhttp3.OkHttpClient;

public class ThirdActivity extends AppCompatActivity {

    @Named("name")
    @Inject
    String name;

    @Named("address")
    @Inject
    String address;
    @Age
    @Inject
    int age;

    @Height
    @Inject
    int height;

    @Inject
    OkHttpClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        DaggerThirdComponent.create().inject(this);
    }
}
