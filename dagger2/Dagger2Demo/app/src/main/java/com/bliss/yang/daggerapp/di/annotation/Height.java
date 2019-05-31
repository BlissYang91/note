package com.bliss.yang.daggerapp.di.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * @author YangTianFu
 * @date 2019/5/31  10:33
 * @description 自定义@Qualifier注解防止module中属性返回值相同引起的迷失问题
 */
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface Height {
}
