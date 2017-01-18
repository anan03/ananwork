// Generated code from Butter Knife. Do not modify!
package com.lvshandian.asktoask.module.setting;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class CouponActivity$$ViewBinder<T extends com.lvshandian.asktoask.module.setting.CouponActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558872, "field 'll'");
    target.ll = view;
    view = finder.findRequiredView(source, 2131558724, "field 'tvTitlebarCentertext'");
    target.tvTitlebarCentertext = finder.castView(view, 2131558724, "field 'tvTitlebarCentertext'");
    view = finder.findRequiredView(source, 2131558725, "field 'llTitlebarZuojiantou'");
    target.llTitlebarZuojiantou = finder.castView(view, 2131558725, "field 'llTitlebarZuojiantou'");
    view = finder.findRequiredView(source, 2131558726, "field 'tvTitlebarRighttext'");
    target.tvTitlebarRighttext = finder.castView(view, 2131558726, "field 'tvTitlebarRighttext'");
    view = finder.findRequiredView(source, 2131558832, "field 'etYouhuiquanDuihuanma'");
    target.etYouhuiquanDuihuanma = finder.castView(view, 2131558832, "field 'etYouhuiquanDuihuanma'");
    view = finder.findRequiredView(source, 2131558833, "field 'tvYouhuiquanGoduihuan'");
    target.tvYouhuiquanGoduihuan = finder.castView(view, 2131558833, "field 'tvYouhuiquanGoduihuan'");
    view = finder.findRequiredView(source, 2131558834, "field 'lvYouhuiquanYouhuiquanlist'");
    target.lvYouhuiquanYouhuiquanlist = finder.castView(view, 2131558834, "field 'lvYouhuiquanYouhuiquanlist'");
    view = finder.findRequiredView(source, 2131558835, "field 'tvNoYouhuijuan'");
    target.tvNoYouhuijuan = finder.castView(view, 2131558835, "field 'tvNoYouhuijuan'");
  }

  @Override public void unbind(T target) {
    target.ll = null;
    target.tvTitlebarCentertext = null;
    target.llTitlebarZuojiantou = null;
    target.tvTitlebarRighttext = null;
    target.etYouhuiquanDuihuanma = null;
    target.tvYouhuiquanGoduihuan = null;
    target.lvYouhuiquanYouhuiquanlist = null;
    target.tvNoYouhuijuan = null;
  }
}
