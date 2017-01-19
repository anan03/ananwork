// Generated code from Butter Knife. Do not modify!
package com.lvshandian.menshen.login;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class registerActivity$$ViewBinder<T extends com.lvshandian.menshen.login.registerActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558633, "field 'etName'");
    target.etName = finder.castView(view, 2131558633, "field 'etName'");
    view = finder.findRequiredView(source, 2131558637, "field 'etCode'");
    target.etCode = finder.castView(view, 2131558637, "field 'etCode'");
    view = finder.findRequiredView(source, 2131558638, "field 'tvCode'");
    target.tvCode = finder.castView(view, 2131558638, "field 'tvCode'");
    view = finder.findRequiredView(source, 2131558639, "field 'imageView'");
    target.imageView = finder.castView(view, 2131558639, "field 'imageView'");
    view = finder.findRequiredView(source, 2131558640, "field 'etPassword'");
    target.etPassword = finder.castView(view, 2131558640, "field 'etPassword'");
    view = finder.findRequiredView(source, 2131558641, "field 'btRegister'");
    target.btRegister = finder.castView(view, 2131558641, "field 'btRegister'");
    view = finder.findRequiredView(source, 2131558542, "field 'ivBack'");
    target.ivBack = finder.castView(view, 2131558542, "field 'ivBack'");
    view = finder.findRequiredView(source, 2131558541, "field 'llParentView'");
    target.llParentView = finder.castView(view, 2131558541, "field 'llParentView'");
    view = finder.findRequiredView(source, 2131558543, "field 'tvTitle'");
    target.tvTitle = finder.castView(view, 2131558543, "field 'tvTitle'");
  }

  @Override public void unbind(T target) {
    target.etName = null;
    target.etCode = null;
    target.tvCode = null;
    target.imageView = null;
    target.etPassword = null;
    target.btRegister = null;
    target.ivBack = null;
    target.llParentView = null;
    target.tvTitle = null;
  }
}
