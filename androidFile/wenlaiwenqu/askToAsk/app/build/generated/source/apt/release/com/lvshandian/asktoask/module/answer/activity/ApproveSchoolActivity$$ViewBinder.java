// Generated code from Butter Knife. Do not modify!
package com.lvshandian.asktoask.module.answer.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class ApproveSchoolActivity$$ViewBinder<T extends com.lvshandian.asktoask.module.answer.activity.ApproveSchoolActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558605, "field 'tvSelectSchoolSave'");
    target.tvSelectSchoolSave = finder.castView(view, 2131558605, "field 'tvSelectSchoolSave'");
    view = finder.findRequiredView(source, 2131558604, "field 'back'");
    target.back = finder.castView(view, 2131558604, "field 'back'");
  }

  @Override public void unbind(T target) {
    target.tvSelectSchoolSave = null;
    target.back = null;
  }
}
