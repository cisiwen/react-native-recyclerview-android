package com.recyclerviewandroid;

import android.graphics.Color;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.facebook.infer.annotation.Assertions;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.uimanager.events.ContentSizeChangeEvent;
import com.facebook.react.views.scroll.ScrollEventType;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.recyclerviewandroid.libs.domain.Media;
import com.recyclerviewandroid.main.HomePage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;
import java.util.Map;

@ReactModule(name = RecyclerviewAndroidViewManager.NAME)
public class RecyclerviewAndroidViewManager extends com.recyclerviewandroid.RecyclerviewAndroidViewManagerSpec<RecyclerviewAndroidView> {

  public static final String NAME = "RecyclerviewAndroidView";

  public static final String COMMAND_NOTIFY_ITEM_RANGE_INSERTED = "inserted";
  public static final String COMMAND_NOTIFY_ITEM_RANGE_REMOVED = "removed";
  public static final String COMMAND_NOTIFY_DATASET_CHANGED = "changed";
  public static final String COMMAND_SCROLL_TO_INDEX = "toIndex";
  public static final String COMMAND_NOTIFY_ITEM_MOVED = "moved";
  private Object one;
  private ThemedReactContext context;

  @Override
  public String getName() {
    return NAME;
  }

  @Override
  public RecyclerviewAndroidView createViewInstance(ThemedReactContext context) {
    this.context = context;
    return new RecyclerviewAndroidView(context);
  }

  @Override
  @ReactProp(name = "color")
  public void setColor(RecyclerviewAndroidView view, @Nullable String color) {
    view.setBackgroundColor(Color.parseColor(color));
  }


  @Override
  @ReactProp(name = "dataSource")
  public void setDataSource(RecyclerviewAndroidView view, @Nullable ReadableArray dataSource) {
    Date now = new Date();
    Long tick  = now.getTime();
    Object one = dataSource.toArrayList().get(0);
    Log.i("setDataSource",String.format("%s-%s",tick,one.toString()));
    new HomePage(view,this.context,this.context.getCurrentActivity()).loadGallery();
  }

  @Override
  @ReactProp(name = "dataSourceString")
  public  void setDataSourceString(RecyclerviewAndroidView view, @Nullable String dataSource) {
    Type listmedia = new TypeToken<List<Media>>(){}.getType();
    List<Media> sources= new Gson().fromJson(dataSource,listmedia);
    Date now = new Date();
    Long tick  = now.getTime();
    //ObjectMapper mapper = new ObjectMapper();

    Log.i("setDataSourceString",String.format("%s-%s",tick,sources.size()));
    new HomePage(view,this.context,this.context.getCurrentActivity()).loadGallery();

  }


  @Override
  public void addView(RecyclerviewAndroidView parent, View child, int index) {
    Assertions.assertCondition(child instanceof RecyclerviewAndroidViewItemView, "Views attached to RNRecycleview must be RNRecycleviewItemview views.");
    RecyclerviewAndroidViewItemView item = (RecyclerviewAndroidViewItemView) child;
    parent.addViewToAdapter(item, index);
  }

  @Override
  public int getChildCount(RecyclerviewAndroidView parent) {
    return parent.getChildCountFromAdapter();
  }

  @Override
  public View getChildAt(RecyclerviewAndroidView parent, int index) {
    return parent.getChildAtFromAdapter(index);
  }

  @Override
  public void removeViewAt(RecyclerviewAndroidView parent, int index) {
    parent.removeViewFromAdapter(index);
  }

  @ReactProp(name = "itemCount")
  public void setItemCount(RecyclerviewAndroidView parent, int itemCount) {
    parent.setItemCount(itemCount);
    parent.getAdapter().notifyDataSetChanged();
  }

  @ReactProp(name = "inverted", defaultBoolean = false)
  public void setInverted(RecyclerviewAndroidView parent, boolean inverted) {
    parent.setInverted(inverted);
  }

  @ReactProp(name = "itemAnimatorEnabled", defaultBoolean = true)
  public void setItemAnimatorEnabled(RecyclerviewAndroidView parent, boolean enabled) {
    parent.setItemAnimatorEnabled(enabled);
  }

  @ReactProp(name = "column",defaultInt = 1)
  public void setColumn(RecyclerviewAndroidView parent, int column){
    parent.setColumn(column);
  }

