// Generated code from Butter Knife. Do not modify!
package com.lvshandian.asktoask.module.user.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class MineRewardActivity$$ViewBinder<T extends com.lvshandian.asktoask.module.user.activity.MineRewardActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558724, "field 'tvTitlebarCentertext'");
    target.tvTitlebarCentertext = finder.castView(view, 2131558724, "field 'tvTitlebarCentertext'");
    view = finder.findRequiredView(source, 2131558725, "field 'llTitlebarZuojiantou'");
    target.llTitlebarZuojiantou = finder.castView(view, 2131558725, "field 'llTitlebarZuojiantou'");
    view = finder.findRequiredView(source, 2131558825, "field 'btnWodexuanshangMoney'");
    target.btnWodexuanshangMoney = finder.castView(view, 2131558825, "field 'btnWodexuanshangMoney'");
    view = finder.findRequiredView(source, 2131558826, "field 'btnWodexuanshangGotixian'");
    target.btnWodexuanshangGotixian = finder.castView(view, 2131558826, "field 'btnWodexuanshangGotixian'");
  }

  @Override public void unbind(T target) {
    target.tvTitlebarCentertext = null;
    target.llTitlebarZuojiantou = null;
    target.btnWodexuanshangMoney = null;
    target.btnWodexuanshangGotixian = null;
  }
}
