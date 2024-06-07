package com.example.rentalexpertz.Model


import com.google.gson.annotations.SerializedName

data class GetAllStatusBean(
    @SerializedName("data")
    val `data`: List<String>,
    @SerializedName("error")
    val error: Boolean, // false
    @SerializedName("msg")
    val msg: String // Data Load Successfully
)