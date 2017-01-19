// Generated code from Butter Knife. Do not modify!
package com.lvshandian.partylive.moudles.index.live;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class StartLiveReadyActivity$$ViewBinder<T extends com.lvshandian.partylive.moudles.index.live.StartLiveReadyActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624278, "field 'head'");
    target.head = finder.castView(view, 2131624278, "field 'head'");
    view = finder.findRequiredView(source, 2131624279, "field 'btnStart'");
    target.btnStart = finder.castView(view, 2131624279, "field 'btnStart'");
  }

  @Override public void unbind(T target) {
    target.head = null;
    target.btnStart = null;
  }
}
