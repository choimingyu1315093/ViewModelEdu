package com.example.viewmodeledu.api

import com.example.viewmodeledu.model.Album
import retrofit2.Call
import retrofit2.http.GET

interface AlbumApi {
    @GET("albums")
    fun getAlbums(): Call<List<Album>>
}