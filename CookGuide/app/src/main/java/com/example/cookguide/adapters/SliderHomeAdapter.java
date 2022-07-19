package com.example.cookguide.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.cookguide.R;
import com.example.cookguide.models.PhotoSliderHome;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;


public class SliderHomeAdapter extends PagerAdapter {

    private Context context;
    private List<PhotoSliderHome> photoSliderHomeList;

    public SliderHomeAdapter(Context context, List<PhotoSliderHome> photoSliderHomeList) {
        this.context = context;
        this.photoSliderHomeList = photoSliderHomeList;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.item_photo_slider_home, container, false);
        RoundedImageView imageView = view.findViewById(R.id.imageSliderHome);

        PhotoSliderHome photoSliderHome = photoSliderHomeList.get(position);
        if(photoSliderHome != null){
            Glide.with(context).load(photoSliderHome.getResourceId()).into(imageView);
        }

        container.addView(view);

        return view;
    }

    @Override
    public int getCount() {
        if(photoSliderHomeList != null){
            return photoSliderHomeList.size();
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
}
