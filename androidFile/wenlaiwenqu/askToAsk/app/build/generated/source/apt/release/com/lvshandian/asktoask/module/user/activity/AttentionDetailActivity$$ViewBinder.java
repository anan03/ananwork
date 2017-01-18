// Generated code from Butter Knife. Do not modify!
package com.lvshandian.asktoask.module.user.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class AttentionDetailActivity$$ViewBinder<T extends com.lvshandian.asktoask.module.user.activity.AttentionDetailActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558530, "field 'pullLvCollect'");
    target.pullLvCollect = finder.castView(view, 2131558530, "field 'pullLvCollect'");
    view = finder.findRequiredView(source, 2131558583, "field 'tvLeaveMessage'");
    target.tvLeaveMessage = finder.castView(view, 2131558583, "field 'tvLeaveMessage'");
    view = finder.findRequiredView(source, 2131558582, "field 'llAnswerDetailLeaveWords'");
    target.llAnswerDetailLeaveWords = finder.castView(view, 2131558582, "field 'llAnswerDetailLeaveWords'");
    view = finder.findRequiredView(source, 2131558584, "field 'llAnswerDetailFocus'");
    target.llAnswerDetailFocus = finder.castView(view, 2131558584, "field 'llAnswerDetailFocus'");
  }

  @Override public void unbind(T target) {
    target.pullLvCollect = null;
    target.tvLeaveMessage = null;
    target.llAnswerDetailLeaveWords = null;
    target.llAnswerDetailFocus = null;
  }
}
