package dingshi.com.hibook.utils;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import dingshi.com.hibook.bean.Avatar;
import dingshi.com.hibook.bean.Book;
import dingshi.com.hibook.bean.User;

/**
 * @author wangqi
 * @since 2017/11/9 14:10
 */

public class SpUtils {

    private static final String SP_NAME = "dingshi";

    private static final String KEY_SEARCH = "search";

    private static final String KEY_BOOT = "boot_page";


    private static SharedPreferences sp;
    private static Gson gson;


    private SpUtils() {
    }

    public static void init(Application context) {
        sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        gson = new Gson();
    }


    public static void putUser(User user) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(User.class.getName(), gson.toJson(user));
        editor.apply();
    }


    public static User getUser() {
        String json = sp.getString(User.class.getName(), "");
        User user = gson.fromJson(json, User.class);
        if (user == null) {
            user = new User();
        }
        return user;
    }


    public static void putSearch(String text) {
        SharedPreferences.Editor editor = sp.edit();
        //将本地的数据取出来
        ArrayList<String> list = new Gson().fromJson(sp.getString(KEY_SEARCH, ""), ArrayList.class);

        if (list == null) {
            list = new ArrayList<>();
        }
        //添加数据
        list.add(text);
        //转成字符串
        text = new Gson().toJson(list);
        //存储
        editor.putString(KEY_SEARCH, text);
        editor.apply();
    }

    public static ArrayList<String> getSearch() {
        ArrayList list = new Gson().fromJson(sp.getString(KEY_SEARCH, ""), ArrayList.class);
        if (list == null) {
            list = new ArrayList();
        }
        return list;
    }

    public static void putAllSearch(List<String> list) {
        SharedPreferences.Editor editor = sp.edit();
        String text = new Gson().toJson(list);
        //存储
        editor.putString(KEY_SEARCH, text);
        editor.apply();
    }


    public static void putAvatar(String userId, Avatar avatar) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(userId, new Gson().toJson(avatar));
        editor.apply();
    }

    public static Avatar getAvatar(String userId) {
        return new Gson().fromJson(sp.getString(userId, ""), Avatar.class);
    }


    /**
     * 是否启动过
     *
     * @return
     */
    public static boolean getBootPage() {
        return sp.getBoolean(KEY_BOOT, false);
    }

    public static void setBootPage() {
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(KEY_BOOT, true);
        editor.apply();
    }


    /**
     * @param userId 用户id
     * @param flag   判断当前是是否已被处理
     */
    public static void putInvite(String userId, boolean flag) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(userId + "#borrow", flag);
        editor.apply();
    }


    public static Boolean getInvite(String userId) {
        return sp.getBoolean(userId + "#borrow", false);
    }


}
