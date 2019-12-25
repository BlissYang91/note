package com.thesis.course.minicourse.umeng;

import android.app.Activity;
import android.graphics.Bitmap;
import android.util.Log;

import com.thesis.course.minicourse.bean.ShareContent;
import com.thesis.course.minicourse.utils.BitMapUtil;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

public class UMShareUtil {
    private static final String TAG = "UMShareUtil";
    private volatile static UMShareUtil instance = new UMShareUtil();

    public static UMShareUtil getInstance() {
        return instance;
    }

    /**
     *  分享图片给微信好友，并设置要分享的图片为缩略图
     * @param picUrl
     * @param activity
     */
    public void shareImgToWechatFriend(String picUrl,Activity activity){
        UMImage image = new UMImage(activity, picUrl);//网络图片
        image.compressStyle = UMImage.CompressStyle.SCALE;//大小压缩，默认为大小压缩，适合普通很大的图
        image.compressStyle = UMImage.CompressStyle.QUALITY;//质量压缩，适合长图的分享
//        压缩格式设置
        image.compressFormat = Bitmap.CompressFormat.PNG;//用户分享透明背景的图片可以设置这种方式，但是qq好友，微信朋友圈，不支持透明背景图片，会变成黑色
        new Thread(() -> {
            byte[] bytes = BitMapUtil.getFile(picUrl);
            Log.e(TAG, "run: 异步 bytes " + bytes);
            UMImage thumb =  new UMImage(activity, bytes);
            image.setThumb(thumb);
        }
        ).start();

        new ShareAction(activity)
                .setPlatform(SHARE_MEDIA.WEIXIN)
                .setDisplayList(SHARE_MEDIA.WEIXIN)
                .setCallback(UMShareListenerIml.getInstance())
                .withMedia(image).open();

    }
    /**
     * 分享链接给微信好友
     * @param content 要分享的详细内容
     * @param context  分享的宿主acvitity
     */
    public void shareLinkToWechatFriend(ShareContent content, Activity context){
        UMWeb web = new UMWeb(content.getLink());
        new Thread(() -> {
            byte[] bytes = BitMapUtil.getFile(content.getImgUrl());
            Log.e(TAG, "run: 异步 bytes " + bytes);
            UMImage thumb =(new UMImage(context, bytes));
            web.setThumb(thumb);
        }
        ).start();
        web.setTitle(content.getTitle());//标题
        web.setDescription(content.getDesc());//描述
        new ShareAction(context)
                .withMedia(web)
                .setDisplayList(SHARE_MEDIA.WEIXIN)
                .setPlatform(SHARE_MEDIA.WEIXIN)
                .setCallback(UMShareListenerIml.getInstance())
                .share();
    }

    /**
     * 分享链接给微信朋友圈
     * @param content 要分享的详细内容
     * @param context  分享的宿主acvitity
     */
    public void shareLinkToWechatCircle(ShareContent content, Activity context){
        UMWeb web = new UMWeb(content.getLink());
        new Thread(() -> {
            byte[] bytes = BitMapUtil.getFile(content.getImgUrl());
            Log.e(TAG, "run: 异步 bytes " + bytes);
            UMImage thumb =(new UMImage(context, bytes));
            web.setThumb(thumb);
        }
        ).start();
        web.setTitle(content.getTitle());//标题
        web.setDescription(content.getDesc());//描述
        new ShareAction(context)
                .withMedia(web)
                .setDisplayList(SHARE_MEDIA.WEIXIN)
                .setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
                .setCallback(UMShareListenerIml.getInstance())
                .share();
    }
}
