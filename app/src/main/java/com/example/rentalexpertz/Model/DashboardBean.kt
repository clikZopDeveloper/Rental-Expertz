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