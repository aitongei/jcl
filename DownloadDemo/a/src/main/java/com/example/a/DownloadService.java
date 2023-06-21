package com.example.a;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloader;

import java.io.File;

public class DownloadService extends Service {
    private static final int ONGOING_NOTIFICATION_ID = 1;
    private static final String CHANNEL_ID = "DownloadChannel";
    private PendingIntent mPendingIntent;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String downloadUrl = intent.getStringExtra("downloadUrl");
        String path = intent.getStringExtra("path"); // 下载文件存放的路径

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "应用程序：MyApplication的下载任务", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        // 创建一个用于打开MainActivity的Intent
        Intent notificationIntent = new Intent(this, MainActivity.class);
        mPendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Download Service")
                .setContentText("Downloading file...")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentIntent(mPendingIntent)   // 设置通知点击事件
                .setTicker("Downloading")
                .build();

        startForeground(ONGOING_NOTIFICATION_ID, notification);
        startDownload(downloadUrl, path);
        return START_NOT_STICKY;
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void startDownload(String url, String path) {
        FileDownloader.setup(this);
        FileDownloader.getImpl().create(url)
                .setPath(path)
                .setListener(new FileDownloadListener() {
                    @Override
                    public void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        Log.d("pending", ":pending ");
                    }

                    @Override
                    public void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        int progress = (int) ((soFarBytes * 1.0 / totalBytes) * 100);
                        Log.d("progress", String.valueOf(progress));
                        // 更新Notification
                        NotificationCompat.Builder builder = new NotificationCompat.Builder(DownloadService.this, CHANNEL_ID)
                                .setContentTitle("Download Service")
                                .setContentText("Downloading file... (" + progress + "%)")
                                .setProgress(100, progress, false)
                                .setSmallIcon(R.drawable.ic_launcher_background)
                                .setContentIntent(mPendingIntent);
                        NotificationManagerCompat.from(DownloadService.this).notify(ONGOING_NOTIFICATION_ID, builder.build());
                        Log.d("progress", String.valueOf(progress));
                    }


                    @Override
                    public void completed(BaseDownloadTask task) {
                        stopForeground(true); // 下载完成后，结束前台服务
                        Log.d("completed", ":completed ");
                        File file = new File(path);
                    }

                    @Override
                    public void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        Log.d("paused", ":paused ");
                    }

                    @Override
                    public void error(BaseDownloadTask task, Throwable e) {
                        Log.d("error", ":error ");
                    }

                    @Override
                    public void warn(BaseDownloadTask task) {
                        Log.d("warn", ":warn ");
                    }
                }).start();
    }
}
