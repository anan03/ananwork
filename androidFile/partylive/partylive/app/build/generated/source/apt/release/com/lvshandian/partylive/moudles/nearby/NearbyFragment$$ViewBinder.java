// Generated code from Butter Knife. Do not modify!
package com.lvshandian.partylive.moudles.nearby;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class NearbyFragment$$ViewBinder<T extends com.lvshandian.partylive.moudles.nearby.NearbyFragment> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131624446, "field 'nearbyRecycler'");
    target.nearbyRecycler = finder.castView(view, 2131624446, "field 'nearbyRecycler'");
  }

  @Override public void unbind(T target) {
    target.nearbyRecycler = null;
  }
}
