package com.example.ynaito.androidsamples;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.CookieManager;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    private String TAG = MainActivity.class.getSimpleName();

    private DownloadManager mDownloadManager;
    private long downloadId;

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        private String TAG = BroadcastReceiver.class.getSimpleName();
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG,"onReceive");
            String action = intent.getAction();
            if (action == null) {
                return;
            }
            if (action.equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)) {
                Log.d(TAG, "DOWNLOAD COMPLETE");

                DownloadManager.Query query = new DownloadManager.Query();
                query.setFilterById(downloadId);
                Cursor cursor = mDownloadManager.query(query);

                if (cursor == null || !cursor.moveToFirst()) {
                    Log.e(TAG, "Unable to find record: downloadId=" + downloadId + " setStatus DOWNLOAD_STATUS_FAILED");
                    return;
                }

                int status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
                String scrUrl = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_URI));
                String localPath = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
                String statusStr = "";
                switch (status) {
                    case DownloadManager.STATUS_FAILED:
                        statusStr = "STATUS_FAILED";
                        break;
                    case DownloadManager.STATUS_PAUSED:
                        statusStr = "STATUS_PAUSED";
                        break;
                    case DownloadManager.STATUS_PENDING:
                        statusStr = "STATUS_PENDING";
                        break;
                    case DownloadManager.STATUS_RUNNING:
                        statusStr = "STATUS_RUNNING";
                        break;
                    case DownloadManager.STATUS_SUCCESSFUL:
                        statusStr = "STATUS_SUCCESSFUL";
                        break;
                }
                Log.d(TAG, "status=" + statusStr + " url=" + scrUrl + " localPath=" + localPath);

            } else {
                Log.d(TAG, "DOWNLOAD NOT COMPLETE. ACTION " + action);
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDownloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);

        IntentFilter filter = new IntentFilter(
                DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        registerReceiver(broadcastReceiver, filter);

        String contentUrlStr = "http://contents/path";
        String filename = Uri.parse(contentUrlStr).getLastPathSegment();

        Uri srcUri = Uri.parse(contentUrlStr);
        File movieDir = getExternalFilesDir(android.os.Environment.DIRECTORY_MOVIES);
        Uri destPathUri = Uri.fromFile(movieDir);
        destPathUri = Uri.withAppendedPath(destPathUri, "/" + filename);

        downloadId = enqueueDownloadRequest(srcUri, destPathUri, null, "hoge");
    }


    private long enqueueDownloadRequest(Uri srcUri, Uri dstUri,
                                        String mimeType, String title) {
        Log.d(TAG, "[enqueueDownloadRequest]" + "title=" + title + ", srcUri=" + srcUri + ", dstUri=" + dstUri);

        // ダウンロードリクエストの登録
        DownloadManager.Request req = new DownloadManager.Request(srcUri);

        // 保存先の設定
        if (dstUri != null) {
            req.setDestinationUri(dstUri);
        }

        req.setTitle(title);
        req.setVisibleInDownloadsUi(false);

//        req.setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN);

        // NOTE: 機種依存を解決する為にMIMEタイプを指定
        // サーバが未知のMIMEタイプを返す場合にダウンロードが失敗する機種がある
        if (TextUtils.isEmpty(mimeType)) {
            req.setMimeType("application/octet-stream");

            Log.d(TAG, "no mime type");

        } else {
            req.setMimeType(mimeType);
        }

        String cookie = CookieManager.getInstance().getCookie(
                srcUri.toString());
        if (!TextUtils.isEmpty(cookie)) {
            Log.d(TAG, "Download cookie = " + cookie);
            req.addRequestHeader("Cookie", cookie);
        }

        return mDownloadManager.enqueue(req);
    }
}
