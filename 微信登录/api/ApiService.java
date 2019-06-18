package com.thesis.course.minicourse.api;

import com.thesis.course.minicourse.bean.VerifyWXAccessToken;
import com.thesis.course.minicourse.bean.WXACCESS_TOKEN;
import com.thesis.course.minicourse.bean.WXRefreshToken;
import com.thesis.course.minicourse.bean.WX_UserInfo;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * @autor YangTianFu
 * @Date 2019/3/21  17:59
 */
public interface ApiService {
    /**
     * 微信登录模块获取access_token
     * @param url 路径
     * @param map 查询参数
     * @return
     */
    @GET
    Observable<WXACCESS_TOKEN> getWxAccessToken(@Url String url, @QueryMap Map<String,String> map);

    /**
     * 检验授权凭证（access_token）是否有效
     * @param url 全路径
     * @param access_token 获取到的token
     * @param openid 获取到的openid
     * @return
     */
    @GET
    Observable<VerifyWXAccessToken> verifyWxAccessToken(@Url String url,@Query("access_token") String access_token,@Query("openid") String openid);

    /**
     * 刷新或续期微信access_token使用,token 失效时调用
     * @param url
     * @param map
     * @return
     */
    @GET
    Observable<WXRefreshToken> refreshWxAccessToken(@Url String url,@QueryMap Map<String,String> map);

    /**
     * 拉取微信用户信息
     * @param url 全路径
     * @param map 请求参数
     * @return 用户信息的实体类
     */
    @GET
    Observable<WX_UserInfo> getWx_UserInfo(@Url String url, @QueryMap Map<String,String> map);
}
