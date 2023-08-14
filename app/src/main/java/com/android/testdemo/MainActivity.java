package com.android.testdemo;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;

import java.io.File;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {

    @SuppressLint({"ResourceType", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MainActivity mainActivity = this;
        Button recycle_btn = findViewById(R.id.recycleyView);

        Button login = findViewById(R.id.login);
        Button cancel = findViewById(R.id.cancel);
        cancel.setOnClickListener(view -> {
            deleteFileFromDocument();
        });

        recycle_btn.setOnClickListener(view -> {
            Intent intent = new Intent(this, RecycleViewDemo.class);
            startActivity(intent);
        });

        login.setOnClickListener(view -> {
            deleteFileFromDocument();
            addFileToDocument();
        });

    }

    private void deleteFileFromDocument() {

        String fileName = "zhangzhou.txt";
        String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + Environment.DIRECTORY_DOCUMENTS +
                File.separator + "zhangzhou" + File.separator + fileName;

        // 查询是否存在指定文件
        Cursor cursor = getContentResolver().query(MediaStore.Files.getContentUri("external"),
                new String[]{MediaStore.MediaColumns.DATA},
                MediaStore.MediaColumns.DATA + "=?",
                new String[]{filePath},
                null);

        if (cursor != null && cursor.moveToFirst()) {
            // 文件存在，删除文件
            Log.d("zhang", "moveToFirst");
            getContentResolver().delete(MediaStore.Files.getContentUri("external"),
                    MediaStore.MediaColumns.DATA + "=?",
                    new String[]{filePath});
        }
        if (cursor != null) {
            cursor.close();
        }

        // 访问所有txt文件
//        Uri queryUri = MediaStore.Files.getContentUri(MediaStore.VOLUME_EXTERNAL);
//        Log.d("zhang", "queryUri : " + queryUri);
//        String[] projection = {MediaStore.Files.FileColumns._ID, MediaStore.Files.FileColumns.DISPLAY_NAME, MediaStore.MediaColumns.DATA};
//        String selection = MediaStore.Files.FileColumns.MIME_TYPE + "=?";
//        String[] selectionArgs = new String[]{MimeTypeMap.getSingleton().getMimeTypeFromExtension("jpg")};
//
//        Cursor cursor = getContentResolver().query(queryUri, projection, selection, selectionArgs, null);
//        if (cursor != null && cursor.moveToFirst()) {
//            do {
//                @SuppressLint("Range") long fileId = cursor.getLong(cursor.getColumnIndex(MediaStore.Files.FileColumns._ID));
//                @SuppressLint("Range") String queryName = cursor.getString(cursor.getColumnIndex(MediaStore.Files.FileColumns.DISPLAY_NAME));
//                @SuppressLint("Range") String queryPath = cursor.getString(cursor.getColumnIndex(MediaStore.Files.FileColumns.DATA));
//
//                // 在此处可以处理每个文件的信息
//                Log.d("zhang", "queryName : " + queryName);
//                Log.d("zhang", "queryPath : " + queryPath);
//            } while (cursor.moveToNext());
//        }
//        if (cursor != null) {
//            cursor.close();
//        }
    }

    private void addFileToDocument() {
        // 写入文件
        String fileName = "zhangzhou.txt";
        String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + Environment.DIRECTORY_DOCUMENTS +
                File.separator + "zhang" + File.separator + fileName;
        Log.d("zhang", "filePath : " + filePath);
        ContentValues values_document = new ContentValues();
        values_document.put(MediaStore.Files.FileColumns.DISPLAY_NAME, fileName);
        values_document.put(MediaStore.Files.FileColumns.MIME_TYPE, "text/plain");
        values_document.put(MediaStore.Files.FileColumns.RELATIVE_PATH, Environment.DIRECTORY_DOCUMENTS + File.separator + "zhang");

        try {
            // 插入文件数据库并获取到文件的Uri
            Uri uri_document = getContentResolver().insert(MediaStore.Files.getContentUri(MediaStore.VOLUME_EXTERNAL), values_document);
            OutputStream external_outputStream = getContentResolver().openOutputStream(uri_document);
            if (external_outputStream != null) {
                external_outputStream.write("Hello, world!".getBytes());
                external_outputStream.close();
            }
        } catch (Exception ignored) {
            Log.e("zhang", "error : " + ignored);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

}