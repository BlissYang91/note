package com.thesis.course.minicourse.bean

/**
 * @author YangTianFu
 * @date 2019/6/14  15:43
 * @description 验证token是否有效
 */
class VerifyWXAccessToken {

    /**
     * errcode : 0
     * errmsg : ok
     */

    var errcode: Int = 0
    var errmsg: String? = null
    override fun toString(): String {
        return "VerifyWXAccessToken(errcode=$errcode, errmsg=$errmsg)"
    }

}
