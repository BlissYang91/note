package com.bliss.yang.daggerapp.db;

import com.bliss.yang.daggerapp.di.annotation.APPScoped;

import javax.inject.Inject;

/**
 * @author YangTianFu
 * @date 2019/5/31  9:46
 * @description DBManager 全局单例
 */
@APPScoped
public class DBManager {
    @Inject
    DBHelper helper;

    @Inject
    public DBManager() {
    }
}
