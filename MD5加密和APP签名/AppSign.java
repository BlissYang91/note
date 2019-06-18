package dingshi.com.hibook.utils;

import android.os.Build;
import android.util.Base64;
import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dingshi.com.hibook.BuildConfig;
import dingshi.com.hibook.Constant;
import dingshi.com.hibook.MyApplicationLike;

/**
 * @author wangqi
 * @since 2017/11/8 下午3:57
 */

public class AppSign {

    public static final String APP_KEY = "0OHgJsh+z,NKTD=8,VgA7AoLGS@rkwue";

    /**
     * 构造请求参数
     *
     * @param map 请求参数
     * @return
     */
    public static HashMap<String, String> buildMap(HashMap<String, String> map) {
        map.putAll(getParams());

        List<String> keys = new ArrayList<String>(map.keySet());
        Collections.sort(keys);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < keys.size() - 1; i++) {
            String key = keys.get(i);
            String value = map.get(key);
            sb.append(buildKeyValue(key, value, false));
            sb.append("&");
        }
        String tailKey = keys.get(keys.size() - 1);
        String tailValue = map.get(tailKey);
        sb.append(buildKeyValue(tailKey, tailValue, false));


        String params = sb.toString() + "&appkey=" + APP_KEY;


        String md5;
        try {
            md5 = MD5.getMD5(params);
            map.put("sign", md5);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return map;
    }

    public static String buildString(HashMap<String, String> map) {
        map.putAll(getParams());

        List<String> keys = new ArrayList<String>(map.keySet());
        Collections.sort(keys);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < keys.size() - 1; i++) {
            String key = keys.get(i);
            String value = map.get(key);
            sb.append(buildKeyValue(key, value, false));
            sb.append("&");
        }
        String tailKey = keys.get(keys.size() - 1);
        String tailValue = map.get(tailKey);
        sb.append(buildKeyValue(tailKey, tailValue, false));


        String params = sb.toString() + "&appkey=" + APP_KEY;
        String md5;
        try {
            md5 = MD5.getMD5(params);
//            Log.i("params", md5);
            params = sb.toString() + "&sign=" + md5;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return params;
    }


    public static String buidParam(HashMap<String, String> map) {
        List<String> keys = new ArrayList<String>(map.keySet());
        Collections.sort(keys);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < keys.size() - 1; i++) {
            String key = keys.get(i);
            String value = map.get(key);
            sb.append(buildKeyValue(key, value, false));
            sb.append("&");
        }
        String tailKey = keys.get(keys.size() - 1);
        String tailValue = map.get(tailKey);
        sb.append(buildKeyValue(tailKey, tailValue, false));

        return sb.toString();
    }


    /**
     * 拼接键值对
     *
     * @param key
     * @param value
     * @param isEncode
     * @return
     */
    private static String buildKeyValue(String key, String value, boolean isEncode) {
        StringBuilder sb = new StringBuilder();
        sb.append(key);
        sb.append("=");
        if (isEncode) {
            try {
                sb.append(URLEncoder.encode(value, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                sb.append(value);
            }
        } else {
            sb.append(value);
        }
        return sb.toString();
    }


    public static HashMap<String, String> getParams() {
        HashMap<String, String> map = new HashMap<>();
        map.put("timestamp", String.valueOf(System.currentTimeMillis()));
        map.put("os_type", "android");
        map.put("os_version", String.valueOf(Build.VERSION.SDK_INT));
        map.put("channel", MyApplicationLike.channel);
        map.put("device_token", MyApplicationLike.deviceToken);
        return map;
    }


    public static String getPassword(String password) {
        byte[] encode = Base64.encode((password + APP_KEY).getBytes(), Base64.DEFAULT);
        String enc = new String(encode);
        return enc;
    }
}
