// Generated code from Butter Knife. Do not modify!
package com.lvshandian.asktoask.module.setting;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class ChangePhoneNumActivity$$ViewBinder<T extends com.lvshandian.asktoask.module.setting.ChangePhoneNumActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558724, "field 'tvTitlebarCentertext'");
    target.tvTitlebarCentertext = finder.castView(view, 2131558724, "field 'tvTitlebarCentertext'");
    view = finder.findRequiredView(source, 2131558725, "field 'llTitlebarZuojiantou'");
    target.llTitlebarZuojiantou = finder.castView(view, 2131558725, "field 'llTitlebarZuojiantou'");
    view = finder.findRequiredView(source, 2131558616, "field 'etChangephonenumPhonenum'");
    target.etChangephonenumPhonenum = finder.castView(view, 2131558616, "field 'etChangephonenumPhonenum'");
    view = finder.findRequiredView(source, 2131558617, "field 'etChangephonenumYanzhengma'");
    target.etChangephonenumYanzhengma = finder.castView(view, 2131558617, "field 'etChangephonenumYanzhengma'");
    view = finder.findRequiredView(source, 2131558618, "field 'btnChangephonenumGetyanzhengma'");
    target.btnChangephonenumGetyanzhengma = finder.castView(view, 2131558618, "field 'btnChangephonenumGetyanzhengma'");
    view = finder.findRequiredView(source, 2131558619, "field 'etChangephonenumnewpphonenum'");
    target.etChangephonenumnewpphonenum = finder.castView(view, 2131558619, "field 'etChangephonenumnewpphonenum'");
    view = finder.findRequiredView(source, 2131558620, "field 'btnChangephonenumGochange'");
    target.btnChangephonenumGochange = finder.castView(view, 2131558620, "field 'btnChangephonenumGochange'");
  }

  @Override public void unbind(T target) {
    target.tvTitlebarCentertext = null;
    target.llTitlebarZuojiantou = null;
    target.etChangephonenumPhonenum = null;
    target.etChangephonenumYanzhengma = null;
    target.btnChangephonenumGetyanzhengma = null;
    target.etChangephonenumnewpphonenum = null;
    target.btnChangephonenumGochange = null;
  }
}
