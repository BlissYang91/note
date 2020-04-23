// BaseActivity中全局监听
private var screenManager: ScreenShotListenManager? = null
if (screenManager == null) {
    screenManager = ScreenShotListenManager.newInstance(this)
}
screenManager?.startListen()

screenManager?.setListener {
    Log.i("----tag----", "---$it")
    val shareDialog = ShareScreenShotDialog()
    shareDialog.path = it
    shareDialog.show(supportFragmentManager, "ShareScreenShotDialog")
}

override fun onDestroy() {
    screenManager?.stopListen()
    super.onDestroy()
}

// 需要权限
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
