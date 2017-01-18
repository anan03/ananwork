// Generated code from Butter Knife. Do not modify!
package com.lvshandian.asktoask.module.postquestion;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class CouponActivity$$ViewBinder<T extends com.lvshandian.asktoask.module.postquestion.CouponActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558635, "field 'ivHudongDetailBack'");
    target.ivHudongDetailBack = finder.castView(view, 2131558635, "field 'ivHudongDetailBack'");
    view = finder.findRequiredView(source, 2131558529, "field 'tvHudongDetailType'");
    target.tvHudongDetailType = finder.castView(view, 2131558529, "field 'tvHudongDetailType'");
    view = finder.findRequiredView(source, 2131558636, "field 'tvNumsCoupon'");
    target.tvNumsCoupon = finder.castView(view, 2131558636, "field 'tvNumsCoupon'");
    view = finder.findRequiredView(source, 2131558637, "field 'lvAsk'");
    target.lvAsk = finder.castView(view, 2131558637, "field 'lvAsk'");
  }

  @Override public void unbind(T target) {
    target.ivHudongDetailBack = null;
    target.tvHudongDetailType = null;
    target.tvNumsCoupon = null;
    target.lvAsk = null;
  }
}
