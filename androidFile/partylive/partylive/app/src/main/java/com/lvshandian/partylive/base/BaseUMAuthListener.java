package com.lvshandian.partylive.base;

import android.os.Bundle;

import com.lvshandian.partylive.utils.ToastUtil;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.listener.SocializeListeners;
import com.umeng.socialize.exception.SocializeException;

import java.util.Map;

/**
 * Created by Administrator on 2016/12/1.
 */

public class BaseUMAuthListener implements SocializeListeners.UMAuthListener {

    @Override
    public void onStart(SHARE_MEDIA share_media) {

    }

    @Override
    public void onComplete(Bundle bundle, SHARE_MEDIA share_media) {

    }

    @Override
    public void onError(SocializeException e, SHARE_MEDIA share_media) {

    }

    @Override
    public void onCancel(SHARE_MEDIA share_media) {

    }
}
