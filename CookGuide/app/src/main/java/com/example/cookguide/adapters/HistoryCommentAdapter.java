package com.example.cookguide.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.text.Html;
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
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.cookguide.CommentFoodActivity;
import com.example.cookguide.R;
import com.example.cookguide.api.ApiService;
import com.example.cookguide.common.Constant;
import com.example.cookguide.models.HistoryCommentDetail;
import com.makeramen.roundedimageview.RoundedImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryCommentAdapter extends RecyclerView.Adapter<HistoryCommentAdapter.ViewHolder> {

    Context context;
    List<HistoryCommentDetail> historyCommentDetailList;

    public HistoryCommentAdapter(Context context, List<HistoryCommentDetail> historyCommentDetailList) {
        this.context = context;
        this.historyCommentDetailList = historyCommentDetailList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_history_comment,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String contentNameFood = "Bạn đã bình luận về món <b>"+historyCommentDetailList.get(position).getNameFood()+"</b>";
        holder.textViewNameFood.setText(Html.fromHtml(contentNameFood));

        holder.textViewContentComment.setText(historyCommentDetailList.get(position).getContentCmt());
        if(historyCommentDetailList.get(position).getImageCmt()!=null){
            String url = Constant.DOMAIN_NAME+historyCommentDetailList.get(position).getImageCmt();
//            LoadImage loadImage = new LoadImage(holder.imageViewComment);
//            loadImage.execute(url);

            Glide.with(holder.itemView.getContext())
                    .load(url)
                    .placeholder(R.drawable.image_load)
                    .error(R.drawable.image_load)
                    .centerCrop()
                    .into(holder.imageViewComment);
            holder.imageViewComment.setVisibility(View.VISIBLE);
        }else{
            holder.imageViewComment.setVisibility(View.GONE);
        }

        //set avatar
        if(getAvatar()==null){
            holder.imageAvatarHistoryComment.setImageResource(R.drawable.avatar_default);
        }else{
            Glide.with(holder.itemView.getContext())
                    .load(Constant.DOMAIN_NAME+getAvatar())
                    .placeholder(R.drawable.avatar_default)
                    .error(R.drawable.avatar_default)
                    .centerCrop()
                    .into(holder.imageAvatarHistoryComment);
        }
        int i = position;

        holder.imageButtonExpandHistoryComment.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View view) {
                @SuppressLint("RestrictedApi") MenuBuilder menuBuilder =new MenuBuilder(view.getContext());
                MenuInflater menuInflater = new MenuInflater(view.getContext());
                menuInflater.inflate(R.menu.popup_menu_history_comment, menuBuilder);
                @SuppressLint("RestrictedApi") MenuPopupHelper optionsMenu = new MenuPopupHelper(view.getContext(), menuBuilder, holder.imageButtonExpandHistoryComment);
                optionsMenu.setForceShowIcon(true);
                menuBuilder.setCallback(new MenuBuilder.Callback() {
                    @Override
                    public boolean onMenuItemSelected(@NonNull MenuBuilder menu, @NonNull MenuItem item) {
                        if(item.getItemId()==R.id.view){
                            Intent intentCommentFood = new Intent(context, CommentFoodActivity.class);
                            Long foodId = historyCommentDetailList.get(i).getFoodId();
                            intentCommentFood.putExtra("foodId",foodId);
                            context.startActivity(intentCommentFood);
                        }else{
                            Long cmtId = historyCommentDetailList.get(i).getCmtId();
                            historyCommentDetailList.remove(i);
                            HistoryCommentAdapter.this.notifyDataSetChanged();
                            ApiService.apiService.deleteComment(getToken(),cmtId).enqueue(new Callback<Boolean>() {
                                @Override
                                public void onResponse(Call<Boolean> call, Response<Boolean> response) {

                                }

                                @Override
                                public void onFailure(Call<Boolean> call, Throwable t) {

                                }
                            });
                        }

                        return true;
                    }

                    @Override
                    public void onMenuModeChange(@NonNull MenuBuilder menu) {

                    }
                });
                optionsMenu.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return historyCommentDetailList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView textViewNameFood;
        TextView textViewContentComment;
        RoundedImageView imageViewComment;
        CircleImageView imageAvatarHistoryComment;
        ImageButton imageButtonExpandHistoryComment;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNameFood = itemView.findViewById(R.id.textViewNameFood);
            textViewContentComment = itemView.findViewById(R.id.textViewContentComment);
            imageViewComment = itemView.findViewById(R.id.imageViewComment);
            imageAvatarHistoryComment = itemView.findViewById(R.id.imageAvatarHistoryComment);
            imageButtonExpandHistoryComment = itemView.findViewById(R.id.imageButtonExpandHistoryComment);
        }
    }

    private String getAvatar() {
        SharedPreferences prefs;
        prefs=context.getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        String avatar = prefs.getString("Avatar","");
        return avatar;
    }

    private String getToken() {
        SharedPreferences prefs;
        prefs=context.getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        String token = prefs.getString("token","");
        return "Bearer "+token;
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
