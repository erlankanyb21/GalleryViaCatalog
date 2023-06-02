package com.example.galleryviacatalog.ui.home.api

import com.example.galleryviacatalog.ui.home.model.GetProfile
import com.example.galleryviacatalog.ui.home.model.PhotoResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface ApiService {

    @GET("business/v1/businesses/255/")
    fun getProf(): Call<GetProfile>

    @Multipart
    @POST("/business/v1/profile/banners/")
    fun addGalleryPhoto(@Part editBanner: MultipartBody.Part?): Call<PhotoResponse>

    @DELETE("business/v1/profile/banners/{id}/")
    fun deletePhoto(
        @Path("id")id: Int
    ): Call<Unit>
}