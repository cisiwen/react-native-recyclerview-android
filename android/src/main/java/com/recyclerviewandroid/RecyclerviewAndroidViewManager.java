package com.recyclerviewandroid;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.uimanager.events.ContentSizeChangeEvent;
import com.facebook.react.views.scroll.ScrollEventType;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.recyclerviewandroid.libs.domain.SectionHeaderStyle;
import com.recyclerviewandroid.libs.events.EventDispatcher;
import com.recyclerviewandroid.libs.events.LongPressEvent;
import com.recyclerviewandroid.libs.events.OnItemSelectStateChangedEvent;
import com.recyclerviewandroid.libs.events.OnPressEvent;
import com.recyclerviewandroid.libs.events.OnRefreshingEvent;
import com.recyclerviewandroid.libs.events.VisibleItemsChangeEvent;
import com.recyclerviewandroid.libs.javascript.ReactRecyclerProps;
import com.recyclerviewandroid.libs.javascript.ReactSectionDataSource;
import com.recyclerviewandroid.main.HomePage;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;
import java.util.Map;

@ReactModule(name = RecyclerviewAndroidViewManager.NAME)
public class RecyclerviewAndroidViewManager extends RecyclerviewAndroidViewManagerSpec<SwipeRefreshLayout> {

  public static final String NAME = "GalleryListView";

  public static final String CMD_TOGGLE_SELECTION_MODE = "toggleSelectionMode";
  public  static final String CMD_ONREFRESHING_END="onRefreshEnd";

  public  static final String CMD_UPDATE_DATA="updateDataSource";

  private SectionHeaderStyle style;

  private Map<String, String> httpHeaders;
  private ThemedReactContext context;

  private HomePage homePage;

  private ReactRecyclerProps props;
  @Override
  public String getName() {
    return NAME;
  }

  private RecyclerviewAndroidView verticalRecyclerView;


  private  SwipeRefreshLayout  swipeRefreshLayout;

  public SwipeRefreshLayout.OnRefreshListener refreshListener = new SwipeRefreshLayout.OnRefreshListener() {

    @Override
    public void onRefresh() {
      swipeRefreshLayout.setRefreshing(true);
      EventDispatcher.sendOnRefreshingEvent(context,swipeRefreshLayout.getId());
    }
  };
  @Override
  public SwipeRefreshLayout createViewInstance(ThemedReactContext context) {
    this.context = context;
    this.swipeRefreshLayout = (SwipeRefreshLayout) LayoutInflater.from(context).inflate(R.layout.recyclerviewandroidview,null);
    this.swipeRefreshLayout.setOnRefreshListener(refreshListener);
    verticalRecyclerView = this.swipeRefreshLayout.findViewById(R.id.vertical_recyclerview);
    return this.swipeRefreshLayout;
  }



  @ReactProp(name = "color")
  public void setColor(SwipeRefreshLayout view, @Nullable String color) {
    //view.setBackgroundColor(Color.parseColor(color));
  }


  @ReactProp(name = "dataSource")
  public void setDataSource(SwipeRefreshLayout view, @Nullable ReadableArray dataSource) {
    Date now = new Date();
    Long tick = now.getTime();
    Object one = dataSource.toArrayList().get(0);
    Log.i("setDataSource", String.format("%s-%s", tick, one.toString()));
    homePage = new HomePage(this.verticalRecyclerView,this.swipeRefreshLayout, this.context, this.context.getCurrentActivity());
    homePage.loadGallery(style,httpHeaders);
  }


  @ReactProp(name = "dataSourceString")
  public void setDataSourceString(SwipeRefreshLayout view, @Nullable String dataSource) {
    Type listmedia = new TypeToken<List<ReactSectionDataSource>>() {
    }.getType();
    List<ReactSectionDataSource> sources = new Gson().fromJson(dataSource, listmedia);
    Date now = new Date();
    Long tick = now.getTime();
    //ObjectMapper mapper = new ObjectMapper();

    Log.i("setDataSourceString", String.format("%s-%s", tick, sources.size()));
    homePage = new HomePage(this.verticalRecyclerView,this.swipeRefreshLayout, this.context, this.context.getCurrentActivity());
    homePage.setDataSourceMedia(sources, style,httpHeaders,null);
  }


