
import android.media.AudioAttributes;
import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @autor YangTianFu
 * @Date 2019/5/9 18:03
 * @Description 获取指定文路径下所有指定文件
 */
public class FileUtils {
    private static final String TAG = "FileUtils";
    private static List<File> fileList = new ArrayList<>();
    private static String brand;

    public static List<File> getFilesPath() throws InterruptedException {
        String path = Environment.getExternalStorageDirectory().getPath();

        File sdDir = Environment.getExternalStorageDirectory();
        File file = null;
        brand = Build.BRAND;
        Log.e(TAG, "getFilesPath: brand == " + brand.toLowerCase());
        if (TextUtils.equals(brand.toLowerCase(), "lenovo")) {
            // /storage/emulated/0/Sound_recorder/Phone_recorder/19145520861/19145520861_通话_05月13日_15点26分.amr
            file = new File(path + File.separator + "Sound_recorder/Phone_recorder/");
        } else if (TextUtils.equals(brand.toLowerCase(), "meizu")) {
            file = new File(path + File.separator + "Recorder/call" + File.separator);
        } else if (TextUtils.equals(brand.toLowerCase(), "xiaomi")) {
            file = new File(path + File.separator + "MIUI/sound_recorder/call_rec" + File.separator);
        } else if (TextUtils.equals(brand.toLowerCase(), "vivo")) {
            // /storage/emulated/0/Record/Call/19145520861 2019-05-16 10-06-40.amr
            file = new File(path + File.separator + "Record/Call" + File.separator);
        } else if (TextUtils.equals(brand.toLowerCase(), "honor")) {
            file = new File(path + File.separator + "PhoneRecord" + File.separator);
        }
        if (!file.exists()) {
            file.mkdirs();
        }
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File[] files = file.listFiles();
            Log.e(TAG, "getFilesPath: " + files.toString());
            List<File> fileList = null;
            File[] filesArray = null;
            try {
                fileList = getFileName(files);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (fileList != null) {
                Log.e(TAG, "getFilesPath: " + fileList.toString());
                filesArray = fileList.toArray(new File[fileList.size()]);
                Arrays.sort(filesArray, new Comparator<File>() {
                    @Override
                    public int compare(File o1, File o2) {
                        long diff = o1.lastModified() - o2.lastModified();
                        // 按照文件修改日期递增
                        if (diff > 0) {
                            return -1;
                        } else if (diff == 0L) {
                            return 0;
                        } else {
                            return 1;
                        }
                    }

                    @Override
                    public boolean equals(Object obj) {
                        return true;
                    }
                });
                return Arrays.asList(filesArray);
            }
        }
        return null;
    }

    public static List<File> getFileName(File[] files) throws InterruptedException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmm");// HH:mm:ss

        if (files != null) {
            for (File file : files) {
                if (file.isDirectory() && file.exists()) {
                    getFileName(file.listFiles());
                } else {
                    fileList.add(file);
                }
            }
        }
        if (fileList != null) {
            Collections.sort(fileList);
            Collections.reverse(fileList);
        }

        Set set = new HashSet();
        List<File> newList = new ArrayList<>();
        for (File file : fileList) {
            if (set.add(file)) {
                newList.add(file);
            }
        }
        fileList.clear();
        fileList.addAll(newList);
        // for (int i = 0; i < fileList.size(); i++) {
        // Log.e(TAG, "新路径getFileName: " + fileList.get(i));
        // }
        return fileList;
    }

}
