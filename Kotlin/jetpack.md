
[TOC]

## Livecycle
- [x] 简单监听
```

/**
 * @Author yangtianfu
 * @CreateTime 2020/5/22 17:13
 * @Describe
 * ① LifecycleObserver: Lifecycle观察者。我们需要自定义类实现LifecycleObserver，通过注解的方式可观察生命周期方法。
 * ② Lifecycle: 生命周期抽象类。持有添加和移除监听方法。定义State和Event枚举。
 * ③ 接受事件是通过注解OnLifecycleEvent来完成的，参数即Event枚举
 * ④ LifecycleOwner：Lifecycle持有者。让Activity或者fragment实现该接口，当生命周期改变是事件会被LifecycleObserver接收到。
 */
class MyObserver :LifecycleObserver {
    private val TAG: String
        get() = "MyObserver"

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun  connectListener(){
        Log.e(TAG, "connectListener: ==ON_RESUME==")
    }
    
    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun disconnectListener(){
        Log.e(TAG, "disconnectListener: ==ON_PAUSE==")
    }
```

```
 lifecycle.addObserver(MyObserver()) // 监听activity的生命周期
```






## LiveData

- [x] ViewModel KTX

```
dependencies {
        implementation "androidx.lifecycle:lifecycle-viewmodel-ktx:2.2.0"
    }
    
    
    class MainViewModel : ViewModel() {
        // Make a network request without blocking the UI thread
        private fun makeNetworkRequest() {
            // launch a coroutine in viewModelScope
            viewModelScope.launch  {
                remoteApi.slowFetch()
                ...
            }
        }

        // No need to override onCleared()
    }
```
- [x] 数据初始化

```
   val currentName:MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
```


- [x] LiveData KTX

```
  dependencies {
        implementation "androidx.lifecycle:lifecycle-livedata-ktx:2.2.0"
    }
    
```
- 以下示例中，loadUser() 是在其他地方声明的暂停函数。您可以使用 liveData 构建器函数异步调用 loadUser()，然后使用 emit() 来发出结果：

```
val user: LiveData<User> = liveData {
        val data = database.loadUser() // loadUser is a suspend function.
        emit(data)
    }
```

## Navigation
- [x] Navigation导航编辑器旨在简化Android开发中导航的实现，可以帮助我们很好的处理Activity和fragment之间通过FragmentTransaction交互的复杂性，也可以很好的处理页面的转场效果；Deeplink的支持，绕过activity直接跳到fragment；并且传递参数更安全。在Android Studio3.2可以使用。

```
 dependencies {
      def nav_version = "2.3.0-alpha01"

      // Java language implementation
      implementation "androidx.navigation:navigation-fragment:$nav_version"
      implementation "androidx.navigation:navigation-ui:$nav_version"

      // Kotlin
      implementation "androidx.navigation:navigation-fragment-ktx:$nav_version"
      implementation "androidx.navigation:navigation-ui-ktx:$nav_version"

      // Dynamic Feature Module Support
      implementation "androidx.navigation:navigation-dynamic-features-fragment:$nav_version"

      // Testing Navigation
      androidTestImplementation "androidx.navigation:navigation-testing:$nav_version"
    }
```

- [x] android:name 属性包含 NavHost 实现的类名称。
app:navGraph 属性将 NavHostFragment 与导航图相关联。导航图会在此 NavHostFragment 中指定用户可以导航到的所有目的地。
app:defaultNavHost="true" 属性确保您的 NavHostFragment 会拦截系统返回按钮。请注意，只能有一个默认 NavHost。如果同一布局（例如，双窗格布局）中有多个主机，请务必仅指定一个默认 NavHost。




