package com.example.pear;

import retrofit2.Call;
import retrofit2.http.GET;

interface MainInterface {
    @GET("user/previous_orders/Qu2cRybfWGMaki7eJtk2O0oxE3y2")
    Call<String> STRING_CALL();

}
