package com.recyclerviewandroid;

import androidx.annotation.Nullable;
import android.content.Context;
import android.util.AttributeSet;
import android.view.ContextThemeWrapper;

import android.view.ViewGroup;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerviewAndroidView extends RecyclerView {

  public RecyclerviewAndroidView(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
  }

  public RecyclerviewAndroidView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }


  public RecyclerviewAndroidView(Context context) {
    super(new ContextThemeWrapper(context, R.style.ScrollbarRecyclerView));
    //setHasFixedSize(true);
    ((DefaultItemAnimator)getItemAnimator()).setSupportsChangeAnimations(false);

    GridLayoutManager layoutManager = new GridLayoutManager(context,4);
    setLayoutManager(layoutManager);
    layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup(){
      @Override
      public int getSpanSize(int position) {
        return  getAdapter().getItemViewType(position)==1 ? 4: 1;
      }
    });
    RecyclerView.LayoutParams params = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
      ViewGroup.LayoutParams.WRAP_CONTENT);
    this.setLayoutParams(params);
    //setAdapter(new RecyclerviewAndroidView.ReactListAdapter(this));
  }
}
