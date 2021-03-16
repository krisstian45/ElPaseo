package com.example.elpaseov4.network;

import com.example.elpaseov4.model.Category;
import com.example.elpaseov4.model.News;
import com.example.elpaseov4.model.Producer;
import com.example.elpaseov4.model.Product;
import com.example.elpaseov4.model.User;
import com.example.elpaseov4.model.UserLogin;
import com.example.elpaseov4.pojoModel.GeneralPojo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ServiceRetrofit {
    @GET("/api/product")
    Call<List<Product>> getProducts();

    @GET("/api/category")
    Call<List<Category>> getCategories();

    @GET("/api/news")
    Call<PaginationNews> getNews();

    @GET("/api/producer")
    Call<List<Producer>> getProducers();

    @POST("/api/user/signup")
    Call<List<User>> postUserSignup();

    @POST("api/token/generate-token")
    Call<UserLogin> login(@Body LoginUser loginUser);

    @GET("/api/product")
    Call<Pagination> getProducts(
            @Query("range") String range,  // 0-10
            @Query("sort") String fieldsToSort, //id, desc
            @Query("properties") String properties);

    @POST("/api/cart")
    Call<CartPostResponse> postCart(@Header("Authorization") String token,
                  @Body CartPost newCart);

    @GET("/api/cart")
    Call<List<CartPostResponse>> getCart(@Header("Authorization") String token,
                                         @Query("range") String range,  // 0-10
                                         @Query("sort") String fieldsToSort, //id, desc
                                         @Query("properties") String properties);

    @GET("/api/general/active")
    Call<NodeGeneral> getGeneralActive();
}
