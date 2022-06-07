package me.fixca.barcord.backend;

import retrofit2.Call;
import retrofit2.Response;

public interface IResponse {
    void onResponse(Call call, Response response);
}
