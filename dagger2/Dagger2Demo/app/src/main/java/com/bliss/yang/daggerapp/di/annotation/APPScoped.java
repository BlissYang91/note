package com.bliss.yang.daggerapp.di.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * @author YangTianFu
 * @date 2019/5/31  11:50
 * @description APP全局单例
 * 此注解使用的 Component 要全局范围内唯一 ，不然无法实现全局单例
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface APPScoped {
}
