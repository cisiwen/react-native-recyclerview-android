package com.recyclerviewandroid;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class SwipeRefreshControl extends SwipeRefreshLayout {
  public SwipeRefreshControl(@NonNull Context context) {
    super(context);
  }

  public SwipeRefreshControl(@NonNull Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
  }
}
