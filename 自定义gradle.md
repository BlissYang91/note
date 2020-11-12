- [方式一] 
```
ext{
        android = [
              compileSdkVersion: 23,
              buildToolsVersion: "24.0.0 rc1",
              applicationId    : "com.sivan.rxretrofitdemo",
              minSdkVersion    : 16,
              targetSdkVersion : 23,
              versionCode      : 1,
              versionName      : "1.0"

        ]
        dependencies = [
                    "appcompat-v7"       : "com.android.support:appcompat-v7:23.3.0",
                    "rxjava"             : "io.reactivex:rxjava:1.1.3",
                    "rxandroid"          : "io.reactivex:rxandroid:1.1.0",
                    "retrofit"           : "com.squareup.retrofit2:retrofit:2.0.0-beta4",
                    "gson"               : "com.google.code.gson:gson:2.6.2",
                    "converter-gson"     : "com.squareup.retrofit2:converter-gson:2.0.0-beta4",
                    "adapter-rxjava"     : "com.squareup.retrofit2:adapter-rxjava:2.0.0-beta4",
                    "butterknife"        : "com.jakewharton:butterknife:8.4.0",
                    "butterknife-compiler"        : "com.jakewharton:butterknife-compiler:8.4.0",
                    "logging-interceptor": "com.squareup.okhttp3:logging-interceptor:3.0.1",
                    "tiny":"com.zxy.android:tiny:1.1.0",
                    "pickview":"com.brucetoo.pickview:library:1.2.3"
            ]
        host = [
            "release":"",
            "debug":""

        ]
}



```

```
dependencies {
    implementation fileTree(include: ['*.jar'], dir: 'libs')
    androidTestImplementation 'com.android.support.test.espresso:espresso-core:3.0.1'
    api 'com.android.support:appcompat-v7:27.1.1'
    api 'com.android.support:recyclerview-v7:26.0.0-alpha1'
    api rootProject.ext.dependencies["constraint"]
    api rootProject.ext.dependencies["retrofit2"]
    api rootProject.ext.dependencies["retrofit2-adapter"]
    api rootProject.ext.dependencies["retrofit2-converter"]
    api rootProject.ext.dependencies["rxjava2"]
    api rootProject.ext.dependencies["rxandroid"]
    api rootProject.ext.dependencies["glide"]
    annotationProcessor rootProject.ext.dependencies["glide-compiler"]
    api 'jp.wasabeef:glide-transformations:3.1.1'//圆角图片
    implementation rootProject.ext.dependencies["logger"]
    api rootProject.ext.dependencies["barlibrary"]//沉浸状态栏
    api rootProject.ext.dependencies["fragmentation"]//fragment管理
    api 'com.flyco.dialog:FlycoDialog_Lib:1.3.2@aar' //
    //友盟统计
    api 'com.umeng.sdk:common:1.5.0'
    api 'com.umeng.sdk:analytics:7.5.0'

}

```

```
  minSdkVersion rootProject.ext.android["minSdkVersion"]
        targetSdkVersion rootProject.ext.android["targetSdkVersion"]
        
```

- [方式二] 

```
ext.versions = [
        compileSdkVersion  : 28,
        minSdkVersion      : 18,
        targetSdkVersion   : 28,
        versionCode        : 1,
        versionName        : "1.0.0",
        supportVersion     : "28.0.0",
        multidex           : "1.0.1",
        eventbus           : "3.1.1",
        butterknife        : "8.4.0",
        gson               : "2.8.5",
        glide              : "4.8.0",
        rxjava             : "2.2.7",
        rxandroid          : "2.1.1",
        retrofit           : "2.5.0",
        rxbinding          : "3.0.0-alpha2",
//        logger             : "2.2.0",
        crashreport        : "2.8.6",
        crashreport_upgrade: "1.3.6",
        tinker             : "1.9.9",
        TINKER_SUPPORT     : "1.1.5",

]

ext.appConfig = [
        multidex               : true,
        minifyEnabled_release  : true,
        minifyEnabled_debug    : false,
        zipAlignEnabled        : true,
        shrinkResources_release: true,
        shrinkResources_debug  : false,
        DEBUG                  : true,
        applicationId          : 'com.thesis.course.minicourse',
        JPUSH_APPKEY           : '454a40e3ef65da960f77e821',
    
        LOCALHOST_TEST         : "\"http://t.weather.sojson.com/api/\"",
        LOCALHOST_WAN          : "\"https://www.wanandroid.com/\"",
]
```


