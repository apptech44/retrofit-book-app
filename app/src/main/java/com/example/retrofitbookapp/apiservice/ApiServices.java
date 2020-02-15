package com.example.retrofitbookapp.apiservice;

import com.example.retrofitbookapp.model.Book;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiServices {

    @FormUrlEncoded
    @POST("/volleyapi/v1/insert.php")
    Call<Book> insert(@FieldMap Map<String, String> map);

    @GET("/volleyapi/v1/display.php")
    Call<List<Book>> displays();
}
