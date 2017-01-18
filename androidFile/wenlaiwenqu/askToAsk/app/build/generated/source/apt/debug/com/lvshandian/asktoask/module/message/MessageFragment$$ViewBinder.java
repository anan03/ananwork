// Generated code from Butter Knife. Do not modify!
package com.lvshandian.asktoask.module.message;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class MessageFragment$$ViewBinder<T extends com.lvshandian.asktoask.module.message.MessageFragment> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131559031, "field 'tvLeavemessage'");
    target.tvLeavemessage = finder.castView(view, 2131559031, "field 'tvLeavemessage'");
    view = finder.findRequiredView(source, 2131559032, "field 'tvLeaveline'");
    target.tvLeaveline = finder.castView(view, 2131559032, "field 'tvLeaveline'");
    view = finder.findRequiredView(source, 2131559033, "field 'tvInstationmessage'");
    target.tvInstationmessage = finder.castView(view, 2131559033, "field 'tvInstationmessage'");
    view = finder.findRequiredView(source, 2131559034, "field 'tvInstationline'");
    target.tvInstationline = finder.castView(view, 2131559034, "field 'tvInstationline'");
    view = finder.findRequiredView(source, 2131558750, "field 'viewpager'");
    target.viewpager = finder.castView(view, 2131558750, "field 'viewpager'");
  }

  @Override public void unbind(T target) {
    target.tvLeavemessage = null;
    target.tvLeaveline = null;
    target.tvInstationmessage = null;
    target.tvInstationline = null;
    target.viewpager = null;
  }
}
