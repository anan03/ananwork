// Generated code from Butter Knife. Do not modify!
package com.lvshandian.partylive.moudles.index.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class SearchActivity$$ViewBinder<T extends com.lvshandian.partylive.moudles.index.activity.SearchActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624218, "field 'ivPrivateChatBack'");
    target.ivPrivateChatBack = finder.castView(view, 2131624218, "field 'ivPrivateChatBack'");
    view = finder.findRequiredView(source, 2131624219, "field 'etSearchInput'");
    target.etSearchInput = finder.castView(view, 2131624219, "field 'etSearchInput'");
    view = finder.findRequiredView(source, 2131624220, "field 'tvSearchBtn'");
    target.tvSearchBtn = finder.castView(view, 2131624220, "field 'tvSearchBtn'");
    view = finder.findRequiredView(source, 2131624217, "field 'llTitle'");
    target.llTitle = finder.castView(view, 2131624217, "field 'llTitle'");
    view = finder.findRequiredView(source, 2131624221, "field 'lvSearch'");
    target.lvSearch = finder.castView(view, 2131624221, "field 'lvSearch'");
  }

  @Override public void unbind(T target) {
    target.ivPrivateChatBack = null;
    target.etSearchInput = null;
    target.tvSearchBtn = null;
    target.llTitle = null;
    target.lvSearch = null;
  }
}
