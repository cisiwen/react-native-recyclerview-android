package com.recyclerviewandroid.libs.events;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.Event;
import com.google.gson.Gson;

public class OnRefreshingEvent extends Event<OnRefreshingEvent> {


  public OnRefreshingEvent(int viewTag){
    super(viewTag);
  }

  @Override
  public WritableMap getEventData() {
    WritableMap event = Arguments.createMap();

    event.putBoolean("data",true);
    return event;
  }
  public static final String EVENT_NAME = "OnRefreshing";
  @Override
  public String getEventName() {
    return EVENT_NAME;
  }
}
