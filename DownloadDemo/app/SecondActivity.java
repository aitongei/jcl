//package com.example.downloaddemo;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.app.ActivityCompat;
//import androidx.core.content.ContextCompat;
//
//import android.Manifest;
//import android.content.ComponentName;
//import android.content.Intent;
//import android.content.ServiceConnection;
//import android.content.pm.PackageManager;
//import android.os.Bundle;
//import android.os.Environment;
//import android.os.IBinder;
//import android.util.Log;
//import android.view.View;
//
//public class SecondActivity extends AppCompatActivity {
//
//    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1;
//    private DownloadService.DownloadBinder mbinder;
//    private CustomProgressBar mProgressBar;
//    private int beginProgress;
//    private DownloadStatus status;
//    private DownloadProgressListener downloadProgressListener=new DownloadProgressListener() {
//        @Override
//        public void onProgressUpdate(int progress,DownloadStatus status) {
//            mProgressBar.setProgress(progress);
//
//        }
//    };
//    private ServiceConnection mServiceConnection = new ServiceConnection() {
//        @Override
//        public void onServiceConnected(ComponentName name, IBinder service) {
//            mbinder = (DownloadService.DownloadBinder) service;
//            mbinder.addListener(downloadProgressListener);
//            beginProgress = mbinder.getmProgress();
//            status = mbinder.getStatus();
//        }
//
//        @Override
//        public void onServiceDisconnected(ComponentName name) {
//            new RuntimeException("onServiceDisconnected连接失败");
//        }
//    };
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_second);
//        Intent bindIntent = new Intent(this, DownloadService.class);
//        bindService(bindIntent, mServiceConnection, BIND_AUTO_CREATE);
//        mProgressBar = findViewById(R.id.progress_bar);
////        mProgressBar.setProgress(beginProgress);
////        mProgressBar.setText(begintext);
//        mProgressBar.setOnClickListener(new View.OnClickListener() {
//                if(status == null) {
//                mProgressBar.setText("暂停");
//                check();    //开始下载
//            }
//            @Override
//            public void onClick(View v) {
//                switch (status) {
//                    case PAUSED:
//                        mProgressBar.setText("暂停"); // 实际下载中，显示为”暂停“
//                        mbinder.reStartDownload();
//                        break;
//                    case DOWNLOADING:
//                        mProgressBar.setText("继续下载"); //实际暂停，显示为”继续下载“
//                        mbinder.pauseDownload();
//                    default:
//                        mProgressBar.setText("暂停");
//                        check();    //开始下载
//                        break;
//                }
//            }
//        });
//    }
//
//    public void check() {
//        if (ContextCompat.checkSelfPermission(SecondActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(SecondActivity.this,
//                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
//                    MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
//        } else {
//            startDownload();
//        }
//    }
//
//    private void startDownload() {
//        String downloadUrl = "https://dl1.msshuo.cn/market/apk/X7Market-4.107.999_1036.4537-prod-official-release.apk";
//        String path = Environment.getExternalStorageDirectory() + "/小7.apk";
//        Intent downloadIntent = new Intent(this, DownloadService.class);
//        downloadIntent.putExtra("downloadUrl", downloadUrl);
//        downloadIntent.putExtra("path", path);
//        startService(downloadIntent);
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode,
//                                           String[] permissions,
//                                           int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        switch (requestCode) {
//            case MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE: {
//                // 如果请求被取消，那么结果数组将为空
//                if (grantResults.length > 0
//                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    startDownload();
//                } else {
//                    // 权限被拒绝
//                    Log.d("Permissions", "WRITE_EXTERNAL_STORAGE permission denied");
//                }
//                return;
//            }
//            default:
//                break;
//        }
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        mbinder.removeListener(downloadProgressListener);
//        unbindService(mServiceConnection);
//    }
//}
