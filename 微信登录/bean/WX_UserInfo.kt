package com.thesis.course.minicourse.bean

/**
 * @author YangTianFu
 * @date 2019/6/14  16:23
 * @description 获取微信用户信息
 * openid	普通用户的标识，对当前开发者帐号唯一
   nickname	普通用户昵称
   sex	普通用户性别，1为男性，2为女性
   province	普通用户个人资料填写的省份
   city	普通用户个人资料填写的城市
   country	国家，如中国为CN
   headimgurl	用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空
   privilege	用户特权信息，json数组，如微信沃卡用户为（chinaunicom）
   unionid	用户统一标识。针对一个微信开放平台帐号下的应用，同一用户的unionid是唯一的。
 * 开发者最好保存unionID信息，以便以后在不同应用之间进行用户信息互通。
 */
data class WX_UserInfo(
        var city: String,
        var country: String,
        var headimgurl: String,
        var nickname: String,
        var openid: String,
        var privilege: List<String>,
        var province: String,
        var sex: Int,
        var unionid: String
)