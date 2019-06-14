package com.thesis.course.minicourse.wxapi;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.google.gson.JsonObject;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.thesis.course.minicourse.R;
import com.thesis.course.minicourse.SampleApplicationLike;
import com.thesis.course.minicourse.api.ApiService;
import com.thesis.course.minicourse.bean.VerifyWXAccessToken;
import com.thesis.course.minicourse.bean.WXACCESS_TOKEN;
import com.thesis.course.minicourse.bean.WXEntry;
import com.thesis.course.minicourse.bean.WXRefreshToken;
import com.thesis.course.minicourse.bean.WX_UserInfo;
import com.thesis.course.minicourse.config.ConfigUrl;
import com.thesis.course.minicourse.config.Constants;
import com.thesis.course.minicourse.retrofit.RetrofitManager;
import com.thesis.course.minicourse.retrofit.RxSchedulers;
import com.thesis.course.minicourse.utils.GsonUtil;
import com.umeng.socialize.weixin.view.WXCallbackActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


/**
 * @autor YangTianFu
 * @Date 2019/3/25  13:34
 * @Description
 */
public class WXEntryActivity extends WXCallbackActivity implements IWXAPIEventHandler {
    private static final String TAG = "WXEntryActivity";
    private IWXAPI api;

    private static final int RETURN_MSG_TYPE_LOGIN = 1;
    private static final int RETURN_MSG_TYPE_SHARE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SampleApplicationLike.msgApi.handleIntent(getIntent(), this);
    }

    /**
     * 微信发送请求到第三方应用时，会回调到该方法
     *
     * @param req
     */
    @Override
    public void onReq(BaseReq req) {
        super.onReq(req);
    }

    /**
     * 第三方应用发送到微信的请求处理后的响应结果，会回调到该方法
     * app发送消息给微信，处理返回消息的回调
     *
     * @param resp
     */
    @Override
    public void onResp(BaseResp resp) {
//        super.onResp(resp);
        int type = resp.getType();
        int result = 0;
        String code = "";

        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                result = R.string.errcode_success;
                String respJson = JSON.toJSONString(resp);
                WXEntry entry = GsonUtil.changeGsonToBean(respJson, WXEntry.class);
                Log.e(TAG, "onResp: WXEntry = " + entry.toString());
                if (entry != null) {
                    code = entry.getCode();
//                    getAccessToken(code);
                    getCompositeUserInfo(code);
                }
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                result = R.string.errcode_cancel;
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                result = R.string.errcode_deny;
                break;
            case BaseResp.ErrCode.ERR_UNSUPPORT:
                result = R.string.errcode_unsupported;
                break;
            default:
                result = R.string.errcode_unknown;
                break;
        }
        Log.e(TAG, "onResp: type == " + type + " errStr = " + resp.errStr + " openId = " + resp.openId + " result" + result);
        Log.e(TAG, "onResp: 微信授权返回json：\n" + JSON.toJSONString(resp));
    }

    private WXACCESS_TOKEN tokenEntry;
    private  Disposable disposable;

    /**
     * 集中获取微信用户信息
     * @param code
     */
    private void getCompositeUserInfo(String code) {
        Map<String, String> quaryMap = new HashMap<>();
        quaryMap.put("appid", Constants.UM_WX_APPID);
        quaryMap.put("secret", Constants.UM_WX_APPSECRET);
        quaryMap.put("code", code);
        quaryMap.put("grant_type", "authorization_code");
        disposable = RetrofitManager.getInstance().getRequestService()
                .getWxAccessToken(ConfigUrl.WX_ACCESS_TOKEN, quaryMap)
                .compose(RxSchedulers.io_main())
                .doOnNext(wxaccess_token -> {
                    Log.e(TAG, "accept: " + wxaccess_token.toString());
                    tokenEntry = wxaccess_token;

                })
                .observeOn(Schedulers.io())
                .flatMap((Function<WXACCESS_TOKEN, ObservableSource<VerifyWXAccessToken>>) wxaccess_token -> {
                    Log.e(TAG, "accept:拿到token " + wxaccess_token.toString());
                    String access_token = wxaccess_token.getAccess_token();
                    String openid = wxaccess_token.getOpenid();

                    return RetrofitManager.getInstance()
                            .getRequestService()
                            .verifyWxAccessToken(ConfigUrl.WX_VERIFY_TOKEN, access_token, openid);
                })
                .flatMap((Function<VerifyWXAccessToken, ObservableSource<WX_UserInfo>>) verifyWXAccessToken -> {
                    if (verifyWXAccessToken.getErrcode() == 0){
                        Map<String, String> quaryMap1 = new HashMap<>();
                        quaryMap1.put("access_token", tokenEntry.getAccess_token());
                        quaryMap1.put("openid", tokenEntry.getOpenid());
                        quaryMap1.put("lang", "zh_CN");
                        return   RetrofitManager.getInstance().getRequestService()
                                .getWx_UserInfo(ConfigUrl.WX_USER_INFO, quaryMap1);
                    }else {
//                        token验证失败，刷新token
                        Log.e(TAG, "getCompositeUserInfo:  token验证失败，刷新token" );
                        return null;
                    }
                }).subscribe(wx_userInfo -> {
            Log.e(TAG, "flatMap 拉取用户信息成功：\n" + wx_userInfo.toString());
            EventBus.getDefault().post(wx_userInfo);
            disposable.dispose();
        }, throwable -> {
            Log.e(TAG, "getCompositeUserInfo: 异常：" + throwable.toString() );
        });



    }

    /**
     * 获取微信accessToken
     *
     * @param code
     */
    private void getAccessToken(String code) {
        Map<String, String> quaryMap = new HashMap<>();
        quaryMap.put("appid", Constants.UM_WX_APPID);
        quaryMap.put("secret", Constants.UM_WX_APPSECRET);
        quaryMap.put("code", code);
        quaryMap.put("grant_type", "authorization_code");
        Log.e(TAG, "执行getAccessToken:code == " + code);
        if (!"".equals(code)) {
            RetrofitManager.getInstance()
                    .getRequestService()
                    .getWxAccessToken(ConfigUrl.WX_ACCESS_TOKEN, quaryMap)
                    .compose(RxSchedulers.io_main())
                    .subscribe(wxaccess_token -> {
                        Log.e(TAG, "accept: " + wxaccess_token.toString());
                        verifyAccessToken(wxaccess_token);
                    }, throwable -> Log.e(TAG, "accept: 异常： " + throwable.toString()));
        }
    }

    /**
     * 检验授权凭证（access_token）是否有效
     * @param wxaccess_token 获取到的token
     */
    private void verifyAccessToken(WXACCESS_TOKEN wxaccess_token) {
        String access_token = wxaccess_token.getAccess_token();
        String openid = wxaccess_token.getOpenid();
        RetrofitManager.getInstance()
                .getRequestService()
                .verifyWxAccessToken(ConfigUrl.WX_VERIFY_TOKEN, access_token, openid)
                .compose(RxSchedulers.io_main())
                .subscribe(new Consumer<VerifyWXAccessToken>() {
                    @Override
                    public void accept(VerifyWXAccessToken verifyWXAccessToken) throws Exception {
                        Log.e(TAG, "token是否有效: VerifyWXAccessToken = " + verifyWXAccessToken.toString());
                        if (verifyWXAccessToken.getErrcode() == 0) {
//                            token有效，去获取微信用户信息
                            getWxUserInfo(wxaccess_token.getAccess_token(), wxaccess_token.getOpenid());
                        } else {
//                            刷新accessToken
                            if (wxaccess_token.getRefresh_token() != null) {
                                refreshWxAccessToken(wxaccess_token.getRefresh_token());
                            }
                        }

                    }
                });
    }

    /**
     * 刷新token
     *
     * @param wxaccess_token
     */
    private void refreshWxAccessToken(String wxaccess_token) {
        Map<String, String> quaryMap = new HashMap<>();
        quaryMap.put("appid", Constants.UM_WX_APPID);
        quaryMap.put("refresh_token", wxaccess_token);
        quaryMap.put("grant_type", "refresh_token");
        RetrofitManager.getInstance()
                .getRequestService()
                .refreshWxAccessToken(ConfigUrl.WX_REFRESH_TOKEN, quaryMap)
                .compose(RxSchedulers.io_main())
                .subscribe(new Consumer<WXRefreshToken>() {
                    @Override
                    public void accept(WXRefreshToken wxRefreshToken) throws Exception {
                        Log.e(TAG, "accept: 刷新token成功：" + wxRefreshToken.toString());
                        getWxUserInfo(wxRefreshToken.getAccess_token(), wxRefreshToken.getOpenid());
                    }

                });
    }

    /**
     * 获取微信用户信息
     */
    private void getWxUserInfo(String access_token, String openid) {
        Map<String, String> quaryMap = new HashMap<>();
        quaryMap.put("access_token", access_token);
        quaryMap.put("openid", openid);
        quaryMap.put("lang", "zh_CN");
        RetrofitManager.getInstance().getRequestService()
                .getWx_UserInfo(ConfigUrl.WX_USER_INFO, quaryMap)
                .compose(RxSchedulers.io_main())
                .subscribe(new Consumer<WX_UserInfo>() {
                    @Override
                    public void accept(WX_UserInfo wx_userInfo) throws Exception {
                        Log.e(TAG, "accept: 拉取用户信息成功：\n" + wx_userInfo.toString());
                        EventBus.getDefault().post(wx_userInfo);
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (disposable != null){
            disposable.dispose();
        }
    }
}
