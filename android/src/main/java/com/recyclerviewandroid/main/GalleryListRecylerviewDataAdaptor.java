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
import android.widget.ImageView;
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


public class GalleryListRecylerviewDataAdaptor extends RecyclerView.Adapter<GalleryListRecylerviewDataAdaptor.ViewHolder> implements EventDispatcher.OnItemLongPressListener {


  private static final String TAG = "GalleryListDataAdaptor";
  private GetPhotoOutput photos;

  private SectionHeaderStyle headerStyle;

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
    EventDispatcher.sendItemOnLongPress(this.reactContext, view.getId());
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
    TextView headerTextView =null;
    BitmapFactory.Options options;
    Context context;
    GalleryListRecylerviewDataAdaptor adaptor;

    public ViewHolder(@NonNull View itemView, Context context, GalleryListRecylerviewDataAdaptor adapter) {
      super(itemView);
      this.adaptor = adapter;
      imageView = itemView.findViewById(R.id.list_item_imageview);
      headerTextView = itemView.findViewById(R.id.header_title);
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

    public void setData(Asset asset) throws IOException {

      if(imageView!=null) {
        Glide.with(this.context).load(asset.image.imageUri.toString()).into(imageView);
      }
      else if(asset.type=="Header"){

          headerTextView.setText(asset.group_name);
      }
          /*
            new LoadLocalMediaStoreDataTask(asset,this.context, new Callback(){

              @Override
              public void onResult(Bitmap result) {
                imageView.setImageBitmap(result);
              }
            }).execute();
            */
    }
  }
}
