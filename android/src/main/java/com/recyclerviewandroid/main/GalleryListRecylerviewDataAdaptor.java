package com.recyclerviewandroid.main;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Log;
import android.util.Size;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.facebook.react.bridge.ReactContext;
import com.recyclerviewandroid.R;
import com.recyclerviewandroid.libs.domain.Asset;
import com.recyclerviewandroid.libs.domain.GetPhotoOutput;
import com.recyclerviewandroid.libs.domain.SectionHeaderStyle;
import com.recyclerviewandroid.libs.events.EventDispatcher;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class GalleryListRecylerviewDataAdaptor extends RecyclerView.Adapter<GalleryListRecylerviewDataAdaptor.ViewHolder> implements EventDispatcher.OnItemLongPressListener {


  private static final String TAG = "GalleryListDataAdaptor";
  private GetPhotoOutput photos;

  private SectionHeaderStyle headerStyle;

  private List<ViewHolder> visibleItems;
  private boolean isSelectionMode;
  ReactContext reactContext;
  View view;
  public GalleryListRecylerviewDataAdaptor(View view,GetPhotoOutput photos, SectionHeaderStyle headerStyle, ReactContext reactContext) {
    this.photos = photos;
    this.reactContext = reactContext;
    this.view = view;
    this.headerStyle = headerStyle;
    this.setHasStableIds(true);
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View v;
    if (viewType == 1) {
      v = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.list_section_header, parent, false);
      this.setSectionHeaderStyle(v,this.headerStyle);
    } else {
      v = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.photo_list_item, parent, false);
      v.setPadding(2,2,2,2);
    }
    return new ViewHolder(v, parent.getContext(), this);
  }

  public  void  setSectionHeaderStyle(View view, SectionHeaderStyle style){
    if(style!=null) {
      if (style.BackgroudColor != null) {
        view.setBackgroundColor(Color.parseColor(style.BackgroudColor));
      }
      if (style.Padding > -1) {
        //view.setPadding(style.Padding, style.Padding, style.Padding, style.Padding);
      }
      if (style.FontSize > 0 || style.FontWeight > 0) {
        TextView textView = view.findViewById(R.id.header_title);
        if (textView != null) {
          if (style.FontSize > 0) {
            textView.setTextSize(style.FontSize);
          }
          if(style.FontColor!=null){
            textView.setTextColor(Color.parseColor(style.FontColor));
          }
          if (style.FontWeight > 0) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
              textView.setTypeface(Typeface.create(null,style.FontWeight,false));
            }
          }
        }
      }
    }
  }


  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    Log.println(Log.INFO,"onBindViewHolder", String.valueOf(position));
    try {
      holder.setData(this.photos.assets.get(position));
    } catch (IOException e) {
      //throw new RuntimeException(e);
      Log.println(Log.DEBUG, "Error", e.toString());
    }
  }


  @Override
  public long getItemId(int position) {
    Asset asset = this.photos.assets.get(position);
    return asset.type == "Header" ? asset.group_id :  asset.image.imageId;
  }

  @Override
  public int getItemCount() {
    return this.photos.assets.size();
  }

  @Override
  public void onItemLongPressed(int position) {
    toggleSelectionMode();
    EventDispatcher.sendItemOnLongPress(this.reactContext, view.getId());
  }

  public  void  onItemPressed(int position) {
    EventDispatcher.sendItemOnPress(this.reactContext, view.getId());
  }


  private  void toggleSelectionMode() {
    this.isSelectionMode = !this.isSelectionMode;
    for (int i = 0; i < this.photos.assets.size(); i++) {
      this.photos.assets.get(i).isSelectionMode = this.isSelectionMode;
    }
    if (this.visibleItems != null) {
      for (int i = 0; i < this.visibleItems.size(); i++) {
        this.visibleItems.get(i).toggleSelectionMode(this.isSelectionMode);
      }
    }
  }

  @Override
  public void onViewAttachedToWindow(ViewHolder viewHolder){
    if(this.visibleItems == null){
      this.visibleItems = new ArrayList<ViewHolder>();
    }
    this.visibleItems.add(viewHolder);
  }
  @Override
  public void  onViewDetachedFromWindow(ViewHolder viewHolder) {
    int index = this.visibleItems.indexOf(viewHolder);
    if(index>-1){
      this.visibleItems.remove(index);
    }
  }

  @Override
  public int getItemViewType(int position) {
    Asset asset = this.photos.assets.get(position);
    return  asset.type=="Header" ? 1 :0;
  }
  public interface Callback {
    void onResult(Bitmap result);
  }

  public static class ViewHolder extends RecyclerView.ViewHolder {


    ImageView imageView = null;
    TextView headerTextView = null;
    CheckBox checkBox = null;
    RelativeLayout controlContainer;
    BitmapFactory.Options options;
    Context context;
    GalleryListRecylerviewDataAdaptor adaptor;

    Boolean isInSelectionMode = false;





    public ViewHolder(@NonNull View itemView, Context context, GalleryListRecylerviewDataAdaptor adapter) {
      super(itemView);
      this.adaptor = adapter;
      imageView = itemView.findViewById(R.id.list_item_imageview);
      headerTextView = itemView.findViewById(R.id.header_title);
      checkBox = itemView.findViewById(R.id.list_item_checkbox);
      controlContainer = itemView.findViewById(R.id.list_item_control_container);
      options = new BitmapFactory.Options();

      if (itemView != null) {
        itemView.setOnLongClickListener(new View.OnLongClickListener() {
          @Override
          public boolean onLongClick(View v) {
            // Call the onItemLongPressed() method of the adapter

            Log.i("REPRESSION", "Triggered");
            adapter.onItemLongPressed(getAbsoluteAdapterPosition());
            return true;
          }
        });

        itemView.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              adapter.onItemPressed(getAbsoluteAdapterPosition());
          }
        });
        this.context = context;
        //options.inSampleSize=true
      }
    }


    private static class LoadLocalMediaStoreDataTask extends AsyncTask<Void, Void, Bitmap> {
      private Context context;
      private Uri uri;
      private Long imageId;

      private Callback callback;

      public LoadLocalMediaStoreDataTask(Asset asset, Context context, Callback bitmapDataCallback) {
        this.context = context;
        this.uri = asset.image.imageUri;

        this.imageId = asset.image.imageId;
        this.callback = bitmapDataCallback;
      }

      private Bitmap errorBitmap() {
        int width = 200;
        int height = 200;
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawPaint(paint);

        paint.setColor(Color.WHITE);
        paint.setAntiAlias(true);
        paint.setTextSize(14.f);
        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText("Hello Android!", (width / 2.f), (height / 2.f), paint);
        return bitmap;
      }

      @Override
      protected Bitmap doInBackground(Void... voids) {
        Bitmap thumbBitmap = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
          try {
            thumbBitmap = this.context.getContentResolver().loadThumbnail(this.uri, new Size(200, 200), null);
          } catch (IOException e) {
            Log.e("LoadMediaStoreDataTask", this.uri.toString(), e);
          } catch (Exception e) {
            Log.e("LoadMediaStoreDataTask", this.uri.toString(), e);
          }
        } else {
          thumbBitmap = MediaStore.Images.Thumbnails.getThumbnail(this.context.getContentResolver(),
            this.imageId, MediaStore.Images.Thumbnails.MINI_KIND, null);
        }
        if (thumbBitmap == null) {
          thumbBitmap = errorBitmap();
        }
        return thumbBitmap;
      }

      @Override
      protected void onPostExecute(Bitmap result) {
        callback.onResult(result);
      }
    }

    public  void toggleSelectionMode(boolean visible) {
      if (controlContainer != null && visible != this.isInSelectionMode) {
        controlContainer.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
        this.isInSelectionMode = visible;
      }
    }
    public void setData(Asset asset) throws IOException {

      toggleSelectionMode(asset.isSelectionMode);
      if(imageView!=null) {
        imageView.setZ(0);
        Glide.with(this.context).load(asset.image.imageUri.toString()).into(imageView);

      }
      else if(asset.type=="Header"){

          headerTextView.setText(asset.group_name);
      }

    }
  }
}
