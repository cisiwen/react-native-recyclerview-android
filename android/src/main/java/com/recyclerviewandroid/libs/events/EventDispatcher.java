package com.recyclerviewandroid.libs.events;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.facebook.react.uimanager.UIManagerHelper;
import com.facebook.react.uimanager.events.Event;
import com.facebook.react.uimanager.events.RCTEventEmitter;
import com.facebook.react.uimanager.events.RCTModernEventEmitter;
import com.recyclerviewandroid.libs.domain.Asset;

public class EventDispatcher {

  public interface OnItemLongPressListener {
    void onItemLongPressed(Asset asset);
  }

  public static void sendItemOnLongPress(ReactContext reactContext, int viewTag, Asset asset) {
    //reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit(ON_ITEM_LONG_PRESS,"TESTING");

    //reactContext.getJSModule(RCTEventEmitter.class).receiveEvent(3, "longPressed", event);
    UIManagerHelper.getEventDispatcherForReactTag(reactContext, viewTag).dispatchEvent(
      new LongPressEvent(viewTag, asset)
    );
  }

  public  static  void sendOnRefreshingEvent(ReactContext reactContext, int viewTag){
    UIManagerHelper.getEventDispatcherForReactTag(reactContext, viewTag).dispatchEvent(
      new OnRefreshingEvent(viewTag)
    );
  }
  public static void sendItemOnPress(ReactContext reactContext, int viewTag, Asset asset) {
    //reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit(ON_ITEM_LONG_PRESS,"TESTING");

    //reactContext.getJSModule(RCTEventEmitter.class).receiveEvent(3, "longPressed", event);
    UIManagerHelper.getEventDispatcherForReactTag(reactContext, viewTag).dispatchEvent(
      new OnPressEvent(viewTag, asset)
    );
  }

  public static void sendSingleItemSelectChanged(ReactContext reactContext, int viewTag, Asset asset) {
    UIManagerHelper.getEventDispatcherForReactTag(reactContext, viewTag).dispatchEvent(
      new OnItemSelectStateChangedEvent(viewTag, asset)
    );
  }
}
