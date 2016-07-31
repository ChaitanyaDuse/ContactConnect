package com.dnr.contactconnect;

import com.dnr.contactconnect.model.Contact;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {
    // Request method and URL specified in the annotation
    // Callback for the parsed response is the last parameter

    @GET("/contacts.json")
    Call<List<Contact>> getUser();


    @GET("/contacts/{id}.json")
    Call<Contact> getContactdetails(@Path("id") int contactId);

   /* @GET("group/{id}/users")
    Call<List<Contact>> getContacList(@Path("id") int groupId, @Query("sort") String sort);
*/

    @PUT("/contacts/{id}.json")
    Call<Contact> updateContact(@Path("id") int contactId,@Body Contact contact);

    @POST("/contacts.json")
    Call<Object> createContact(@Body Contact contact);
}