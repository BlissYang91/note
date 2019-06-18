package com.thesis.mentor.base;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;
import android.text.TextUtils;

import com.thesis.mentor.mycourses.MycoursePresenter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import bang.lib.utils.LogUtils;

import static android.support.v4.app.ActivityCompat.requestPermissions;

/**
 * @autor YangTianFu
 * @Email ytfunny@126.com
 * @CSDN https://blog.csdn.net/ytfunnysite
 * @Date 2019/1/8  17:25
 */
public class RequestPermissionUtil {
    private static  volatile RequestPermissionUtil instance;

    public RequestPermissionUtil() {
    }

    public static RequestPermissionUtil getInstance() {
        if (instance == null){
            instance =new RequestPermissionUtil();
        }
        return instance;
    }
    //检查手机权限
   public void checkPermissionInFragment(Fragment context,int requestCode) {
        final List<String> permissionsList = new ArrayList<>();
        String[] permissions= new String[]{Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.RECORD_AUDIO};
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (int i = 0; i <permissions.length ; i++) {
                permissionsList.add(permissions[i]);
            }
            if (permissionsList.size()>0){
                context.requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),requestCode);
            }
        }
    }
    public void checkPermissionInAcvitity(Context context,int resultCode) {
        final List<String> permissionsList = new ArrayList<>();
        String[] permissions= new String[]{Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.RECORD_AUDIO};
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (int i = 0; i <permissions.length ; i++) {
                permissionsList.add(permissions[i]);
            }
            if (permissionsList.size()>0){
                ActivityCompat.requestPermissions((Activity) context,permissionsList.toArray(new String[permissionsList.size()]),resultCode);
            }
        }
    }

    public List<Integer> checkPermission(Context context) {
        final List<String> permissionsList = new ArrayList<>();
        List<Integer> listGrantResults = new ArrayList<>(Arrays.asList(0,0,0));
        String[] permissions= new String[]{Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.RECORD_AUDIO};
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (int i = 0; i <permissions.length ; i++) {
                permissionsList.add(permissions[i]);
            }
        }
        int writeAndReadLimit = ContextCompat.checkSelfPermission(context,permissions[1]);
        int isCameraCanUse = ContextCompat.checkSelfPermission(context,permissions[0]);
        int isAudioUse = ContextCompat.checkSelfPermission(context,permissions[2]);
        if (isCameraCanUse==-1){
            listGrantResults.set(0,-1);
        }
        if (writeAndReadLimit!=0){
            listGrantResults.set(1,-1);
        }
        if (isAudioUse==-1){
            listGrantResults.set(2,-1);
        }
        String brand = Build.BRAND;
        if (TextUtils.equals(brand.toLowerCase(), "meizu")|| TextUtils.equals(brand.toLowerCase(), "360") || Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
            if (!isCameraCanUse()){
                listGrantResults.set(0,-1);
            }
            if (!isAudioUse()){
                listGrantResults.set(2,-1);
            }
        }

        return listGrantResults;
    }
    public boolean isAudioUse() {
        boolean isAvailable = true;
        AudioRecord record = null;
        try {
            record = new AudioRecord(MediaRecorder.AudioSource.MIC,44100,
                    AudioFormat.CHANNEL_IN_MONO,AudioFormat.ENCODING_DEFAULT,44100);
            if (record.getRecordingState() != AudioRecord.RECORDSTATE_STOPPED){
                isAvailable =false;
            }
            record.startRecording();
            if (record.getRecordingState() != AudioRecord.RECORDSTATE_RECORDING){
                record.stop();
                isAvailable=false;
            }
            record.stop();
        }catch (Exception e){
            isAvailable=false;
            LogUtils.e("麦克风权限检测异常",e.toString());
        }finally {
            record.release();
            record = null;
        }
        return isAvailable;
    }

    public boolean isCameraCanUse() {
        boolean canUse = true;
        Camera camera =null;
        try{
            camera = Camera.open();
            Camera.Parameters parameters = camera.getParameters();
            camera.setParameters(parameters);
        }catch (Exception e){
            canUse = false;
        }
        if (camera != null){
            camera.release();
        }
        return  canUse;
    }

}
