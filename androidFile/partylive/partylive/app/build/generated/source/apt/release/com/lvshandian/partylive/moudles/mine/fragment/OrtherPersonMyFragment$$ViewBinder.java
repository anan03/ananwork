// Generated code from Butter Knife. Do not modify!
package com.lvshandian.partylive.moudles.mine.fragment;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class OrtherPersonMyFragment$$ViewBinder<T extends com.lvshandian.partylive.moudles.mine.fragment.OrtherPersonMyFragment> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624448, "field 'tvReciver'");
    target.tvReciver = finder.castView(view, 2131624448, "field 'tvReciver'");
    view = finder.findRequiredView(source, 2131624449, "field 'tvServiceRate'");
    target.tvServiceRate = finder.castView(view, 2131624449, "field 'tvServiceRate'");
    view = finder.findRequiredView(source, 2131624450, "field 'tvScorRate'");
    target.tvScorRate = finder.castView(view, 2131624450, "field 'tvScorRate'");
    view = finder.findRequiredView(source, 2131624195, "field 'tvSign'");
    target.tvSign = finder.castView(view, 2131624195, "field 'tvSign'");
    view = finder.findRequiredView(source, 2131624451, "field 'tvVedioMoney'");
    target.tvVedioMoney = finder.castView(view, 2131624451, "field 'tvVedioMoney'");
  }

  @Override public void unbind(T target) {
    target.tvReciver = null;
    target.tvServiceRate = null;
    target.tvScorRate = null;
    target.tvSign = null;
    target.tvVedioMoney = null;
  }
}
