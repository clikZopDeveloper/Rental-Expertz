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
import com.example.rentalexpertz.R
import com.example.rentalexpertz.Utills.GeneralUtilities
import com.example.rentalexpertz.Utills.RvStatusClickListner
import com.stpl.antimatter.Utils.ApiContants


class AllTaskAdapter(
    var context: Activity,
    var list: List<AllTaskListBean.Data>,
    var rvClickListner: RvStatusClickListner
) : RecyclerView.Adapter<AllTaskAdapter.MyViewHolder>(),
    Filterable {
    var mFilteredList: MutableList<AllTaskListBean.Data> = list as MutableList<AllTaskListBean.Data>

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder { // infalte the item Layout
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_all_task, parent, false)
        return MyViewHolder(v)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.setIsRecyclable(false)

        holder.tvStatus.text = mFilteredList[position].status.toString()
        holder.tvTask.text = mFilteredList[position].task.toString()
        holder.tvLeadDate.text = mFilteredList[position].createdAt.toString()

        if (mFilteredList[position].status.equals("Complete")) {
            holder.tvStatus.setTextColor(context.getResources().getColor(R.color.green))
        } else if (mFilteredList[position].status.equals("Pending")) {
            holder.tvStatus.setTextColor(context.getResources().getColor(R.color.yellow_color))
        } else {
            holder.tvStatus.setTextColor(
                context.getResources().getColor(R.color.paymentsdk_color_red)
            )
        }

        // holder.ivImage.setImageDrawable(context.resources.getDrawable(list[position].drawableId))

        /*  if ("Retailer"=="Retailer"){
        //      holder.itemView.visibility=View.GONE
          }*/

        holder.TvUpdate.setOnClickListener {
            rvClickListner.clickPos(mFilteredList[position].status, mFilteredList[position].id)
        }
    }

    override fun getItemCount(): Int {
        return mFilteredList.size
    }

    inner class MyViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        val tvStatus: TextView = itemview.findViewById(R.id.tvStatus)
        val tvTask: TextView = itemview.findViewById(R.id.tvTask)
        val TvUpdate: TextView = itemview.findViewById(R.id.TvUpdate)
        val tvLeadDate: TextView = itemview.findViewById(R.id.tvLeadDate)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                val charString = charSequence.toString()
                if (charString.isEmpty()) {
                    mFilteredList = list as MutableList<AllTaskListBean.Data>
                } else {
                    val filteredList = ArrayList<AllTaskListBean.Data>()
                    for (serviceBean in list) {
                        if (serviceBean.status.toString().toLowerCase()
                                .contains(charString.toLowerCase())
                        ) {
                            filteredList.add(serviceBean)
                        }
                    }
                    mFilteredList = filteredList
                }
                val filterResults = FilterResults()
                filterResults.values = mFilteredList
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
                mFilteredList = filterResults.values as ArrayList<AllTaskListBean.Data>
                android.os.Handler().postDelayed(Runnable { notifyDataSetChanged() }, 200)
            }
        }
    }

}