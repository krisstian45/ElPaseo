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
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.addInterceptor(new LoggingInterceptor());

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(httpClient.build())
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

    static class LoggingInterceptor implements Interceptor {
        @Override public Response intercept(Interceptor.Chain chain) throws IOException {
            Request request = chain.request();

            long t1 = System.nanoTime();
           /* Log.info(String.format("Sending request %s on %s%n%s",
                    request.url(), chain.connection(), request.headers()));
*/
            Response response = chain.proceed(request);

            long t2 = System.nanoTime();
            /*logger.info(String.format("Received response for %s in %.1fms%n%s",
                    response.request().url(), (t2 - t1) / 1e6d, response.headers()));
*/
            return response;
        }
    }

}