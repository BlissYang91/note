package com.thesis.mentor.common;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @autor YangTianFu
 * @Date 2019/4/19  14:15
 * @Description 拼接URL的工具类
 */
public class URLJoint {

    /**
     *
     * @param url 明文拼接的path
     * @param params path中的参数集合
     * @return 拼接后的path路径
     */
    public static String urlJoint(String url, Map<String, Object> params) {
        StringBuilder realURL = new StringBuilder(url);
//        realURL = realURL.append(url);
        boolean isFirst = true;
        if (params == null) {
            params = new HashMap<>();
        } else {
            Set<Map.Entry<String, Object>> entrySet = params.entrySet();
            for (Map.Entry<String, Object> entry : entrySet) {
                if (isFirst && !url.contains("?")) {
                    isFirst = false;
                    realURL.append("?");
                } else {
                    realURL.append("&");
                }
                realURL.append(entry.getKey());
                realURL.append("=");
                if (entry.getValue() == null) {
                    realURL.append(" ");
                } else {
                    realURL.append(entry.getValue());
                }

            }
        }

        return realURL.toString();
    }
}
