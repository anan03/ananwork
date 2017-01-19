// Generated code from Butter Knife. Do not modify!
package com.lvshandian.partylive.moudles.mine.my;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class SettingActivity$$ViewBinder<T extends com.lvshandian.partylive.moudles.mine.my.SettingActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624223, "field 'allChangeKey'");
    target.allChangeKey = finder.castView(view, 2131624223, "field 'allChangeKey'");
    view = finder.findRequiredView(source, 2131624224, "field 'allBlackList'");
    target.allBlackList = finder.castView(view, 2131624224, "field 'allBlackList'");
    view = finder.findRequiredView(source, 2131624225, "field 'allSystemMessage'");
    target.allSystemMessage = finder.castView(view, 2131624225, "field 'allSystemMessage'");
    view = finder.findRequiredView(source, 2131624226, "field 'allSayTips'");
    target.allSayTips = finder.castView(view, 2131624226, "field 'allSayTips'");
    view = finder.findRequiredView(source, 2131624228, "field 'tvCache'");
    target.tvCache = finder.castView(view, 2131624228, "field 'tvCache'");
    view = finder.findRequiredView(source, 2131624227, "field 'allClearCache'");
    target.allClearCache = finder.castView(view, 2131624227, "field 'allClearCache'");
    view = finder.findRequiredView(source, 2131624229, "field 'btnQuit'");
    target.btnQuit = finder.castView(view, 2131624229, "field 'btnQuit'");
  }

  @Override public void unbind(T target) {
    target.allChangeKey = null;
    target.allBlackList = null;
    target.allSystemMessage = null;
    target.allSayTips = null;
    target.tvCache = null;
    target.allClearCache = null;
    target.btnQuit = null;
  }
}
