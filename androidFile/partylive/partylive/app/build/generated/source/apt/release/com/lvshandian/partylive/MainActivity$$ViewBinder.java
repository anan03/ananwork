// Generated code from Butter Knife. Do not modify!
package com.lvshandian.partylive;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class MainActivity$$ViewBinder<T extends com.lvshandian.partylive.MainActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624181, "field 'realtabcontent'");
    target.realtabcontent = finder.castView(view, 2131624181, "field 'realtabcontent'");
    view = finder.findRequiredView(source, 16908306, "field 'tabhost'");
    target.tabhost = finder.castView(view, 16908306, "field 'tabhost'");
    view = finder.findRequiredView(source, 2131624180, "field 'activityMain'");
    target.activityMain = finder.castView(view, 2131624180, "field 'activityMain'");
  }

  @Override public void unbind(T target) {
    target.realtabcontent = null;
    target.tabhost = null;
    target.activityMain = null;
  }
}
