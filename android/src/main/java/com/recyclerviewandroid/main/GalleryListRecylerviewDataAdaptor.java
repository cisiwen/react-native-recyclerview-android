package com.recyclerviewandroid.main;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
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
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.NotificationCompatSideChannelService;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.Headers;
import com.bumptech.glide.load.model.LazyHeaders;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.facebook.react.bridge.ReactContext;
import com.recyclerviewandroid.R;
import com.recyclerviewandroid.libs.domain.Asset;
import com.recyclerviewandroid.libs.domain.GetPhotoOutput;
import com.recyclerviewandroid.libs.domain.SectionHeaderStyle;
import com.recyclerviewandroid.libs.domain.TopHeaderItemStyle;
import com.recyclerviewandroid.libs.domain.TopHeaderTemplate;
import com.recyclerviewandroid.libs.events.EventDispatcher;

import java.io.IOException;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;


public class GalleryListRecylerviewDataAdaptor extends RecyclerView.Adapter<GalleryListRecylerviewDataAdaptor.ViewHolder> implements EventDispatcher.OnItemLongPressListener {


  public static  int VIEWTYPE_HEADER=1;

  public  static  int VIEWTYPE_TOPHEADER=4;
  public  static  int VIEWTYPE_TOPHEADER_FULL=5;
  public static int VIEWTYPE_PHOTO=0;
  public static int VIEWTYPE_VIDEO=2;
  private static final String TAG = "GalleryListDataAdaptor";
  private GetPhotoOutput photos;

  private SectionHeaderStyle headerStyle;

  private Map<String, String> httpHeaders;

  private List<ViewHolder> visibleItems;
  private boolean isSelectionMode;
  ReactContext reactContext;
  View view;

