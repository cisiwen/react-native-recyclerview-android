package com.recyclerviewandroid.libs.events;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.Event;

public class OnPressEvent extends Event<OnPressEvent> {

  public static final String EVENT_NAME = "topItemPressed";

  public OnPressEvent(int viewTag) {
    super(viewTag);
  }

  @Override
  public WritableMap getEventData() {
    WritableMap event = Arguments.createMap();
    //event.putDouble("progress", Double.isNaN(progress) ? 0.0 : progress);
    //event.putInt("loaded", loaded);
    //event.putInt("total", total);
    event.putString("message","Pressed");
    return event;
  }
  @Override
  public String getEventName() {
    return EVENT_NAME;
  }
}
