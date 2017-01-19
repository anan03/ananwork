// Generated code from Butter Knife. Do not modify!
package com.lvshandian.partylive.moudles.group.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class FollowListForGroupActivity$$ViewBinder<T extends com.lvshandian.partylive.moudles.group.activity.FollowListForGroupActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624147, "field 'lvList'");
    target.lvList = finder.castView(view, 2131624147, "field 'lvList'");
    view = finder.findRequiredView(source, 2131624166, "field 'uikitLayout'");
    target.uikitLayout = finder.castView(view, 2131624166, "field 'uikitLayout'");
  }

  @Override public void unbind(T target) {
    target.lvList = null;
    target.uikitLayout = null;
  }
}
