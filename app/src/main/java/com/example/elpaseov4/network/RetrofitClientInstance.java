package com.example.elpaseov4.network;

import android.util.Log;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class RetrofitClientInstance{
    private static Retrofit retrofit;
    private static final String BASE_URL = "http://ec2-3-227-239-131.compute-1.amazonaws.com";

    public static  Retrofit getRetrofitInstance(String authorizationValue) {
        if (retrofit == null) {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(getHeader(authorizationValue))
                    .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                    .build();
        }

        return retrofit;
    }public static  Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                    .build();
        }

        return retrofit;
    }

    public static OkHttpClient getHeader(final String authorizationValue ) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okClient = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addNetworkInterceptor(
                        new Interceptor() {
                            @Override
                            public Response intercept(Interceptor.Chain chain) throws IOException {
                                Request request = null;
                                if (authorizationValue != null) {
                                    Log.d("--Authorization-- ", authorizationValue);

                                    Request original = chain.request();
                                    // Request customization: add request headers
                                    Request.Builder requestBuilder = original.newBuilder()
                                            .addHeader("Authorization", authorizationValue);

                                    request = requestBuilder.build();
                                }
                                return chain.proceed(request);
                            }
                        })
                .build();
        return okClient;

    }
    public static class AddHeaderInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {

            Request.Builder builder = chain.request().newBuilder();
            builder.addHeader("Authorization", "MyauthHeaderContent");

            return chain.proceed(builder.build());
        }
    }
}