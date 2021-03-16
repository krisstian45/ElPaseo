package com.example.elpaseov4.network;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class RetrofitClientInstance{
    private static Retrofit retrofit;
    private static final String BASE_URL = "http://ec2-3-227-239-131.compute-1.amazonaws.com";

    public static  Retrofit getRetrofitInstance(){
        if (retrofit == null ){
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                    .build();
        }
        return retrofit;
    }
}