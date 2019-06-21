public static String getAndroidID() {
    String id = Settings.Secure.getString(getContentResolver(),Settings.Secure.ANDROID_ID);
    Log.e(TAG, "getAndroidID: " + id );
    return id == null ? "" : id;
}




    /**
     * @return String 版本号
     * @brief 获得当前应用版本号
     */
    public String getVersionCode() {
        return BuildConfig.VERSION_CODE + "";
    }

    /**
     * @return String 版本号
     * @brief 获得当前应用版本号
     */
    public String getVersionName() {
        return BuildConfig.VERSION_NAME;
    }

    /**
     * @return brand系统厂商
     * @brief 获得该手机系统的定制产商名称
     */
    public String getPhoneBrand() {
        return Build.BRAND;
    }

    /**
     * @return
     * @brief 获得手机型号
     */
    public String getPhoneModel() {
        return Build.MODEL;
    }

    /**
     * @return String 系统版本号
     * @brief 获得当前系统版本号
     */
    public String getOSVersion() {
        return Build.VERSION.RELEASE;
    }

    /**
     * @return String 当前设备ID
     * @brief 获得设备ID
     */
    public String getDeviceId() {
        String deviceId = SPUtils.getStringData(SPUtils.DEVICEID, null);
        if (TextUtils.isEmpty(deviceId)) {
            deviceId = getUniqueMarker();
            SPUtils.saveStringData(SPUtils.DEVICEID, deviceId);
            return deviceId;
        }
        return deviceId;
    }
    @SuppressLint("MissingPermission")
    private String getUniqueMarker() {
        TelephonyManager tm = (TelephonyManager) getBaseContext().getSystemService(Context.TELEPHONY_SERVICE);
        String tmDevice, tmSerial, androidId;
        tmDevice = tm.getDeviceId();
        tmSerial = tm.getSimSerialNumber();
        androidId = Settings.Secure.getString(getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);

        UUID deviceUuid = null;
        String uniqueId = null;
        if (!TextUtils.isEmpty(androidId)) {
            deviceUuid = new UUID(androidId.hashCode(),
                    ((long) (tmDevice == null ? 0 : tmDevice.hashCode() << 32) |
                            (tmSerial == null ? 100 : tmSerial.hashCode())));
            uniqueId = deviceUuid.toString();
        } else {
            deviceUuid = UUID.randomUUID();
            uniqueId = deviceUuid.toString();
        }
        return uniqueId;
    }
