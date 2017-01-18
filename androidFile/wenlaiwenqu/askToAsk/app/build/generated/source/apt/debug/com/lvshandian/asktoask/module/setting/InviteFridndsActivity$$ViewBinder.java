// Generated code from Butter Knife. Do not modify!
package com.lvshandian.asktoask.module.setting;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class InviteFridndsActivity$$ViewBinder<T extends com.lvshandian.asktoask.module.setting.InviteFridndsActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558818, "field 'llTuijianhaoyouWeixinhaoyou'");
    target.llTuijianhaoyouWeixinhaoyou = finder.castView(view, 2131558818, "field 'llTuijianhaoyouWeixinhaoyou'");
    view = finder.findRequiredView(source, 2131558817, "field 'llTuijianhaoyouQqhaoyou'");
    target.llTuijianhaoyouQqhaoyou = finder.castView(view, 2131558817, "field 'llTuijianhaoyouQqhaoyou'");
    view = finder.findRequiredView(source, 2131558819, "field 'llTuijianhaoyouXinlanghaoyou'");
    target.llTuijianhaoyouXinlanghaoyou = finder.castView(view, 2131558819, "field 'llTuijianhaoyouXinlanghaoyou'");
    view = finder.findRequiredView(source, 2131558724, "field 'tvtitlebarcentertext'");
    target.tvtitlebarcentertext = finder.castView(view, 2131558724, "field 'tvtitlebarcentertext'");
    view = finder.findRequiredView(source, 2131558725, "field 'llTitlebarZuojiantou'");
    target.llTitlebarZuojiantou = finder.castView(view, 2131558725, "field 'llTitlebarZuojiantou'");
  }

  @Override public void unbind(T target) {
    target.llTuijianhaoyouWeixinhaoyou = null;
    target.llTuijianhaoyouQqhaoyou = null;
    target.llTuijianhaoyouXinlanghaoyou = null;
    target.tvtitlebarcentertext = null;
    target.llTitlebarZuojiantou = null;
  }
}
