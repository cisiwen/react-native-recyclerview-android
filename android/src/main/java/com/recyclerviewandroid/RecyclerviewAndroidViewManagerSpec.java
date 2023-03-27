package com.recyclerviewandroid;

import android.view.View;

import androidx.annotation.Nullable;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.annotations.ReactProp;

import java.util.List;

public abstract class RecyclerviewAndroidViewManagerSpec<T extends View> extends SimpleViewManager<T> {
  public abstract void setColor(T view, @Nullable String value);
  public abstract void setDataSource(T view, @Nullable ReadableArray dataSource);
  public abstract void setDataSourceString(RecyclerviewAndroidView view, @Nullable String dataSource);

  public abstract void addView(RecyclerviewAndroidView parent, View child, int index);

  public abstract int getChildCount(RecyclerviewAndroidView parent);

  public abstract View getChildAt(RecyclerviewAndroidView parent, int index);

  public abstract void removeViewAt(RecyclerviewAndroidView parent, int index);
}
