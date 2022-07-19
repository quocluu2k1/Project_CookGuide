package com.example.cookguide.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.cookguide.R;
import com.example.cookguide.models.Photo;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

public class PhotoAdapter extends PagerAdapter {

    private Context context;
    private List<Photo> photoList;

    public PhotoAdapter(Context context, List<Photo> photoList) {
        this.context = context;
        this.photoList = photoList;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.item_photo, container, false);
        ImageView imageView = (ImageView) view.findViewById(R.id.imgPhoto);

        Photo photo = photoList.get(position);
        if(photo != null){
            String url = photo.getUrl();
            LoadImage loadImage = new LoadImage(imageView);
            loadImage.execute(url);
//            Glide.with(view.getContext())
//                    .load("http://projectappcookguide.koreacentral.cloudapp.azure.com/images/food/food1_1.png")
//                    .placeholder(R.drawable.image_load)
//                    .error(R.drawable.image_load)
//                    .centerCrop()
//                    .into(imageView);
//            Glide.with(context).load(photo.getResourceId()).into(imageView);
        }
        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        if(photoList != null){
            return photoList.size();
        }
        return 0;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    private class LoadImage extends AsyncTask<String,Void, Bitmap> {
        ImageView imageView;
        public LoadImage(ImageView imageView){
            this.imageView = imageView;
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            String url = strings[0];
            Bitmap bitmap = null;
            try {
                InputStream inputStream = new URL(url).openStream();
                bitmap = BitmapFactory.decodeStream(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            imageView.setImageBitmap(bitmap);
        }
    }
}
