package com.recyclerviewandroid.main;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.recyclerviewandroid.R;
import com.recyclerviewandroid.libs.domain.Asset;
import com.recyclerviewandroid.libs.domain.GetPhotoOutput;

import java.io.IOException;


public class GalleryListRecylerviewDataAdaptor extends RecyclerView.Adapter<GalleryListRecylerviewDataAdaptor.ViewHolder> {


    private static final String TAG = "GalleryListDataAdaptor";
    private GetPhotoOutput photos;

    public  GalleryListRecylerviewDataAdaptor(GetPhotoOutput photos) {
        this.photos = photos;
        this.setHasStableIds(true);
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.photo_list_item, parent, false);
        return  new ViewHolder(v,parent.getContext());
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {
            holder.setData(this.photos.assets.get(position));
        } catch (IOException e) {
            //throw new RuntimeException(e);
            Log.println(Log.DEBUG,"Error",e.toString());
        }
    }


    @Override
    public long getItemId(int position) {
        Asset asset = this.photos.assets.get(position);
        return  asset.image.imageId;
    }
    @Override
    public int getItemCount() {
        return this.photos.assets.size();
    }

    public interface  Callback {
      void onResult(Bitmap result);
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {


        ImageView imageView=null;
        BitmapFactory.Options options;
        Context context;
        public ViewHolder(@NonNull View itemView,Context context) {
            super(itemView);
            imageView = itemView.findViewById(R.id.list_item_imageview);
            options = new BitmapFactory.Options();
            this.context = context;
            //options.inSampleSize=true
        }


        private static class  LoadLocalMediaStoreDataTask extends AsyncTask<Void,Void,Bitmap> {
            private Context context;
            private Uri uri;
            private Long imageId;

            private Callback callback;

            public LoadLocalMediaStoreDataTask(Asset asset, Context context,Callback bitmapDataCallback) {
                this.context = context;
                this.uri= asset.image.imageUri;

                this.imageId=asset.image.imageId;
                this.callback = bitmapDataCallback;
            }

            private  Bitmap errorBitmap(){
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
                canvas.drawText("Hello Android!", (width / 2.f) , (height / 2.f), paint);
                return  bitmap;
            }
            @Override
            protected Bitmap doInBackground(Void... voids) {
                Bitmap thumbBitmap = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    try {
                        thumbBitmap = this.context.getContentResolver().loadThumbnail(this.uri, new Size(200,200), null);
                    } catch (IOException e) {
                        Log.e("LoadMediaStoreDataTask",this.uri.toString(),e);
                    }
                    catch (Exception e){
                        Log.e("LoadMediaStoreDataTask",this.uri.toString(),e);
                    }
                } else {
                    thumbBitmap = MediaStore.Images.Thumbnails.getThumbnail(this.context.getContentResolver(),
                            this.imageId, MediaStore.Images.Thumbnails.MINI_KIND, null);
                }
                if(thumbBitmap==null){
                    thumbBitmap = errorBitmap();
                }
                return thumbBitmap;
            }
            @Override
            protected void onPostExecute(Bitmap result) {
                callback.onResult(result);
            }
        }

        public void  setData(Asset asset) throws IOException {
          Glide.with(this.context).load(asset.image.imageUri.toString()).into(imageView);
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
