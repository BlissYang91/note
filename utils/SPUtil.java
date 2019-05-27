
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.bliss.yang.signingapplication.model.LoginMessage;
import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * @autor YangTianFu
 * @Date 2019/4/8 17:36
 * @Description
 */
public class SPUtil {
    private static final String SP_NAME = "userInfo";
    private static SharedPreferences sp;
    private static Gson gson;
    private static SPUtil instance;

    public static SPUtil getInstance(Application context) {
        if (instance == null) {
            synchronized (SPUtil.class) {
                if (instance == null) {
                    instance = new SPUtil(context);
                }
            }
        }
        return instance;
    }

    public SPUtil(Application context) {
        sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        gson = new Gson();
    }

    public void putUser(LoginMessage user) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(LoginMessage.class.getName(), gson.toJson(user));
        editor.apply();
    }

    public LoginMessage getUser() {
        String json = sp.getString(LoginMessage.class.getName(), "");
        LoginMessage user = gson.fromJson(json, LoginMessage.class);
        if (user == null) {
            user = new LoginMessage();
        }
        return user;
    }

}
