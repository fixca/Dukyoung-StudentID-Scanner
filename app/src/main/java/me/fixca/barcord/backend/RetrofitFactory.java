package me.fixca.barcord.backend;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import me.fixca.barcord.BuildConfig;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RetrofitFactory {

    private static RetrofitFactory instance;

    public static RetrofitFactory getInstance() {
        if(instance == null) {
            instance = new RetrofitFactory();
        }
        return instance;
    }

    public Retrofit getRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
