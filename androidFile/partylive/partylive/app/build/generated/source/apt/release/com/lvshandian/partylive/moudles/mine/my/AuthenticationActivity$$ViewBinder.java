// Generated code from Butter Knife. Do not modify!
package com.lvshandian.partylive.moudles.mine.my;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class AuthenticationActivity$$ViewBinder<T extends com.lvshandian.partylive.moudles.mine.my.AuthenticationActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624104, "field 'ivImage'");
    target.ivImage = finder.castView(view, 2131624104, "field 'ivImage'");
    view = finder.findRequiredView(source, 2131624103, "field 'aflLayout'");
    target.aflLayout = finder.castView(view, 2131624103, "field 'aflLayout'");
    view = finder.findRequiredView(source, 2131624105, "field 'etName'");
    target.etName = finder.castView(view, 2131624105, "field 'etName'");
    view = finder.findRequiredView(source, 2131624106, "field 'etIdentyNum'");
    target.etIdentyNum = finder.castView(view, 2131624106, "field 'etIdentyNum'");
    view = finder.findRequiredView(source, 2131624107, "field 'etPhone'");
    target.etPhone = finder.castView(view, 2131624107, "field 'etPhone'");
    view = finder.findRequiredView(source, 2131624108, "field 'etIdentyName'");
    target.etIdentyName = finder.castView(view, 2131624108, "field 'etIdentyName'");
    view = finder.findRequiredView(source, 2131624109, "field 'btnSubmit'");
    target.btnSubmit = finder.castView(view, 2131624109, "field 'btnSubmit'");
  }

  @Override public void unbind(T target) {
    target.ivImage = null;
    target.aflLayout = null;
    target.etName = null;
    target.etIdentyNum = null;
    target.etPhone = null;
    target.etIdentyName = null;
    target.btnSubmit = null;
  }
}
