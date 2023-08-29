package com.android.testdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.FocusMeteringAction;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.video.Recorder;
import androidx.camera.video.Recording;
import androidx.camera.video.VideoCapture;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.icu.text.SimpleDateFormat;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SoundEffectConstants;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.android.testdemo.Util.PermissionUtil;
import com.android.testdemo.Util.ViewHelper;
import com.android.testdemo.databinding.ActivityCameraBinding;
import com.bumptech.glide.Glide;
import com.google.common.util.concurrent.ListenableFuture;

import java.nio.ByteBuffer;
import java.util.Locale;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CameraActivity extends AppCompatActivity {
    private static final String FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS";
    private static final int WRITE_EXTERNAL_STORAGE = 101;
    // 将XML布局文件和Activity绑定
    private ActivityCameraBinding viewBinding;
    private ImageCapture imageCapture;
    private VideoCapture<Recorder> videoCapture;
    private Recording recording;
    // 定义一个专门用于处理相机的线程池

    private Camera mCamera;
    private ExecutorService cameraExecutor;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewBinding = ActivityCameraBinding.inflate(getLayoutInflater());
        setContentView(viewBinding.getRoot());

        viewBinding.imageCaptureButton.setOnClickListener( view -> takePhoto());
        viewBinding.close.setOnClickListener( view -> closeCamera());

        // 镜头聚焦
        viewBinding.viewFinder.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                try {
                    if (mCamera != null) {
                        FocusMeteringAction action = new FocusMeteringAction.Builder(viewBinding.viewFinder.getMeteringPointFactory().createPoint(event.getX(), event.getY())).build();
                        showTapView((int) event.getX(), (int) event.getY());
                        mCamera.getCameraControl().startFocusAndMetering(action);
                    }
                } catch (Exception ignored) {}
                return false;
            }
        });

        cameraExecutor = Executors.newSingleThreadExecutor();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 如果系统版本小于28，还需要读写外部存储权限
        String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P && !PermissionUtil.hasPermission(this, permissions)) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                MainActivity.showDialog(this, "申请文件读写权限", "使用该功能需要申请您的文件读写权限！",
                        (dialog, which) -> MainActivity.gotoSettings(CameraActivity.this), (dialog, which) -> closeCamera());
            } else {
                ActivityCompat.requestPermissions(this, permissions, WRITE_EXTERNAL_STORAGE);
            }
        } else startCamera();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cameraExecutor.shutdown();
    }

    // 展现对焦边框
    private void showTapView(int x, int y) {
        int dp2px50 = ViewHelper.dp2px(this, 50);
        PopupWindow popupWindow = new PopupWindow(dp2px50, dp2px50);
        ImageView imageView = new ImageView(this);
        imageView.setImageResource(R.drawable.ic_focus_view);
        popupWindow.setContentView(imageView);

        // 聚焦框底部不能超出viewFinder底部,这个pop框的绘制是从父组件底部开始的
        int viewFinderHeight = viewBinding.viewFinder.getHeight();
        // 所以偏移量是相对于父组件的位置
        int bottomLimitY = - dp2px50;
        int popupY = -viewFinderHeight + y;

        // 聚焦框右侧不能超出viewFinder右侧
        int rightLimitY = viewBinding.viewFinder.getWidth() - dp2px50;

        popupWindow.showAsDropDown(viewBinding.viewFinder, Math.min(x, rightLimitY), Math.min(bottomLimitY, popupY));
        // 600毫秒后消失
        viewBinding.viewFinder.postDelayed(popupWindow::dismiss, 600);
        viewBinding.viewFinder.playSoundEffect(SoundEffectConstants.CLICK);
    }

    // 展现拍照缩略图
    private void showPhotoThumbnail(Image image) {
        if (image == null) return;
        int dp2px80 = ViewHelper.dp2px(this, 80);
        PopupWindow popupWindow = new PopupWindow(dp2px80, dp2px80);
        ImageView imageView = new ImageView(this);

        // 创建一个ByteBuffer来保存Image的数据
        ByteBuffer buffer = image.getPlanes()[0].getBuffer();
        // 将ByteBuffer数据转换为byte数组
        byte[] byteArray = new byte[buffer.remaining()];
        buffer.get(byteArray);
        // 使用ImageDecoder类来加载和解码图像
        Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        // 因为解析到的原始bitmap被旋转了，所以现在需要进行旋转，创建Matrix对象，并设置旋转角度为90度
        Matrix matrix = new Matrix();
        matrix.postRotate(90);
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        // 使用ImageDecoder类来加载和解码图像
        image.close();

        imageView.setImageBitmap(bitmap);
        popupWindow.setContentView(imageView);
        popupWindow.showAsDropDown(viewBinding.viewFinder, 0, 0);
        // 1000毫秒后消失
        viewBinding.viewFinder.postDelayed(popupWindow::dismiss, 3000);
        viewBinding.viewFinder.playSoundEffect(SoundEffectConstants.CLICK);
    }

    // 开始使用相机，preview 实现预览模式
    private void startCamera() {
        // 创建 ProcessCameraProvider 的实例。
        ListenableFuture<ProcessCameraProvider> cameraProviderFuture = ProcessCameraProvider.getInstance(this);

        // 向 cameraProviderFuture 添加监听器。添加 Runnable 作为一个参数。添加 ContextCompat.getMainExecutor() 作为第二个参数。这将返回一个在主线程上运行的 Executor。
        // 在 Runnable 中，添加 ProcessCameraProvider。它用于将相机的生命周期绑定到应用进程中的 LifecycleOwner。
        cameraProviderFuture.addListener(() -> {
            try {
                // 将相机生命周期与Activity进行绑定。这消除了打开和关闭相机的任务，因为 CameraX 具有生命周期感知能力。
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                // 初始化 Preview 对象，在其上调用 build，从取景器中获取 Surface 提供程序，然后在预览上进行设置。
                Preview preview = new Preview.Builder().build();
                preview.setSurfaceProvider(viewBinding.viewFinder.getSurfaceProvider());

                imageCapture = new ImageCapture.Builder().build();

                // 默认设置为后置摄像头
                CameraSelector cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA;
                // 在重新绑定之前取消绑定
                cameraProvider.unbindAll();
                // 将相机与视图绑定
                mCamera = cameraProvider.bindToLifecycle(CameraActivity.this, cameraSelector, preview, imageCapture);
            } catch (ExecutionException | InterruptedException ignored) {
                Toast.makeText(CameraActivity.this, "Oops! 出现了一些错误 ： " + ignored, Toast.LENGTH_SHORT).show();
            }
        }, ContextCompat.getMainExecutor(this));
    }

    // ImageCapture 实现拍照功能
    private void takePhoto() {
        // 获取可修改图像捕获用例的稳定引用
        if (imageCapture == null) return;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            String name = new SimpleDateFormat(FILENAME_FORMAT, Locale.CHINA).format(System.currentTimeMillis());
            ContentValues contentValues = new ContentValues();
            contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, name);
            contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg");
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
                contentValues.put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/CameraActivityImage");
            }

            // 创建包含文件+元数据的输出选项对象,拍摄后的照片保存信息
            // 可以指定所需的输出内容。我们希望将输出保存在 MediaStore 中，以便其他应用可以显示它，因此，请添加我们的 MediaStore 条目
            ImageCapture.OutputFileOptions outputOptions = new ImageCapture.OutputFileOptions.Builder(getContentResolver(), MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues).build();

            imageCapture.takePicture(ContextCompat.getMainExecutor(this), new ImageCapture.OnImageCapturedCallback() {
                @Override
                public void onCaptureSuccess(@NonNull ImageProxy image) {
                    super.onCaptureSuccess(image);
                    try {
                        showPhotoThumbnail(image.getImage());
                    } catch (Exception e) {
                        Log.e("zhang", "Exception : " + e);
                    }
                }

                @Override
                public void onError(@NonNull ImageCaptureException exception) {
                    super.onError(exception);
                }
            });

            // 设置图像监听器,将图片保存到本地，图片保存后的回调
            imageCapture.takePicture(outputOptions, ContextCompat.getMainExecutor(this), new ImageCapture.OnImageSavedCallback() {
                @Override
                public void onImageSaved(@NonNull ImageCapture.OutputFileResults outputFileResults) {
                    Toast.makeText(CameraActivity.this, "照片保存成功 ：" + outputFileResults.getSavedUri().getPath(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onError(@NonNull ImageCaptureException exception) {
                    Log.e("zhang", "照片保存失败 ： " + exception);
                    Toast.makeText(CameraActivity.this, "照片保存失败", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void closeCamera() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == WRITE_EXTERNAL_STORAGE) {
            if (grantResults.length != 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startCamera();
                Toast.makeText(this, "文件读写权限已开启", Toast.LENGTH_SHORT).show();
            } else {
                closeCamera();
            }
        }
    }
}