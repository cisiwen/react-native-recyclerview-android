package com.recyclerviewandroid.libs.events;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.Event;
import com.google.gson.Gson;
import com.recyclerviewandroid.libs.domain.Asset;

public class LongPressEvent extends Event<LongPressEvent> {

  public static final String EVENT_NAME = "topLongPressed";

  private Asset asset;
  public LongPressEvent(int viewTag, Asset asset) {
    super(viewTag);
    this.asset = asset;
  }

  @Override
  public WritableMap getEventData() {
    WritableMap event = Arguments.createMap();
    //event.putDouble("progress", Double.isNaN(progress) ? 0.0 : progress);
    //event.putInt("loaded", loaded);
    //event.putInt("total", total);
    String  data = new Gson().toJson(asset);
    event.putString("data",data);
    return event;
  }
  @Override
  public String getEventName() {
    return EVENT_NAME;
  }
}
