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
import com.recyclerviewandroid.libs.domain.SectionHeaderStyle;
import com.recyclerviewandroid.libs.events.LongPressEvent;
import com.recyclerviewandroid.libs.javascript.ReactSectionDataSource;
import com.recyclerviewandroid.main.HomePage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.security.PublicKey;
import java.util.Date;
import java.util.List;
import java.util.Map;

@ReactModule(name = RecyclerviewAndroidViewManager.NAME)
public class RecyclerviewAndroidViewManager extends com.recyclerviewandroid.RecyclerviewAndroidViewManagerSpec<RecyclerviewAndroidView> {

  public static final String NAME = "GalleryListView";

  public static final String COMMAND_NOTIFY_ITEM_RANGE_INSERTED = "inserted";
  public static final String COMMAND_NOTIFY_ITEM_RANGE_REMOVED = "removed";
  public static final String COMMAND_NOTIFY_DATASET_CHANGED = "changed";
  public static final String COMMAND_SCROLL_TO_INDEX = "toIndex";
  public static final String COMMAND_NOTIFY_ITEM_MOVED = "moved";
  private Object one;

  private SectionHeaderStyle style;
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
    Type listmedia = new TypeToken<List<ReactSectionDataSource>>(){}.getType();
    List<ReactSectionDataSource> sources= new Gson().fromJson(dataSource,listmedia);
    Date now = new Date();
    Long tick  = now.getTime();
    //ObjectMapper mapper = new ObjectMapper();

    Log.i("setDataSourceString",String.format("%s-%s",tick,sources.size()));
    new HomePage(view,this.context,this.context.getCurrentActivity()).setDataSourceMedia(sources,style);
  }

  @Override
  @ReactProp(name = "sectionHeaderStyle")
  public void setSectionHeaderStyle(RecyclerviewAndroidView view, @Nullable String headerStyle) {
    Log.i("setSectionHeaderStyle",headerStyle);
    Type sectionHeaderStyle = new TypeToken<SectionHeaderStyle>(){}.getType();
    style = new Gson().fromJson(headerStyle,sectionHeaderStyle);
  }

  @Override
  public  Map getExportedCustomDirectEventTypeConstants(){
    return MapBuilder.builder().put(
      LongPressEvent.EVENT_NAME, MapBuilder.of("registrationName","onLongPressed")
    ).build();
  }


}
