package com.android.testdemo.Util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class FileUtil {
    public static void writeToFile(String data, String filePath, boolean isAppend) {
        File file = new File(filePath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException ignored) {
                Log.e("zhang", "Exception : " + ignored);
            }
        }
        try (BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, isAppend)))) {
            out.write(data + "\n");
        } catch (Exception ignored) {
            Log.e("zhang", "Exception : " + ignored);
        }
    }

    public static void writeClientIdToExternal(String filePath, String content) {
        try {
            File parentDir = new File(filePath).getParentFile();
            Log.d("zhang", "parentDir : " + parentDir.toString());
            if (!parentDir.exists()) {
                parentDir.mkdir();
            }
        } catch (Exception ignored) {}
        writeToFile(content, filePath, false);
    }

    public static String readFile(Context context, String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            return "";
        }
        StringBuilder sb = new StringBuilder("");
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
            String content = "";
            while((content = br.readLine()) != null) {
                sb.append(content);
            }
        } catch (Exception e) {
            Log.e("zhang", "exception : " + e);
        } finally {
            try {
                if(br != null) {
                    br.close();
                }
            } catch (IOException e) {
                Log.e("zhang", "exception : " + e);
            }
        }
        return sb.toString();
    }

    public static void checkPermission(Activity activity, String[] permissions, int requestCode){
        if(permissions == null || permissions.length == 0 || activity == null) return;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(activity, permissions, requestCode);
        }
    }

    public static boolean hasPermissions(Activity activity, String... permissions){
        if(activity == null || permissions == null || permissions.length < 1) return false;
        for(String permission : permissions) {
            if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED ||
                    ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                return false;
            }
        }
        return true;
    }
}