  RecyclerView recyclerView;
  public GalleryListRecylerviewDataAdaptor(View view,RecyclerView recyclerView, GetPhotoOutput photos, SectionHeaderStyle headerStyle, Map<String, String> httpHeaders, ReactContext reactContext) {
    this.photos = photos;
    this.reactContext = reactContext;
    this.view = view;
    this.headerStyle = headerStyle;
    this.httpHeaders = httpHeaders;
    this.recyclerView = recyclerView;
    this.setHasStableIds(true);

  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View v;
    if (viewType == VIEWTYPE_TOPHEADER) {
      v = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.top_header_item, parent, false);
    } else if (viewType == VIEWTYPE_TOPHEADER_FULL) {
      v = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.top_header_item_full_image, parent, false);
    } else if (viewType == VIEWTYPE_HEADER) {
      v = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.list_section_header, parent, false);
      this.setSectionHeaderStyle(v, this.headerStyle);
    } else if (viewType == VIEWTYPE_VIDEO) {
      v = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.video_list_item, parent, false);
      //v.setPadding(2,2,2,2);
    } else {
      v = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.photo_list_item, parent, false);
    }
    return new ViewHolder(v, parent.getContext(), this);
  }

  public void setSectionHeaderStyle(View view, SectionHeaderStyle style) {
    if (style != null) {
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
          if (style.FontColor != null) {
            textView.setTextColor(Color.parseColor(style.FontColor));
          }
          if (style.FontWeight > 0) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
              textView.setTypeface(Typeface.create(null, style.FontWeight, false));
            }
          }
        }
      }
    }
  }


  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    Log.println(Log.INFO, "onBindViewHolder", String.valueOf(position));
    try {
      holder.setData(this.photos.assets.get(position),position);
    } catch (IOException e) {
      //throw new RuntimeException(e);
      Log.println(Log.DEBUG, "Error", e.toString());
    }
  }


  @Override
  public long getItemId(int position) {
    Asset asset = this.photos.assets.get(position);
    return  asset.image.imageId;
    //return asset.type == "Header" || asset.type=="TopHeader" ? asset.group_id : asset.image.imageId;
  }

  @Override
  public int getItemCount() {
    return this.photos.assets.size();
  }

  @Override
  public void onItemLongPressed(Asset asset) {
    this.isSelectionMode = !this.isSelectionMode;
    toggleSelectionMode(this.isSelectionMode);
    EventDispatcher.sendItemOnLongPress(this.reactContext, view.getId(), asset);
  }

  public void onItemPressed(Asset asset) {
    EventDispatcher.sendItemOnPress(this.reactContext, view.getId(), asset);
  }

  public void onSingleItemSelectChanged(boolean selected, Asset asset,int position) {
    asset.selected = selected;
    EventDispatcher.sendSingleItemSelectChanged(this.reactContext, view.getId(), asset);
    Log.i("SingleItemSelectChanged","view changed for "+position);
    //this.notifyItemChanged(position);
    for(int i=0;i<this.visibleItems.size();i++){
      if(this.visibleItems.get(i).myPosition==position) {
          this.visibleItems.get(i).toggleSelectedStatus(asset,true);
      }
    }
    //this.notifyItemChanged(position,asset);
  }


  public void toggleSelectionMode(boolean isSelectionMode) {

    this.isSelectionMode = isSelectionMode;
    for (int i = 0; i < this.photos.assets.size(); i++) {
      this.photos.assets.get(i).isSelectionMode = isSelectionMode;
    }
    if (this.visibleItems != null) {
      for (int i = 0; i < this.visibleItems.size(); i++) {
        this.visibleItems.get(i).toggleSelectionMode(isSelectionMode);
      }
    }
  }

  @Override
  public void onViewAttachedToWindow(ViewHolder viewHolder) {
    if (this.visibleItems == null) {
      this.visibleItems = new ArrayList<ViewHolder>();
    }
    this.visibleItems.add(viewHolder);
  }

  @Override
  public void onViewDetachedFromWindow(ViewHolder viewHolder) {
    int index = this.visibleItems.indexOf(viewHolder);
    if (index > -1) {
      this.visibleItems.remove(index);
    }
  }

  @Override
  public int getItemViewType(int position) {
    Asset asset = this.photos.assets.get(position);
    if (asset.type == "Header") {
      return VIEWTYPE_HEADER;
    } else if (asset.type == "TopHeader") {
      if (asset.topHeaderItem.topHeaderTemplate == TopHeaderTemplate.face) {
        return VIEWTYPE_TOPHEADER;
      } else {
        return VIEWTYPE_TOPHEADER_FULL;
      }
    } else if (asset.originalAsset.mediaType.equals("video")) {
      return VIEWTYPE_VIDEO;
    } else {
      return VIEWTYPE_PHOTO;
    }
  }

  public interface Callback {
    void onResult(Bitmap result);
  }

  public static class ViewHolder extends RecyclerView.ViewHolder {


    ConstraintLayout rootLayout;
    ImageView topHeaderImageView = null;
    ImageView imageView = null;
    TextView headerTextView = null;

    TextView headerSubTextView = null;
    TextView headerLinkTextView = null;
    CheckBox checkBox = null;
    RelativeLayout controlContainer;
    BitmapFactory.Options options;
    Context context;
    GalleryListRecylerviewDataAdaptor adaptor;

    Boolean isInSelectionMode = false;

    ImageButton playButton;
    RelativeLayout list_item_wrapper;
    Asset asset = null;

    int myPosition;
    public ViewHolder(@NonNull View itemView, Context context, GalleryListRecylerviewDataAdaptor adapter) {
      super(itemView);
      this.context = context;
      this.adaptor = adapter;
      imageView = itemView.findViewById(R.id.list_item_imageview);
      headerTextView = itemView.findViewById(R.id.header_title);
      checkBox = itemView.findViewById(R.id.list_item_checkbox);
      controlContainer = itemView.findViewById(R.id.list_item_control_container);
      playButton = itemView.findViewById(R.id.list_item_play_button);
      headerLinkTextView = itemView.findViewById(R.id.header_link_title);
      headerSubTextView = itemView.findViewById(R.id.header_sub_title);

      rootLayout = itemView.findViewById(R.id.photo_list_item);
      list_item_wrapper = itemView.findViewById(R.id.list_item_wrapper);
      //topHeaderImageView = itemView.findViewById(R.id.top_header_list_item_imageview);
      options = new BitmapFactory.Options();

      initializeViewHolder();
    }

    private void initializeViewHolder() {

      if (itemView != null) {
        itemView.setOnLongClickListener(new View.OnLongClickListener() {
          @Override
          public boolean onLongClick(View v) {
            // Call the onItemLongPressed() method of the adapter

            Log.i("REPRESSION", "Triggered");
            adaptor.onItemLongPressed(asset);
            return true;
          }
        });

        itemView.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {

            adaptor.onItemPressed(asset);
          }
        });

        //options.inSampleSize=true
      }
      if (checkBox != null) {
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
          @Override
          public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            asset.selected = b;
            adaptor.onSingleItemSelectChanged(b, asset,myPosition);
            //toggleSelectedStatus(asset,true);
          }
        });
      }
    }


    public  void animatedPadding(View sender, int startValue, int endValue){
      ValueAnimator animator = ValueAnimator.ofInt(startValue, endValue);
      animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
      {
        @Override
        public void onAnimationUpdate(ValueAnimator valueAnimator){
          int newValue = (Integer) valueAnimator.getAnimatedValue();
          sender.setPadding(newValue,newValue,newValue,newValue);
        }
      });
      animator.setDuration(200);
      animator.start();
    }

    public  void toggleSelectedStatus(Asset asset,boolean animated) {
      if (this.isInSelectionMode) {
        if (list_item_wrapper != null) {
          //ViewGroup.MarginLayoutParams marginParams =(ViewGroup.MarginLayoutParams)imageView.getLayoutParams();
          int margin = asset.selected ? 40 : 0;
          int startValue = asset.selected ? 0 : 40;
          //marginParams.setMargins(margin,margin,margin,margin);
          if (!animated) {
            list_item_wrapper.setPadding(margin, margin, margin, margin);
            list_item_wrapper.invalidate();
          } else {
            animatedPadding(list_item_wrapper, startValue, margin);
          }

          //this.adaptor.notifyItemChanged(getLayoutPosition());
        }
        if (checkBox != null) {
          checkBox.setChecked(asset.selected);
        }
      } else {
        rootLayout.setPadding(0, 0, 0, 0);
      }
    }
    public void toggleSelectionMode(boolean visible) {
      if (controlContainer != null && visible != this.isInSelectionMode) {
        controlContainer.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
        this.isInSelectionMode = visible;
      }
    }


    private GlideUrl getGlideUrl(Asset asset) {
      GlideUrl glideUrl = null;
      Uri source = asset.image.imageUri;
      if (source.getScheme().startsWith("http")) {
        glideUrl = new GlideUrl(source.toString(), new Headers() {
          @Override
          public Map<String, String> getHeaders() {
            return adaptor.httpHeaders;
          }
        });
      }
      return glideUrl;
    }

    public void setDataBackground(Asset asset, Context context) throws ExecutionException, InterruptedException {
      this.asset = asset;
      if (asset.originalAsset != null && asset.originalAsset.mediaType.equals("video")) {
        if (playButton != null) {
          playButton.setVisibility(View.VISIBLE);
        }
      }
      toggleSelectionMode(asset.isSelectionMode);
      if (imageView != null) {
        imageView.setZ(0);

        GlideUrl glideUrl = getGlideUrl(asset);

        if (glideUrl != null) {
          Glide.with(context).asBitmap().load(glideUrl).into(imageView);
        } else {
          Glide.with(context).asBitmap().load(asset.image.imageUri.toString()).into(imageView);
        }
      }
      if (headerTextView != null && asset.group_name != null) {
        headerTextView.setText(asset.group_name);
      }
      if (asset.topHeaderItem != null) {
        TopHeaderItemStyle style = asset.topHeaderItem.style;

        if(style.titleColor!=null) {
            headerTextView.setTextColor(Color.parseColor(style.titleColor));
        }
        if(style.titleFontSize>0) {
          headerTextView.setTextSize(style.titleFontSize);
        }

        if (headerSubTextView != null) {
          headerSubTextView.setText(asset.topHeaderItem.subTitle);
          if(style.subTitleColor!=null) {
            headerSubTextView.setTextColor(Color.parseColor(style.subTitleColor));
          }
          if(style.subTitleFontSize>0) {
            headerSubTextView.setTextSize(style.subTitleFontSize);
          }
        }
        else {
          headerSubTextView.setVisibility(View.GONE);
        }
        if (headerLinkTextView != null) {
          headerLinkTextView.setText(asset.topHeaderItem.linkText);
          if(style.linkTextColor!=null) {
            headerLinkTextView.setTextColor(Color.parseColor(style.linkTextColor));
          }
          if(style.titleFontSize>0) {
            headerLinkTextView.setTextSize(style.linkTextFontSize);
          }
        }
        else {
          headerLinkTextView.setVisibility(View.GONE);
        }
      }

      toggleSelectedStatus(asset,false);
    }

    public void setData(Asset asset,int position) throws IOException {
      Log.i("setdata","setdata"+position);
      myPosition = position;
      new RunBackend().execute(new Runnable() {
        @Override
        public void run() {
          try {
            setDataBackground(asset, context);
          } catch (Exception ex) {
            Log.e("setData", ex.getMessage());
          }
        }
      });
    }

    public static class RunBackend implements Executor {
      @Override
      public void execute(Runnable runnable) {
        runnable.run();
      }
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

}
