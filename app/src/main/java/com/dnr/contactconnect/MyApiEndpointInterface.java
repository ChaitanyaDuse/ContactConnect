package com.dnr.contactconnect;

import com.dnr.contactconnect.model.Contact;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MyApiEndpointInterface {
    // Request method and URL specified in the annotation
    // Callback for the parsed response is the last parameter

    @GET("/contacts.json")
    Call<List<Contact>> getUser();

   /* @GET("group/{id}/users")
    Call<List<Contact>> getContacList(@Path("id") int groupId, @Query("sort") String sort);

    @POST("users/new")
    Call<Contact> createUser(@Body Contact user);*/
}