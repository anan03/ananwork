// Generated code from Butter Knife. Do not modify!
package com.lvshandian.asktoask.module.answer.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class ApproveStudentZhengActivity$$ViewBinder<T extends com.lvshandian.asktoask.module.answer.activity.ApproveStudentZhengActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558541, "field 'ivAnswerActivityBack'");
    target.ivAnswerActivityBack = finder.castView(view, 2131558541, "field 'ivAnswerActivityBack'");
    view = finder.findRequiredView(source, 2131558607, "field 'tvAddStudentZheng'");
    target.tvAddStudentZheng = finder.castView(view, 2131558607, "field 'tvAddStudentZheng'");
    view = finder.findRequiredView(source, 2131558593, "field 'tvNextStep'");
    target.tvNextStep = finder.castView(view, 2131558593, "field 'tvNextStep'");
    view = finder.findRequiredView(source, 2131558540, "field 'llRootPopuwindws'");
    target.llRootPopuwindws = finder.castView(view, 2131558540, "field 'llRootPopuwindws'");
    view = finder.findRequiredView(source, 2131558608, "field 'tvTakephptoStudentZheng'");
    target.tvTakephptoStudentZheng = finder.castView(view, 2131558608, "field 'tvTakephptoStudentZheng'");
  }

  @Override public void unbind(T target) {
    target.ivAnswerActivityBack = null;
    target.tvAddStudentZheng = null;
    target.tvNextStep = null;
    target.llRootPopuwindws = null;
    target.tvTakephptoStudentZheng = null;
  }
}
