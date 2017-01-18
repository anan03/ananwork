// Generated code from Butter Knife. Do not modify!
package com.lvshandian.asktoask.view;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class HuDongSharePopupwindow$$ViewBinder<T extends com.lvshandian.asktoask.view.HuDongSharePopupwindow> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558936, "field 'qqShare'");
    target.qqShare = finder.castView(view, 2131558936, "field 'qqShare'");
    view = finder.findRequiredView(source, 2131558937, "field 'weiboShare'");
    target.weiboShare = finder.castView(view, 2131558937, "field 'weiboShare'");
    view = finder.findRequiredView(source, 2131558938, "field 'weixinShare'");
    target.weixinShare = finder.castView(view, 2131558938, "field 'weixinShare'");
    view = finder.findRequiredView(source, 2131558939, "field 'qqKongShare'");
    target.qqKongShare = finder.castView(view, 2131558939, "field 'qqKongShare'");
    view = finder.findRequiredView(source, 2131558940, "field 'weixinkongShare'");
    target.weixinkongShare = finder.castView(view, 2131558940, "field 'weixinkongShare'");
    view = finder.findRequiredView(source, 2131558889, "field 'tvCancle'");
    target.tvCancle = finder.castView(view, 2131558889, "field 'tvCancle'");
  }

  @Override public void unbind(T target) {
    target.qqShare = null;
    target.weiboShare = null;
    target.weixinShare = null;
    target.qqKongShare = null;
    target.weixinkongShare = null;
    target.tvCancle = null;
  }
}
