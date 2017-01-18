// Generated code from Butter Knife. Do not modify!
package com.lvshandian.asktoask.module.login;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class LoginActivity$$ViewBinder<T extends com.lvshandian.asktoask.module.login.LoginActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558724, "field 'tvTitlebarCentertext'");
    target.tvTitlebarCentertext = finder.castView(view, 2131558724, "field 'tvTitlebarCentertext'");
    view = finder.findRequiredView(source, 2131558704, "field 'etLoginPhonenum'");
    target.etLoginPhonenum = finder.castView(view, 2131558704, "field 'etLoginPhonenum'");
    view = finder.findRequiredView(source, 2131558705, "field 'etLoginPassword'");
    target.etLoginPassword = finder.castView(view, 2131558705, "field 'etLoginPassword'");
    view = finder.findRequiredView(source, 2131558706, "field 'tvLoginLijizhuce'");
    target.tvLoginLijizhuce = finder.castView(view, 2131558706, "field 'tvLoginLijizhuce'");
    view = finder.findRequiredView(source, 2131558707, "field 'tvLoginWangjimima'");
    target.tvLoginWangjimima = finder.castView(view, 2131558707, "field 'tvLoginWangjimima'");
    view = finder.findRequiredView(source, 2131558708, "field 'btnLoginClick'");
    target.btnLoginClick = finder.castView(view, 2131558708, "field 'btnLoginClick'");
    view = finder.findRequiredView(source, 2131558709, "field 'ivLoginXinlang'");
    target.ivLoginXinlang = finder.castView(view, 2131558709, "field 'ivLoginXinlang'");
    view = finder.findRequiredView(source, 2131558710, "field 'ivLoginWeixin'");
    target.ivLoginWeixin = finder.castView(view, 2131558710, "field 'ivLoginWeixin'");
    view = finder.findRequiredView(source, 2131558711, "field 'ivLoginQq'");
    target.ivLoginQq = finder.castView(view, 2131558711, "field 'ivLoginQq'");
    view = finder.findRequiredView(source, 2131558725, "field 'llTitlebarZuojiantou'");
    target.llTitlebarZuojiantou = finder.castView(view, 2131558725, "field 'llTitlebarZuojiantou'");
  }

  @Override public void unbind(T target) {
    target.tvTitlebarCentertext = null;
    target.etLoginPhonenum = null;
    target.etLoginPassword = null;
    target.tvLoginLijizhuce = null;
    target.tvLoginWangjimima = null;
    target.btnLoginClick = null;
    target.ivLoginXinlang = null;
    target.ivLoginWeixin = null;
    target.ivLoginQq = null;
    target.llTitlebarZuojiantou = null;
  }
}
