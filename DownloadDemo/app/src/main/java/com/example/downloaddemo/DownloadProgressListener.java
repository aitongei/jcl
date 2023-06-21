package com.example.downloaddemo;

/**
 * @author jcl
 */
public interface DownloadProgressListener {
    void onProgressUpdate(int progress);

    /**
     * 与FileDownloader的回调方法同时触发，告诉activity：FileDownloader触发了哪个回调（需要修改成什么状态）
     * 0表示paused（暂停下载）
     * 1表示pending（开始下载）
     */
//    void onStateUpdate(String  state);
}
