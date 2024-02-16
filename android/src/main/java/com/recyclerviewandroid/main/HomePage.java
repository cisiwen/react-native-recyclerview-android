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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.facebook.react.bridge.ReactContext;
import com.facebook.react.uimanager.ThemedReactContext;
import com.recyclerviewandroid.libs.GalleryAlbumProvider;
import com.recyclerviewandroid.libs.domain.Asset;
import com.recyclerviewandroid.libs.domain.GetPhotoInput;
import com.recyclerviewandroid.libs.domain.GetPhotoOutput;
import com.recyclerviewandroid.libs.domain.Image;
import com.recyclerviewandroid.libs.domain.Media;
import com.recyclerviewandroid.libs.domain.SectionHeaderStyle;
import com.recyclerviewandroid.libs.domain.TopHeaderItem;
import com.recyclerviewandroid.libs.javascript.ReactAsset;
import com.recyclerviewandroid.libs.javascript.ReactRecyclerProps;
import com.recyclerviewandroid.libs.javascript.ReactSectionDataSource;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HomePage {


  private RecyclerView recyclerView;
  private ThemedReactContext context;
  private Activity activity;


  private  SwipeRefreshLayout swipeRefreshLayout;

  public HomePage(RecyclerView recyclerView,SwipeRefreshLayout swipeRefreshLayout, ThemedReactContext context, Activity activity) {
    this.recyclerView = recyclerView;
    this.context = context;
    this.activity = activity;
    this.swipeRefreshLayout= swipeRefreshLayout;
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

  public void setDataSourceMedia(List<ReactSectionDataSource> media, SectionHeaderStyle headerStyle, Map<String,String> httpHeaders, TopHeaderItem topHeaderItem) {
    GetPhotoOutput result = new GetPhotoOutput();
    result.assets = new ArrayList<Asset>();
    int id = 0;
    for (ReactSectionDataSource temp : media) {
      id++;
      if(temp.sectionTitle!=null && temp.sectionTitle.length()>0) {
        Asset header = new Asset();
        header.type = "Header";
        header.group_name = temp.sectionTitle;
        header.group_id = new Long(id);
        header.originalSection = new ReactSectionDataSource();
        header.originalSection.sectionId = temp.sectionId;
        header.originalSection.sectionTitle = temp.sectionTitle;
        result.assets.add(header);
      }

      for (ReactAsset asset : temp.data) {
        id++;
        Asset oneMedia = new Asset();
        oneMedia.type = "IMAGE";
        oneMedia.image = new Image();
        oneMedia.image.imageId = new Long(id);
        oneMedia.image.imageUri = Uri.parse(asset.uri);
        oneMedia.originalAsset = asset;
        //oneMedia.image.imageId= asset.contentId;
        result.assets.add(oneMedia);
      }
    }
    dataAdaptor = new GalleryListRecylerviewDataAdaptor(swipeRefreshLayout,recyclerView, result,headerStyle, httpHeaders, (ReactContext) context);
    recyclerView.setAdapter(dataAdaptor);
  }

  public void setDataSourceMedia(ReactRecyclerProps props) {
    GetPhotoOutput result = new GetPhotoOutput();
    result.assets = new ArrayList<Asset>();
    long id = 0;

    if(props.topHeaderItem!=null) {
      Asset topHeader = new Asset();
      topHeader.type="TopHeader";
      topHeader.group_id=new Long(-9999);
      topHeader.image = new Image();
      topHeader.image.imageId=id;
      topHeader.image.imageUri = Uri.parse(props.topHeaderItem.imageUri);
      topHeader.group_name = props.topHeaderItem.title;
      topHeader.topHeaderItem = props.topHeaderItem;
      result.assets.add(topHeader);
    }

    for (ReactSectionDataSource temp : props.data) {
      id++;
      if (temp.sectionTitle != null && temp.sectionTitle.length() > 0) {
        Asset header = new Asset();
        header.type = "Header";
        header.group_name = temp.sectionTitle;
        header.group_id = id;
        header.image = new Image();
        header.image.imageId = id;
        header.originalSection = new ReactSectionDataSource();
        header.originalSection.sectionId = temp.sectionId;
        header.originalSection.sectionTitle = temp.sectionTitle;
        result.assets.add(header);
      }

      for (ReactAsset asset : temp.data) {
        id++;
        Asset oneMedia = new Asset();
        oneMedia.type = "IMAGE";
        oneMedia.image = new Image();
        oneMedia.image.imageId = id;
        oneMedia.image.imageUri = Uri.parse(asset.uri);
        oneMedia.originalAsset = asset;
        //oneMedia.image.imageId= asset.contentId;
        result.assets.add(oneMedia);
      }
    }
    dataAdaptor = new GalleryListRecylerviewDataAdaptor(swipeRefreshLayout,recyclerView, result,props.headerStyle, props.httpHeaders, (ReactContext) context);
    recyclerView.setAdapter(dataAdaptor);
  }



  public void  toggleSeledtionMode(boolean selecteMode) {
    dataAdaptor.toggleSelectionMode(selecteMode);
  }
  public void loadGallery(SectionHeaderStyle headerStyle, Map<String,String> httpHeaders) {
    if (this.checkPermission()) {
      GetPhotoInput input = new GetPhotoInput();
      input.AssetType = "All";
      input.First = 10;
      GalleryAlbumProvider.getPhotos(input, this.context, new GalleryAlbumProvider.GetPhotoCallback() {
        @Override
        public void onResult(GetPhotoOutput result) {
          dataAdaptor = new GalleryListRecylerviewDataAdaptor(swipeRefreshLayout,recyclerView, result, headerStyle,httpHeaders, (ReactContext) context);
          recyclerView.setAdapter(dataAdaptor);
        }
      });
    }
  }
}
