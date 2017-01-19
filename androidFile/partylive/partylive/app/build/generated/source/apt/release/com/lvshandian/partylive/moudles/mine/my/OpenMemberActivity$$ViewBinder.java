// Generated code from Butter Knife. Do not modify!
package com.lvshandian.partylive.moudles.mine.my;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class OpenMemberActivity$$ViewBinder<T extends com.lvshandian.partylive.moudles.mine.my.OpenMemberActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624146, "field 'btnBuy'");
    target.btnBuy = finder.castView(view, 2131624146, "field 'btnBuy'");
  }

  @Override public void unbind(T target) {
    target.btnBuy = null;
  }
}
