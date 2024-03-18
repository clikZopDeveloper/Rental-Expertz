package com.example.rentalexpertz.Model


import com.google.gson.annotations.SerializedName

data class LeadDetailBean(
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("error")
    val error: Boolean, // false
    @SerializedName("msg")
    val msg: String // Data Load Successfully
) {
    data class Data(
        @SerializedName("address")
        val address: String, // In front of krishna institute of technology,  Village-amiliha post-tatiyaganj Mandhana ,kanpur
        @SerializedName("agent_id")
        val agentId: Int, // 3
        @SerializedName("campaign")
        val campaign: String, // Sushma
        @SerializedName("category_id")
        val categoryId: Any, // null
        @SerializedName("city")
        val city: String, // Kalyanpur
        @SerializedName("classification")
        val classification: Any, // null
        @SerializedName("client_id")
        val clientId: Int, // 1
        @SerializedName("comments")
        val comments: String, // Not interested
        @SerializedName("created_date")
        val createdDate: String, // 2022-05-23 22:35:45
        @SerializedName("doa")
        val doa: Any, // null
        @SerializedName("dob")
        val dob: Any, // null
        @SerializedName("email")
        val email: String, // omveer7522884264@gmail.com
        @SerializedName("id")
        val id: Int, // 3
        @SerializedName("last_updated")
        val lastUpdated: String, // 2022-05-22 21:26:51
        @SerializedName("mobile")
        val mobile: String, // 7522884264
        @SerializedName("name")
        val name: String, // Omveer singh2
        @SerializedName("project")
        val project: Any, // null
        @SerializedName("remind")
        val remind: Any, // null
        @SerializedName("source")
        val source: Any, // null
        @SerializedName("state")
        val state: Any, // null
        @SerializedName("status")
        val status: String, // pending
        @SerializedName("sub_category_id")
        val subCategoryId: Any, // null
        @SerializedName("type")
        val type: Any // null
    )
}