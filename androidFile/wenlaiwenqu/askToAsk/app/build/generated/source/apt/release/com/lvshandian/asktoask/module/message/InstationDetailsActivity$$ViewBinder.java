// Generated code from Butter Knife. Do not modify!
package com.lvshandian.asktoask.module.message;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class InstationDetailsActivity$$ViewBinder<T extends com.lvshandian.asktoask.module.message.InstationDetailsActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558721, "field 'ivMessageInstation'");
    target.ivMessageInstation = finder.castView(view, 2131558721, "field 'ivMessageInstation'");
    view = finder.findRequiredView(source, 2131558696, "field 'tvTitle'");
    target.tvTitle = finder.castView(view, 2131558696, "field 'tvTitle'");
    view = finder.findRequiredView(source, 2131558722, "field 'tvMessageDate'");
    target.tvMessageDate = finder.castView(view, 2131558722, "field 'tvMessageDate'");
    view = finder.findRequiredView(source, 2131558720, "field 'rlAnswerDetail'");
    target.rlAnswerDetail = finder.castView(view, 2131558720, "field 'rlAnswerDetail'");
    view = finder.findRequiredView(source, 2131558723, "field 'tvMessageData'");
    target.tvMessageData = finder.castView(view, 2131558723, "field 'tvMessageData'");
    view = finder.findRequiredView(source, 2131558528, "field 'ivBackApproveAddress'");
    target.ivBackApproveAddress = finder.castView(view, 2131558528, "field 'ivBackApproveAddress'");
    view = finder.findRequiredView(source, 2131558527, "field 'llParentView'");
    target.llParentView = finder.castView(view, 2131558527, "field 'llParentView'");
    view = finder.findRequiredView(source, 2131558526, "field 'rlTitle'");
    target.rlTitle = finder.castView(view, 2131558526, "field 'rlTitle'");
  }

  @Override public void unbind(T target) {
    target.ivMessageInstation = null;
    target.tvTitle = null;
    target.tvMessageDate = null;
    target.rlAnswerDetail = null;
    target.tvMessageData = null;
    target.ivBackApproveAddress = null;
    target.llParentView = null;
    target.rlTitle = null;
  }
}
