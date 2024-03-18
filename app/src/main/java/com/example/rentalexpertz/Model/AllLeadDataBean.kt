package com.example.rentalexpertz.Model


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class AllLeadDataBean(
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("error")
    val error: Boolean, // false
    @SerializedName("msg")
    val msg: String
):Serializable {
    data class Data(
        @SerializedName("address")
        val address: String, // sdfsf
        @SerializedName("agent_id")
        val agentId: Int, // 40
        @SerializedName("campaign")
        val campaign: String, // 12
        @SerializedName("campaign_id")
        val campaignId: String, // 12
        @SerializedName("category")
        val category: String, // 2BHK
        @SerializedName("category_id")
        val categoryId: Int, // 2
        @SerializedName("city")
        val city: String, // Daman
        @SerializedName("classification")
        val classification: String, // Hot
        @SerializedName("classification_id")
        val classificationId: Int, // 2
        @SerializedName("client_id")
        val clientId: Int, // 23
        @SerializedName("comments")
        val comments: String, // <hr>2024-03-16 13:35:50 - fsdfsdf
        @SerializedName("created_date")
        val createdDate: String, // 2024-03-16 13:35:50
        @SerializedName("doa")
        val doa: Any, // null
        @SerializedName("dob")
        val dob: Any, // null
        @SerializedName("email")
        val email: String, // werwer
        @SerializedName("id")
        val id: Int, // 3
        @SerializedName("last_updated")
        val lastUpdated: String, // 2024-03-16 13:35:50
        @SerializedName("mobile")
        val mobile: String, // 9874444345
        @SerializedName("name")
        val name: String, // werwer
        @SerializedName("project")
        val project: String, // Sushma
        @SerializedName("project_id")
        val projectId: Int, // 2
        @SerializedName("remind")
        val remind: Any, // null
        @SerializedName("source")
        val source: String, // Instagram
        @SerializedName("source_id")
        val sourceId: Int, // 2
        @SerializedName("state")
        val state: String, // Daman Diu
        @SerializedName("status")
        val status: String, // new lead
        @SerializedName("sub_category")
        val subCategory: String, // High Rise Apertmanet
        @SerializedName("sub_category_id")
        val subCategoryId: Int, // 2
        @SerializedName("type")
        val type: String, // Residential
        @SerializedName("type_id")
        val typeId: Int // 2
    ):Serializable
}