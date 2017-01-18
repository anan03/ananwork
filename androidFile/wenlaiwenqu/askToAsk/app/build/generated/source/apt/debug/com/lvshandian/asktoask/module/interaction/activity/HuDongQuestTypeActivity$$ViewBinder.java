// Generated code from Butter Knife. Do not modify!
package com.lvshandian.asktoask.module.interaction.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class HuDongQuestTypeActivity$$ViewBinder<T extends com.lvshandian.asktoask.module.interaction.activity.HuDongQuestTypeActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558660, "field 'tvHudongType'");
    target.tvHudongType = finder.castView(view, 2131558660, "field 'tvHudongType'");
    view = finder.findRequiredView(source, 2131558659, "field 'ivHudongQuestTypeback'");
    target.ivHudongQuestTypeback = finder.castView(view, 2131558659, "field 'ivHudongQuestTypeback'");
    view = finder.findRequiredView(source, 2131558662, "field 'frame'");
    target.frame = finder.castView(view, 2131558662, "field 'frame'");
    view = finder.findRequiredView(source, 2131558664, "field 'ckHudongCollectNumQuestType'");
    target.ckHudongCollectNumQuestType = finder.castView(view, 2131558664, "field 'ckHudongCollectNumQuestType'");
    view = finder.findRequiredView(source, 2131558667, "field 'ckHudongPraiseNumeQuestType'");
    target.ckHudongPraiseNumeQuestType = finder.castView(view, 2131558667, "field 'ckHudongPraiseNumeQuestType'");
    view = finder.findRequiredView(source, 2131558669, "field 'ivShareHudongQuestType'");
    target.ivShareHudongQuestType = finder.castView(view, 2131558669, "field 'ivShareHudongQuestType'");
    view = finder.findRequiredView(source, 2131558665, "field 'tvHudongCollectNumDetail'");
    target.tvHudongCollectNumDetail = finder.castView(view, 2131558665, "field 'tvHudongCollectNumDetail'");
    view = finder.findRequiredView(source, 2131558668, "field 'tvPraiseNumDetail'");
    target.tvPraiseNumDetail = finder.castView(view, 2131558668, "field 'tvPraiseNumDetail'");
    view = finder.findRequiredView(source, 2131558661, "field 'llHasdata'");
    target.llHasdata = finder.castView(view, 2131558661, "field 'llHasdata'");
    view = finder.findRequiredView(source, 2131558670, "field 'tvShowNodata'");
    target.tvShowNodata = finder.castView(view, 2131558670, "field 'tvShowNodata'");
  }

  @Override public void unbind(T target) {
    target.tvHudongType = null;
    target.ivHudongQuestTypeback = null;
    target.frame = null;
    target.ckHudongCollectNumQuestType = null;
    target.ckHudongPraiseNumeQuestType = null;
    target.ivShareHudongQuestType = null;
    target.tvHudongCollectNumDetail = null;
    target.tvPraiseNumDetail = null;
    target.llHasdata = null;
    target.tvShowNodata = null;
  }
}
