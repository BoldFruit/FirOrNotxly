package com.neuqer.fitornot.network.response;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class RspCallback<T> implements Callback<T> {
    public abstract void onSuccess(T data);
    public abstract void onFailed(Throwable t);

    @Override
    public void onResponse(Call<T> call, Response<T> response) {

        onSuccess(response.body());
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        onFailed(t);

    }
}
