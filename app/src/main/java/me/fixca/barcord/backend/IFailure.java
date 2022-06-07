package me.fixca.barcord.backend;

import retrofit2.Call;

public interface IFailure {
    void onFailure(Call call, Throwable t);
}
