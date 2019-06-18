package com.bliss.yang.signingapplication.utils;

import android.hardware.Camera;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.util.Log;


/**
 * @author YangTianFu
 * @date 2019/6/6  19:52
 * @description
 */
public class MySensorListener implements SensorEventListener {
    private static final String TAG = "MySensorListener";
    private Camera m_Camera = null;
    private Camera.Parameters parameters = null;

    @Override
    public void onSensorChanged(SensorEvent event) {

        float lux = event.values[0];//获取光线强度
        int retval = Float.compare(lux, (float) 10.0);
        if (retval > 50) {//光线强度>50.0
            //关闪光灯
            closeLightOff();
        } else {
            //开闪光灯
            openLightOn();
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void openLightOn() {

        try{
            m_Camera = Camera.open();
            Camera.Parameters mParameters;
            mParameters = m_Camera.getParameters();
            mParameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            m_Camera.setParameters(mParameters);
        } catch(Exception ex){
            Log.e(TAG, "openLightOn: "+ ex.toString() );
        }
    }

    public void closeLightOff() {
        try{
            Camera.Parameters mParameters;
            mParameters = m_Camera.getParameters();
            mParameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
            m_Camera.setParameters(mParameters);
            m_Camera.release();
        } catch(Exception ex){
            Log.e(TAG, "openLightOn: "+ ex.toString() );
        }
    }
}
/************************************ 使用 *********************************** */
// private SensorManager sm;
// private MySensorListener mySensorListener;
// mySensorListener = new MySensorListener();
// sm = (SensorManager) getSystemService(SENSOR_SERVICE);
// Sensor ligthSensor = sm.getDefaultSensor(Sensor.TYPE_LIGHT);
// sm.registerListener(mySensorListener, ligthSensor, SensorManager.SENSOR_DELAY_NORMAL);
