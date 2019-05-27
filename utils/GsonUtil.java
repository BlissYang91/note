
import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * Created by YTF on 2017/9/12. 功能：将后台返回的json数据直接转换成list结合或者事先封装好的实体类同时完成赋值
 * 达到可以直接使用get方法获取到对应字段的数据 使用方法： String json = "这里是网络请求来的json字符串 " Bean bean =
 * GsonUtil.changGsonToBean(json,Bean.class); TextView text
 * =text.setText(bean.getName());
 */

public class GsonUtil {

    private static String TAG = "Gson解析异常：";

    public GsonUtil() {
        // TODO Auto-generated constructor stub
    }

    public static String createGsonString(Object object) {
        Gson gson = new Gson();
        String gsonString = gson.toJson(object);
        return gsonString;
    }

    // 把json字符串变成实体类Bean并对对应参数赋值
    public static <T> T changeGsonToBean(String gsonString, Class<T> cls) {
        try {
            Gson gson = new Gson();
            T t = gson.fromJson(gsonString, cls);
            return t;
        } catch (Exception e) {
            Log.d(TAG, "gson转实体类异常: " + e.getMessage());
            return null;
        }
    }

    // 把json字符串变成List<T>集合
    public static <T> List<T> changeGsonToList(String gsonString, Class<T> cls) {
        List<T> list = null;
        try {
            Gson gson = new Gson();
            list = gson.fromJson(gsonString, new TypeToken<List<T>>() {
            }.getType());
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            Log.d(TAG, "gson转List<T>集合异常: " + e.getMessage());
        }
        return list;
    }

    // 把json字符串变成List<Map<String, T>集合
    public static <T> List<Map<String, T>> changeGsonToListMaps(String gsonString) {
        List<Map<String, T>> list = null;
        try {
            Gson gson = new Gson();
            list = gson.fromJson(gsonString, new TypeToken<List<Map<String, T>>>() {
            }.getType());
        } catch (JsonSyntaxException e) {
            Log.d(TAG, "gson转List<Map<String, T>集合异常: " + e.getMessage());
            e.printStackTrace();
        }
        return list;
    }

    // 把json字符串变成Map集合
    public static <T> Map<String, T> changeGsonToMaps(String gsonString) {
        Map<String, T> map = null;
        try {
            Gson gson = new Gson();
            map = gson.fromJson(gsonString, new TypeToken<Map<String, T>>() {
            }.getType());
        } catch (JsonSyntaxException e) {
            Log.d(TAG, "gson转Map集合异常: " + e.getMessage());
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 把json字符串变成实体类集合 params: new TypeToken<List<yourbean>>(){}.getType(),
     *
     * @param json
     * @param type new TypeToken<List<yourbean>>(){}.getType()
     * @return
     */
    public static <T> List<T> parseJsonToList(String json, Type type) {
        List<T> list = null;
        try {
            Gson gson = new Gson();
            list = gson.fromJson(json, type);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            Log.d(TAG, "gson转实体类异常: " + e.getMessage());
        }
        return list;
    }

}
