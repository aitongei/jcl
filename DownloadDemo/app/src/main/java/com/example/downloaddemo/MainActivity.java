package com.example.downloaddemo;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {
    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1;
    private DownloadService.DownloadBinder mbinder;
    private CustomProgressBar customProgressBar;
    private Intent downloadIntent;
    private DownloadProgressListener downloadProgressListener=new DownloadProgressListener() {
        @Override
        public void onProgressUpdate(int progress) {
            customProgressBar.setProgress(progress);
        }
    };

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mbinder = (DownloadService.DownloadBinder) service;
            mbinder.addListener(downloadProgressListener);
            customProgressBar.setProgress(mbinder.getProgress());
            customProgressBar.setText(mbinder.getText());
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d("onServiceDisconnected","onServiceDisconnected");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent bindIntent = new Intent(this, DownloadService.class);
        bindService(bindIntent, mServiceConnection, BIND_AUTO_CREATE);
        customProgressBar = findViewById(R.id.progress_bar);
        customProgressBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomProgressBar view = (CustomProgressBar) v;
                switch (view.getText()) {
                    case "下载":
                        customProgressBar.setText("暂停");
                        check();
                        break;
                    case "暂停":
                        customProgressBar.setText("继续下载");
                        mbinder.pauseDownload();
                        break;
                    case "继续下载":
                        customProgressBar.setText("暂停");
                        mbinder.reStartDownload();
                    default:
                        break;
                }
            }
        });

        View button = findViewById(R.id.go_to_2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(intent);
            }
        });
    }


    public void check() {
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
        } else {
            startDownload();
        }
    }

    private void startDownload() {
        String downloadUrl = "https://dl1.msshuo.cn/market/apk/X7Market-4.107.999_1036.4537-prod-official-release.apk";
        String path = Environment.getExternalStorageDirectory() + "/a/小7.apk";
        downloadIntent = new Intent(this, DownloadService.class);
        downloadIntent.putExtra("downloadUrl", downloadUrl);
        downloadIntent.putExtra("path", path);
        startService(downloadIntent);
    }

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


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mbinder.removeListener(downloadProgressListener);
        unbindService(mServiceConnection);
    }
}