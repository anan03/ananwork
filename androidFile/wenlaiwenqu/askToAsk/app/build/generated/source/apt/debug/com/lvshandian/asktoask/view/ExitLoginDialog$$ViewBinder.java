// Generated code from Butter Knife. Do not modify!
package com.lvshandian.asktoask.view;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class ExitLoginDialog$$ViewBinder<T extends com.lvshandian.asktoask.view.ExitLoginDialog> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558883, "field 'tvExitlogindialogQuxiao'");
    target.tvExitlogindialogQuxiao = finder.castView(view, 2131558883, "field 'tvExitlogindialogQuxiao'");
    view = finder.findRequiredView(source, 2131558884, "field 'tvExitlogindialogQueding'");
    target.tvExitlogindialogQueding = finder.castView(view, 2131558884, "field 'tvExitlogindialogQueding'");
  }

  @Override public void unbind(T target) {
    target.tvExitlogindialogQuxiao = null;
    target.tvExitlogindialogQueding = null;
  }
}
