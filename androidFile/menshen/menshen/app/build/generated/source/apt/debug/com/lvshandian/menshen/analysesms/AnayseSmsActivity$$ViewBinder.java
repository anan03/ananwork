// Generated code from Butter Knife. Do not modify!
package com.lvshandian.menshen.analysesms;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class AnayseSmsActivity$$ViewBinder<T extends com.lvshandian.menshen.analysesms.AnayseSmsActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558542, "field 'ivBack'");
    target.ivBack = finder.castView(view, 2131558542, "field 'ivBack'");
    view = finder.findRequiredView(source, 2131558582, "field 'rlGuanjianzi'");
    target.rlGuanjianzi = finder.castView(view, 2131558582, "field 'rlGuanjianzi'");
    view = finder.findRequiredView(source, 2131558544, "field 'rlDelete'");
    target.rlDelete = finder.castView(view, 2131558544, "field 'rlDelete'");
    view = finder.findRequiredView(source, 2131558540, "field 'view'");
    target.view = view;
    view = finder.findRequiredView(source, 2131558587, "field 'rlSc'");
    target.rlSc = finder.castView(view, 2131558587, "field 'rlSc'");
  }

  @Override public void unbind(T target) {
    target.ivBack = null;
    target.rlGuanjianzi = null;
    target.rlDelete = null;
    target.view = null;
    target.rlSc = null;
  }
}
