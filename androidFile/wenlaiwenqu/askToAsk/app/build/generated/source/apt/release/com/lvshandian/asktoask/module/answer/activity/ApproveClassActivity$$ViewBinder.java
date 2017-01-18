// Generated code from Butter Knife. Do not modify!
package com.lvshandian.asktoask.module.answer.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class ApproveClassActivity$$ViewBinder<T extends com.lvshandian.asktoask.module.answer.activity.ApproveClassActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558590, "field 'ivApproveClassBack'");
    target.ivApproveClassBack = finder.castView(view, 2131558590, "field 'ivApproveClassBack'");
    view = finder.findRequiredView(source, 2131558591, "field 'tvApproveClassSave'");
    target.tvApproveClassSave = finder.castView(view, 2131558591, "field 'tvApproveClassSave'");
  }

  @Override public void unbind(T target) {
    target.ivApproveClassBack = null;
    target.tvApproveClassSave = null;
  }
}
