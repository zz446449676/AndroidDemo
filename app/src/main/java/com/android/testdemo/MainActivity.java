package com.android.testdemo;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDialog;
import androidx.core.app.ActivityCompat;

import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Button;
import android.widget.Toast;

import com.android.testdemo.Util.PermissionUtil;

public class MainActivity extends AppCompatActivity {
    public static final int CAMERA_PERMISSION = 1000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button recycle_btn = findViewById(R.id.recycleyView);

        Button camera = findViewById(R.id.camera);
        camera.setOnClickListener(view -> {
            String[] permission = new String[] {Manifest.permission.CAMERA};
            if (PermissionUtil.hasPermission(this, permission)) {
                // 有权限，打开自定义相机界面
                gotoCameraActivity();
            } else if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                // 用户已经拒绝过，需要手动去开启权限
                Dialog dialogCompat = new AppCompatDialog(this);
                dialogCompat.setTitle("申请相机权限提示");
                showDialog(this, "申请相机权限提示", "使用该功能需要获取您的相机权限！", (dialog, which) -> gotoSettings(this), null);
            } else {
                // 第一次申请权限
                ActivityCompat.requestPermissions(this, permission, CAMERA_PERMISSION);
            }
        });

        recycle_btn.setOnClickListener(view -> {
            Intent intent = new Intent(this, RecycleViewDemo.class);
            startActivity(intent);
        });
    }

    private void gotoCameraActivity() {
        Intent cameraIntent = new Intent(this, CameraActivity.class);
        startActivity(cameraIntent);
    }

    public static void gotoSettings(Activity activity) {
        if (activity == null) return;
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + activity.getPackageName()));
        activity.startActivity(intent);
    }

    public static void showDialog(Context context, String title, String message, DialogInterface.OnClickListener positiveClick, DialogInterface.OnClickListener negativeClick) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(false);
        builder.setPositiveButton("确定", positiveClick);
        builder.setNegativeButton("取消", negativeClick);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION) {
            if (grantResults.length != 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                gotoCameraActivity();
            } else {
                Toast.makeText(this, "您已拒绝权限，请开启相机权限后再使用该功能", Toast.LENGTH_SHORT).show();
            }
        }
    }
}