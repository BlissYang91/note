package com.bliss.yang.daggerapp.single;

import com.bliss.yang.daggerapp.di.annotation.ActivityScoped;

import javax.inject.Inject;

/**
 * @author YangTianFu
 * @date 2019/5/31  11:38
 * @description 在 Activity范围内 单例
 */
@ActivityScoped
public class SingletonObj {
    @Inject
    public SingletonObj() {
    }
}
