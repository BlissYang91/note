package dingshi.com.hibook.utils;

import android.graphics.Color;

import com.yuyh.library.imgsel.config.ISListConfig;

import dingshi.com.hibook.R;

/**
 * @author wangqi
 * @since 2017/11/6 17:58
 */

public class ImageSelector {

    public static ISListConfig getSingleConfig() {
        // 自由配置选项
        return new ISListConfig.Builder()
                // 是否多选, 默认true
                .multiSelect(false)
                // 是否记住上次选中记录, 仅当multiSelect为true的时候配置，默认为true
                .rememberSelected(false)
                // “确定”按钮背景色
                .btnBgColor(0xff000000)
                // “确定”按钮文字颜色
                .btnTextColor(Color.RED)
                // 使用沉浸式状态栏
                .statusBarColor(Color.parseColor("#000000"))
                // 返回图标ResId
                .backResId(R.drawable.ic_arrow_back_black_24dp)
                // 标题
                .title("选择图片")
                // 标题文字颜色
                .titleColor(Color.BLACK)
                // TitleBar背景色
                .titleBgColor(Color.parseColor("#ffffff"))
                // 裁剪大小。needCrop为true的时候配置
                .cropSize(1, 1, 200, 200)
                .needCrop(true)
                // 第一个是否显示相机，默认true
                .needCamera(true)
                // 最大选择图片数量，默认9
                .maxNum(1)
                .build();
    }

    public static ISListConfig getSingleNoCropConfig() {
        // 自由配置选项
        return new ISListConfig.Builder()
                // 是否多选, 默认true
                .multiSelect(false)
                // 是否记住上次选中记录, 仅当multiSelect为true的时候配置，默认为true
                .rememberSelected(false)
                // “确定”按钮背景色
                .btnBgColor(0xff000000)
                // “确定”按钮文字颜色
                .btnTextColor(Color.RED)
                // 使用沉浸式状态栏
                .statusBarColor(Color.parseColor("#000000"))
                // 返回图标ResId
                .backResId(R.drawable.ic_arrow_back_black_24dp)
                // 标题
                .title("选择图片")
                // 标题文字颜色
                .titleColor(Color.BLACK)
                // TitleBar背景色
                .titleBgColor(Color.parseColor("#ffffff"))
                // 裁剪大小。needCrop为true的时候配置
                .cropSize(1, 1, 200, 200)
                .needCrop(false)
                // 第一个是否显示相机，默认true
                .needCamera(true)
                // 最大选择图片数量，默认9
                .maxNum(1)
                .build();
    }


    public static ISListConfig getMultiConfig() {
        // 自由配置选项
        return new ISListConfig.Builder()
                // 是否多选, 默认true
                .multiSelect(true)
                // 是否记住上次选中记录, 仅当multiSelect为true的时候配置，默认为true
                .rememberSelected(false)
                // “确定”按钮背景色
                .btnBgColor(0xff000000)
                // “确定”按钮文字颜色
                .btnTextColor(0xffffffff)
                // 使用沉浸式状态栏
                .statusBarColor(Color.parseColor("#000000"))
                // 返回图标ResId
                .backResId(R.drawable.ic_arrow_back_black_24dp)
                //、 标题
                .title("选择图片")
                // 标题文字颜色
                .titleColor(Color.BLACK)
                // TitleBar背景色
                .titleBgColor(Color.parseColor("#ffffff"))
                // 裁剪大小。needCrop为true的时候配置
                .cropSize(1, 1, 200, 200)
                .needCrop(true)
                // 第一个是否显示相机，默认true
                .needCamera(true)
                // 最大选择图片数量，默认9
                .maxNum(9)
                .build();
    }


}
