package com.example.cookguide.profile_fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.cookguide.ChangeNewPasswordActivity;
import com.example.cookguide.DescriptionFragment;
import com.example.cookguide.R;
import com.example.cookguide.api.ApiService;
import com.example.cookguide.interfaces.ISendDataFullName;
import com.example.cookguide.main_fragment.ProfileFragment;
import com.example.cookguide.models.UpdateProfileRequest;
import com.example.cookguide.models.UserResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DescriptionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    Button btnChangepw;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    TextView tx ;
    TextView email1;



    private EditText editTextPhone;
    private EditText editTextFullName;
    private ImageButton imageButtonPhone;
    private ImageButton imageButtonFullName;

    private boolean statusButtonFullName;
    private boolean statusButtonPhone;
    ISendDataFullName iSendDataFullName;
    public UserProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DescriptionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserProfileFragment newInstance(String param1, String param2) {
        UserProfileFragment fragment = new UserProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public void onAttachToParentFragment(Fragment fragment)
    {
        try
        {
            iSendDataFullName = (ISendDataFullName) fragment;

        }
        catch (ClassCastException e)
        {
            throw new ClassCastException(
                    fragment.toString() + " must implement OnPlayerSelectionSetListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onAttachToParentFragment(getParentFragment());
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View returnView= inflater.inflate(R.layout.user_fragment_profile, container, false);
        tx = (TextView) returnView.findViewById(R.id.usrn);
        email1 = returnView.findViewById(R.id.email);
        btnChangepw = returnView.findViewById(R.id.ChangePassword);
        //btnChangePhone = returnView.findViewById(R.id.ChangePhone);
        statusButtonFullName = false;
        statusButtonPhone = false;


        editTextFullName = returnView.findViewById(R.id.editTextFullName);
        imageButtonFullName = returnView.findViewById(R.id.imageButtonFullName);
        statusButtonFullName = false;
        imageButtonFullName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(statusButtonFullName){
                    imageButtonFullName.setImageResource(R.drawable.ic_baseline_border_color_24);
                    imageButtonFullName.setBackgroundResource(R.drawable.custom_shape_button_edit);
                    editTextFullName.setEnabled(false);
                    iSendDataFullName.sendDataFullName(editTextFullName.getText().toString());
                    updateProfile();
                }else{
                    imageButtonFullName.setImageResource(R.drawable.ic_baseline_check_edit_btn);
                    imageButtonFullName.setBackgroundResource(R.drawable.custom_shape_button_edit_change);
                    editTextFullName.setEnabled(true);
                }
                statusButtonFullName = !statusButtonFullName;
            }
        });
        editTextPhone = returnView.findViewById(R.id.editTextPhone);
        imageButtonPhone = returnView.findViewById(R.id.imageButtonPhone);
        statusButtonPhone = false;
        imageButtonPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(statusButtonPhone){
                    imageButtonPhone.setImageResource(R.drawable.ic_baseline_border_color_24);
                    imageButtonPhone.setBackgroundResource(R.drawable.custom_shape_button_edit);
                    editTextPhone.setEnabled(false);
                    updateProfile();
                }else{
                    imageButtonPhone.setImageResource(R.drawable.ic_baseline_check_edit_btn);
                    imageButtonPhone.setBackgroundResource(R.drawable.custom_shape_button_edit_change);
                    editTextPhone.setEnabled(true);
                }
                statusButtonPhone = !statusButtonPhone;
            }
        });


        ApiService.apiService.getProfile(getToken()).enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                UserResponse userResponse = response.body();
                //Toast.makeText(getContext(),response.body().getUsername(), Toast.LENGTH_SHORT).show();
                tx.setText(userResponse.getUsername());
                email1.setText(userResponse.getEmail());
                editTextPhone.setText(userResponse.getPhone());
                editTextFullName.setText(userResponse.getFullName());
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {

            }
        });

        btnChangepw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ChangeNewPasswordActivity.class);
                startActivity(intent);
            }
        });
//        if(getToken()!=null){
//            ApiService.apiService.getProfile(getToken()).enqueue(new Callback<UserResponse>() {
//                @Override
//                public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
//                    UserResponse userResponse = response.body();
//                    SharedPreferences prefs;
//                    SharedPreferences.Editor edit;
//                    prefs=getContext().getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
//                    edit=prefs.edit();
//                    String saveFullname = userResponse.getFullName();
//                    edit.putString("Fullname",saveFullname);
//                    String saveUsername = userResponse.getUsername();
//                    edit.putString("username",saveUsername);
//                    String savePhone = userResponse.getPhone();
//                    edit.putString("Phone",savePhone);
//                    String saveEmail = userResponse.getEmail();
//                    edit.putString("Email",saveEmail);
//                    String saveAvatar = userResponse.getAvatar();
//                    edit.putString("Avatar",saveAvatar);
//                    edit.commit();
//                }
//
//                @Override
//                public void onFailure(Call<UserResponse> call, Throwable t) {
//
//                }
//            });
//        }
        //tx1.setText("Abc");
        return  returnView;

    }

    private void updateProfile(){
        UpdateProfileRequest updateProfileRequest = new UpdateProfileRequest();
        updateProfileRequest.setFullName(editTextFullName.getText().toString());
        updateProfileRequest.setPhone(editTextPhone.getText().toString());
        ApiService.apiService.updateProfile(getToken(),updateProfileRequest).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                Boolean result = response.body();
                if(result){
                    Toast.makeText(getContext(),"successfully",Toast.LENGTH_LONG).show();
//                    new Handler().postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//
//                            //startActivity(new Intent(LoginActivity.this,UserProfileActivity.class).putExtra("data",token));
////                            Intent intent = new Intent(getContext(), ProfileFragment.class);
////                            intent.putExtra("fullname",updateProfileRequest.getFullName());
////                            startActivity(intent);
//                        }
//                    },700);
                }else {
                    Toast.makeText(getContext(),"Failed",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Toast.makeText(getContext(),t.getLocalizedMessage(),Toast.LENGTH_LONG).show();
            }
        });


    }


    private String getToken() {
        SharedPreferences prefs;
        prefs=getContext().getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        String token = prefs.getString("token","");
        return "Bearer "+token;
    }
}