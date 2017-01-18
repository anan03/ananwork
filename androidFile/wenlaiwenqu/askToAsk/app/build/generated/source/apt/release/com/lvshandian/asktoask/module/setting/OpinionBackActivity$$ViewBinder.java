// Generated code from Butter Knife. Do not modify!
package com.lvshandian.asktoask.module.setting;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class OpinionBackActivity$$ViewBinder<T extends com.lvshandian.asktoask.module.setting.OpinionBackActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558829, "field 'etyijianfankuiyouxiangdizhi'");
    target.etyijianfankuiyouxiangdizhi = finder.castView(view, 2131558829, "field 'etyijianfankuiyouxiangdizhi'");
    view = finder.findRequiredView(source, 2131558635, "field 'ivHudongDetailBack'");
    target.ivHudongDetailBack = finder.castView(view, 2131558635, "field 'ivHudongDetailBack'");
    view = finder.findRequiredView(source, 2131558827, "field 'tvSubmit'");
    target.tvSubmit = finder.castView(view, 2131558827, "field 'tvSubmit'");
    view = finder.findRequiredView(source, 2131558529, "field 'tvHudongDetailType'");
    target.tvHudongDetailType = finder.castView(view, 2131558529, "field 'tvHudongDetailType'");
    view = finder.findRequiredView(source, 2131558651, "field 'rlHeadTitle'");
    target.rlHeadTitle = finder.castView(view, 2131558651, "field 'rlHeadTitle'");
    view = finder.findRequiredView(source, 2131558828, "field 'etYijianfankuiContent'");
    target.etYijianfankuiContent = finder.castView(view, 2131558828, "field 'etYijianfankuiContent'");
  }

  @Override public void unbind(T target) {
    target.etyijianfankuiyouxiangdizhi = null;
    target.ivHudongDetailBack = null;
    target.tvSubmit = null;
    target.tvHudongDetailType = null;
    target.rlHeadTitle = null;
    target.etYijianfankuiContent = null;
  }
}
