package com.bliss.yang.daggerapp.di.component;

import com.bliss.yang.daggerapp.db.DBManager;
import com.bliss.yang.daggerapp.di.annotation.APPScoped;
import com.bliss.yang.daggerapp.di.module.APIModule;
import com.bliss.yang.daggerapp.di.module.APPModule;
import com.bliss.yang.daggerapp.di.module.MainModule;
import com.bliss.yang.daggerapp.di.module.SecondModule;
import dagger.Component;

/**
 * @author YangTianFu
 * @date 2019/5/31  11:27
 * @description
 */
@APPScoped
@Component(modules = {APIModule.class,APPModule.class})
public interface APPComponent {
    MainComponent plus(MainModule mainModule);
    SecondCompoent plus(SecondModule secondModule);
    DBManager getDBManager();
}