  @ReactProp(name = "httpHeadersString")
  public void setHttpHeaders(SwipeRefreshLayout view, @Nullable String httpHeaders) {
    if (httpHeaders != null) {
      Type headerType = new TypeToken<Map<String,String>>(){}.getType();
      this.httpHeaders = new Gson().fromJson(httpHeaders,headerType);
    }
  }



  @ReactProp(name = "recyclerPropString")
  public void setReactRecyclerProps(SwipeRefreshLayout view, @Nullable String recyclerProps) {
    Type props = new TypeToken<ReactRecyclerProps>() {
    }.getType();
    this.props = new Gson().fromJson(recyclerProps, props);
    homePage = new HomePage(this.verticalRecyclerView,this.swipeRefreshLayout, this.context, this.context.getCurrentActivity());
    homePage.setDataSourceMedia(this.props);
    //homePage.loadGallery(style,httpHeaders);
  }


  @ReactProp(name = "sectionHeaderStyle")
  public void setSectionHeaderStyle(SwipeRefreshLayout view, @Nullable String headerStyle) {
    Log.i("setSectionHeaderStyle", headerStyle);
    Type sectionHeaderStyle = new TypeToken<SectionHeaderStyle>() {
    }.getType();
    style = new Gson().fromJson(headerStyle, sectionHeaderStyle);
  }

  @Override
  public Map getExportedCustomDirectEventTypeConstants() {
    return MapBuilder.builder().put(LongPressEvent.EVENT_NAME, MapBuilder.of("registrationName", "onLongPressed"))
      .put(OnPressEvent.EVENT_NAME, MapBuilder.of("registrationName", "onItemPressed"))
      .put(OnItemSelectStateChangedEvent.EVENT_NAME, MapBuilder.of("registrationName", "OnItemSelectStateChanged"))
      .put(ScrollEventType.getJSEventName(ScrollEventType.BEGIN_DRAG), MapBuilder.of("registrationName", "onScrollBeginDrag"))
      .put(ScrollEventType.getJSEventName(ScrollEventType.SCROLL), MapBuilder.of("registrationName", "onScroll"))
      .put(ScrollEventType.getJSEventName(ScrollEventType.END_DRAG), MapBuilder.of("registrationName", "onScrollEndDrag"))
      .put(VisibleItemsChangeEvent.EVENT_NAME, MapBuilder.of("registrationName", "onVisibleItemsChange"))
      .put(OnRefreshingEvent.EVENT_NAME,MapBuilder.of("registrationName", "OnRefreshing"))
      .put(ContentSizeChangeEvent.EVENT_NAME, MapBuilder.of("registrationName", "onContentSizeChange")).build();
  }

  @Override
  public Map getCommandsMap() {
    return MapBuilder.builder()
      .put("toggleSelectionMode", CMD_TOGGLE_SELECTION_MODE)
      .put("onRefreshEnd",CMD_ONREFRESHING_END)
      .put("updateDataSource",CMD_UPDATE_DATA)
      .build();
  }

  @Override
  public void receiveCommand(final SwipeRefreshLayout parent, String commandType, @Nullable ReadableArray args) {
    Log.i("receiveCommand", commandType);
    switch (commandType) {
      case CMD_TOGGLE_SELECTION_MODE:
        boolean selectMode = args.getBoolean(0);
        homePage.toggleSeledtionMode(selectMode);
        break;
      case CMD_ONREFRESHING_END:
        boolean ended=args.getBoolean(0);
        swipeRefreshLayout.setRefreshing(!ended);
        break;
      case CMD_UPDATE_DATA:
        String dataSource = args.getString(0);
        setReactRecyclerProps(swipeRefreshLayout,dataSource);
        swipeRefreshLayout.setRefreshing(false);
        break;
    }
  }
}
