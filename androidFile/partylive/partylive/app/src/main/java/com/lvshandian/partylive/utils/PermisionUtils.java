package com.lvshandian.partylive.utils;

import android.Manifest;
import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.lvshandian.partylive.lib.acp.Acp;
import com.lvshandian.partylive.lib.acp.AcpListener;
import com.lvshandian.partylive.lib.acp.AcpOptions;
import com.lvshandian.partylive.view.ToastUtils;

import java.util.List;

/**
 * Created by gjj on 2016/10/28.
 */
public class PermisionUtils {
    private static PermisionUtils utils;

    private PermisionUtils() {
    }

    public static PermisionUtils newInstance() {
        if (utils == null) {
            synchronized (PermisionUtils.class) {
                if (utils == null) {
                    utils = new PermisionUtils();
                }
            }
        }
        return utils;
    }

    /**
     * 多个权限检查
     *
     * @param context
     * @param permission
     * @param listener
     */
    public void checkPermisson(Context context, String[] permission, AcpListener listener) {
        Acp.getInstance(context).request(new AcpOptions.Builder().setPermissions(permission).build(), listener);
    }


    /**
     * 单个权限检查
     *
     * @param context
     * @param permission
     * @param listener
     */
    public void checkPermisson(Context context, String permission, AcpListener listener) {
        Acp.getInstance(context).request(new AcpOptions.Builder().setPermissions(permission).build(), listener);
    }


    /**
     * camera权限检查
     *
     * @param lintener
     */
    public void checkCameraPermission(Context context, final OnPermissionGrantedLintener lintener) {
        Acp.getInstance(context).request(new AcpOptions.Builder().setPermissions(Manifest.permission.CAMERA).build(), new AcpListener() {
            @Override
            public void onGranted() {
                if (lintener != null) {
                    lintener.permissionGranted();
                }
            }

            @Override
            public void onDenied(List<String> permissions) {
                ToastUtil.makeToast("摄像权限拒绝");
            }
        });
    }

    /**
     * 授权后回调
     */
    public interface OnPermissionGrantedLintener {
        void permissionGranted();
    }


    /**
     * CallPhone权限检查
     *
     * @param lintener
     */
    public void checkCallPhonePermission(Context context, final OnPermissionGrantedLintener lintener) {
        Acp.getInstance(context).request(new AcpOptions.Builder().setPermissions(Manifest.permission.CALL_PHONE).build(), new AcpListener() {
            @Override
            public void onGranted() {
                if (lintener != null) {

                    lintener.permissionGranted();
                }
            }

            @Override
            public void onDenied(List<String> permissions) {
                ToastUtil.makeToast("拨打电话权限拒绝");
            }
        });
    }

    /**
     * 写入sdk权限
     *
     * @param lintener
     */
    public void checkWriteStoragePermission(Context context, final OnPermissionGrantedLintener lintener) {
        Acp.getInstance(context).request(new AcpOptions.Builder().setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE).build(), new AcpListener() {
            @Override
            public void onGranted() {
                if (lintener != null) {
                    lintener.permissionGranted();
                }
            }

            @Override
            public void onDenied(List<String> permissions) {
                ToastUtil.makeToast("读写SDK权限拒绝");
            }
        });
    }


    /**
     * 检查定位权限
     *
     * @param listener
     */
    public void checkLocationPermission(Context context, final OnPermissionGrantedLintener listener) {
        Acp.getInstance(context).request(new AcpOptions.Builder().setPermissions(Manifest.permission.ACCESS_COARSE_LOCATION).build(), new AcpListener() {
            @Override
            public void onGranted() {
                if (listener != null) {
                    listener.permissionGranted();
                }
            }

            @Override
            public void onDenied(List<String> permissions) {
                ToastUtil.makeToast("定位权限拒绝");
            }
        });
    }
}
