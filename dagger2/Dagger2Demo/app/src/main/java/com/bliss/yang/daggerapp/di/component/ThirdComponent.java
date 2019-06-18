package com.bliss.yang.daggerapp.di.component;

import com.bliss.yang.daggerapp.third.ThirdActivity;
import com.bliss.yang.daggerapp.di.module.ThirdModule;

import dagger.Component;

/**
 * @author YangTianFu
 * @date 2019/5/31  10:17
 * @description
 */
@Component(modules = ThirdModule.class)
public interface ThirdComponent {
    void inject(ThirdActivity thirdActivity);
}
