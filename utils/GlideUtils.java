
import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import dingshi.com.hibook.R;
import dingshi.com.hibook.view.GlideCircleTransform;
import dingshi.com.hibook.view.GlideRoundTransform;

/**
 * Created by apple on 2017/10/26.
 */

public class GlideUtils {

    /**
     * 加载圆形图片
     */
    public static void loadCircleImage(Context context, String url, ImageView imageView) {
        Glide.with(context).load(url).transform(new GlideCircleTransform(context)).error(R.drawable.my_default_photo)
                .into(imageView);
    }

    public static void loadCircleImage(Context context, int res, ImageView imageView) {
        Glide.with(context).load(res).transform(new GlideCircleTransform(context)).error(R.drawable.my_default_photo)
                .into(imageView);
    }

    public static void loadCircleImage(Context context, int res, int error, ImageView imageView) {
        Glide.with(context).load(res).error(error).transform(new GlideCircleTransform(context)).into(imageView);
    }

    public static void loadCircleImage(Context context, String url, int error, ImageView imageView) {
        Glide.with(context).load(url).error(error).transform(new GlideCircleTransform(context)).into(imageView);
    }

    public static void loadRoundImage(Context context, String url, ImageView imageView) {
        imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        Glide.with(context).load(url).transform(new GlideRoundTransform(context)).error(R.drawable.loading)
                .into(imageView);
    }

    /**
     * 加载图片
     */
    public static void load(Context context, String url, ImageView imageView) {
        Glide.with(context).load(url).into(imageView);
    }

    public static void load(Context context, int res, ImageView imageView) {
        Glide.with(context).load(res).into(imageView);
    }

}
