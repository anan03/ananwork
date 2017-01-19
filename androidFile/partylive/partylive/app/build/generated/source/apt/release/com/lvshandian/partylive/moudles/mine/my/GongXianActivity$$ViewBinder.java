// Generated code from Butter Knife. Do not modify!
package com.lvshandian.partylive.moudles.mine.my;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class GongXianActivity$$ViewBinder<T extends com.lvshandian.partylive.moudles.mine.my.GongXianActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624168, "field 'tvGongXian'");
    target.tvGongXian = finder.castView(view, 2131624168, "field 'tvGongXian'");
    view = finder.findRequiredView(source, 2131624169, "field 'rlvList'");
    target.rlvList = finder.castView(view, 2131624169, "field 'rlvList'");
    view = finder.findRequiredView(source, 2131624165, "field 'mrlLayout'");
    target.mrlLayout = finder.castView(view, 2131624165, "field 'mrlLayout'");
    view = finder.findRequiredView(source, 2131624167, "field 'allLayout'");
    target.allLayout = finder.castView(view, 2131624167, "field 'allLayout'");
  }

  @Override public void unbind(T target) {
    target.tvGongXian = null;
    target.rlvList = null;
    target.mrlLayout = null;
    target.allLayout = null;
  }
}
