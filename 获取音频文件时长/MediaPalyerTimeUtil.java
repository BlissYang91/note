package com.thesis.course.minicourse.utils;

import android.media.MediaPlayer;
import android.util.Log;

/**
 * @author YangTianFu
 * @date 2019/5/30  17:49
 * @description 音频文件的时长
 */
public class MediaPalyerTimeUtil {
    private static final String TAG = "MediaPalyerTimeUtil";
    private volatile static MediaPalyerTimeUtil instance;
    private  MediaPlayer mediaPlayer;

    public MediaPalyerTimeUtil() {
    }

    public static MediaPalyerTimeUtil getInstance() {
        if (instance == null){
            synchronized (MediaPalyerTimeUtil.class){
                if (instance == null){
                    instance = new MediaPalyerTimeUtil();
                }
            }
        }
        return instance;
    }

    /**
     *
     * @param path 文件路劲
     * @return 时间长度，单位秒
     */
    public String getDuringTime(String path){
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(path);
            mediaPlayer.prepare();
        } catch (Exception e) {
            Log.e(TAG, "getDuringTime: 异常" + e.toString());
            e.printStackTrace();
        }
        int during = mediaPlayer.getDuration()/1000;
        Log.e(TAG, "getDuringTime: 时长 ==  " + during);
        mediaPlayer.release();
        return String.valueOf(during);
    }
}
