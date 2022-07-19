package com.example.cookguide.main_fragment;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.example.cookguide.CommentFoodActivity;
import com.example.cookguide.LoginActivity;
import com.example.cookguide.MainActivity;
import com.example.cookguide.R;
import com.example.cookguide.adapters.UserprofileAdapter;
import com.example.cookguide.api.ApiService;
import com.example.cookguide.common.Constant;
import com.example.cookguide.common.RealPathUtil;
import com.example.cookguide.interfaces.ISendDataFullName;
import com.example.cookguide.models.UserResponse;
import com.google.android.gms.common.api.Api;
import com.google.android.material.tabs.TabLayout;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import de.hdodenhof.circleimageview.CircleImageView;
import me.relex.circleindicator.CircleIndicator;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment implements ISendDataFullName {

    private static final int MY_REQUEST_CODE = 1;
    private TabLayout tabLayout;
    private ViewPager detailViewPager;
    private TextView fullN;
    private TextView username;
    public Button btnChangepw;
    CircleImageView imageButton;
    Uri mUri;

    Button btnLogout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile,container,false);

        tabLayout = view.findViewById(R.id.tab_layout_userprofile);
        detailViewPager = view.findViewById(R.id.view_pager_userprofile);
        btnLogout = (Button) view.findViewById(R.id.btnLogout);
        fullN = view.findViewById(R.id.fullName);
        username = view.findViewById(R.id.username);
        imageButton = view.findViewById(R.id.imageButton);

        UserprofileAdapter userprofileAdapter = new UserprofileAdapter(getChildFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        detailViewPager.setAdapter(userprofileAdapter);
        tabLayout.setupWithViewPager(detailViewPager);

        ApiService.apiService.getProfile(getToken()).enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                UserResponse userResponse = response.body();
                fullN.setText(userResponse.getFullName());
                username.setText("@"+userResponse.getUsername());
                String url = Constant.DOMAIN_NAME+getAvatar();

                Glide.with(getContext())
                        .load(url)
                        .placeholder(R.drawable.avatar_default)
                        .error(R.drawable.avatar_default)
                        .centerCrop()
                        .into(imageButton);
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {

            }
        });
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
                removeToken();
            }
        });


        imageButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                requestPermission();
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, 1);
            }
        });

        return view;
    }

    private String getToken() {
        SharedPreferences prefs;
        prefs=getContext().getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        String token = prefs.getString("token","");
        return "Bearer "+token;
    }
    private String getAvatar() {
        SharedPreferences prefs;
        prefs=getContext().getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        String avatar = prefs.getString("Avatar","");
        return avatar;
    }

    private void removeToken() {
        SharedPreferences prefs;
        SharedPreferences.Editor edit;
        prefs=getContext().getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        edit=prefs.edit();
        edit.remove("token");
        edit.remove("Fullname");
        edit.remove("username");
        edit.remove("Phone");
        edit.remove("Email");
        edit.remove("Avatar");

        edit.commit();
    }

    @Override
    public void sendDataFullName(String fullName) {
        fullN.setText(fullName);
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

    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            try {
                final Uri imageUri = data.getData();
                mUri = imageUri;
                final InputStream imageStream = getContext().getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                imageButton.setImageBitmap(selectedImage);
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
                Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_LONG).show();
            }

        }else {
            Toast.makeText(getContext(), "You haven't picked Image",Toast.LENGTH_LONG).show();
        }
    }

    public void requestPermission(){
        if(getContext().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED){
            return;
        }else{
            String[] permission = {Manifest.permission.READ_EXTERNAL_STORAGE};
            requestPermissions(permission, MY_REQUEST_CODE);
        }
    }
}
