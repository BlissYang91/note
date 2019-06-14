package com.thesis.course.minicourse.retrofit;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;

/**
 * @autor YangTianFu
 * @Date 2019/3/26  13:46
 * @Description Observables 和 Subscribers管理
 */
public class RxManager {
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    /**
     * 添加observer
     * @param observer
     */
    public void addObserver(DisposableObserver observer){
        if(observer!=null){
            compositeDisposable.add(observer);
        }
    }
    public void clear(){
        compositeDisposable.dispose();
    }
}
