package com.example.galleryviacatalog.ui.home.api

import com.example.galleryviacatalog.ui.home.model.CatalogResponse
import com.example.galleryviacatalog.ui.home.model.GetProductsDto
import com.example.galleryviacatalog.ui.home.model.GetProfile
import com.example.galleryviacatalog.ui.home.model.PhotoResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
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

    /*____________________________________*/

    @POST("/business/v1/businesses/{businessId}/catalog/")
    @FormUrlEncoded
    fun createCatalog(
        @Path("businessId") businessId: Int,
        @Field("name") name:String
    ): Call<CatalogResponse>

    @GET("/product/v1/personal/products/")
    fun getProducts(): Call<GetProductsDto>

}