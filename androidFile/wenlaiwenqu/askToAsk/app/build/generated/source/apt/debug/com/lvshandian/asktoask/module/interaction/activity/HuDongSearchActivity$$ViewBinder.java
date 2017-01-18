// Generated code from Butter Knife. Do not modify!
package com.lvshandian.asktoask.module.interaction.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class HuDongSearchActivity$$ViewBinder<T extends com.lvshandian.asktoask.module.interaction.activity.HuDongSearchActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558536, "field 'ivBack'");
    target.ivBack = finder.castView(view, 2131558536, "field 'ivBack'");
    view = finder.findRequiredView(source, 2131558537, "field 'ivSearch'");
    target.ivSearch = finder.castView(view, 2131558537, "field 'ivSearch'");
    view = finder.findRequiredView(source, 2131558655, "field 'pullLvHudongSearch'");
    target.pullLvHudongSearch = finder.castView(view, 2131558655, "field 'pullLvHudongSearch'");
    view = finder.findRequiredView(source, 2131558538, "field 'etSearch'");
    target.etSearch = finder.castView(view, 2131558538, "field 'etSearch'");
  }

  @Override public void unbind(T target) {
    target.ivBack = null;
    target.ivSearch = null;
    target.pullLvHudongSearch = null;
    target.etSearch = null;
  }
}
