// Generated code from Butter Knife. Do not modify!
package com.lvshandian.asktoask.module.user.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class LeaveMessageActivity$$ViewBinder<T extends com.lvshandian.asktoask.module.user.activity.LeaveMessageActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558541, "field 'ivAnswerActivityBack'");
    target.ivAnswerActivityBack = finder.castView(view, 2131558541, "field 'ivAnswerActivityBack'");
    view = finder.findRequiredView(source, 2131558700, "field 'tvName'");
    target.tvName = finder.castView(view, 2131558700, "field 'tvName'");
    view = finder.findRequiredView(source, 2131558701, "field 'tvSend'");
    target.tvSend = finder.castView(view, 2131558701, "field 'tvSend'");
    view = finder.findRequiredView(source, 2131558702, "field 'etMessage'");
    target.etMessage = finder.castView(view, 2131558702, "field 'etMessage'");
  }

  @Override public void unbind(T target) {
    target.ivAnswerActivityBack = null;
    target.tvName = null;
    target.tvSend = null;
    target.etMessage = null;
  }
}
