// Generated code from Butter Knife. Do not modify!
package com.lvshandian.partylive.moudles.index;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class IndexPagerFragment$$ViewBinder<T extends com.lvshandian.partylive.moudles.index.IndexPagerFragment> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624418, "field 'ivHotSearch'");
    target.ivHotSearch = finder.castView(view, 2131624418, "field 'ivHotSearch'");
    view = finder.findRequiredView(source, 2131624419, "field 'tabs'");
    target.tabs = finder.castView(view, 2131624419, "field 'tabs'");
    view = finder.findRequiredView(source, 2131624420, "field 'ivHotPrivateChat'");
    target.ivHotPrivateChat = finder.castView(view, 2131624420, "field 'ivHotPrivateChat'");
    view = finder.findRequiredView(source, 2131624421, "field 'pager'");
    target.pager = finder.castView(view, 2131624421, "field 'pager'");
    view = finder.findRequiredView(source, 2131624261, "field 'tabNewIndicator'");
    target.tabNewIndicator = finder.castView(view, 2131624261, "field 'tabNewIndicator'");
    view = finder.findRequiredView(source, 2131624262, "field 'tabNewMsgLabel'");
    target.tabNewMsgLabel = finder.castView(view, 2131624262, "field 'tabNewMsgLabel'");
    view = finder.findRequiredView(source, 2131624422, "field 'unreadCover'");
    target.unreadCover = finder.castView(view, 2131624422, "field 'unreadCover'");
  }

  @Override public void unbind(T target) {
    target.ivHotSearch = null;
    target.tabs = null;
    target.ivHotPrivateChat = null;
    target.pager = null;
    target.tabNewIndicator = null;
    target.tabNewMsgLabel = null;
    target.unreadCover = null;
  }
}
