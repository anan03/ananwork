// Generated code from Butter Knife. Do not modify!
package com.lvshandian.asktoask.module.message;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class LeaveListMEActivity$$ViewBinder<T extends com.lvshandian.asktoask.module.message.LeaveListMEActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558638, "field 'vTitle'");
    target.vTitle = view;
    view = finder.findRequiredView(source, 2131558536, "field 'ivBack'");
    target.ivBack = finder.castView(view, 2131558536, "field 'ivBack'");
    view = finder.findRequiredView(source, 2131558640, "field 'tvLeaveedNameMen'");
    target.tvLeaveedNameMen = finder.castView(view, 2131558640, "field 'tvLeaveedNameMen'");
    view = finder.findRequiredView(source, 2131558526, "field 'rlTitle'");
    target.rlTitle = finder.castView(view, 2131558526, "field 'rlTitle'");
    view = finder.findRequiredView(source, 2131558698, "field 'pulvLeaveList'");
    target.pulvLeaveList = finder.castView(view, 2131558698, "field 'pulvLeaveList'");
    view = finder.findRequiredView(source, 2131558699, "field 'btnLeave'");
    target.btnLeave = finder.castView(view, 2131558699, "field 'btnLeave'");
  }

  @Override public void unbind(T target) {
    target.vTitle = null;
    target.ivBack = null;
    target.tvLeaveedNameMen = null;
    target.rlTitle = null;
    target.pulvLeaveList = null;
    target.btnLeave = null;
  }
}
