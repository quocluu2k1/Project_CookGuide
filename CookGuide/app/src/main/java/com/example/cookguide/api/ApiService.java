package com.example.cookguide.api;

import android.os.storage.StorageManager;

import com.example.cookguide.common.ChangepwRequest;
import com.example.cookguide.models.Comment;
import com.example.cookguide.models.Food;
import com.example.cookguide.models.HistoryComment;
import com.example.cookguide.models.HistorySearch;
import com.example.cookguide.models.Implementation;
import com.example.cookguide.models.Ingredient;
import com.example.cookguide.models.LoginRequest;
import com.example.cookguide.models.LoginResponse;
import com.example.cookguide.models.SignupRequest;
import com.example.cookguide.models.SignupResponse;
import com.example.cookguide.models.UpdateProfileRequest;
import com.example.cookguide.models.UserResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {


    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();
    ApiService apiService = new Retrofit.Builder()
            .baseUrl("http://projectappcookguide.koreacentral.cloudapp.azure.com/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService.class);
    @GET("api/cookguide/detail/{id}")
    Call<Food> getDetailCookGuide(@Header("Authorization")String token,@Path("id") Long id);

    @GET("api/cookguide/getingredient/{id}")
    Call<List<Ingredient>> getIngredient(@Header("Authorization")String token,@Path("id") Long id);

    @GET("api/cookguide/getimplementation/{id}")
    Call<List<Implementation>> getImplementation(@Header("Authorization")String token, @Path("id") Long id);

    @GET("api/cookguide/getstatusbookmarkuser/{id}")
    Call<Boolean> getStatusBookmarkUser(@Header("Authorization")String token,@Path("id") Long id);

    @GET("api/cookguide/addbookmark/{id}")
    Call<Boolean> addBookmarkUser(@Header("Authorization")String token,@Path("id") Long id);

    @GET("api/cookguide/deletebookmark/{id}")
    Call<Boolean> deleteBookmarkUser(@Header("Authorization")String token,@Path("id") Long id);

    @GET("api/cookguide/getstatusreactclap/{id}")
    Call<Boolean> getStatusReactClap(@Header("Authorization")String token,@Path("id") Long id);

    @GET("api/cookguide/getstatusreactheart/{id}")
    Call<Boolean> getStatusReactHeart(@Header("Authorization")String token,@Path("id") Long id);

    @GET("api/cookguide/getstatusreactsavoring/{id}")
    Call<Boolean> getStatusReactSavoring(@Header("Authorization")String token,@Path("id") Long id);

    @GET("api/cookguide/addreactclap/{id}")
    Call<Boolean> addReactClap(@Header("Authorization")String token,@Path("id") Long id);

    @GET("api/cookguide/addreactheart/{id}")
    Call<Boolean> addReactHeart(@Header("Authorization")String token,@Path("id") Long id);

    @GET("api/cookguide/addreactsavoring/{id}")
    Call<Boolean> addReactSavoring(@Header("Authorization")String token,@Path("id") Long id);

    @GET("api/cookguide/deletereactclap/{id}")
    Call<Boolean> deleteReactClap(@Header("Authorization")String token,@Path("id") Long id);

    @GET("api/cookguide/deletereactheart/{id}")
    Call<Boolean> deleteReactHeart(@Header("Authorization")String token,@Path("id") Long id);

    @GET("api/cookguide/deletereactsavoring/{id}")
    Call<Boolean> deleteReactSavoring(@Header("Authorization")String token,@Path("id") Long id);

    @GET("api/home/getoutstandingfood/1")
    Call<List<Food>> getOutstandingFood();

    @GET("api/home/getnewfood/1")
    Call<List<Food>> getNewFood();

    @GET("api/home/getfavoritefood/1")
    Call<List<Food>> getFavoriteFood();

    @GET("api/comment/history")
    Call<List<HistoryComment>> getHistoryComment(@Header("Authorization")String token);

    @GET("api/comment/delete/{cmtId}")
    Call<Boolean> deleteComment(@Header("Authorization")String token, @Path("cmtId") Long cmtId);

    @GET("api/search/featuredkeywords")
    Call<List<HistorySearch>> getSuggestionSearch(@Header("Authorization")String token);

    @GET("api/search/suggestion")
    Call<List<Food>> getSuggestionFood(@Header("Authorization")String token);

    @GET("api/search/recent")
    Call<List<HistorySearch>> getHistorySearch(@Header("Authorization")String token);

    @GET("api/search/recent/delete/{hisId}")
    Call<Boolean> deleteHistorySearch(@Header("Authorization")String token, @Path("hisId") Long hisId);


    //////////////////////////////

    @POST("api/auth/signin")
    Call<LoginResponse> userLogin(@Body LoginRequest loginRequest);

    @GET("api/user/generateotp")
    Call<String> forgotPassword (@Query("email") String email);

    @Multipart
    @POST("api/user/changepassword")
    Call<Boolean> changePassword(@Header("Authorization") String token,
                                 @Part(ChangepwRequest.KEY_OLDPASSWORD) RequestBody oldpassword,
                                 @Part(ChangepwRequest.KEY_NEWPASSWORD) RequestBody newpassword);

    @POST("api/auth/signup")
    Call<SignupResponse> userSignup(@Body SignupRequest signupRequest);

    @GET("api/user/getbookmark")
    Call<ArrayList<Food>> getMarkbook (@Header("Authorization") String token);

    @GET("api/user/profile")
    Call<UserResponse> getProfile(@Header("Authorization") String token);

    @PUT("api/user/profile/update")
    Call<Boolean> updateProfile (@Header("Authorization") String token, @Body UpdateProfileRequest updateProfileRequest);
    //@GET(api/user/validateotp)

    @GET("api/auth/verify")
    Call<Boolean> verifyToken(@Header("Authorization") String token);

    @GET("api/search/namefood")
    Call<List<Food>> searchByNameFood(@Header("Authorization") String token, @Query("new") int status,  @Query("q") String query);

    @GET("api/search/namematerial")
    Call<List<Food>> searchByNameMaterial(@Header("Authorization") String token, @Query("new") int status,  @Query("q") String query);

    @GET("api/search/description")
    Call<List<Food>> searchByDescription(@Header("Authorization") String token, @Query("new") int status,  @Query("q") String query);

    @GET("api/comment/get")
    Call<List<Comment>> getCommentByFoodId(@Header("Authorization") String token, @Query("foodid") Long foodId, @Query("npage") int nPage);

    @Multipart
    @POST("api/comment/add")
    Call<Boolean> commentFood(@Header("Authorization") String token,
                              @Part("foodId") RequestBody foodId,
                              @Part("content") RequestBody content,
                              @Part MultipartBody.Part file);

    @Multipart
    @POST("api/comment/addnormal")
    Call<Boolean> commentFoodNormal(@Header("Authorization") String token,
                              @Part("foodId") RequestBody foodId,
                              @Part("content") RequestBody content);

    @Multipart
    @POST("api/user/changeavatar")
    Call<Boolean> changeAvatar(@Header("Authorization") String token,
                              @Part MultipartBody.Part file);

}