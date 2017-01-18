// Generated code from Butter Knife. Do not modify!
package com.lvshandian.asktoask.module.message;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class MessageDetailsActivity$$ViewBinder<T extends com.lvshandian.asktoask.module.message.MessageDetailsActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558638, "field 'vTitle'");
    target.vTitle = view;
    view = finder.findRequiredView(source, 2131558528, "field 'ivBackApproveAddress'");
    target.ivBackApproveAddress = finder.castView(view, 2131558528, "field 'ivBackApproveAddress'");
    view = finder.findRequiredView(source, 2131558639, "field 'leaveName'");
    target.leaveName = finder.castView(view, 2131558639, "field 'leaveName'");
    view = finder.findRequiredView(source, 2131558640, "field 'tvLeaveedNameMen'");
    target.tvLeaveedNameMen = finder.castView(view, 2131558640, "field 'tvLeaveedNameMen'");
    view = finder.findRequiredView(source, 2131558526, "field 'rlTitle'");
    target.rlTitle = finder.castView(view, 2131558526, "field 'rlTitle'");
    view = finder.findRequiredView(source, 2131558621, "field 'llLeavemessage'");
    target.llLeavemessage = finder.castView(view, 2131558621, "field 'llLeavemessage'");
    view = finder.findRequiredView(source, 2131558641, "field 'etLeaveData'");
    target.etLeaveData = finder.castView(view, 2131558641, "field 'etLeaveData'");
  }

  @Override public void unbind(T target) {
    target.vTitle = null;
    target.ivBackApproveAddress = null;
    target.leaveName = null;
    target.tvLeaveedNameMen = null;
    target.rlTitle = null;
    target.llLeavemessage = null;
    target.etLeaveData = null;
  }
}
