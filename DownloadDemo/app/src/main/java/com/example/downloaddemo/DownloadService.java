package com.example.downloaddemo;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloader;

import java.util.ArrayList;
import java.util.List;

public class DownloadService extends Service {
    public String url;
    public String path;

    private BaseDownloadTask task;
    private int mProgress;
    private String text="下载";
    private DownloadBinder mBinder = new DownloadBinder();
    private List<DownloadProgressListener> listeners = new ArrayList<>();


    class DownloadBinder extends Binder {
        public void addListener(DownloadProgressListener listener) {
            if (!listeners.contains(listener)) {
                listeners.add(listener);
            }
        }

        public void removeListener(DownloadProgressListener listener) {
            listeners.remove(listener);
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

        public int getProgress() {
            return mProgress;
        }

        //当前状态
        public String getText(){
            return text;
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
                            mProgress = 0;
                            text = "下载中";
                            notifyAllListeners();
                        }

                        @Override
                        public void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                            mProgress = (int) ((soFarBytes * 1.0 / totalBytes) * 100);
                            text = "下载中";
                            notifyAllListeners();
                            Log.d("progress", String.valueOf(mProgress));
                        }

                        @Override
                        public void completed(BaseDownloadTask task) {
                            Log.d("completed", ":completed ");
                            mProgress = 100;
                            text = "下载完成";
                            notifyAllListeners();
                            for (DownloadProgressListener listener:listeners) {
                                mBinder.removeListener(listener);
                            }
                        }

                        @Override
                        public void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                            Log.d("paused", ":paused ");
                            text = "暂停";
                            notifyAllListeners();
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

    private void notifyAllListeners() {
        for (DownloadProgressListener listener : listeners) {
            listener.onProgressUpdate(mProgress);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
}
