package com.recyclerviewandroid;

import android.view.View;

import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.annotations.ReactProp;

import java.util.List;

public abstract class RecyclerviewAndroidViewManagerSpec<T extends View> extends ViewGroupManager {

  public abstract void setColor(T view, @Nullable String value);

  public abstract void setDataSource(T view, @Nullable ReadableArray dataSource);

  public abstract void setDataSourceString(T view, @Nullable String dataSource);

  public abstract void setSectionHeaderStyle(T view, @Nullable String headerStyle);

  public abstract void setHttpHeaders(T view, @Nullable String httpHeaders);

  public  abstract  void  setReactRecyclerProps(T view, @Nullable String recyclerProps);

  public abstract void receiveCommand(SwipeRefreshLayout parent, String commandType, @Nullable ReadableArray args);
}
