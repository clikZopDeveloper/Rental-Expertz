package com.example.rentalexpertz.Model


import com.google.gson.annotations.SerializedName

data class AllTaskListBean(
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("error")
    val error: Boolean,
    @SerializedName("msg")
    val msg: String
) {
    data class Data(
        @SerializedName("agent_id")
        val agentId: Int,
        @SerializedName("client_id")
        val clientId: Int,
        @SerializedName("created_at")
        val createdAt: String,
        @SerializedName("id")
        val id: Int,
        @SerializedName("status")
        val status: String,
        @SerializedName("task")
        val task: String,
        @SerializedName("updated_at")
        val updatedAt: String
    )
}