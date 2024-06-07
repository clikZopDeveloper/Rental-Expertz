package com.example.rentalexpertz.Model


import com.google.gson.annotations.SerializedName

data class DashboardBean(
    @SerializedName("data")
    val `data`: Data,
    @SerializedName("error")
    val error: Boolean,
    @SerializedName("msg")
    val msg: String
) {
    data class Data(
        @SerializedName("all_lead")
        val allLead: AllLead,
        @SerializedName("all_leads")
        val allLeads: AllLeads,
        @SerializedName("call_schedule")
        val callSchedule: List<CallSchedule>,
        @SerializedName("channel_partner")
        val channelPartner: List<ChannelPartner>,
        @SerializedName("missed_followup")
        val missedFollowup: List<MissedFollowup>,
        @SerializedName("today_lead")
        val todayLead: TodayLead,
        @SerializedName("visit_schedule")
        val visitSchedule: List<VisitSchedule>
    ) {
        data class AllLead(
            @SerializedName("call_scheduled")
            val callScheduled: String,
            @SerializedName("channel_partner")
            val channelPartner: String,
            @SerializedName("converted_leads")
            val convertedLeads: String,
            @SerializedName("interested_leads")
            val interestedLeads: String,
            @SerializedName("new_leads")
            val newLeads: String,
            @SerializedName("not_interested")
            val notInterested: String,
            @SerializedName("not_picked")
            val notPicked: String,
            @SerializedName("not_reachable")
            val notReachable: String,
            @SerializedName("others")
            val others: String,
            @SerializedName("pending_leads")
            val pendingLeads: String,
            @SerializedName("processing_leads")
            val processingLeads: String,
            @SerializedName("total_leads")
            val totalLeads: Int,
            @SerializedName("visit_done")
            val visitDone: String,
            @SerializedName("visit_scheduled")
            val visitScheduled: String,
            @SerializedName("booked")
            val booked: String,
            @SerializedName("cancelled")
            val cancelled: String,
            @SerializedName("completed")
            val completed: String,
            @SerializedName("wrong_number")
            val wrongNumber: String
        )

        data class AllLeads(
            @SerializedName("all_lead")
            val allLead: AllLead,
            @SerializedName("future_lead")
            val futureLead: FutureLead,
            @SerializedName("this_month_lead")
            val thisMonthLead: ThisMonthLead,
            @SerializedName("today_lead")
            val todayLead: TodayLead
        ) {
            data class AllLead(
                @SerializedName("total_leads")
                val totalLeads: Int
            )

            data class FutureLead(
                @SerializedName("total_leads")
                val totalLeads: Int
            )

            data class ThisMonthLead(
                @SerializedName("total_leads")
                val totalLeads: Int
            )

            data class TodayLead(
                @SerializedName("total_leads")
                val totalLeads: Int
            )
        }

        data class CallSchedule(
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

        data class ChannelPartner(
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

        data class TodayLead(
            @SerializedName("call_scheduled")
            val callScheduled: String,
            @SerializedName("channel_partner")
            val channelPartner: String,
            @SerializedName("converted_leads")
            val convertedLeads: String,
            @SerializedName("interested_leads")
            val interestedLeads: String,
            @SerializedName("new_leads")
            val newLeads: String,
            @SerializedName("not_interested")
            val notInterested: String,
            @SerializedName("not_picked")
            val notPicked: String,
            @SerializedName("not_reachable")
            val notReachable: String,
            @SerializedName("others")
            val others: String,
            @SerializedName("pending_leads")
            val pendingLeads: String,
            @SerializedName("processing_leads")
            val processingLeads: String,
            @SerializedName("total_leads")
            val totalLeads: Int,
            @SerializedName("visit_done")
            val visitDone: String,
            @SerializedName("visit_scheduled")
            val visitScheduled: String,
            @SerializedName("future_lead")
            val futureLead: String,
            @SerializedName("booked")
            val booked: String,
            @SerializedName("cancelled")
            val cancelled: String,
            @SerializedName("completed")
            val completed: String,
            @SerializedName("wrong_number")
            val wrongNumber: String
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
    }
}