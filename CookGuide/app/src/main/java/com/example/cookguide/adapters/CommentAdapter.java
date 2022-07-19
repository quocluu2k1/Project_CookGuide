package com.example.cookguide.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuPopupHelper;

import com.bumptech.glide.Glide;
import com.example.cookguide.CookGuideCompleteActivity;
import com.example.cookguide.R;
import com.example.cookguide.api.ApiService;
import com.example.cookguide.common.Constant;
import com.example.cookguide.common.LoadImageFromUrl;
import com.example.cookguide.models.CustomComment;

import java.io.ByteArrayOutputStream;
import java.sql.Timestamp;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentAdapter extends BaseAdapter {
    Context context;
    int layout;
    List<CustomComment> customCommentList;

    public CommentAdapter(Context context, int layout, List<CustomComment> customCommentList) {
        this.context = context;
        this.layout = layout;
        this.customCommentList = customCommentList;
    }

    @Override
    public int getCount() {
        return customCommentList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(layout,null);

        CircleImageView imageAvatarComment = view.findViewById(R.id.imageAvatarComment);//
        TextView textViewFUllNameComment = view.findViewById(R.id.textViewFUllNameComment);
        TextView textViewTimelineComment = view.findViewById(R.id.textViewTimelineComment);
        ImageButton imageButtonExpand = view.findViewById(R.id.imageButtonExpand);//
        TextView textViewContentComment = view.findViewById(R.id.textViewContentComment);
        ImageView imageComment = view.findViewById(R.id.imageComment);//

        textViewFUllNameComment.setText(customCommentList.get(i).getFullName());
        if(customCommentList.get(i).getStatusUser()){
            imageButtonExpand.setVisibility(View.VISIBLE);
        }else {
            imageButtonExpand.setVisibility(View.GONE);
        }


        textViewContentComment.setText(customCommentList.get(i).getContent());
        textViewTimelineComment.setText(convertToStrTimeLine(customCommentList.get(i).getTime()));

        if(customCommentList.get(i).getAvatar()!=null){
            String url = Constant.DOMAIN_NAME+customCommentList.get(i).getAvatar();
//            LoadImageFromUrl loadImage = new LoadImageFromUrl(imageAvatarComment);
//            loadImage.execute(url);
            Glide.with(view.getContext())
                    .load(url)
                    .placeholder(R.drawable.avatar_default)
                    .error(R.drawable.avatar_default)
                    .centerCrop()
                    .into(imageAvatarComment);
        }

        //Set Image Comment
        if(customCommentList.get(i).getUrlImage()!=null){
            String url = Constant.DOMAIN_NAME+customCommentList.get(i).getUrlImage();
            Glide.with(view.getContext())
                    .load(url)
                    .placeholder(R.drawable.image_load)
                    .error(R.drawable.image_load)
                    .centerCrop()
                    .into(imageComment);

//            LoadImageFromUrl loadImage = new LoadImageFromUrl(imageComment);
//            loadImage.execute(url);
        }

        if(customCommentList.get(i).getImageView()!=null){
            ImageView imageView = customCommentList.get(i).getImageView();

            BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
            Bitmap bitmap = drawable.getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();

            Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            imageComment.setImageBitmap(bmp);
        }
        if((customCommentList.get(i).getUrlImage()==null)&&(customCommentList.get(i).getImageView()==null)){
            imageComment.setVisibility(View.GONE);
        }

        imageButtonExpand.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View view) {
                @SuppressLint("RestrictedApi") MenuBuilder menuBuilder =new MenuBuilder(view.getContext());
                MenuInflater menuInflater = new MenuInflater(view.getContext());
                menuInflater.inflate(R.menu.popup_menu_comment, menuBuilder);
                @SuppressLint("RestrictedApi") MenuPopupHelper optionsMenu = new MenuPopupHelper(view.getContext(), menuBuilder, imageButtonExpand);
                optionsMenu.setForceShowIcon(true);
                menuBuilder.setCallback(new MenuBuilder.Callback() {
                    @Override
                    public boolean onMenuItemSelected(@NonNull MenuBuilder menu, @NonNull MenuItem item) {
                        Long cmtId = customCommentList.get(i).getCmtId();
                        customCommentList.remove(i);
                        CommentAdapter.this.notifyDataSetChanged();
                        ApiService.apiService.deleteComment(getToken(),cmtId).enqueue(new Callback<Boolean>() {
                            @Override
                            public void onResponse(Call<Boolean> call, Response<Boolean> response) {

                            }

                            @Override
                            public void onFailure(Call<Boolean> call, Throwable t) {

                            }
                        });
                        return true;
                    }

                    @Override
                    public void onMenuModeChange(@NonNull MenuBuilder menu) {

                    }
                });
               optionsMenu.show();

//                customCommentList.remove(i);
//                CommentAdapter.this.notifyDataSetChanged();
            }
        });

        return view;
    }

    protected String convertToStrTimeLine(Timestamp timestamp){
        String strTimeLine = null;
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        Long diff = (currentTime.getTime()-timestamp.getTime())/1000;
        if(diff<=0){
            strTimeLine = "Vừa xong";
        }else if(diff<60){
            strTimeLine = diff + " giây trước";
        }else if(diff<3600){
            strTimeLine = diff/60 + " phút trước";
        }else if(diff<86400){
            strTimeLine = diff/3600 + " giờ trước";
        }else if(diff<2592000){
            strTimeLine = diff/86400 + " ngày trước";
        }else if(diff<31536000){
            strTimeLine = diff/2592000 + " tháng trước";
        }else {
            strTimeLine = diff/31536000 + " năm trước";
        }
        return strTimeLine;
    }

    private String getToken() {
        SharedPreferences prefs;
        prefs=context.getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        String token = prefs.getString("token","");
        return "Bearer "+token;
    }
}
