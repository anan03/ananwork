// Generated code from Butter Knife. Do not modify!
package com.lvshandian.asktoask.module.setting;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class UserAgreementActivity$$ViewBinder<T extends com.lvshandian.asktoask.module.setting.UserAgreementActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558724, "field 'tvTitlebarCentertext'");
    target.tvTitlebarCentertext = finder.castView(view, 2131558724, "field 'tvTitlebarCentertext'");
    view = finder.findRequiredView(source, 2131558725, "field 'llTitlebarZuojiantou'");
    target.llTitlebarZuojiantou = finder.castView(view, 2131558725, "field 'llTitlebarZuojiantou'");
    view = finder.findRequiredView(source, 2131558830, "field 'tvYonghuxieyiTitle'");
    target.tvYonghuxieyiTitle = finder.castView(view, 2131558830, "field 'tvYonghuxieyiTitle'");
    view = finder.findRequiredView(source, 2131558831, "field 'tvYonghuxieyiXieyineirong'");
    target.tvYonghuxieyiXieyineirong = finder.castView(view, 2131558831, "field 'tvYonghuxieyiXieyineirong'");
  }

  @Override public void unbind(T target) {
    target.tvTitlebarCentertext = null;
    target.llTitlebarZuojiantou = null;
    target.tvYonghuxieyiTitle = null;
    target.tvYonghuxieyiXieyineirong = null;
  }
}
