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
import com.example.rentalexpertz.Model.SearchBean
import com.example.rentalexpertz.R
import com.example.rentalexpertz.Utills.GeneralUtilities
import com.example.rentalexpertz.Utills.RvStatusClickListner
import com.stpl.antimatter.Utils.ApiContants


class SearchDataAdapter(var context: Activity, var mFilteredList: List<SearchBean.Data>, var leadStatus:String?, var rvClickListner: RvStatusClickListner) : RecyclerView.Adapter<SearchDataAdapter.MyViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder { // infalte the item Layout
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_allleaddata, parent, false)
        return MyViewHolder(v)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.setIsRecyclable(false)

        holder.tvID.text= mFilteredList[position].id.toString()
        holder.tvSource.text= mFilteredList[position].source.toString()
      //  holder.tvPlumber.text= "Plumber : "+mFilteredList[position].plumber
    //    holder.tvArchitect.text= mFilteredList[position].architect
        holder.tvClient.text= mFilteredList[position].name
     //   holder.tvNameFirstLatter.text=mFilteredList[position].clientName.first().toString()
     //   ApiContants.nameFirstLatter(mFilteredList[position].clientName,holder.tvNameFirstLatter)
        holder.tvLeadDate.text=mFilteredList[position].createdDate.toString()
      //  holder.tvmep.text=mFilteredList[position].mep
        holder.tvStatus.text=mFilteredList[position].status
        holder.tvComments.text=mFilteredList[position].comments
        holder.tvClientNumber.text=mFilteredList[position].mobile?.toString()

        holder.ivCall.setOnClickListener {
            GeneralUtilities.getInstance().makeCall(context, mFilteredList[position].mobile?.toString())
        }
        holder.ivUpdate.setOnClickListener {
          context.startActivity(Intent(context,UpdateLeadActivity::class.java)
               .putExtra("leadID",mFilteredList[position].id.toString())
               .putExtra("leadStatus",mFilteredList[position].status.toString()))
        }

        holder.ivEditLead.setOnClickListener {
            context.startActivity(Intent(context, AddLeadActivity::class.java)
                .putExtra("wayResponse",mFilteredList[position])
                .putExtra("way","Lead Edit"))
        }
       // holder.ivImage.setImageDrawable(context.resources.getDrawable(list[position].drawableId))

      /*  if ("Retailer"=="Retailer"){
      //      holder.itemView.visibility=View.GONE
        }*/

        holder.itemView.setOnClickListener {
            rvClickListner.clickPos(mFilteredList[position].status,mFilteredList[position].id)
        }
    }

    override fun getItemCount(): Int {
        return mFilteredList.size
    }

    inner class MyViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        val tvID: TextView = itemview.findViewById(R.id.tvID)
       val tvSource: TextView = itemview.findViewById(R.id.tvSource)
       val tvClient: TextView = itemview.findViewById(R.id.tvClient)
       val tvLeadDate: TextView = itemview.findViewById(R.id.tvLeadDate)
       val tvStatus: TextView = itemview.findViewById(R.id.tvStatus)
       val tvNameFirstLatter: TextView = itemview.findViewById(R.id.tvNameFirstLatter)
       val tvClientNumber: TextView = itemview.findViewById(R.id.tvClientNumber)
       val tvComments: TextView = itemview.findViewById(R.id.tvComments)
       val ivEditLead: TextView = itemview.findViewById(R.id.ivEditLead)
       val ivUpdate: ImageView = itemview.findViewById(R.id.ivUpdate)
       val ivCall: ImageView = itemview.findViewById(R.id.ivCall)
    }


}