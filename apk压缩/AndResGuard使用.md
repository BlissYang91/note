### AndResGuard使用

### AndResGuard的配置

#### 项目根目录下build.gradle中，添加插件的依赖：

```
 dependencies {
         classpath 'com.tencent.mm:AndResGuard-gradle-plugin:1.2.15'
    }
    
复制代码
```

#### app模块中build.gradle中，添加相关配置

  个人建议单独出一个gradle文件，在app目录下，创建**and_res_guard.gradle**文件，如图：



![image](https://user-gold-cdn.xitu.io/2018/1/18/16107f4d948158cd?imageView2/0/w/1280/h/960/format/webp/ignore-error/1)



**and_res_guard.gradle**文件中的配置：

```
apply plugin: 'AndResGuard'

andResGuard {
    mappingFile = null
    use7zip = true
    useSign = true
    keepRoot = false
    compressFilePattern = [
            "*.png",
            "*.jpg",
            "*.jpeg",
            "*.gif",
            "resources.arsc"
    ]
    whiteList = [
            // your icon
            "R.drawable.icon",
            // for fabric
            "R.string.com.crashlytics.*",
            // for umeng update
            "R.string.tb_*",
            "R.layout.tb_*",
            "R.drawable.tb_*",
            "R.drawable.u1*",
            "R.drawable.u2*",
            "R.color.tb_*",
            // umeng share for sina
            "R.drawable.sina*",
            // for google-services.json
            "R.string.google_app_id",
            "R.string.gcm_defaultSenderId",
            "R.string.default_web_client_id",
            "R.string.ga_trackingId",
            "R.string.firebase_database_url",
            "R.string.google_api_key",
            "R.string.google_crash_reporting_api_key",

                      //友盟
            "R.string.umeng*",
            "R.string.UM*",
            "R.layout.umeng*",
            "R.drawable.umeng*",
            "R.id.umeng*",
            "R.anim.umeng*",
            "R.color.umeng*",
            "R.style.*UM*",
            "R.style.umeng*",

            //融云
            "R.drawable.u*",
            "R.drawable.rc_*",
            "R.string.rc_*",
            "R.layout.rc_*",
            "R.color.rc_*",
            "R.id.rc_*",
            "R.style.rc_*",
            "R.dimen.rc_*",
            "R.array.rc_*"
    ]

    sevenzip {
        artifact = 'com.tencent.mm:SevenZip:1.2.10'
    }
}

```

  其中whiteList（白名单）中指定不需要进行混淆的资源路径规则，主要是一些第三方SDK，因为有些SDK的代码中引用到对应的资源文件，如果对其进行混淆，会导致找不到对应资源文件，出现crash，所以不能对其资源文件进行混淆。由于公司的项目中使用到了友盟和融云，所以将这两个SDK加入白名单，更多的白名单可以查看：

[AndResGuard白名单](https://link.juejin.im?target=https%3A%2F%2Fgithub.com%2Fshwenzhang%2FAndResGuard%2Fblob%2Fmaster%2Fdoc%2Fwhite_list.md)

  由于我们并非是在app模块下的build.gradle中添加AndResGuard的配置，而是单独出**and_res_guard.gradle**，所以需要在app模块下的build.gradle文件中引用，在app模块下的build.gradle文件开头添加以下代码引用：

```
apply from: 'and_res_guard.gradle'
复制代码
```

### AndResGuard的使用

集成完AndResGuard后，在app的gradle的tasks中，多了一个叫做andresguard的task,如图：



![image](https://user-gold-cdn.xitu.io/2018/1/18/16107f4db5f9eb57?imageView2/0/w/1280/h/960/format/webp/ignore-error/1)



如果想打debug包，则执行resguardDebug指令；

如果想打preview包，则执行resguardPreview指令；

如果想打release包，则执行resguardRelease指令。

  演示下打release包，我们双击执行resguardRelease指令，执行完毕后，我们可以在app目录下的/build/output/apk/release/AndResGuard_{apk_name}/ 文件夹中找到混淆后的Apk:



![image](https://user-gold-cdn.xitu.io/2018/1/18/16107f4db514566c?imageView2/0/w/1280/h/960/format/webp/ignore-error/1)



其中**app-release_aligned_signed.apk**为进行混淆并签名过的apk，双击查看该apk:



![image](https://user-gold-cdn.xitu.io/2018/1/18/16107f4dc3310dd3?imageView2/0/w/1280/h/960/format/webp/ignore-error/1)





![image](https://user-gold-cdn.xitu.io/2018/1/18/16107f4dbee43cd9?imageView2/0/w/1280/h/960/format/webp/ignore-error/1)



可以看到res文件夹变为r，且里面的目录名称都已经是混淆过的。

  到这里为止AndResGuard的使用就已经介绍完毕，如果有不清楚的地方，可以参考我写的demo,demo代码地址：

[github.com/chaychan/An…](https://link.juejin.im?target=https%3A%2F%2Fgithub.com%2Fchaychan%2FAndResGuardDemo)

  对于AndResGuard中的配置有不清楚的地方，可以查看官方文档：

[AndResGuard中文文档](https://link.juejin.im?target=https%3A%2F%2Fgithub.com%2Fshwenzhang%2FAndResGuard%2Fblob%2Fmaster%2FREADME.zh-cn.md)

作者：chaychan

链接：https://juejin.im/post/5a603eb06fb9a01c9332ba97

来源：掘金

著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。