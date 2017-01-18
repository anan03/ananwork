// Generated code from Butter Knife. Do not modify!
package com.lvshandian.asktoask.module.postquestion;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class PostQuestionPayActivity$$ViewBinder<T extends com.lvshandian.asktoask.module.postquestion.PostQuestionPayActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558783, "field 'backPay'");
    target.backPay = finder.castView(view, 2131558783, "field 'backPay'");
    view = finder.findRequiredView(source, 2131558784, "field 'tvConfirmReal'");
    target.tvConfirmReal = finder.castView(view, 2131558784, "field 'tvConfirmReal'");
    view = finder.findRequiredView(source, 2131558787, "field 'ckWeixin'");
    target.ckWeixin = finder.castView(view, 2131558787, "field 'ckWeixin'");
    view = finder.findRequiredView(source, 2131558790, "field 'ckZhifubao'");
    target.ckZhifubao = finder.castView(view, 2131558790, "field 'ckZhifubao'");
    view = finder.findRequiredView(source, 2131558791, "field 'btnPay'");
    target.btnPay = finder.castView(view, 2131558791, "field 'btnPay'");
  }

  @Override public void unbind(T target) {
    target.backPay = null;
    target.tvConfirmReal = null;
    target.ckWeixin = null;
    target.ckZhifubao = null;
    target.btnPay = null;
  }
}
