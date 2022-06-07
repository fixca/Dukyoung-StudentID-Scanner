package me.fixca.barcord.backend;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * T should be a Result class
 * */

public class CallBackAdapter<T> implements Callback<T> {

    private IResponse iResponse;
    private IFailure iFailure;

    public void setIFailure(IFailure iFailure) {
        this.iFailure = iFailure;
    }

    public void setIResponse(IResponse iResponse) {
        this.iResponse = iResponse;
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        iResponse.onResponse(call, response);
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        iFailure.onFailure(call, t);
    }
}
