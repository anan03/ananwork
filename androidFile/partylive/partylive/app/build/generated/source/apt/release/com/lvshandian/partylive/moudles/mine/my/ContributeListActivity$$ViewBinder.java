// Generated code from Butter Knife. Do not modify!
package com.lvshandian.partylive.moudles.mine.my;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class ContributeListActivity$$ViewBinder<T extends com.lvshandian.partylive.moudles.mine.my.ContributeListActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624147, "field 'lvList'");
    target.lvList = finder.castView(view, 2131624147, "field 'lvList'");
  }

  @Override public void unbind(T target) {
    target.lvList = null;
  }
}
