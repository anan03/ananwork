// Generated code from Butter Knife. Do not modify!
package com.lvshandian.menshen.login;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class LoginActivity$$ViewBinder<T extends com.lvshandian.menshen.login.LoginActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558633, "field 'etName'");
    target.etName = finder.castView(view, 2131558633, "field 'etName'");
    view = finder.findRequiredView(source, 2131558639, "field 'imageView'");
    target.imageView = finder.castView(view, 2131558639, "field 'imageView'");
    view = finder.findRequiredView(source, 2131558640, "field 'etPassword'");
    target.etPassword = finder.castView(view, 2131558640, "field 'etPassword'");
    view = finder.findRequiredView(source, 2131558642, "field 'tvPassword'");
    target.tvPassword = finder.castView(view, 2131558642, "field 'tvPassword'");
    view = finder.findRequiredView(source, 2131558643, "field 'btLogin'");
    target.btLogin = finder.castView(view, 2131558643, "field 'btLogin'");
    view = finder.findRequiredView(source, 2131558641, "field 'btRegister'");
    target.btRegister = finder.castView(view, 2131558641, "field 'btRegister'");
  }

  @Override public void unbind(T target) {
    target.etName = null;
    target.imageView = null;
    target.etPassword = null;
    target.tvPassword = null;
    target.btLogin = null;
    target.btRegister = null;
  }
}
