// Generated code from Butter Knife. Do not modify!
package com.lvshandian.menshen.settings;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class SettingPerson$$ViewBinder<T extends com.lvshandian.menshen.settings.SettingPerson> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558540, "field 'view'");
    target.view = view;
    view = finder.findRequiredView(source, 2131558542, "field 'ivBack'");
    target.ivBack = finder.castView(view, 2131558542, "field 'ivBack'");
    view = finder.findRequiredView(source, 2131558541, "field 'llParentView'");
    target.llParentView = finder.castView(view, 2131558541, "field 'llParentView'");
    view = finder.findRequiredView(source, 2131558543, "field 'tvTitle'");
    target.tvTitle = finder.castView(view, 2131558543, "field 'tvTitle'");
    view = finder.findRequiredView(source, 2131558566, "field 'ivHead'");
    target.ivHead = finder.castView(view, 2131558566, "field 'ivHead'");
    view = finder.findRequiredView(source, 2131558662, "field 'tvName'");
    target.tvName = finder.castView(view, 2131558662, "field 'tvName'");
    view = finder.findRequiredView(source, 2131558597, "field 'img'");
    target.img = finder.castView(view, 2131558597, "field 'img'");
    view = finder.findRequiredView(source, 2131558661, "field 'rlName'");
    target.rlName = finder.castView(view, 2131558661, "field 'rlName'");
    view = finder.findRequiredView(source, 2131558567, "field 'tvPhone'");
    target.tvPhone = finder.castView(view, 2131558567, "field 'tvPhone'");
    view = finder.findRequiredView(source, 2131558664, "field 'img1'");
    target.img1 = finder.castView(view, 2131558664, "field 'img1'");
    view = finder.findRequiredView(source, 2131558663, "field 'rlPhone'");
    target.rlPhone = finder.castView(view, 2131558663, "field 'rlPhone'");
  }

  @Override public void unbind(T target) {
    target.view = null;
    target.ivBack = null;
    target.llParentView = null;
    target.tvTitle = null;
    target.ivHead = null;
    target.tvName = null;
    target.img = null;
    target.rlName = null;
    target.tvPhone = null;
    target.img1 = null;
    target.rlPhone = null;
  }
}
