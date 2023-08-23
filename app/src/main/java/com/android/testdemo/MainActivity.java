package com.android.testdemo;

import android.Manifest;
import android.content.Intent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.os.Bundle;
import android.widget.Button;

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
                Intent cameraIntent = new Intent(this, CameraActivity.class);
                startActivity(cameraIntent);
            } else {
                // 申请权限
                ActivityCompat.requestPermissions(this, permission, CAMERA_PERMISSION);
            }
        });

        recycle_btn.setOnClickListener(view -> {
            Intent intent = new Intent(this, RecycleViewDemo.class);
            startActivity(intent);
        });
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

}