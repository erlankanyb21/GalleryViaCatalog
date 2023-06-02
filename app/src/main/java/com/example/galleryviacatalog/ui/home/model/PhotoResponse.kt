package com.example.galleryviacatalog.ui.home.model


import com.google.gson.annotations.SerializedName

data class PhotoResponse(
    @SerializedName("business")
    val business: Int = 0,
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("photo")
    val photo: String = "",
    @SerializedName("text")
    val text: String = ""
)