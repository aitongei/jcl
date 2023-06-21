package com.example.a;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import com.liulishuo.filedownloader.FileDownloader;


import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    // 自定义的权限请求代码
    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 检查权限
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
        } else {
            startDownload();
        }
    }

    private void startDownload() {
        String downloadUrl = "https://dl1.msshuo.cn/market/apk/X7Market-4.107.999_1036.4537-prod-official-release.apk";
        String path = Environment.getExternalStorageDirectory()  + "/小7.apk";
        Log.d("path:", path);
        Intent intent = new Intent(this, DownloadService.class);
        intent.putExtra("downloadUrl", downloadUrl);
        intent.putExtra("path", path);
        startService(intent);
    }
// /storage/emulated/0/小7.apk
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions,
                                           int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE: {
                // 如果请求被取消，那么结果数组将为空
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startDownload();
                } else {
                    // 权限被拒绝
                    Log.d("Permissions", "WRITE_EXTERNAL_STORAGE permission denied");
                }
                return;
            }
            default:
                break;

        }
    }
}
