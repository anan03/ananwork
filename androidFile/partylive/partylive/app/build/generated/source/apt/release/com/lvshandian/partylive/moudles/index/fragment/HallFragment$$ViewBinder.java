// Generated code from Butter Knife. Do not modify!
package com.lvshandian.partylive.moudles.index.fragment;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class HallFragment$$ViewBinder<T extends com.lvshandian.partylive.moudles.index.fragment.HallFragment> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624412, "field 'lvLiveRoom'");
    target.lvLiveRoom = finder.castView(view, 2131624412, "field 'lvLiveRoom'");
    view = finder.findRequiredView(source, 2131624411, "field 'refreshLayout'");
    target.refreshLayout = finder.castView(view, 2131624411, "field 'refreshLayout'");
  }

  @Override public void unbind(T target) {
    target.lvLiveRoom = null;
    target.refreshLayout = null;
  }
}
