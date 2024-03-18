package com.example.rentalexpertz.Model


import com.google.gson.annotations.SerializedName

data class SubCategoryBean(
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("error")
    val error: Boolean,
    @SerializedName("msg")
    val msg: String
) {
    data class Data(
        @SerializedName("category_id")
        val categoryId: Int,
        @SerializedName("client_id")
        val clientId: Int,
        @SerializedName("created_at")
        val createdAt: String,
        @SerializedName("id")
        val id: Int,
        @SerializedName("name")
        val name: String
    )
}