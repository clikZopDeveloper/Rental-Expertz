package com.example.rentalexpertz.Adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.rentalexpertz.Activity.AddLeadActivity
import com.example.rentalexpertz.Activity.UpdateLeadActivity
import com.example.rentalexpertz.Model.AllLeadDataBean
import com.example.rentalexpertz.Model.AllTaskListBean
import com.example.rentalexpertz.Model.DashboardBean
import com.example.rentalexpertz.R
import com.example.rentalexpertz.Utills.GeneralUtilities
import com.example.rentalexpertz.Utills.RvStatusClickListner
import com.stpl.antimatter.Utils.ApiContants


class VisitSechduleAdapter(
    var context: Activity,
    var mFilteredList: List<DashboardBean.Data.VisitSchedule>,
    var rvClickListner: RvStatusClickListner
) : RecyclerView.Adapter<VisitSechduleAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder { // infalte the item Layout
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.item_call_sechdule, parent, false)
        return MyViewHolder(v)
    }

    private fun setHeaderBg(view: View) {
        view.setBackgroundResource(R.drawable.btn_border_shape)
    }

    private fun setContentBg(view: View) {
        view.setBackgroundResource(R.drawable.btn_border_shape)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.setIsRecyclable(false)
        holder.tvLeadID.text = mFilteredList[position].id.toString()
        holder.tvCampaign.text = mFilteredList[position].campaign.toString()
        holder.tvName.text = mFilteredList[position].name.toString()
        holder.tvClassification.text = mFilteredList[position].classification.toString()
        holder.tvComment.text = mFilteredList[position].comments.toString()
        //     holder.tvDate.text = mFilteredList[position].createdDate.toString()

        /*
        if (position == 0) {
            // Header Cells. Main Headings appear here
            holder.apply {
                setHeaderBg(holder.tvLeadID)
                setHeaderBg(holder.tvName)
                setHeaderBg(holder.tvCampaign)
                setHeaderBg(holder.tvClassification)
                setHeaderBg(holder.tvComment)
              //  setHeaderBg(holder.tvDate)

                holder.tvLeadID.text = "LeadID"
                holder.tvName.text = "Name"
                holder.tvCampaign.text = "Campaign"
                holder.tvClassification.text = "Classification"
                holder.tvComment.text = "Comment"
              //  holder.tvDate.text = "Budget (in Millions)"
            }
        } else {
          //  val modal =List<DashboardBean.Data.VisitSchedule> <DashboardBean.Data.VisitSchedule>[position - 1]

            holder.apply {
                setContentBg(holder.tvLeadID)
                setContentBg(holder.tvName)
                setContentBg(holder.tvCampaign)
                setContentBg(holder.tvClassification)
                setContentBg(holder.tvComment)

                holder.tvLeadID.text = mFilteredList[position].id.toString()
                holder.tvCampaign.text = mFilteredList[position].campaign.toString()
                holder.tvName.text = mFilteredList[position].name.toString()
                holder.tvClassification.text = mFilteredList[position].classification.toString()
                holder.tvComment.text = mFilteredList[position].comments.toString()

            }
        }*/

        /*  if (mFilteredList[position].status.equals("Complete")) {
              holder.tvStatus.setTextColor(context.getResources().getColor(R.color.green))
          } else if (mFilteredList[position].status.equals("Pending")) {
              holder.tvStatus.setTextColor(context.getResources().getColor(R.color.yellow_color))
          } else {
              holder.tvStatus.setTextColor(
                  context.getResources().getColor(R.color.paymentsdk_color_red)
              )
          }*/

        // holder.ivImage.setImageDrawable(context.resources.getDrawable(list[position].drawableId))

        /*  if ("Retailer"=="Retailer"){
        //      holder.itemView.visibility=View.GONE
          }*/

        /* holder.TvUpdate.setOnClickListener {
             rvClickListner.clickPos(mFilteredList[position].status, mFilteredList[position].id)
         }*/
    }

    override fun getItemCount(): Int {
        return mFilteredList.size
    }

    inner class MyViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        val tvLeadID: TextView = itemview.findViewById(R.id.tvLeadID)
        val tvCampaign: TextView = itemview.findViewById(R.id.tvCampaign)
        val tvName: TextView = itemview.findViewById(R.id.tvName)
        val tvClassification: TextView = itemview.findViewById(R.id.tvClassification)
        val tvComment: TextView = itemview.findViewById(R.id.tvComment)
        val tvDate: TextView = itemview.findViewById(R.id.tvDate)
    }


}