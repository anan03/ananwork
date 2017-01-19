// Generated code from Butter Knife. Do not modify!
package com.lvshandian.menshen.phone;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class PhoneHoldUpActivity$$ViewBinder<T extends com.lvshandian.menshen.phone.PhoneHoldUpActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558540, "field 'view'");
    target.view = view;
    view = finder.findRequiredView(source, 2131558542, "field 'ivBack'");
    target.ivBack = finder.castView(view, 2131558542, "field 'ivBack'");
    view = finder.findRequiredView(source, 2131558541, "field 'llParentView'");
    target.llParentView = finder.castView(view, 2131558541, "field 'llParentView'");
    view = finder.findRequiredView(source, 2131558543, "field 'tvTitle'");
    target.tvTitle = finder.castView(view, 2131558543, "field 'tvTitle'");
    view = finder.findRequiredView(source, 2131558657, "field 'rlAdd'");
    target.rlAdd = finder.castView(view, 2131558657, "field 'rlAdd'");
    view = finder.findRequiredView(source, 2131558581, "field 'rlRl'");
    target.rlRl = finder.castView(view, 2131558581, "field 'rlRl'");
    view = finder.findRequiredView(source, 2131558629, "field 'lv'");
    target.lv = finder.castView(view, 2131558629, "field 'lv'");
  }

  @Override public void unbind(T target) {
    target.view = null;
    target.ivBack = null;
    target.llParentView = null;
    target.tvTitle = null;
    target.rlAdd = null;
    target.rlRl = null;
    target.lv = null;
  }
}
