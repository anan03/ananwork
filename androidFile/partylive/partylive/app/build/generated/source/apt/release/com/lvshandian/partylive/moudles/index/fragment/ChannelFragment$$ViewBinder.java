// Generated code from Butter Knife. Do not modify!
package com.lvshandian.partylive.moudles.index.fragment;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class ChannelFragment$$ViewBinder<T extends com.lvshandian.partylive.moudles.index.fragment.ChannelFragment> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624447, "field 'nearbyRoomRecycler'");
    target.nearbyRoomRecycler = finder.castView(view, 2131624447, "field 'nearbyRoomRecycler'");
  }

  @Override public void unbind(T target) {
    target.nearbyRoomRecycler = null;
  }
}
