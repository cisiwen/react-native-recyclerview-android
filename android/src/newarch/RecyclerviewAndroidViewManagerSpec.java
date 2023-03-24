package com.recyclerviewandroid;

import android.view.View;

import androidx.annotation.Nullable;

import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ViewManagerDelegate;
import com.facebook.react.viewmanagers.RecyclerviewAndroidViewManagerDelegate;
import com.facebook.react.viewmanagers.RecyclerviewAndroidViewManagerInterface;
import com.facebook.soloader.SoLoader;

public abstract class RecyclerviewAndroidViewManagerSpec<T extends View> extends SimpleViewManager<T> implements RecyclerviewAndroidViewManagerInterface<T> {
  static {
    if (BuildConfig.CODEGEN_MODULE_REGISTRATION != null) {
      SoLoader.loadLibrary(BuildConfig.CODEGEN_MODULE_REGISTRATION);
    }
  }

  private final ViewManagerDelegate<T> mDelegate;

  public RecyclerviewAndroidViewManagerSpec() {
    mDelegate = new RecyclerviewAndroidViewManagerDelegate(this);
  }

  @Nullable
  @Override
  protected ViewManagerDelegate<T> getDelegate() {
    return mDelegate;
  }
}
