package com.recyclerviewandroid;

import androidx.annotation.Nullable;
import android.content.Context;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.ContextThemeWrapper;

import android.view.MotionEvent;
import android.view.ViewGroup;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearSmoothScroller;

import com.facebook.react.bridge.ReactContext;
import com.facebook.react.common.SystemClock;
import com.facebook.react.uimanager.PixelUtil;
import com.facebook.react.uimanager.UIManagerModule;
import com.facebook.react.uimanager.events.ContentSizeChangeEvent;
import com.facebook.react.uimanager.events.NativeGestureUtil;
import com.facebook.react.views.scroll.OnScrollDispatchHelper;
import com.facebook.react.views.scroll.ScrollEvent;
import com.facebook.react.views.scroll.ScrollEventType;
import com.recyclerviewandroid.libs.events.VisibleItemsChangeEvent;
import com.recyclerviewandroid.main.GalleryListRecylerviewDataAdaptor;

public class RecyclerviewAndroidView extends RecyclerView {

  private final OnScrollDispatchHelper mOnScrollDispatchHelper = new OnScrollDispatchHelper();

  private boolean mDragging;
  private int mFirstVisibleIndex, mLastVisibleIndex;
  static class ScrollOptions {
    @Nullable Float millisecondsPerInch;
    @Nullable Float viewPosition;
    @Nullable Float viewOffset;
  }



