package com.recyclerviewandroid;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;

public class RecyclerviewAndroidViewModule extends ReactContextBaseJavaModule {

  public RecyclerviewAndroidViewModule(ReactApplicationContext reactContext) {
    super(reactContext);
  }
  @NonNull
  @Override
  public String getName() {
    return "GalleryListView";
  }
}
