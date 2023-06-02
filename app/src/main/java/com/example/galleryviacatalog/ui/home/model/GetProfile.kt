package com.example.galleryviacatalog.ui.home.model


import com.google.gson.annotations.SerializedName

data class GetProfile(
    @SerializedName("banner")
    val banner: String = "",
    @SerializedName("banners")
    val banners: List<Banner> = listOf(),
    @SerializedName("branches")
    val branches: List<Any> = listOf(),
    @SerializedName("catalogs")
    val catalogs: List<Catalog> = listOf(),
    @SerializedName("category")
    val category: Category = Category(),
    @SerializedName("contacts")
    val contacts: List<Contact> = listOf(),
    @SerializedName("creation_date")
    val creationDate: String = "",
    @SerializedName("deactivate_date")
    val deactivateDate: String = "",
    @SerializedName("description")
    val description: String = "",
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("is_not_demo")
    val isNotDemo: Boolean = false,
    @SerializedName("logo")
    val logo: String = "",
    @SerializedName("name")
    val name: String = "",
    @SerializedName("product_count")
    val productCount: Int = 0,
    @SerializedName("product_limit")
    val productLimit: Any? = null,
    @SerializedName("rating")
    val rating: Any? = null,
    @SerializedName("reviews")
    val reviews: List<Any> = listOf(),
    @SerializedName("reviews_count")
    val reviewsCount: Int = 0,
    @SerializedName("schedule")
    val schedule: List<Schedule> = listOf(),
    @SerializedName("time_left")
    val timeLeft: Int = 0,
    @SerializedName("unread_reviews_count")
    val unreadReviewsCount: Int = 0,
    @SerializedName("user")
    val user: User = User()
) {
    data class Banner(
        @SerializedName("business")
        val business: Int = 0,
        @SerializedName("id")
        val id: Int = 0,
        @SerializedName("photo")
        val photo: String = "",
        @SerializedName("text")
        val text: String? = null
    )

    data class Catalog(
        @SerializedName("business")
        val business: Int = 0,
        @SerializedName("id")
        val id: Int = 0,
        @SerializedName("name")
        val name: String = "",
        @SerializedName("product_count")
        val productCount: Int = 0,
        @SerializedName("products")
        val products: List<Any> = listOf(),
        @SerializedName("products_count")
        val productsCount: Int = 0
    )

    data class Category(
        @SerializedName("icon")
        val icon: Any? = null,
        @SerializedName("id")
        val id: Int = 0,
        @SerializedName("image")
        val image: Any? = null,
        @SerializedName("order")
        val order: Int = 0,
        @SerializedName("parent")
        val parent: Int = 0,
        @SerializedName("product_count")
        val productCount: Int = 0,
        @SerializedName("title_ky")
        val titleKy: String = "",
        @SerializedName("title_ru")
        val titleRu: String = "",
        @SerializedName("title_slug")
        val titleSlug: Any? = null
    )

    data class Contact(
        @SerializedName("business")
        val business: Int = 0,
        @SerializedName("id")
        val id: Int = 0,
        @SerializedName("type")
        val type: String = "",
        @SerializedName("value")
        val value: String = ""
    )

    data class Schedule(
        @SerializedName("business")
        val business: Int = 0,
        @SerializedName("id")
        val id: Int = 0,
        @SerializedName("time")
        val time: String = "",
        @SerializedName("weekday")
        val weekday: String = ""
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