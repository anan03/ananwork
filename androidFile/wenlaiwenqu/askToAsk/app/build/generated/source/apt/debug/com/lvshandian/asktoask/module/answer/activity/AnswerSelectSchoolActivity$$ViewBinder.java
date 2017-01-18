// Generated code from Butter Knife. Do not modify!
package com.lvshandian.asktoask.module.answer.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class AnswerSelectSchoolActivity$$ViewBinder<T extends com.lvshandian.asktoask.module.answer.activity.AnswerSelectSchoolActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558536, "field 'ivBack'");
    target.ivBack = finder.castView(view, 2131558536, "field 'ivBack'");
    view = finder.findRequiredView(source, 2131558585, "field 'countryLvcountry'");
    target.countryLvcountry = finder.castView(view, 2131558585, "field 'countryLvcountry'");
    view = finder.findRequiredView(source, 2131558587, "field 'sidrbar'");
    target.sidrbar = finder.castView(view, 2131558587, "field 'sidrbar'");
  }

  @Override public void unbind(T target) {
    target.ivBack = null;
    target.countryLvcountry = null;
    target.sidrbar = null;
  }
}
