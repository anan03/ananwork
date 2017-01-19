// Generated code from Butter Knife. Do not modify!
package com.lvshandian.partylive.moudles.start;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class RegisterActivity$$ViewBinder<T extends com.lvshandian.partylive.moudles.start.RegisterActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624205, "field 'edRegisterPhone'");
    target.edRegisterPhone = finder.castView(view, 2131624205, "field 'edRegisterPhone'");
    view = finder.findRequiredView(source, 2131624206, "field 'edRegisterCode'");
    target.edRegisterCode = finder.castView(view, 2131624206, "field 'edRegisterCode'");
    view = finder.findRequiredView(source, 2131624207, "field 'tvSendCode'");
    target.tvSendCode = finder.castView(view, 2131624207, "field 'tvSendCode'");
    view = finder.findRequiredView(source, 2131624208, "field 'edRegisterPassword'");
    target.edRegisterPassword = finder.castView(view, 2131624208, "field 'edRegisterPassword'");
    view = finder.findRequiredView(source, 2131624209, "field 'edRegisterRecommendCode'");
    target.edRegisterRecommendCode = finder.castView(view, 2131624209, "field 'edRegisterRecommendCode'");
    view = finder.findRequiredView(source, 2131624175, "field 'tvForgetPassword'");
    target.tvForgetPassword = finder.castView(view, 2131624175, "field 'tvForgetPassword'");
    view = finder.findRequiredView(source, 2131624177, "field 'btnRegister'");
    target.btnRegister = finder.castView(view, 2131624177, "field 'btnRegister'");
  }

  @Override public void unbind(T target) {
    target.edRegisterPhone = null;
    target.edRegisterCode = null;
    target.tvSendCode = null;
    target.edRegisterPassword = null;
    target.edRegisterRecommendCode = null;
    target.tvForgetPassword = null;
    target.btnRegister = null;
  }
}
