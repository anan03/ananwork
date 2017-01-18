// Generated code from Butter Knife. Do not modify!
package com.lvshandian.asktoask;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class MainActivity$$ViewBinder<T extends com.lvshandian.asktoask.MainActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558713, "field 'dRgGraoup'");
    target.dRgGraoup = finder.castView(view, 2131558713, "field 'dRgGraoup'");
    view = finder.findRequiredView(source, 2131558718, "field 'questionIv'");
    target.questionIv = finder.castView(view, 2131558718, "field 'questionIv'");
  }

  @Override public void unbind(T target) {
    target.dRgGraoup = null;
    target.questionIv = null;
  }
}
