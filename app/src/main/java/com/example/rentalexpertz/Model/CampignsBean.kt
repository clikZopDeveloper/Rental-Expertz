package com.example.rentalexpertz.Model


import com.google.gson.annotations.SerializedName

data class CampignsBean(
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("error")
    val error: Boolean,
    @SerializedName("msg")
    val msg: String
) {
    data class Data(
        @SerializedName("campaign_name")
        val campaignName: String,
        @SerializedName("client_id")
        val clientId: Int,
        @SerializedName("created_date")
        val createdDate: String,
        @SerializedName("id")
        val id: Int
    )
}