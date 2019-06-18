package dingshi.com.hibook.utils;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

/**
 * @author wangqi
 * @since 2018/2/6 11:19
 */

public class KefuUtils {

    /**
     * 跳转到qq
     *
     * @param context
     */
    public static void jump(Application context) {
        try {
            final String url="mqqwpa://im/chat?chat_type=wpa&uin=3040896184";
           boolean isqq=isQQClientAvailable(context);
           if (isqq){
               Intent intent=new Intent(Intent.ACTION_VIEW,Uri.parse(url));
               intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
               if(isValidIntent(context,intent)){
                   context.startActivity(intent);
               }

           }

        } catch (Exception e) {
            Log.e("临时会话",e.getMessage().toString());
            Toast.makeText(context, "未安装qq", Toast.LENGTH_SHORT).show();
        }
    }
    /**
     * 判断 Uri是否有效
     */
    public static boolean isValidIntent(Context context, Intent intent) {
        PackageManager packageManager = context.getPackageManager();
        List<ResolveInfo> activities = packageManager.queryIntentActivities(intent, 0);
        return !activities.isEmpty();
    }

    /**
     * 判断qq是否可用
     *
     * @param context
     * @return
     */
    public static boolean isQQClientAvailable(Context context) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mobileqq")) {
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * 打电话
     *
     * @param context
     */
    public static void callPhone(Application context,String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+phoneNumber));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
