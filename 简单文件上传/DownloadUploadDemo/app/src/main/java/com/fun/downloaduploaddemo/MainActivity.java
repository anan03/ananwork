package com.fun.downloaduploaddemo;

import android.app.ProgressDialog;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private static final String BASE_URL = "http://120.24.45.234:8080/static/login/sever";
    private static final String BASE_PATH = Environment.getExternalStorageDirectory().getPath() + File.separator;
    private Button buttonDownloadFile;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonDownloadFile = (Button) findViewById(R.id.bt_downloadFile);
        buttonDownloadFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = BASE_URL + "/building.xls";
                String path = BASE_PATH + "building.xls";
                downloadFile(url, path);
            }
        });
    }

    private void downloadFile(final String url, String path) {
        progressDialog = new ProgressDialog(this);
        RequestParams requestParams = new RequestParams(url);
        requestParams.setSaveFilePath(path);
        x.http().get(requestParams, new Callback.ProgressCallback<File>() {
            @Override
            public void onWaiting() {
            }

            @Override
            public void onStarted() {
            }

            @Override
            public void onLoading(long total, long current, boolean isDownloading) {
                progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                progressDialog.setMessage("亲，努力下载中。。。");
                progressDialog.show();
                progressDialog.setMax((int) total);
                progressDialog.setProgress((int) current);
            }

            @Override
            public void onSuccess(File result) {
                Toast.makeText(MainActivity.this, "下载成功", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                ex.printStackTrace();
                Toast.makeText(MainActivity.this, "下载失败，请检查网络和SD卡", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(CancelledException cex) {
            }

            @Override
            public void onFinished() {
            }
        });
    }
}
