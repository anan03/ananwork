// Generated code from Butter Knife. Do not modify!
package com.lvshandian.asktoask.module.answer.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class AnswerLeaveWordsActivity$$ViewBinder<T extends com.lvshandian.asktoask.module.answer.activity.AnswerLeaveWordsActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558635, "field 'ivHudongDetailBack'");
    target.ivHudongDetailBack = finder.castView(view, 2131558635, "field 'ivHudongDetailBack'");
    view = finder.findRequiredView(source, 2131558862, "field 'tvAnswerNameLeave'");
    target.tvAnswerNameLeave = finder.castView(view, 2131558862, "field 'tvAnswerNameLeave'");
    view = finder.findRequiredView(source, 2131558863, "field 'tvConfirm'");
    target.tvConfirm = finder.castView(view, 2131558863, "field 'tvConfirm'");
  }

  @Override public void unbind(T target) {
    target.ivHudongDetailBack = null;
    target.tvAnswerNameLeave = null;
    target.tvConfirm = null;
  }
}
