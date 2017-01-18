// Generated code from Butter Knife. Do not modify!
package com.lvshandian.asktoask.module.answer.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class AnserSearchActivity$$ViewBinder<T extends com.lvshandian.asktoask.module.answer.activity.AnserSearchActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558536, "field 'ivBack'");
    target.ivBack = finder.castView(view, 2131558536, "field 'ivBack'");
    view = finder.findRequiredView(source, 2131558538, "field 'etSearch'");
    target.etSearch = finder.castView(view, 2131558538, "field 'etSearch'");
    view = finder.findRequiredView(source, 2131558539, "field 'pullLvAnserSearch'");
    target.pullLvAnserSearch = finder.castView(view, 2131558539, "field 'pullLvAnserSearch'");
  }

  @Override public void unbind(T target) {
    target.ivBack = null;
    target.etSearch = null;
    target.pullLvAnserSearch = null;
  }
}
