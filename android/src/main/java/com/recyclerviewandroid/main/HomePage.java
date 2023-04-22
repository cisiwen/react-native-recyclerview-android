package com.recyclerviewandroid.main;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.react.bridge.ReactContext;
import com.facebook.react.uimanager.ThemedReactContext;
import com.recyclerviewandroid.libs.GalleryAlbumProvider;
import com.recyclerviewandroid.libs.domain.GetPhotoInput;
import com.recyclerviewandroid.libs.domain.GetPhotoOutput;

public class HomePage {


  private RecyclerView recyclerView;
  private ThemedReactContext context;
  private  Activity activity;

  public HomePage(RecyclerView recyclerView, ThemedReactContext context, Activity activity) {
    this.recyclerView = recyclerView;
    this.context = context;
    this.activity = activity;
  }

  public interface GetPhotoCallback {
    void onResult(GetPhotoOutput result);
  }

  private GalleryListRecylerviewDataAdaptor dataAdaptor;


  private boolean checkPermission() {
    if (ContextCompat.checkSelfPermission(this.context,
      Manifest.permission.READ_MEDIA_IMAGES)
      != PackageManager.PERMISSION_GRANTED) {

      // Permission is not granted
      // Should we show an explanation?
      if (ActivityCompat.shouldShowRequestPermissionRationale(
        activity,
        Manifest.permission.READ_MEDIA_IMAGES)
      ) {
        Log.println(Log.INFO, "wweng", "need rational");
      } else {
        // No explanation needed, we can request the permission.
        ActivityCompat.requestPermissions(
          activity,
          new String[]{Manifest.permission.READ_MEDIA_IMAGES},
          45
        );

        return true;
        // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
        // app-defined int constant. The callback method gets the
        // result of the request.
      }
    } else {
      return true;
    }
    return false;
  }

  public void loadGallery() {
    if (this.checkPermission()) {
      GetPhotoInput input = new GetPhotoInput();
      input.AssetType = "All";
      input.First = 5000;
      GalleryAlbumProvider.getPhotos(input, this.context, new GalleryAlbumProvider.GetPhotoCallback() {
        @Override
        public void onResult(GetPhotoOutput result) {
          dataAdaptor = new GalleryListRecylerviewDataAdaptor(result, (ReactContext) context);
          recyclerView.setAdapter(dataAdaptor);
        }
      });
    }
  }
}
