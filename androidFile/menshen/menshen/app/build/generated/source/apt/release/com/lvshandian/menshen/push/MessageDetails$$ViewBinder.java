// Generated code from Butter Knife. Do not modify!
package com.lvshandian.menshen.push;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class MessageDetails$$ViewBinder<T extends com.lvshandian.menshen.push.MessageDetails> implements ViewBinder<T> {
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
    view = finder.findRequiredView(source, 2131558631, "field 'tvDate'");
    target.tvDate = finder.castView(view, 2131558631, "field 'tvDate'");
    view = finder.findRequiredView(source, 2131558630, "field 'llItem'");
    target.llItem = finder.castView(view, 2131558630, "field 'llItem'");
    view = finder.findRequiredView(source, 2131558592, "field 'tvContext'");
    target.tvContext = finder.castView(view, 2131558592, "field 'tvContext'");
    view = finder.findRequiredView(source, 2131558658, "field 'tvTitleMessage'");
    target.tvTitleMessage = finder.castView(view, 2131558658, "field 'tvTitleMessage'");
  }

  @Override public void unbind(T target) {
    target.ivBack = null;
    target.llParentView = null;
    target.tvTitle = null;
    target.rlInclud = null;
    target.tvDate = null;
    target.llItem = null;
    target.tvContext = null;
    target.tvTitleMessage = null;
  }
}
