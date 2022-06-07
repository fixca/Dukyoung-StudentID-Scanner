package me.fixca.barcord.backend.logger;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LoggerService {

    @POST("api/logger")
    Call<LoggerResult> log(@Body LoggerBody body);
}
