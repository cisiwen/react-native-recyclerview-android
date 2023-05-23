package com.recyclerviewandroid;

import androidx.annotation.Nullable;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.ContextThemeWrapper;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.common.SystemClock;
import com.facebook.react.uimanager.PixelUtil;
import com.facebook.react.uimanager.UIManagerModule;
import com.facebook.react.uimanager.events.Event;
import com.facebook.react.uimanager.events.NativeGestureUtil;
import com.facebook.react.uimanager.events.RCTEventEmitter;
import com.facebook.react.views.scroll.OnScrollDispatchHelper;
import com.facebook.react.views.scroll.ScrollEvent;
import com.facebook.react.views.scroll.ScrollEventType;

import java.util.ArrayList;
import java.util.List;

public class RecyclerviewAndroidView extends RecyclerView {

  private final OnScrollDispatchHelper mOnScrollDispatchHelper = new OnScrollDispatchHelper();

  static class ScrollOptions {
    @Nullable Float millisecondsPerInch;
    @Nullable Float viewPosition;
    @Nullable Float viewOffset;
  }

  private static class ConcreteViewHolder extends ViewHolder {
    public ConcreteViewHolder(View itemView) {
      super(itemView);
    }
  }


  private boolean mDragging;
  private int mFirstVisibleIndex, mLastVisibleIndex;

  private ReactContext getReactContext() {
    return (ReactContext) ((ContextThemeWrapper) getContext()).getBaseContext();
  }


  protected void XXXonScrollChanged(int l, int t, int oldl, int oldt) {
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


  int getItemCount() {

    return getAdapter().getItemCount();
  }



  public boolean XXXXonInterceptTouchEvent(MotionEvent ev) {
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


  public boolean XXonTouchEvent(MotionEvent ev) {
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

  private boolean mRequestedLayout = false;


  public void XXXXrequestLayout() {
    super.requestLayout();

    if (!mRequestedLayout) {
      mRequestedLayout = true;
      this.post(new Runnable() {
        @SuppressLint("WrongCall")
        @Override
        public void run() {
          mRequestedLayout = false;
          layout(getLeft(), getTop(), getRight(), getBottom());
          onLayout(false, getLeft(), getTop(), getRight(), getBottom());
        }
      });
    }
  }


  public void xxxxsmoothScrollToPosition(int position) {
    this.smoothScrollToPosition(position, new RecyclerviewAndroidView.ScrollOptions());
  }

  public void smoothScrollToPosition(int position, final RecyclerviewAndroidView.ScrollOptions options) {
    final SmoothScroller smoothScroller = new LinearSmoothScroller(this.getContext()) {
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

  public void setInverted(boolean inverted) {
    LinearLayoutManager layoutManager = (LinearLayoutManager) getLayoutManager();
    assert layoutManager != null;
    layoutManager.setReverseLayout(inverted);
  }

  public void setItemAnimatorEnabled(boolean enabled) {
    if (enabled) {
      DefaultItemAnimator animator = new DefaultItemAnimator();
      animator.setSupportsChangeAnimations(false);
      setItemAnimator(animator);
    } else {
      setItemAnimator(null);
    }
  }

  public void setColumn(int column){
    if (column <= 1) return;
    GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),column);
    setLayoutManager(gridLayoutManager);
  }


  public class VisibleItemsChangeEvent extends Event<RecyclerviewAndroidView.VisibleItemsChangeEvent> {

    public static final String EVENT_NAME = "visibleItemsChange";

    private final int mFirstIndex;
    private final int mLastIndex;

    public VisibleItemsChangeEvent(int viewTag, long timestampMs, int firstIndex, int lastIndex) {
      super(viewTag);
      mFirstIndex = firstIndex;
      mLastIndex = lastIndex;
    }

    @Override
    public String getEventName() {
      return EVENT_NAME;
    }

    @Override
    public void dispatch(RCTEventEmitter rctEventEmitter) {
      WritableMap data = Arguments.createMap();
      data.putInt("firstIndex", mFirstIndex);
      data.putInt("lastIndex", mLastIndex);
      //rctEventEmitter.receiveEvent(getViewTag(), EVENT_NAME, data);
    }
  }
}
