// Generated code from Butter Knife. Do not modify!
package com.lvshandian.partylive.moudles.start;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class RegisterUserActivity$$ViewBinder<T extends com.lvshandian.partylive.moudles.start.RegisterUserActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624211, "field 'choiseUserHead'");
    target.choiseUserHead = finder.castView(view, 2131624211, "field 'choiseUserHead'");
    view = finder.findRequiredView(source, 2131624212, "field 'edRegisterNick'");
    target.edRegisterNick = finder.castView(view, 2131624212, "field 'edRegisterNick'");
    view = finder.findRequiredView(source, 2131624213, "field 'edRegisterGender'");
    target.edRegisterGender = finder.castView(view, 2131624213, "field 'edRegisterGender'");
    view = finder.findRequiredView(source, 2131624214, "field 'edRegisterPrivateCosts'");
    target.edRegisterPrivateCosts = finder.castView(view, 2131624214, "field 'edRegisterPrivateCosts'");
    view = finder.findRequiredView(source, 2131624215, "field 'edRegisterSignature'");
    target.edRegisterSignature = finder.castView(view, 2131624215, "field 'edRegisterSignature'");
    view = finder.findRequiredView(source, 2131624216, "field 'btnChangeUser'");
    target.btnChangeUser = finder.castView(view, 2131624216, "field 'btnChangeUser'");
    view = finder.findRequiredView(source, 2131624167, "field 'allLayout'");
    target.allLayout = finder.castView(view, 2131624167, "field 'allLayout'");
    view = finder.findRequiredView(source, 2131624210, "field 'registerLayout'");
    target.registerLayout = finder.castView(view, 2131624210, "field 'registerLayout'");
  }

  @Override public void unbind(T target) {
    target.choiseUserHead = null;
    target.edRegisterNick = null;
    target.edRegisterGender = null;
    target.edRegisterPrivateCosts = null;
    target.edRegisterSignature = null;
    target.btnChangeUser = null;
    target.allLayout = null;
    target.registerLayout = null;
  }
}
