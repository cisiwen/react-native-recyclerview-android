package com.recyclerviewandroid;

import android.graphics.Color;

import androidx.annotation.Nullable;

import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;

@ReactModule(name = RecyclerviewAndroidViewManager.NAME)
public class RecyclerviewAndroidViewManager extends RecyclerviewAndroidViewManagerSpec<RecyclerviewAndroidView> {

  public static final String NAME = "RecyclerviewAndroidView";

  @Override
  public String getName() {
    return NAME;
  }

  @Override
  public RecyclerviewAndroidView createViewInstance(ThemedReactContext context) {
    return new RecyclerviewAndroidView(context);
  }

  @Override
  @ReactProp(name = "color")
  public void setColor(RecyclerviewAndroidView view, @Nullable String color) {
    view.setBackgroundColor(Color.parseColor(color));
  }
}
