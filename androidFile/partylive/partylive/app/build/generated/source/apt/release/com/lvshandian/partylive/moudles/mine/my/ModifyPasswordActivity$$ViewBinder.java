// Generated code from Butter Knife. Do not modify!
package com.lvshandian.partylive.moudles.mine.my;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class ModifyPasswordActivity$$ViewBinder<T extends com.lvshandian.partylive.moudles.mine.my.ModifyPasswordActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624188, "field 'etOldPassword'");
    target.etOldPassword = finder.castView(view, 2131624188, "field 'etOldPassword'");
    view = finder.findRequiredView(source, 2131624189, "field 'etNewPassword'");
    target.etNewPassword = finder.castView(view, 2131624189, "field 'etNewPassword'");
    view = finder.findRequiredView(source, 2131624190, "field 'etRepeatPassword'");
    target.etRepeatPassword = finder.castView(view, 2131624190, "field 'etRepeatPassword'");
    view = finder.findRequiredView(source, 2131624187, "field 'btnSave'");
    target.btnSave = finder.castView(view, 2131624187, "field 'btnSave'");
  }

  @Override public void unbind(T target) {
    target.etOldPassword = null;
    target.etNewPassword = null;
    target.etRepeatPassword = null;
    target.btnSave = null;
  }
}
