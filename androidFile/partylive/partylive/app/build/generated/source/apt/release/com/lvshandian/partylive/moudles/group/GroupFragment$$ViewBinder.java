// Generated code from Butter Knife. Do not modify!
package com.lvshandian.partylive.moudles.group;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class GroupFragment$$ViewBinder<T extends com.lvshandian.partylive.moudles.group.GroupFragment> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624132, "field 'titleBar'");
    target.titleBar = finder.castView(view, 2131624132, "field 'titleBar'");
    view = finder.findRequiredView(source, 2131624414, "field 'groupList'");
    target.groupList = finder.castView(view, 2131624414, "field 'groupList'");
    view = finder.findRequiredView(source, 2131624413, "field 'mainGroup'");
    target.mainGroup = finder.castView(view, 2131624413, "field 'mainGroup'");
  }

  @Override public void unbind(T target) {
    target.titleBar = null;
    target.groupList = null;
    target.mainGroup = null;
  }
}
