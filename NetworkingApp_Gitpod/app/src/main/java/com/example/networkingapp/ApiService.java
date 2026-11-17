package com.example.networkingapp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {
    @GET("posts/{id}")
    Call<Post> getPost(@Path("id") int postId);
}
