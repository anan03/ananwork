// Generated code from Butter Knife. Do not modify!
package com.lvshandian.menshen.analysesms;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class SmsSettingActivity$$ViewBinder<T extends com.lvshandian.menshen.analysesms.SmsSettingActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558542, "field 'ivBack'");
    target.ivBack = finder.castView(view, 2131558542, "field 'ivBack'");
    view = finder.findRequiredView(source, 2131558543, "field 'tvTitle'");
    target.tvTitle = finder.castView(view, 2131558543, "field 'tvTitle'");
    view = finder.findRequiredView(source, 2131558582, "field 'rlGuanjianzi'");
    target.rlGuanjianzi = finder.castView(view, 2131558582, "field 'rlGuanjianzi'");
    view = finder.findRequiredView(source, 2131558544, "field 'rlDelete'");
    target.rlDelete = finder.castView(view, 2131558544, "field 'rlDelete'");
    view = finder.findRequiredView(source, 2131558581, "field 'rlRl'");
    target.rlRl = finder.castView(view, 2131558581, "field 'rlRl'");
    view = finder.findRequiredView(source, 2131558585, "field 'tvGjz'");
    target.tvGjz = finder.castView(view, 2131558585, "field 'tvGjz'");
  }

  @Override public void unbind(T target) {
    target.ivBack = null;
    target.tvTitle = null;
    target.rlGuanjianzi = null;
    target.rlDelete = null;
    target.rlRl = null;
    target.tvGjz = null;
  }
}
