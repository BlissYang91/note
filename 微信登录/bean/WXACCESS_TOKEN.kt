package com.thesis.course.minicourse.bean

/**
 * @author YangTianFu
 * @date 2019/6/14  11:45
 * @description
 */
class WXACCESS_TOKEN {

    /**
     * access_token : ACCESS_TOKEN
     * expires_in : 7200
     * refresh_token : REFRESH_TOKEN
     * openid : OPENID
     * scope : SCOPE
     * unionid : o6_bmasdasdsad6_2sgVt7hMZOPfL
     */

    var access_token: String? = null
    var expires_in: Int = 0
    var refresh_token: String? = null
    var openid: String? = null
    var scope: String? = null
    var unionid: String? = null

    override fun toString(): String {
        return "WXACCESS_TOKEN{" + "\n" +
                "access_token='" + access_token + '\''.toString() + "\n" +
                ", expires_in=" + expires_in +
                ", refresh_token='" + refresh_token + '\''.toString() + "\n" +
                ", openid='" + openid + '\''.toString() + "\n" +
                ", scope='" + scope + '\''.toString() + "\n" +
                ", unionid='" + unionid + '\''.toString() + "\n" +
                '}'.toString()
    }
}
