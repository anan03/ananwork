// Generated code from Butter Knife. Do not modify!
package com.lvshandian.partylive.moudles.mine.my;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class RealNameVertifyActivity$$ViewBinder<T extends com.lvshandian.partylive.moudles.mine.my.RealNameVertifyActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624104, "field 'ivImage'");
    target.ivImage = finder.castView(view, 2131624104, "field 'ivImage'");
    view = finder.findRequiredView(source, 2131624204, "field 'tvContent'");
    target.tvContent = finder.castView(view, 2131624204, "field 'tvContent'");
  }

  @Override public void unbind(T target) {
    target.ivImage = null;
    target.tvContent = null;
  }
}
