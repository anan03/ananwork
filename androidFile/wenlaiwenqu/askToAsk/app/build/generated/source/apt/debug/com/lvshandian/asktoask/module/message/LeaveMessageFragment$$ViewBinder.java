// Generated code from Butter Knife. Do not modify!
package com.lvshandian.asktoask.module.message;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class LeaveMessageFragment$$ViewBinder<T extends com.lvshandian.asktoask.module.message.LeaveMessageFragment> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131559030, "field 'pullLvCollect'");
    target.pullLvCollect = finder.castView(view, 2131559030, "field 'pullLvCollect'");
  }

  @Override public void unbind(T target) {
    target.pullLvCollect = null;
  }
}
