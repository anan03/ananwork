// Generated code from Butter Knife. Do not modify!
package com.lvshandian.asktoask.module.user.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class UserAnswerDatailsActivity$$ViewBinder<T extends com.lvshandian.asktoask.module.user.activity.UserAnswerDatailsActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558530, "field 'pullLvCollect'");
    target.pullLvCollect = finder.castView(view, 2131558530, "field 'pullLvCollect'");
    view = finder.findRequiredView(source, 2131558534, "field 'ivHudongDetailText'");
    target.ivHudongDetailText = finder.castView(view, 2131558534, "field 'ivHudongDetailText'");
    view = finder.findRequiredView(source, 2131558535, "field 'tvSendAnswer'");
    target.tvSendAnswer = finder.castView(view, 2131558535, "field 'tvSendAnswer'");
    view = finder.findRequiredView(source, 2131558531, "field 'llAnswer'");
    target.llAnswer = finder.castView(view, 2131558531, "field 'llAnswer'");
    view = finder.findRequiredView(source, 2131558528, "field 'ivBackApproveAddress'");
    target.ivBackApproveAddress = finder.castView(view, 2131558528, "field 'ivBackApproveAddress'");
  }

  @Override public void unbind(T target) {
    target.pullLvCollect = null;
    target.ivHudongDetailText = null;
    target.tvSendAnswer = null;
    target.llAnswer = null;
    target.ivBackApproveAddress = null;
  }
}
