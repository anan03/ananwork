// Generated code from Butter Knife. Do not modify!
package com.lvshandian.partylive.moudles.start;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class LoginActivity$$ViewBinder<T extends com.lvshandian.partylive.moudles.start.LoginActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624173, "field 'edLoginPhone'");
    target.edLoginPhone = finder.castView(view, 2131624173, "field 'edLoginPhone'");
    view = finder.findRequiredView(source, 2131624174, "field 'edLoginPassword'");
    target.edLoginPassword = finder.castView(view, 2131624174, "field 'edLoginPassword'");
    view = finder.findRequiredView(source, 2131624175, "field 'tvForgetPassword'");
    target.tvForgetPassword = finder.castView(view, 2131624175, "field 'tvForgetPassword'");
    view = finder.findRequiredView(source, 2131624176, "field 'btnLogin'");
    target.btnLogin = finder.castView(view, 2131624176, "field 'btnLogin'");
    view = finder.findRequiredView(source, 2131624177, "field 'btnRegister'");
    target.btnRegister = finder.castView(view, 2131624177, "field 'btnRegister'");
    view = finder.findRequiredView(source, 2131624178, "field 'ivQqlogin'");
    target.ivQqlogin = finder.castView(view, 2131624178, "field 'ivQqlogin'");
    view = finder.findRequiredView(source, 2131624179, "field 'ivWxlogin'");
    target.ivWxlogin = finder.castView(view, 2131624179, "field 'ivWxlogin'");
  }

  @Override public void unbind(T target) {
    target.edLoginPhone = null;
    target.edLoginPassword = null;
    target.tvForgetPassword = null;
    target.btnLogin = null;
    target.btnRegister = null;
    target.ivQqlogin = null;
    target.ivWxlogin = null;
  }
}
