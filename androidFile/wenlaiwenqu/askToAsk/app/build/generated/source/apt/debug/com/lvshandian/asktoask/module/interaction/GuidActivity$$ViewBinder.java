// Generated code from Butter Knife. Do not modify!
package com.lvshandian.asktoask.module.interaction;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class GuidActivity$$ViewBinder<T extends com.lvshandian.asktoask.module.interaction.GuidActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558649, "field 'contentPager'");
    target.contentPager = finder.castView(view, 2131558649, "field 'contentPager'");
  }

  @Override public void unbind(T target) {
    target.contentPager = null;
  }
}
