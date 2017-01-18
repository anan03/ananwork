// Generated code from Butter Knife. Do not modify!
package com.lvshandian.asktoask.module.answer.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class ApproveLifePhotoActivity$$ViewBinder<T extends com.lvshandian.asktoask.module.answer.activity.ApproveLifePhotoActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558558, "field 'ivTakephoto'");
    target.ivTakephoto = finder.castView(view, 2131558558, "field 'ivTakephoto'");
    view = finder.findRequiredView(source, 2131558536, "field 'ivBack'");
    target.ivBack = finder.castView(view, 2131558536, "field 'ivBack'");
    view = finder.findRequiredView(source, 2131558557, "field 'tvSave'");
    target.tvSave = finder.castView(view, 2131558557, "field 'tvSave'");
  }

  @Override public void unbind(T target) {
    target.ivTakephoto = null;
    target.ivBack = null;
    target.tvSave = null;
  }
}
