package com.example.galleryviacatalog.ui.home.model


import com.google.gson.annotations.SerializedName

data class CatalogResponse(
    @SerializedName("business")
    val business: Int? = null,
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("products")
    val products: List<Any>? = null
)