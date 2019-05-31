package com.bliss.yang.daggerapp.di.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * @author YangTianFu
 * @date 2019/5/31  10:33
 * @description
 */
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface Age {
}