  @Override
  public Map getCommandsMap() {
    return MapBuilder.of(
      "notifyItemRangeInserted", COMMAND_NOTIFY_ITEM_RANGE_INSERTED,
      "notifyItemRangeRemoved", COMMAND_NOTIFY_ITEM_RANGE_REMOVED,
      "notifyItemMoved", COMMAND_NOTIFY_ITEM_MOVED,
      "notifyDataSetChanged", COMMAND_NOTIFY_DATASET_CHANGED,
      "scrollToIndex", COMMAND_SCROLL_TO_INDEX
    );
  }

  @Override
  public void receiveCommand(final RecyclerviewAndroidView parent, String commandType, @javax.annotation.Nullable ReadableArray args) {

    Assertions.assertNotNull(parent);
    Assertions.assertNotNull(args);
    switch (commandType) {
      case COMMAND_NOTIFY_ITEM_RANGE_INSERTED: {
        final int position = args.getInt(0);
        final int count = args.getInt(1);
        //Log.d(TAG, String.format("notify item range inserted: position %d, count %d", position, count));

        RecyclerviewAndroidView.ReactListAdapter adapter = (RecyclerviewAndroidView.ReactListAdapter) parent.getAdapter();
        adapter.setItemCount(adapter.getItemCount() + count);
        adapter.notifyItemRangeInserted(position, count);
        return;
      }

      case COMMAND_NOTIFY_ITEM_RANGE_REMOVED: {
        final int position = args.getInt(0);
        final int count = args.getInt(1);
        //Log.d(TAG, String.format("notify item range removed: position %d, count %d", position, count));

        RecyclerviewAndroidView.ReactListAdapter adapter = (RecyclerviewAndroidView.ReactListAdapter) parent.getAdapter();
        adapter.setItemCount(adapter.getItemCount() - count);
        adapter.notifyItemRangeRemoved(position, count);
        return;
      }


      case COMMAND_NOTIFY_ITEM_MOVED: {
        final int currentPosition = args.getInt(0);
        final int nextPosition = args.getInt(1);
        RecyclerviewAndroidView.ReactListAdapter adapter = (RecyclerviewAndroidView.ReactListAdapter) parent.getAdapter();
        adapter.notifyItemMoved(currentPosition, nextPosition);
        return;
      }

      case COMMAND_NOTIFY_DATASET_CHANGED: {
        final int itemCount = args.getInt(0);
        RecyclerviewAndroidView.ReactListAdapter adapter = (RecyclerviewAndroidView.ReactListAdapter) parent.getAdapter();
        adapter.setItemCount(itemCount);
        parent.getAdapter().notifyDataSetChanged();
        return;
      }

      case COMMAND_SCROLL_TO_INDEX: {
        boolean animated = args.getBoolean(0);
        int index = args.getInt(1);
        RecyclerviewAndroidView.ScrollOptions options = new RecyclerviewAndroidView.ScrollOptions();
        options.millisecondsPerInch = args.isNull(2) ? null : (float) args.getDouble(2);
        options.viewPosition = args.isNull(3) ? null : (float) args.getDouble(3);
        options.viewOffset = args.isNull(4) ? null : (float) args.getDouble(4);

        if (animated) {
          parent.smoothScrollToPosition(index, options);
        } else {
          parent.scrollToPosition(index, options);
        }
        return;
      }

      default:
        throw new IllegalArgumentException(String.format(
          "Unsupported command %d received by %s.",
          commandType,
          getClass().getSimpleName()));
    }
  }

  @Override
  public
  @javax.annotation.Nullable
  Map getExportedCustomDirectEventTypeConstants() {
    return MapBuilder.builder()
      .put(ScrollEventType.getJSEventName(ScrollEventType.BEGIN_DRAG), MapBuilder.of("registrationName", "onScrollBeginDrag"))
      .put(ScrollEventType.getJSEventName(ScrollEventType.SCROLL), MapBuilder.of("registrationName", "onScroll"))
      .put(ScrollEventType.getJSEventName(ScrollEventType.END_DRAG), MapBuilder.of("registrationName", "onScrollEndDrag"))
      .put(ContentSizeChangeEvent.EVENT_NAME, MapBuilder.of("registrationName", "onContentSizeChange"))
      .put(RecyclerviewAndroidView.VisibleItemsChangeEvent.EVENT_NAME, MapBuilder.of("registrationName", "onVisibleItemsChange"))
      .build();
  }
}
