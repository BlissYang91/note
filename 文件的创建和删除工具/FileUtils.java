package com.lab.web.entity;
import java.io.File;
import java.io.IOException;

import android.os.Environment;
/**
 * Created by ytf on 2017/11/14.
 */

public class FileUtils {
    /**判断SD卡是否存在
     * @date 2014-1-8 上午9:42:59
     * @return
     * @returnType boolean
     */
    public static boolean existSDCard() {
        boolean sdCardExist = Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED); // 判断sd卡是否存在
        return sdCardExist;
    }

    /**删除文件
     * @author 王帅
     * @date 2014-1-8 上午9:42:02
     * @param filePath
     *            文件绝对路径
     * @return true 成功
     * @returnType boolean
     */
    public static boolean deleteFile(String filePath) {
        boolean ret = false;
        File file = new File(filePath);
        if (file.exists()) {
            ret = file.delete();
        }
        return ret;
    }

    public static File compressFile(String path, String compressPath) {
        File file = new File(path);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }
}
