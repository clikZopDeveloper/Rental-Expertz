package com.example.rentalexpertz.Model


import com.google.gson.annotations.SerializedName

data class DashboardBean(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("error")
    val error: Boolean, // false
    @SerializedName("msg")
    val msg: String // Data Load Successfully
) {
    data class Data(
        @SerializedName("all_lead")
        val allLead: AllLead,
        @SerializedName("all_leads")
        val allLeads: AllLeads,
        @SerializedName("today_lead")
        val todayLead: TodayLead,
        @SerializedName("call_visit_schedule")
        val callVisitSchedule: List<CallVisitSchedule>,
        @SerializedName("visit_schedule")
        val visitSchedule: List<VisitSchedule>,
        @SerializedName("missed_followup")
        val missedFollowup: List<MissedFollowup>,
        @SerializedName("today_leads")
        val todayLeads: TodayLeads
    ) {
        data class AllLead(
            @SerializedName("call_scheduled")
            val callScheduled: String, // 0
            @SerializedName("channel_partner")
            val channelPartner: String, // 0
            @SerializedName("converted_leads")
            val convertedLeads: String, // 0
            @SerializedName("interested_leads")
            val interestedLeads: String, // 0
            @SerializedName("new_leads")
            val newLeads: String, // 2
            @SerializedName("not_interested")
            val notInterested: String, // 0
            @SerializedName("not_picked")
            val notPicked: String, // 0
            @SerializedName("not_reachable")
            val notReachable: String, // 0
            @SerializedName("others")
            val others: String, // 0
            @SerializedName("pending_leads")
            val pendingLeads: String, // 0
            @SerializedName("processing_leads")
            val processingLeads: String, // 1
            @SerializedName("total_leads")
            val totalLeads: Int, // 3
            @SerializedName("visit_done")
            val visitDone: String, // 0
            @SerializedName("visit_scheduled")
            val visitScheduled: String, // 0
            @SerializedName("wrong_number")
            val wrongNumber: String // 0
        )

        data class AllLeads(
            @SerializedName("total_leads")
            val totalLeads: Int // 3
        )
        data class CallVisitSchedule(
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
            val remind: String,
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
        )
        data class MissedFollowup(
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
            val remind: String,
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
        )
        data class VisitSchedule(
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
            @SerializedName("project_id")
            val projectId: Int,
            @SerializedName("remind")
            val remind: String,
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
        )
        data class TodayLead(
            @SerializedName("call_scheduled")
            val callScheduled: String, // 0
            @SerializedName("channel_partner")
            val channelPartner: String, // 0
            @SerializedName("converted_leads")
            val convertedLeads: String, // 0
            @SerializedName("interested_leads")
            val interestedLeads: String, // 0
            @SerializedName("new_leads")
            val newLeads: String, // 2
            @SerializedName("not_interested")
            val notInterested: String, // 0
            @SerializedName("not_picked")
            val notPicked: String, // 0
            @SerializedName("not_reachable")
            val notReachable: String, // 0
            @SerializedName("others")
            val others: String, // 0
            @SerializedName("pending_leads")
            val pendingLeads: String, // 0
            @SerializedName("processing_leads")
            val processingLeads: String, // 1
            @SerializedName("total_leads")
            val totalLeads: Int, // 3
            @SerializedName("visit_done")
            val visitDone: String, // 0
            @SerializedName("visit_scheduled")
            val visitScheduled: String, // 0
            @SerializedName("wrong_number")
            val wrongNumber: String // 0
        )

        data class TodayLeads(
            @SerializedName("total_leads")
            val totalLeads: Int // 3
        )
    }
}