package com.example.rentalexpertz.Adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
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


class MissedFollowupAdapter(
    var context: Activity,
    var mFilteredList: List<DashboardBean.Data.MissedFollowup>,
    var rvClickListner: RvStatusClickListner
) : RecyclerView.Adapter<MissedFollowupAdapter.MyViewHolder>(){

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder { // infalte the item Layout
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_call_sechdule, parent, false)
        return MyViewHolder(v)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.setIsRecyclable(false)

        holder.tvLeadID.text = mFilteredList[position].id.toString()
        holder.tvCampaign.text = mFilteredList[position].source?.toString()
        holder.tvName.text = mFilteredList[position].name?.toString()
        holder.tvClassification.text = mFilteredList[position].classification?.toString()
        holder.tvComment.text = mFilteredList[position].comments?.toString()
        holder.tvDate.text = mFilteredList[position].createdDate?.toString()
        holder.ivCall.setOnClickListener {
            GeneralUtilities.getInstance().makeCall(context, mFilteredList[position].mobile?.toString())
        }
        holder.ivUpdate.setOnClickListener {
            rvClickListner.clickPos(mFilteredList[position].status, mFilteredList[position].id)
        }
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
        val ivCall: ImageView = itemview.findViewById(R.id.ivCall)
        val ivUpdate: ImageView = itemview.findViewById(R.id.ivUpdate)
    }


}