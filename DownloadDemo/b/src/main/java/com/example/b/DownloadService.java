package com.example.b;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloader;

import java.io.File;

public class DownloadService extends Service {
    //    private static final int ONGOING_NOTIFICATION_ID = 1;
    public String url;
    public String path;
    private BaseDownloadTask task;
    private DownloadBinder mBinder = new DownloadBinder();
    private DownloadProgressListener listener;


    class DownloadBinder extends Binder {
        public void setListener(DownloadProgressListener listener) {
            DownloadService.this.listener = listener;
        }

        public void reStartDownload() {
            if (task != null) {
                task.reuse();
                task.start();
            }
        }

        public void pauseDownload() {
            if (task != null) {
                task.pause();
            }
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        url = intent.getStringExtra("downloadUrl");
        path = intent.getStringExtra("path");
        FileDownloader.setup(this);
        if (task == null) {
            task = FileDownloader.getImpl().create(url)
                    .setPath(path)
                    .setListener(new FileDownloadListener() {
                        @Override
                        public void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                            Log.d("pending", ":pending ");
                        }

                        @Override
                        public void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                            int progress = (int) ((soFarBytes * 1.0 / totalBytes) * 100);
                            listener.onProgressUpdate(progress);
                            Log.d("progress", String.valueOf(progress));
                        }

                        @Override
                        public void completed(BaseDownloadTask task) {
                            Log.d("completed", ":completed ");
                            listener.onProgressUpdate(100);
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
                    });
        }
        task.start();
        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
}
