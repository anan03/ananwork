// Generated code from Butter Knife. Do not modify!
package com.lvshandian.asktoask.module.message;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class InstationMessageFragment$$ViewBinder<T extends com.lvshandian.asktoask.module.message.InstationMessageFragment> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558945, "field 'lvMessage'");
    target.lvMessage = finder.castView(view, 2131558945, "field 'lvMessage'");
    view = finder.findRequiredView(source, 2131558944, "field 'rlMessge'");
    target.rlMessge = finder.castView(view, 2131558944, "field 'rlMessge'");
    view = finder.findRequiredView(source, 2131558946, "field 'tvMessage'");
    target.tvMessage = finder.castView(view, 2131558946, "field 'tvMessage'");
  }

  @Override public void unbind(T target) {
    target.lvMessage = null;
    target.rlMessge = null;
    target.tvMessage = null;
  }
}
