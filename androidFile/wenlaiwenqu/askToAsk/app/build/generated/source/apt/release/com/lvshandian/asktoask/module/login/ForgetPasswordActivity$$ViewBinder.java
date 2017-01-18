// Generated code from Butter Knife. Do not modify!
package com.lvshandian.asktoask.module.login;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class ForgetPasswordActivity$$ViewBinder<T extends com.lvshandian.asktoask.module.login.ForgetPasswordActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558724, "field 'tvTitlebarCentertext'");
    target.tvTitlebarCentertext = finder.castView(view, 2131558724, "field 'tvTitlebarCentertext'");
    view = finder.findRequiredView(source, 2131558725, "field 'lltitlebarzuojiantou'");
    target.lltitlebarzuojiantou = finder.castView(view, 2131558725, "field 'lltitlebarzuojiantou'");
    view = finder.findRequiredView(source, 2131558644, "field 'etForgetpasswordPhonenum'");
    target.etForgetpasswordPhonenum = finder.castView(view, 2131558644, "field 'etForgetpasswordPhonenum'");
    view = finder.findRequiredView(source, 2131558645, "field 'etForgetpasswordYanzhengma'");
    target.etForgetpasswordYanzhengma = finder.castView(view, 2131558645, "field 'etForgetpasswordYanzhengma'");
    view = finder.findRequiredView(source, 2131558646, "field 'btnForgetpasswordGetyanzhengma'");
    target.btnForgetpasswordGetyanzhengma = finder.castView(view, 2131558646, "field 'btnForgetpasswordGetyanzhengma'");
    view = finder.findRequiredView(source, 2131558647, "field 'etForgetpasswordPassword'");
    target.etForgetpasswordPassword = finder.castView(view, 2131558647, "field 'etForgetpasswordPassword'");
    view = finder.findRequiredView(source, 2131558648, "field 'btn_forgetpassword_go'");
    target.btn_forgetpassword_go = finder.castView(view, 2131558648, "field 'btn_forgetpassword_go'");
  }

  @Override public void unbind(T target) {
    target.tvTitlebarCentertext = null;
    target.lltitlebarzuojiantou = null;
    target.etForgetpasswordPhonenum = null;
    target.etForgetpasswordYanzhengma = null;
    target.btnForgetpasswordGetyanzhengma = null;
    target.etForgetpasswordPassword = null;
    target.btn_forgetpassword_go = null;
  }
}
