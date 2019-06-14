package com.thesis.course.minicourse.config;

/**
 * @author YangTianFu
 * @date 2019/6/14  14:25
 * @description
 */
public class ConfigUrl {
    /**
     * 获取微信ACCESS_TOKEN
     * https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code
     */
    public  static final String  WX_ACCESS_TOKEN = "https://api.weixin.qq.com/sns/oauth2/access_token";
    /**
     * 刷新refresh_token
     * https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=APPID&grant_type=refresh_token&refresh_token=REFRESH_TOKEN
     */
    public  static final String  WX_REFRESH_TOKEN = "https://api.weixin.qq.com/sns/oauth2/refresh_token";

    /**
     * 检验授权凭证（access_token）是否有效
     * https://api.weixin.qq.com/sns/auth?access_token=ACCESS_TOKEN&openid=OPENID
     */
    public  static final String  WX_VERIFY_TOKEN = "https://api.weixin.qq.com/sns/auth";

    /**
     * 获取微信用户个人信息
     * https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID
     */
    public  static final String  WX_USER_INFO = "https://api.weixin.qq.com/sns/userinfo";
}
