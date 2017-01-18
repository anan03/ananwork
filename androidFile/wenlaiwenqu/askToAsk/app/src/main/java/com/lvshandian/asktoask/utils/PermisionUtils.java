package com.lvshandian.asktoask.utils;

import android.Manifest;
import android.content.Context;
import android.view.View;


import com.lvshandian.asktoask.acp.Acp;
import com.lvshandian.asktoask.acp.AcpListener;
import com.lvshandian.asktoask.acp.AcpOptions;

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
     * @param view
     * @param lintener
     */
    public void checkCameraPermission(final View view, final OnPermissionGrantedLintener lintener) {
        Acp.getInstance(view.getContext()).request(new AcpOptions.Builder().setPermissions(Manifest.permission.CAMERA).build(), new AcpListener() {
            @Override
            public void onGranted() {
                if (lintener != null) {
                    lintener.permissionGranted();
                }
            }

            @Override
            public void onDenied(List<String> permissions) {
                ToastUtils.showSnackBar(view, "请打开摄像权限后使用");
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
     * @param view
     * @param lintener
     */
    public void checkCallPhonePermission(final View view, final OnPermissionGrantedLintener lintener) {
        Acp.getInstance(view.getContext()).request(new AcpOptions.Builder().setPermissions(Manifest.permission.CALL_PHONE).build(), new AcpListener() {
            @Override
            public void onGranted() {
                if (lintener != null) {

                    lintener.permissionGranted();
                }
            }

            @Override
            public void onDenied(List<String> permissions) {
                ToastUtils.showSnackBar(view, "请打开拨打电话权限后使用");
            }
        });
    }

    /**
     * 写入sdk权限
     *
     * @param view
     * @param lintener
     */
    public void checkWriteStoragePermission(final View view, final OnPermissionGrantedLintener lintener) {
        Acp.getInstance(view.getContext()).request(new AcpOptions.Builder().setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE).build(), new AcpListener() {
            @Override
            public void onGranted() {
                if (lintener != null) {
                    lintener.permissionGranted();
                }
            }

            @Override
            public void onDenied(List<String> permissions) {
                ToastUtils.showSnackBar(view, "请打开读写文件权限后使用");
            }
        });
    }

    /**
     * 读取sd限
     *
     * @param view
     * @param lintener
     */
    public void checkReadStoragePermission(final View view, final OnPermissionGrantedLintener lintener) {
        Acp.getInstance(view.getContext()).request(new AcpOptions.Builder().setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE).build(), new AcpListener() {
            @Override
            public void onGranted() {
                if (lintener != null) {
                    lintener.permissionGranted();
                }
            }

            @Override
            public void onDenied(List<String> permissions) {
                ToastUtils.showSnackBar(view, "请打开读写文件权限后使用");
            }
        });
    }

    /**
     * 读取sd限
     *
     * @param view
     * @param lintener
     */
    public void checkReadStoagePermission(final View view, final OnPermissionGrantedLintener lintener) {
        Acp.getInstance(view.getContext()).request(new AcpOptions.Builder().setPermissions(Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS).build(), new AcpListener() {
            @Override
            public void onGranted() {
                if (lintener != null) {
                    lintener.permissionGranted();
                }
            }

            @Override
            public void onDenied(List<String> permissions) {
                ToastUtils.showSnackBar(view, "请打开读写文件权限后使用");
            }
        });
    }

    /**
     * 检查定位权限
     *
     * @param view
     * @param listener
     */
    public void checkLocationPermission(final View view, final OnPermissionGrantedLintener listener) {
        Acp.getInstance(view.getContext()).request(new AcpOptions.Builder().setPermissions(Manifest.permission.ACCESS_COARSE_LOCATION).build(), new AcpListener() {
            @Override
            public void onGranted() {
                if (listener != null) {

                    listener.permissionGranted();
                }
            }

            @Override
            public void onDenied(List<String> permissions) {
                ToastUtils.showSnackBar(view, "请打开定位权限后使用");
            }
        });
    }
}
