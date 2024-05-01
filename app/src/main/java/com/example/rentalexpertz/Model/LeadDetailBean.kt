package com.example.rentalexpertz.Model


import com.google.gson.annotations.SerializedName

data class LeadDetailBean(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("error")
    val error: Boolean,
    @SerializedName("msg")
    val msg: String
) {
    data class Data(
        @SerializedName("lead_comments")
        val leadComments: List<LeadComment>,
        @SerializedName("lead_data")
        val leadData: List<LeadData>
    ) {
        data class LeadComment(
            @SerializedName("agent_id")
            val agentId: Int,
            @SerializedName("client_id")
            val clientId: Int,
            @SerializedName("comment")
            val comment: String,
            @SerializedName("created_date")
            val createdDate: String,
            @SerializedName("id")
            val id: Int,
            @SerializedName("lead_id")
            val leadId: Int,
            @SerializedName("remind")
            val remind: Any,
            @SerializedName("status")
            val status: String,
            @SerializedName("user_name")
            val userName: String
        )

        data class LeadData(
            @SerializedName("address")
            val address: String,
            @SerializedName("agent_id")
            val agentId: Int,
            @SerializedName("campaign")
            val campaign: String,
            @SerializedName("category")
            val category: String,
            @SerializedName("category_id")
            val categoryId: Int,
            @SerializedName("city")
            val city: String,
            @SerializedName("classification")
            val classification: String,
            @SerializedName("client_id")
            val clientId: Int,
            @SerializedName("comments")
            val comments: String,
            @SerializedName("created_date")
            val createdDate: String,
            @SerializedName("doa")
            val doa: Any,
            @SerializedName("dob")
            val dob: Any,
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
            @SerializedName("remind")
            val remind: Any,
            @SerializedName("source")
            val source: String,
            @SerializedName("state")
            val state: String,
            @SerializedName("status")
            val status: String,
            @SerializedName("sub_category")
            val subCategory: String,
            @SerializedName("sub_category_id")
            val subCategoryId: Int,
            @SerializedName("type")
            val type: String
        )
    }
}