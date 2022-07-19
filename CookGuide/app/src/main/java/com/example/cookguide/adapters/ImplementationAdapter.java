package com.example.cookguide.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.cookguide.CookGuideCompleteActivity;
import com.example.cookguide.R;
import com.example.cookguide.common.Constant;
import com.example.cookguide.models.Implementation;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

public class ImplementationAdapter extends BaseAdapter {

    Context context;
    int layout;
    List<Implementation> implementationList;

    int flag=-1;

    public ImplementationAdapter(Context context, int layout, List<Implementation> implementationList) {
        this.context = context;
        this.layout = layout;
        this.implementationList = implementationList;
    }

    @Override
    public int getCount() {
        return implementationList.size();
    }

    @Override
    public Implementation getItem(int i) {
        return implementationList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(layout,null);
        TextView textViewStepOrder = (TextView) view.findViewById(R.id.textViewStepOrder);
        TextView textViewDetail = (TextView) view.findViewById(R.id.textViewDetail);
        ImageView imageViewStep = (ImageView) view.findViewById(R.id.imageViewStep);
        if(i<=flag){
            textViewStepOrder.setBackgroundResource(R.drawable.ic_baseline_check_circle_30);
            textViewDetail.setTextColor(Color.parseColor("#1AC569"));
        }else if(i==flag+1){
            textViewStepOrder.setText(String.valueOf(implementationList.get(i).stepOrder));
            textViewDetail.setTextColor(Color.parseColor("#FF4800"));
        }else{
            textViewStepOrder.setText(String.valueOf(implementationList.get(i).stepOrder));
        }

        textViewDetail.setText(implementationList.get(i).impDetail);

        String url = Constant.DOMAIN_NAME+implementationList.get(i).stepImage;
        Glide.with(view.getContext())
                .load(url)
                .placeholder(R.drawable.image_load)
                .error(R.drawable.image_load)
                .centerCrop()
                .into(imageViewStep);

//        LoadImage loadImage = new LoadImage(imageViewStep);
//        loadImage.execute(url);

        return view;
    }

    public void setView(int i,  View view){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(layout,null);
        TextView textViewStepOrder = (TextView) view.findViewById(R.id.textViewStepOrder);
        textViewStepOrder.setText("0");
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

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }
}
