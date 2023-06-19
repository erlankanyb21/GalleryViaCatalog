package com.example.galleryviacatalog.ui.home.model


import com.google.gson.annotations.SerializedName

data class GetProductsDto(
    @SerializedName("count")
    val count: Int = 0,
    @SerializedName("next")
    val next: Any? = null,
    @SerializedName("previous")
    val previous: Any? = null,
    @SerializedName("results")
    val results: List<Result> = listOf()
) {
    data class Result(
        @SerializedName("category")
        val category: Int = 0,
        @SerializedName("creation_date")
        val creationDate: String = "",
        @SerializedName("currency")
        val currency: String = "",
        @SerializedName("discount")
        val discount: Any? = null,
        @SerializedName("discount_price_kgs")
        val discountPriceKgs: Any? = null,
        @SerializedName("discount_price_usd")
        val discountPriceUsd: Any? = null,
        @SerializedName("favorite_count")
        val favoriteCount: Int = 0,
        @SerializedName("id")
        val id: Int = 0,
        @SerializedName("initial_price")
        val initialPrice: String = "",
        @SerializedName("lat")
        val lat: String = "",
        @SerializedName("lng")
        val lng: String = "",
        @SerializedName("location")
        val location: Location = Location(),
        @SerializedName("photos")
        val photos: List<Photo> = listOf(),
        @SerializedName("price_kgs")
        val priceKgs: String = "",
        @SerializedName("price_usd")
        val priceUsd: String = "",
        @SerializedName("rating")
        val rating: Any? = null,
        @SerializedName("rating_disabled")
        val ratingDisabled: Boolean = false,
        @SerializedName("show_count")
        val showCount: Int = 0,
        @SerializedName("state")
        val state: String = "",
        @SerializedName("title")
        val title: String = "",
        @SerializedName("upvote_date")
        val upvoteDate: String = "",
        @SerializedName("user")
        val user: User = User(),
        @SerializedName("view_count")
        val viewCount: Int = 0
    ) {
        data class Location(
            @SerializedName("id")
            val id: Int = 0,
            @SerializedName("is_end")
            val isEnd: Boolean = false,
            @SerializedName("lat")
            val lat: String = "",
            @SerializedName("lng")
            val lng: String = "",
            @SerializedName("parent")
            val parent: Int = 0,
            @SerializedName("region")
            val region: String? = null,
            @SerializedName("request_ky")
            val requestKy: String? = null,
            @SerializedName("request_ru")
            val requestRu: String = "",
            @SerializedName("title_ky")
            val titleKy: String = "",
            @SerializedName("title_ru")
            val titleRu: String = "",
            @SerializedName("type")
            val type: String = "",
            @SerializedName("type_ky")
            val typeKy: String = "",
            @SerializedName("type_ru")
            val typeRu: String = ""
        )

        data class Photo(
            @SerializedName("photo")
            val photo: String = ""
        )

        data class User(
            @SerializedName("balance")
            val balance: Int = 0,
            @SerializedName("business")
            val business: Int = 0,
            @SerializedName("business_name")
            val businessName: String = "",
            @SerializedName("date_joined")
            val dateJoined: String = "",
            @SerializedName("email")
            val email: String = "",
            @SerializedName("id")
            val id: Int = 0,
            @SerializedName("is_business")
            val isBusiness: Boolean = false,
            @SerializedName("language")
            val language: String = "",
            @SerializedName("last_active")
            val lastActive: String = "",
            @SerializedName("last_seen_ky")
            val lastSeenKy: String = "",
            @SerializedName("last_seen_ru")
            val lastSeenRu: String = "",
            @SerializedName("name")
            val name: String = "",
            @SerializedName("phone")
            val phone: Any? = null,
            @SerializedName("photo")
            val photo: Any? = null,
            @SerializedName("request_status")
            val requestStatus: String = ""
        )
    }
}