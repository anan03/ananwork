// Generated code from Butter Knife. Do not modify!
package com.lvshandian.partylive.wangyiyunxin.live.fragment;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class LiveMessageFragment$$ViewBinder<T extends com.lvshandian.partylive.wangyiyunxin.live.fragment.LiveMessageFragment> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624518, "field 'liveUserName'");
    target.liveUserName = finder.castView(view, 2131624518, "field 'liveUserName'");
    view = finder.findRequiredView(source, 2131624519, "field 'messageFragmentX'");
    target.messageFragmentX = finder.castView(view, 2131624519, "field 'messageFragmentX'");
  }

  @Override public void unbind(T target) {
    target.liveUserName = null;
    target.messageFragmentX = null;
  }
}
