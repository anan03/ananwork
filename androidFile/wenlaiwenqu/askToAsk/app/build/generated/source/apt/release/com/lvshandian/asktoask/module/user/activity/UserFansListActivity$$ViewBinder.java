// Generated code from Butter Knife. Do not modify!
package com.lvshandian.asktoask.module.user.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class UserFansListActivity$$ViewBinder<T extends com.lvshandian.asktoask.module.user.activity.UserFansListActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558528, "field 'ivBackApproveAddress'");
    target.ivBackApproveAddress = finder.castView(view, 2131558528, "field 'ivBackApproveAddress'");
    view = finder.findRequiredView(source, 2131558530, "field 'pullLvCollect'");
    target.pullLvCollect = finder.castView(view, 2131558530, "field 'pullLvCollect'");
  }

  @Override public void unbind(T target) {
    target.ivBackApproveAddress = null;
    target.pullLvCollect = null;
  }
}
