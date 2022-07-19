package com.example.cookguide;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cookguide.adapters.CommentAdapter;
import com.example.cookguide.api.ApiService;
import com.example.cookguide.common.Constant;
import com.example.cookguide.common.LoadImageFromUrl;
import com.example.cookguide.common.RealPathUtil;
import com.example.cookguide.models.Comment;
import com.example.cookguide.models.CustomComment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentFoodActivity extends AppCompatActivity {
    private static final int MY_REQUEST_CODE = 1;
    private ImageButton buttonSelectPhoto;
    private ImageButton buttonRemoveImagePick;
    private ImageView imageShowSelected;
    private LinearLayout layoutShowImageSelected;
    private CommentAdapter commentAdapter;
    private ListView listViewComment;
    private ImageButton buttonSendComment;
    private EditText editTextComment;
    private ImageButton buttonBackComment;
    private ImageButton buttonLoadMore;
    private Uri mUri;

    List<Comment> commentList;
    List<CustomComment> customCommentList;
    Long foodId;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.comment_food);

        Intent intent = getIntent();
        foodId = intent.getLongExtra("foodId",1L);

        buttonSelectPhoto = findViewById(R.id.buttonSelectPhoto);
        imageShowSelected = findViewById(R.id.imageShowSelected);
        layoutShowImageSelected = findViewById(R.id.layoutShowImageSelected);
        buttonRemoveImagePick = findViewById(R.id.buttonRemoveImagePick);
        listViewComment = findViewById(R.id.listViewComment);
        buttonSendComment = findViewById(R.id.buttonSendComment);
        editTextComment = findViewById(R.id.editTextComment);
        buttonBackComment = findViewById(R.id.buttonBackComment);
        LayoutInflater inflaterFooterComment = getLayoutInflater();
        View footerView = inflaterFooterComment.inflate(R.layout.footer_comment, null);
        buttonLoadMore = footerView.findViewById(R.id.buttonLoadMore);

        buttonSelectPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestPermission();
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityIfNeeded(photoPickerIntent, 1);

            }
        });

        buttonRemoveImagePick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layoutShowImageSelected.setVisibility(View.GONE);
            }
        });

        customCommentList = new ArrayList<>();

        ApiService.apiService.getCommentByFoodId(getToken(),foodId,1).enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                commentList = response.body();
                for(int i=0;i<commentList.size();i++){
                    CustomComment customComment = new CustomComment(commentList.get(i));
                    customCommentList.add(customComment);
                }
                commentAdapter = new CommentAdapter(CommentFoodActivity.this,R.layout.item_comment,customCommentList);
                //listViewComment.addFooterView(footerView);
                listViewComment.setAdapter(commentAdapter);

            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                Toast.makeText(CommentFoodActivity.this,"Call API Error",Toast.LENGTH_SHORT).show();
            }
        });
        


        buttonSendComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String contentComment = editTextComment.getText().toString();
                if(!editTextComment.getText().toString().matches("")){
                    CustomComment customCommentSend = new CustomComment();
                    customCommentSend.setFullName(getFullName());
                    customCommentSend.setContent(contentComment);
                    customCommentSend.setAvatar(getAvatar());
                    Timestamp currentTime = new Timestamp(System.currentTimeMillis());
                    customCommentSend.setTime(currentTime);
                    customCommentSend.setStatusUser(true);
                    customCommentList.add(0,customCommentSend);
                    commentAdapter.notifyDataSetChanged();
                    editTextComment.setText("");

                    RequestBody requestBodyContentComment = RequestBody.create(MediaType.parse("multipart/form-data"),contentComment);
                    RequestBody requestBodyContentFoodId = RequestBody.create(MediaType.parse("multipart/form-data"),foodId.toString());
                    if(layoutShowImageSelected.getVisibility()==View.VISIBLE){
                        customCommentSend.setImageView(imageShowSelected);
                        layoutShowImageSelected.setVisibility(View.GONE);

                        String strRealPath = RealPathUtil.getRealPath(CommentFoodActivity.this,mUri);
                        File file = new File(strRealPath);
                        RequestBody requestBodyFile = RequestBody.create(MediaType.parse("multipart/form-data"),file);
                        MultipartBody.Part multipartBodyFile = MultipartBody.Part.createFormData("file", file.getName(),requestBodyFile);

                        ApiService.apiService.commentFood(getToken(),requestBodyContentFoodId,requestBodyContentComment,multipartBodyFile).enqueue(new Callback<Boolean>() {
                            @Override
                            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                                Toast.makeText(CommentFoodActivity.this,"Call API success",Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(Call<Boolean> call, Throwable t) {
                                Toast.makeText(CommentFoodActivity.this,"Call API error",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }else{
                        customCommentSend.setImageView(null);
                        ApiService.apiService.commentFoodNormal(getToken(),requestBodyContentFoodId,requestBodyContentComment).enqueue(new Callback<Boolean>() {
                            @Override
                            public void onResponse(Call<Boolean> call, Response<Boolean> response) {

                            }

                            @Override
                            public void onFailure(Call<Boolean> call, Throwable t) {
                                Toast.makeText(CommentFoodActivity.this,"Call API error",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }

            }
        });

        buttonBackComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            try {
                final Uri imageUri = data.getData();
                mUri = imageUri;
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                imageShowSelected.setImageBitmap(selectedImage);
                layoutShowImageSelected.setVisibility(View.VISIBLE);
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();
            }

        }else {
            Toast.makeText(this, "You haven't picked Image",Toast.LENGTH_LONG).show();
        }
    }

    private String getToken() {
        SharedPreferences prefs;
        prefs=CommentFoodActivity.this.getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        String token = prefs.getString("token","");
        return "Bearer "+token;
    }
    private String getFullName() {
        SharedPreferences prefs;
        prefs=CommentFoodActivity.this.getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        String fullName = prefs.getString("Fullname","");
        return fullName;
    }
    private String getAvatar() {
        SharedPreferences prefs;
        prefs=CommentFoodActivity.this.getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        String avatar = prefs.getString("Avatar","");
        return avatar;
    }
    public void requestPermission(){
        if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED){
            return;
        }else{
            String[] permission = {Manifest.permission.READ_EXTERNAL_STORAGE};
            requestPermissions(permission, MY_REQUEST_CODE);
        }
    }

}
