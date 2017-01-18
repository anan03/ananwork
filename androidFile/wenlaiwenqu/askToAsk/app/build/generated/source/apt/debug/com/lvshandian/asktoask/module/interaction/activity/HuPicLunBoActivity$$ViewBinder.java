// Generated code from Butter Knife. Do not modify!
package com.lvshandian.asktoask.module.interaction.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class HuPicLunBoActivity$$ViewBinder<T extends com.lvshandian.asktoask.module.interaction.activity.HuPicLunBoActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558652, "field 'vpPicNine'");
    target.vpPicNine = finder.castView(view, 2131558652, "field 'vpPicNine'");
    view = finder.findRequiredView(source, 2131558653, "field 'tvShowPics'");
    target.tvShowPics = finder.castView(view, 2131558653, "field 'tvShowPics'");
    view = finder.findRequiredView(source, 2131558654, "field 'tvSavePhoto'");
    target.tvSavePhoto = finder.castView(view, 2131558654, "field 'tvSavePhoto'");
    view = finder.findRequiredView(source, 2131558536, "field 'ivBack'");
    target.ivBack = finder.castView(view, 2131558536, "field 'ivBack'");
  }

  @Override public void unbind(T target) {
    target.vpPicNine = null;
    target.tvShowPics = null;
    target.tvSavePhoto = null;
    target.ivBack = null;
  }
}
