// Generated code from Butter Knife. Do not modify!
package com.lvshandian.partylive.moudles.mine.my;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class Videotails$$ViewBinder<T extends com.lvshandian.partylive.moudles.mine.my.Videotails> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624569, "field 'imageBack'");
    target.imageBack = finder.castView(view, 2131624569, "field 'imageBack'");
    view = finder.findRequiredView(source, 2131624570, "field 'imageDelete'");
    target.imageDelete = finder.castView(view, 2131624570, "field 'imageDelete'");
    view = finder.findRequiredView(source, 2131624573, "field 'etComment'");
    target.etComment = finder.castView(view, 2131624573, "field 'etComment'");
    view = finder.findRequiredView(source, 2131624565, "field 'rlPart'");
    target.rlPart = finder.castView(view, 2131624565, "field 'rlPart'");
    view = finder.findRequiredView(source, 2131624572, "field 'lv'");
    target.lv = finder.castView(view, 2131624572, "field 'lv'");
  }

  @Override public void unbind(T target) {
    target.imageBack = null;
    target.imageDelete = null;
    target.etComment = null;
    target.rlPart = null;
    target.lv = null;
  }
}
