// Generated code from Butter Knife. Do not modify!
package com.lvshandian.partylive.moudles.mine.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class ExchangeCenterActivity$$ViewBinder<T extends com.lvshandian.partylive.moudles.mine.activity.ExchangeCenterActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624158, "field 'tvHasMoney'");
    target.tvHasMoney = finder.castView(view, 2131624158, "field 'tvHasMoney'");
    view = finder.findRequiredView(source, 2131624159, "field 'tvExchangeMoney'");
    target.tvExchangeMoney = finder.castView(view, 2131624159, "field 'tvExchangeMoney'");
    view = finder.findRequiredView(source, 2131624160, "field 'tvExchaneRedBag'");
    target.tvExchaneRedBag = finder.castView(view, 2131624160, "field 'tvExchaneRedBag'");
    view = finder.findRequiredView(source, 2131624161, "field 'tvCanExchangeRedBag'");
    target.tvCanExchangeRedBag = finder.castView(view, 2131624161, "field 'tvCanExchangeRedBag'");
    view = finder.findRequiredView(source, 2131624162, "field 'btnExchangeTop'");
    target.btnExchangeTop = finder.castView(view, 2131624162, "field 'btnExchangeTop'");
    view = finder.findRequiredView(source, 2131624163, "field 'btnExchangeButtom'");
    target.btnExchangeButtom = finder.castView(view, 2131624163, "field 'btnExchangeButtom'");
    view = finder.findRequiredView(source, 2131624164, "field 'tvProblem'");
    target.tvProblem = finder.castView(view, 2131624164, "field 'tvProblem'");
  }

  @Override public void unbind(T target) {
    target.tvHasMoney = null;
    target.tvExchangeMoney = null;
    target.tvExchaneRedBag = null;
    target.tvCanExchangeRedBag = null;
    target.btnExchangeTop = null;
    target.btnExchangeButtom = null;
    target.tvProblem = null;
  }
}
