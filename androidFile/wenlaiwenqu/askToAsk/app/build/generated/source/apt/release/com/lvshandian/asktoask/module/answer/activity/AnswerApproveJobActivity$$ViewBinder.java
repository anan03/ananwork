// Generated code from Butter Knife. Do not modify!
package com.lvshandian.asktoask.module.answer.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class AnswerApproveJobActivity$$ViewBinder<T extends com.lvshandian.asktoask.module.answer.activity.AnswerApproveJobActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558598, "field 'ivApproveJob'");
    target.ivApproveJob = finder.castView(view, 2131558598, "field 'ivApproveJob'");
    view = finder.findRequiredView(source, 2131558599, "field 'tvApproveJobSave'");
    target.tvApproveJobSave = finder.castView(view, 2131558599, "field 'tvApproveJobSave'");
  }

  @Override public void unbind(T target) {
    target.ivApproveJob = null;
    target.tvApproveJobSave = null;
  }
}
