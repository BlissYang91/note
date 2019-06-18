package dingshi.com.hibook.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * @author wangqi
 * @since 2018/1/30 14:14
 */

public class CallUtils {
    public static void callPhone(Context context, String phone) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void msgPhone(Context context, String phone) {
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + phone));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
