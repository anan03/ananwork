// Generated code from Butter Knife. Do not modify!
package com.lvshandian.menshen.analysesms;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class AnayseSmsDetails$$ViewBinder<T extends com.lvshandian.menshen.analysesms.AnayseSmsDetails> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558591, "field 'data'");
    target.data = finder.castView(view, 2131558591, "field 'data'");
    view = finder.findRequiredView(source, 2131558592, "field 'tvContext'");
    target.tvContext = finder.castView(view, 2131558592, "field 'tvContext'");
    view = finder.findRequiredView(source, 2131558543, "field 'tvTitle'");
    target.tvTitle = finder.castView(view, 2131558543, "field 'tvTitle'");
    view = finder.findRequiredView(source, 2131558542, "field 'ivBack'");
    target.ivBack = finder.castView(view, 2131558542, "field 'ivBack'");
  }

  @Override public void unbind(T target) {
    target.data = null;
    target.tvContext = null;
    target.tvTitle = null;
    target.ivBack = null;
  }
}
