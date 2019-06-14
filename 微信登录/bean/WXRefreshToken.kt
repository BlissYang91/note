package com.thesis.course.minicourse.bean

/**
 * @author YangTianFu
 * @date 2019/6/14  17:01
 * @description 刷新或续期微信access_token使用
 * access_token	接口调用凭证
   expires_in	access_token接口调用凭证超时时间，单位（秒）
   refresh_token	用户刷新access_token
   openid	授权用户唯一标识
   scope	用户授权的作用域，使用逗号（,）分隔
 */
data class WXRefreshToken(
        var access_token: String,
        var expires_in: Int,
        var openid: String,
        var refresh_token: String,
        var scope: String
)