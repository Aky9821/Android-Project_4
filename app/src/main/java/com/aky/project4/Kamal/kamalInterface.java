package com.aky.project4.Kamal;

import com.google.gson.JsonObject;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface kamalInterface {
    @GET("v6/99a07bdbe92123b83c586057/latest/{currency}")
    Call<JsonObject> getExchangeCurrency(@Path("currency") String currency);
}
