package com.recyclerviewandroid.libs.events;

import com.facebook.react.bridge.ReactContext;
import com.facebook.react.modules.core.DeviceEventManagerModule;

public class EventDispatcher {

  public interface OnItemLongPressListener {
    void onItemLongPressed(int position);
  }
  public  static String ON_ITEM_LONG_PRESS="ON_ITEM_LONG_PRESS";
  public  static void sendItemOnLongPress(ReactContext reactContext){
    reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit(ON_ITEM_LONG_PRESS,"TESTING");
  }
}
