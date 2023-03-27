package com.recyclerviewandroid;

import androidx.annotation.NonNull;

import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.annotations.ReactProp;

public class RecyclerviewAndroidViewItemViewManager extends ViewGroupManager<RecyclerviewAndroidViewItemView> {
  private static final String REACT_CLASS = "RecyclerviewAndroidViewItemView";

  @NonNull
  @Override
  public String getName() {
    return REACT_CLASS;
  }

  @NonNull
  @Override
  protected RecyclerviewAndroidViewItemView createViewInstance(@NonNull ThemedReactContext reactContext) {
    return new RecyclerviewAndroidViewItemView(reactContext);
  }

  @ReactProp(name = "itemIndex")
  public void setItemIndex(RecyclerviewAndroidViewItemView view, int itemIndex) {
    view.setItemIndex(itemIndex);
  }
}
