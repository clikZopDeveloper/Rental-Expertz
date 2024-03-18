package com.example.rentalexpertz.Model


import com.google.gson.annotations.SerializedName

data class CityBean(
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("error")
    val error: Boolean,
    @SerializedName("msg")
    val msg: String
) {
    data class Data(
        @SerializedName("District")
        val district: String,
        @SerializedName("id")
        val id: Int,
        @SerializedName("state")
        val state: String
    )
}