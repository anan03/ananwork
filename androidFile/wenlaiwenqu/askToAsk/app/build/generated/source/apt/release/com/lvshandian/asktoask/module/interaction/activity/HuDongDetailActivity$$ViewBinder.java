// Generated code from Butter Knife. Do not modify!
package com.lvshandian.asktoask.module.interaction.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class HuDongDetailActivity$$ViewBinder<T extends com.lvshandian.asktoask.module.interaction.activity.HuDongDetailActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558635, "field 'ivHudongDetailBack'");
    target.ivHudongDetailBack = finder.castView(view, 2131558635, "field 'ivHudongDetailBack'");
    view = finder.findRequiredView(source, 2131558529, "field 'tvHudongDetailType'");
    target.tvHudongDetailType = finder.castView(view, 2131558529, "field 'tvHudongDetailType'");
    view = finder.findRequiredView(source, 2131558530, "field 'lvHudongDetail'");
    target.lvHudongDetail = finder.castView(view, 2131558530, "field 'lvHudongDetail'");
    view = finder.findRequiredView(source, 2131558532, "field 'etAnswerContent'");
    target.etAnswerContent = finder.castView(view, 2131558532, "field 'etAnswerContent'");
    view = finder.findRequiredView(source, 2131558535, "field 'tvSendAnswer'");
    target.tvSendAnswer = finder.castView(view, 2131558535, "field 'tvSendAnswer'");
    view = finder.findRequiredView(source, 2131558650, "field 'llHudongDetail'");
    target.llHudongDetail = finder.castView(view, 2131558650, "field 'llHudongDetail'");
  }

  @Override public void unbind(T target) {
    target.ivHudongDetailBack = null;
    target.tvHudongDetailType = null;
    target.lvHudongDetail = null;
    target.etAnswerContent = null;
    target.tvSendAnswer = null;
    target.llHudongDetail = null;
  }
}
