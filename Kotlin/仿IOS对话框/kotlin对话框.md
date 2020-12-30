
> 地址: https://github.com/limuyang2/LDialog/blob/master/README_CN.md
```
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
dependencies {
	//必须导入
	implementation 'com.github.limuyang2.LDialog:ldialog:1.0.2'
	//3种自定义样式，不使用就不导入
	implementation 'com.github.limuyang2.LDialog:custom_ldialog:1.0.2'
}
//如果你使用 Android X，请使用以下内容：
dependencies {
	//必须导入
	implementation 'com.github.limuyang2.LDialog:ldialog:1.0.2_androidx'
	//3种自定义样式，不使用就不导入
	implementation 'com.github.limuyang2.LDialog:custom_ldialog:1.0.2_androidx'
}
//如果你开启了 ProGuard 混淆，需要添加以下配置：
-keep class top.limuyang2.ldialog.base.** { *; }


//使用
 IOSMsgDialog.init(supportFragmentManager)
                .setTitle("是否取消")
                .setMessage("取消订阅后将不能收到相关资讯提醒")
                .setWidthScale(0.85f)
                .setNegativeButton("点错了", View.OnClickListener {

                }, Color.parseColor("#000000"))
                .setPositiveButton("不再订阅", View.OnClickListener {
                  
                }, Color.parseColor("#DAB177"))
                .show()
```
