package com.recyclerviewandroid.main;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.react.bridge.ReactContext;
import com.facebook.react.uimanager.ThemedReactContext;
import com.recyclerviewandroid.libs.GalleryAlbumProvider;
import com.recyclerviewandroid.libs.domain.Asset;
import com.recyclerviewandroid.libs.domain.GetPhotoInput;
import com.recyclerviewandroid.libs.domain.GetPhotoOutput;
import com.recyclerviewandroid.libs.domain.Image;
import com.recyclerviewandroid.libs.domain.Media;
import com.recyclerviewandroid.libs.domain.SectionHeaderStyle;
import com.recyclerviewandroid.libs.javascript.ReactAsset;
import com.recyclerviewandroid.libs.javascript.ReactSectionDataSource;

import java.util.ArrayList;
import java.util.List;

public class HomePage {


  private RecyclerView recyclerView;
  private ThemedReactContext context;
  private Activity activity;

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

  public void setDataSourceMedia(List<ReactSectionDataSource> media, SectionHeaderStyle headerStyle) {
    GetPhotoOutput result = new GetPhotoOutput();
    result.assets = new ArrayList<Asset>();
    int id = 0;
    for (ReactSectionDataSource temp : media) {
      id++;
      Asset header = new Asset();
      header.type = "Header";
      header.group_name = temp.sectionTitle;
      header.group_id = new Long(id);
      result.assets.add(header);


      for (ReactAsset asset : temp.data) {
        id++;
        Asset oneMedia = new Asset();
        oneMedia.type = "IMAGE";
        oneMedia.image = new Image();
        oneMedia.image.imageId = new Long(id);
        oneMedia.image.imageUri = Uri.parse(asset.uri);
        //oneMedia.image.imageId= asset.contentId;
        result.assets.add(oneMedia);
      }
    }
    dataAdaptor = new GalleryListRecylerviewDataAdaptor(recyclerView, result,headerStyle, (ReactContext) context);
    recyclerView.setAdapter(dataAdaptor);
  }

  public void loadGallery() {
    if (this.checkPermission()) {
      GetPhotoInput input = new GetPhotoInput();
      input.AssetType = "All";
      input.First = 5000;
      GalleryAlbumProvider.getPhotos(input, this.context, new GalleryAlbumProvider.GetPhotoCallback() {
        @Override
        public void onResult(GetPhotoOutput result) {
          dataAdaptor = new GalleryListRecylerviewDataAdaptor(recyclerView, result, null, (ReactContext) context);
          recyclerView.setAdapter(dataAdaptor);
        }
      });
    }
  }
}