  public RecyclerviewAndroidView(Context context, @Nullable AttributeSet attrs) {
    super(new ContextThemeWrapper(context, R.style.ScrollbarRecyclerView), attrs);
    ((DefaultItemAnimator) getItemAnimator()).setSupportsChangeAnimations(false);
    GridLayoutManager layoutManager = new GridLayoutManager(context, 4);
    layoutManager.setSmoothScrollbarEnabled(true);
    //this.setScrollbarFadingEnabled(false);
    //this.setHasFixedSize(true);
    setLayoutManager(layoutManager);
    layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
      @Override
      public int getSpanSize(int position) {
        int viewType = getAdapter().getItemViewType(position);
        return (viewType == GalleryListRecylerviewDataAdaptor.VIEWTYPE_TOPHEADER_FULL || viewType == GalleryListRecylerviewDataAdaptor.VIEWTYPE_TOPHEADER || viewType == GalleryListRecylerviewDataAdaptor.VIEWTYPE_HEADER) ? 4 : 1;
      }
    });

    RecyclerView.LayoutParams params = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
      ViewGroup.LayoutParams.WRAP_CONTENT);
    this.setLayoutParams(params);
  }

  public RecyclerviewAndroidView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context,attrs,defStyleAttr);
    ((DefaultItemAnimator)getItemAnimator()).setSupportsChangeAnimations(false);
    GridLayoutManager layoutManager = new GridLayoutManager(context,4);
    layoutManager.setSmoothScrollbarEnabled(true);
    this.setScrollbarFadingEnabled(false);
    this.setHasFixedSize(true);
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
  }


  public RecyclerviewAndroidView(Context context) {
    super(new ContextThemeWrapper(context, R.style.ScrollbarRecyclerView));
    //setHasFixedSize(true);
    ((DefaultItemAnimator)getItemAnimator()).setSupportsChangeAnimations(false);
    GridLayoutManager layoutManager = new GridLayoutManager(context,4,RecyclerView.VERTICAL,false);
    setLayoutManager(layoutManager);
    layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup(){
      @Override
      public int getSpanSize(int position) {
        return  getAdapter().getItemViewType(position)== GalleryListRecylerviewDataAdaptor.VIEWTYPE_HEADER ? 4: 1;
      }
    });

    RecyclerView.LayoutParams params = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
      ViewGroup.LayoutParams.WRAP_CONTENT);
    this.setLayoutParams(params);
    //setAdapter(new RecyclerviewAndroidView.ReactListAdapter(this));
  }

  private ReactContext getReactContext() {
    return (ReactContext) ((ContextThemeWrapper) getContext()).getBaseContext();
  }




  protected void _x_onSizeChanged(int w, int h, int oldw, int oldh) {
    super.onSizeChanged(w,h,oldw,oldh);
    getReactContext().getNativeModule(UIManagerModule.class).getEventDispatcher()
      .dispatchEvent(new ContentSizeChangeEvent(getId(),w,h));
  }


  public boolean _x_onInterceptTouchEvent(MotionEvent ev) {
    if (super.onInterceptTouchEvent(ev)) {
      NativeGestureUtil.notifyNativeGestureStarted(this, ev);
      mDragging = true;
      getReactContext().getNativeModule(UIManagerModule.class).getEventDispatcher()
        .dispatchEvent(ScrollEvent.obtain(
          getId(),
          ScrollEventType.BEGIN_DRAG,
          0, /* offsetX = 0, horizontal scrolling only */
          computeVerticalScrollOffset(),
          0, // xVelocity
          0, // yVelocity
          getWidth(),
          computeVerticalScrollRange(),
          getWidth(),
          getHeight()));
      return true;
    }

    return false;
  }



  public boolean _x_onTouchEvent(MotionEvent ev) {
    int action = ev.getAction() & MotionEvent.ACTION_MASK;
    if (action == MotionEvent.ACTION_UP && mDragging) {
      mDragging = false;
//            mVelocityHelper.calculateVelocity(ev);
      getReactContext().getNativeModule(UIManagerModule.class).getEventDispatcher()
        .dispatchEvent(ScrollEvent.obtain(
          getId(),
          ScrollEventType.END_DRAG,
          0, /* offsetX = 0, horizontal scrolling only */
          computeVerticalScrollOffset(),
          ev.getX(),
          ev.getY(),
          getWidth(),
          computeVerticalScrollRange(),
          getWidth(),
          getHeight()));
    }
    return super.onTouchEvent(ev);
  }



  protected void _x_onScrollChanged(int l, int t, int oldl, int oldt) {
    super.onScrollChanged(l, t, oldl, oldt);
    if (mOnScrollDispatchHelper.onScrollChanged(l, t)) {
      getReactContext().getNativeModule(UIManagerModule.class).getEventDispatcher()
        .dispatchEvent(ScrollEvent.obtain(
          getId(),
          ScrollEventType.SCROLL,
          0, /* offsetX = 0, horizontal scrolling only */
          computeVerticalScrollOffset(),
          mOnScrollDispatchHelper.getXFlingVelocity(),
          mOnScrollDispatchHelper.getYFlingVelocity(),
          getWidth(),
          computeVerticalScrollRange(),
          getWidth(),
          getHeight()));
    }

    final int firstIndex = ((LinearLayoutManager) getLayoutManager()).findFirstVisibleItemPosition();
    final int lastIndex = ((LinearLayoutManager) getLayoutManager()).findLastVisibleItemPosition();

    if (firstIndex != mFirstVisibleIndex || lastIndex != mLastVisibleIndex) {

      getReactContext().getNativeModule(UIManagerModule.class).getEventDispatcher()
        .dispatchEvent(new VisibleItemsChangeEvent(
          getId(),
          SystemClock.nanoTime(),
          firstIndex,
          lastIndex));

      mFirstVisibleIndex = firstIndex;
      mLastVisibleIndex = lastIndex;
    }
  }



  public void _x_smoothScrollToPosition(int position) {
    this.smoothScrollToPosition(position, new ScrollOptions());
  }

  public void smoothScrollToPosition(int position, final ScrollOptions options) {
    final RecyclerView.SmoothScroller smoothScroller = new LinearSmoothScroller(this.getContext()) {
      @Override
      protected int getVerticalSnapPreference() {
        return LinearSmoothScroller.SNAP_TO_START;
      }

      @Override
      public PointF computeScrollVectorForPosition(int targetPosition) {
        return ((LinearLayoutManager) this.getLayoutManager()).computeScrollVectorForPosition(targetPosition);
      }

      @Override
      protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
        if (options.millisecondsPerInch != null) {
          return options.millisecondsPerInch / displayMetrics.densityDpi;
        } else {
          return super.calculateSpeedPerPixel(displayMetrics);
        }
      }

      @Override
      public int calculateDtToFit(int viewStart, int viewEnd, int boxStart, int boxEnd, int snapPreference) {
        int calc = super.calculateDtToFit(viewStart, viewEnd, boxStart, boxEnd, snapPreference);
        if (options.viewPosition != null) {
          int viewHeight = viewEnd - viewStart;
          int boxHeight = boxEnd - boxStart;
          float viewOffset = options.viewOffset != null ? PixelUtil.toPixelFromDIP(options.viewOffset) : 0;
          float target = boxStart + (boxHeight - viewHeight) * options.viewPosition + viewOffset;
          return (int) (target - viewStart);
        } else {
          return super.calculateDtToFit(viewStart, viewEnd, boxStart, boxEnd, snapPreference);
        }
      }
    };

    smoothScroller.setTargetPosition(position);
    this.getLayoutManager().startSmoothScroll(smoothScroller);
  }
}
