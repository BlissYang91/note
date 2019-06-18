package edu.com.gaiwen.firstchoice.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;

import java.util.UUID;

/**
 * Created by xfc on 2016-09-06.
 */
//获取android手机设备编号
public class DeviceID {
    public static String getIMEI(Context context) {
        String id = "";
        TelephonyManager mTelephony = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            id = UUID.randomUUID().toString().replace("-", "");
            return id;
        } else {
            if (mTelephony.getDeviceId() != null) {
                id = mTelephony.getDeviceId();
            } else {
                //android.provider.Settings;
                id = Settings.Secure.getString(context.getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
            }
        }
        return id;
    }
}