```
  implementation "com.android.support:multidex:${versions.multidex}"
    implementation "org.greenrobot:eventbus:${versions.eventbus}"
    implementation "com.jakewharton:butterknife:${versions.butterknife}"
    annotationProcessor "com.jakewharton:butterknife-compiler:${versions.butterknife}"
    implementation "com.github.bumptech.glide:glide:${versions.glide}"
    annotationProcessor "com.github.bumptech.glide:compiler:${versions.glide}"
    implementation "io.reactivex.rxjava2:rxjava:${versions.rxjava}"
    implementation "io.reactivex.rxjava2:rxandroid:${versions.rxandroid}"
    implementation "com.squareup.retrofit2:retrofit:${versions.retrofit}"
    implementation "com.squareup.retrofit2:converter-gson:${versions.retrofit}"
    implementation "com.squareup.retrofit2:adapter-rxjava2:${versions.retrofit}"
    implementation "com.jakewharton.rxbinding3:rxbinding:${versions.rxbinding}"
    implementation "com.google.code.gson:gson:${versions.gson}"
    //    implementation "com.orhanobut:logger:${versions.logger}"
    implementation "com.tencent.bugly:crashreport_upgrade:${versions.crashreport_upgrade}"
    implementation "com.tencent.tinker:tinker-android-lib:${versions.tinker}"
```


```
  applicationId appConfig.applicationId
        minSdkVersion versions.minSdkVersion
        targetSdkVersion versions.targetSdkVersion
        versionCode versions.versionCode
        versionName versions.versionName
        multiDexEnabled appConfig.multidex
```

[TOC]
## config.gradle
```
ext {

    android = [
            compileSdkVersion: 29,
            buildToolsVersion: "29.0.2",
            minSdkVersion    : 26,
            targetSdkVersion : 29,
            versionCode      : 1,
            versionName      : "1.1",
    ]

     retrofit_version = '2.8.1'
    okhttp_version = '4.4.0'
    rx_kotlin_version = '2.3.0'
    rx_android_version = '2.1.1'
    anko_version = '0.10.5'
    loadSir_version = '1.3.6'
    lifecycle_version = '2.2.0-rc02'
    glide_version = '4.9.0'
   


    appId = [
            "app": "com.wjx.android.wanandroidmvvm",
    ]

    dependencies = [
            "appcompat"                  : "androidx.appcompat:appcompat:1.1.0",
            "recyclerview"               : "com.android.support:recyclerview-v7:29.0.0",
            "constraint"                 : "androidx.constraintlayout:constraintlayout:1.1.3",
            "kotlin"                     : "org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlin_version",
            "design"                     : "com.android.support:design:29.0.0",
            "retrofit"                   : "com.squareup.retrofit2:retrofit:$retrofit_version",
            "retrofit-converter-gson"    : "com.squareup.retrofit2:converter-gson:${retrofit_version}",
            "retrofit-adapter-rxjava2"   : "com.squareup.retrofit2:adapter-rxjava2:${retrofit_version}",
            "okhttp3"                    : "com.squareup.okhttp3:okhttp:${okhttp_version}",
            "okhttp3-logging-interceptor": "com.squareup.okhttp3:logging-interceptor:${okhttp_version}",
            "rxkotlin"                   : "io.reactivex.rxjava2:rxkotlin:$rx_kotlin_version",
            "rxandroid"                  : "io.reactivex.rxjava2:rxandroid:$rx_android_version",
            "v4_anko"                    : "org.jetbrains.anko:anko-support-v4-commons:$anko_version",
            "anko"                       : "org.jetbrains.anko:anko-commons:$anko_version",
            "loadSir"                    : "com.kingja.loadsir:loadsir:$loadSir_version",
            "lifecycle"                  : "androidx.lifecycle:lifecycle-extensions:$lifecycle_version",
            "glide"                      : "com.github.bumptech.glide:glide:$glide_version",
            "banner"                     : "com.youth.banner:banner:$banner_version",
            "agentweb"                   : "com.just.agentweb:agentweb:$agentweb_version",
            "room"                       : "androidx.room:room-runtime:$room_version",
            "license-fragment"           : "com.mikepenz:aboutlibraries:$about_fragment_version",
            "defaultlifecycleobserver"   : "androidx.lifecycle:lifecycle-common-java8:$lifecycle_version",
            "lifecycle-viewmodel"        : "androidx.lifecycle:lifecycle-viewmodel:$lifecycle_version",
            "lifecycler-livedata"        : "androidx.lifecycle:lifecycle-livedata:$lifecycle_version",
            "lifecycle-viewmodel-ktx"    : "androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version",
            "lifecycle-runtime-ktx"      : "androidx.lifecycle:lifecycle-runtime-ktx:$lifecycle_version",
            "lifecycle-livedata-ktx"     : "androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle_version",
            "event-bus"                  : "org.greenrobot:eventbus:$event_bus_version",
            "coroutines"                 : "org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutines_version",
            "adapter_helper"             : "com.github.CymChad:BaseRecyclerViewAdapterHelper:$adapter_helper_version",
            "material"                   : "com.google.android.material:material:1.1.0-alpha05",
            "tab_layout"                 : "com.flyco.tablayout:FlycoTabLayout_Lib:$tab_layout_version",
            "flexbox"                    : "com.google.android:flexbox:$flex_box_version",
            "flow_layout"                : "com.hyman:flowlayout-lib:$flow_layout_version",
            "float_button"               : "com.getbase:floatingactionbutton:$float_button_version",
            "lottie"                     : "com.airbnb.android:lottie:$lottie_version",
            "preference"                 : "androidx.preference:preference:$preference_version",
            "material_dialog"            : "com.afollestad.material-dialogs:core:$material_dialog_version",
            "materiaal_dialog_color"     : "com.afollestad.material-dialogs:color:$material_dialog_color_version",
            "litepal"                    : "org.litepal.android:kotlin:$litepal_version",
            "circular_avatar"            : "com.github.wangjianxiandev:CircularAvatar:$circular_avatar",
            "easypermissions"            : "pub.devrel:easypermissions:$easypermissions_version"
    ]

    processor = [
            "lifecycle"     : "androidx.lifecycle:lifecycle-compiler:$lifecycle_version",
            "glide_compiler": "com.github.bumptech.glide:compiler:$glide_version"
    ]
}
```

