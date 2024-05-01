package com.example.rentalexpertz.Model


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class SearchBean(
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("error")
    val error: Boolean,
    @SerializedName("msg")
    val msg: String
):Serializable {
    data class Data(
        @SerializedName("address")
        val address: String,
        @SerializedName("agent_id")
        val agentId: Int,
        @SerializedName("campaign")
        val campaign: String,
        @SerializedName("campaign_id")
        val campaignId: String,
        @SerializedName("category")
        val category: String,
        @SerializedName("category_id")
        val categoryId: Int,
        @SerializedName("city")
        val city: String,
        @SerializedName("classification")
        val classification: String,
        @SerializedName("classification_id")
        val classificationId: Int,
        @SerializedName("client_id")
        val clientId: Int,
        @SerializedName("comments")
        val comments: String,
        @SerializedName("created_date")
        val createdDate: String,
        @SerializedName("doa")
        val doa: String,
        @SerializedName("dob")
        val dob: String,
        @SerializedName("email")
        val email: String,
        @SerializedName("id")
        val id: Int,
        @SerializedName("last_updated")
        val lastUpdated: String,
        @SerializedName("mobile")
        val mobile: String,
        @SerializedName("name")
        val name: String,
        @SerializedName("project")
        val project: String,
        @SerializedName("project_id")
        val projectId: Int,
        @SerializedName("remind")
        val remind: Any,
        @SerializedName("source")
        val source: String,
        @SerializedName("source_id")
        val sourceId: Int,
        @SerializedName("state")
        val state: String,
        @SerializedName("status")
        val status: String,
        @SerializedName("sub_category")
        val subCategory: String,
        @SerializedName("sub_category_id")
        val subCategoryId: Int,
        @SerializedName("type")
        val type: String,
        @SerializedName("type_id")
        val typeId: Int
    ):Serializable
}