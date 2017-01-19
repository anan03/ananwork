// Generated code from Butter Knife. Do not modify!
package com.lvshandian.menshen.settings;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class AboutMeActivity$$ViewBinder<T extends com.lvshandian.menshen.settings.AboutMeActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558542, "field 'ivBack'");
    target.ivBack = finder.castView(view, 2131558542, "field 'ivBack'");
    view = finder.findRequiredView(source, 2131558541, "field 'llParentView'");
    target.llParentView = finder.castView(view, 2131558541, "field 'llParentView'");
    view = finder.findRequiredView(source, 2131558543, "field 'tvTitle'");
    target.tvTitle = finder.castView(view, 2131558543, "field 'tvTitle'");
    view = finder.findRequiredView(source, 2131558667, "field 'rlInclud'");
    target.rlInclud = finder.castView(view, 2131558667, "field 'rlInclud'");
    view = finder.findRequiredView(source, 2131558665, "field 'editText'");
    target.editText = finder.castView(view, 2131558665, "field 'editText'");
    view = finder.findRequiredView(source, 2131558666, "field 'btTj'");
    target.btTj = finder.castView(view, 2131558666, "field 'btTj'");
  }

  @Override public void unbind(T target) {
    target.ivBack = null;
    target.llParentView = null;
    target.tvTitle = null;
    target.rlInclud = null;
    target.editText = null;
    target.btTj = null;
  }
}