## project下的build.gradle引入

```
apply from: 'config.gradle'
```

## app下build.gradle

```
apply plugin: 'com.android.application'

apply plugin: 'kotlin-android'

apply plugin: 'kotlin-android-extensions'

apply plugin: 'kotlin-kapt'

def rootAndroidId = rootProject.ext.android
def appId = rootProject.ext.appId
def support = rootProject.ext.dependencies

android {
    compileSdkVersion rootAndroidId.compileSdkVersion
    buildToolsVersion rootAndroidId.buildToolsVersion
    defaultConfig {
        applicationId appId.app
        minSdkVersion rootAndroidId.minSdkVersion
        targetSdkVersion rootAndroidId.targetSdkVersion
        versionCode rootAndroidId.versionCode
        versionName rootAndroidId.versionName
        testInstrumentationRunner "android.support.test.runner.AndroidJUnitRunner"
    }
    dataBinding {
        enabled = true
    }
}

kapt {
    useBuildCache = true
    javacOptions {
        option("-Xmaxerrs", 500)
    }
}

dependencies {
    implementation fileTree(dir: 'libs', include: ['*.jar'])
    implementation support.appcompat
    implementation 'androidx.appcompat:appcompat:1.1.0'
    implementation 'androidx.constraintlayout:constraintlayout:1.1.3'
    support.each { k, v -> implementation v }
    testImplementation 'junit:junit:4.12'
    androidTestImplementation 'androidx.test.ext:junit:1.1.0'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.1.1'
    implementation 'androidx.palette:palette-ktx:1.0.0'
}

```
```
## jar包重复问题
- [x] Android studio默认会有so文件进行压缩优化，加入以下配置可以不压缩so
- doNotStrip "*/armeabi-v7a/libYTCommon.so"
- [x] pickFirst  当有多个匹配项的时候匹配到第一个就可以了

```
    packagingOptions {
        pickFirst '**/libRSSupport.so'
        pickFirst '**/librsjni.so'
        pickFirst '**/librsjni_androidx.so'
        doNotStrip "*/armeabi/libYTCommon.so"
        doNotStrip "*/armeabi-v7a/libYTCommon.so"
        doNotStrip "*/x86/libYTCommon.so"
        doNotStrip "*/arm64-v8a/libYTCommon.so"
        pickFirst "lib/armeabi-v7a/libc++_shared.so"
    }
```
## 忽略链接错误

```
lintOptions {
        abortOnError false
    }
```








