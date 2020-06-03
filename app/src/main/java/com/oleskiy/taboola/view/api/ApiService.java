package com.oleskiy.taboola.view.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.oleskiy.taboola.view.model.Item;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class ApiService {

    private static Retrofit retrofit = null;
    private static OkHttpClient.Builder okHttp;
    private static HttpLoggingInterceptor interceptor;

    public static Retrofit initApiService(){

        if(retrofit == null){
            retrofit = getRetrofit();
        }
        return retrofit;
    }


    public interface ApiInterface {
        @GET("public/home_assignment/data.json")
        Observable<List<Item>> getItems();
    }
    private static  Retrofit getRetrofit(){
        okHttp = new OkHttpClient.Builder();
        interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        okHttp.addInterceptor(interceptor);

        Gson gson = new GsonBuilder().setLenient().create();

        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttp.build())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl("https://s3-us-west-2.amazonaws.com/taboola-mobile-sdk/")
                .build();

        return retrofit;

    }
}
