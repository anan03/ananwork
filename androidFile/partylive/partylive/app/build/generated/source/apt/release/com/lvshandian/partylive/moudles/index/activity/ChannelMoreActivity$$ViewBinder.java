// Generated code from Butter Knife. Do not modify!
package com.lvshandian.partylive.moudles.index.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class ChannelMoreActivity$$ViewBinder<T extends com.lvshandian.partylive.moudles.index.activity.ChannelMoreActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624132, "field 'titleBar'");
    target.titleBar = finder.castView(view, 2131624132, "field 'titleBar'");
    view = finder.findRequiredView(source, 2131624133, "field 'channelMoreRecycler'");
    target.channelMoreRecycler = finder.castView(view, 2131624133, "field 'channelMoreRecycler'");
    view = finder.findRequiredView(source, 2131624131, "field 'activityChannelMore'");
    target.activityChannelMore = finder.castView(view, 2131624131, "field 'activityChannelMore'");
  }

  @Override public void unbind(T target) {
    target.titleBar = null;
    target.channelMoreRecycler = null;
    target.activityChannelMore = null;
  }
}
