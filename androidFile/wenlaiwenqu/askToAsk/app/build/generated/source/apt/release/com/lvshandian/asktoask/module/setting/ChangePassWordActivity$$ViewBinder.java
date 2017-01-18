// Generated code from Butter Knife. Do not modify!
package com.lvshandian.asktoask.module.setting;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class ChangePassWordActivity$$ViewBinder<T extends com.lvshandian.asktoask.module.setting.ChangePassWordActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558724, "field 'tvTitlebarCentertext'");
    target.tvTitlebarCentertext = finder.castView(view, 2131558724, "field 'tvTitlebarCentertext'");
    view = finder.findRequiredView(source, 2131558725, "field 'llTitlebarZuojiantou'");
    target.llTitlebarZuojiantou = finder.castView(view, 2131558725, "field 'llTitlebarZuojiantou'");
    view = finder.findRequiredView(source, 2131558610, "field 'etChangepasswodPhonenum'");
    target.etChangepasswodPhonenum = finder.castView(view, 2131558610, "field 'etChangepasswodPhonenum'");
    view = finder.findRequiredView(source, 2131558611, "field 'etChangepasswodYanzhengma'");
    target.etChangepasswodYanzhengma = finder.castView(view, 2131558611, "field 'etChangepasswodYanzhengma'");
    view = finder.findRequiredView(source, 2131558612, "field 'btnChangepasswodGetyanzhengma'");
    target.btnChangepasswodGetyanzhengma = finder.castView(view, 2131558612, "field 'btnChangepasswodGetyanzhengma'");
    view = finder.findRequiredView(source, 2131558613, "field 'etChangepasswodPassword'");
    target.etChangepasswodPassword = finder.castView(view, 2131558613, "field 'etChangepasswodPassword'");
    view = finder.findRequiredView(source, 2131558614, "field 'btnChangepasswodGochange'");
    target.btnChangepasswodGochange = finder.castView(view, 2131558614, "field 'btnChangepasswodGochange'");
  }

  @Override public void unbind(T target) {
    target.tvTitlebarCentertext = null;
    target.llTitlebarZuojiantou = null;
    target.etChangepasswodPhonenum = null;
    target.etChangepasswodYanzhengma = null;
    target.btnChangepasswodGetyanzhengma = null;
    target.etChangepasswodPassword = null;
    target.btnChangepasswodGochange = null;
  }
}
