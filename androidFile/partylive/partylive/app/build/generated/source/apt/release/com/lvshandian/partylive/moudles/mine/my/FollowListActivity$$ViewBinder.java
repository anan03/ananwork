// Generated code from Butter Knife. Do not modify!
package com.lvshandian.partylive.moudles.mine.my;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class FollowListActivity$$ViewBinder<T extends com.lvshandian.partylive.moudles.mine.my.FollowListActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624147, "field 'lvList'");
    target.lvList = finder.castView(view, 2131624147, "field 'lvList'");
    view = finder.findRequiredView(source, 2131624165, "field 'mrlLayout'");
    target.mrlLayout = finder.castView(view, 2131624165, "field 'mrlLayout'");
  }

  @Override public void unbind(T target) {
    target.lvList = null;
    target.mrlLayout = null;
  }
}
