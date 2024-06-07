package com.example.rentalexpertz.Adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.rentalexpertz.Model.GetAllStatusBean
import com.example.rentalexpertz.Model.MenuModelBean
import com.example.rentalexpertz.R
import com.example.rentalexpertz.Utills.RvStatusClickListner
import java.util.ArrayList


class UpdateAllStatusAdapter(
    var context: Activity,
    var list: List<String>,
    var rvClickListner: RvStatusClickListner
) : RecyclerView.Adapter<UpdateAllStatusAdapter.MyViewHolder>(){
    private var checkedPosition = 0
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder { // infalte the item Layout
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_all_status_list, parent, false)
        return MyViewHolder(v)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.setIsRecyclable(false)

   /*     holder.tvAdd.background = RoundView(context.resources.getColor(R.color.orange), RoundView.getRadius(20f))
        holder.tvQtyAdd.background = RoundView(context.resources.getColor(R.color.orange), RoundView.getRadius(100f))
        holder.tvQtyMinus.background = RoundView(context.resources.getColor(R.color.orange), RoundView.getRadius(100f))
        holder.tvQty.background = RoundView(Color.TRANSPARENT, RoundView.getRadius(20f), true, R.color.orange)
        holder.tvOff.background = RoundView(context.resources.getColor(R.color.orange), RoundView.getRadius(20f))
        holder.tvAdd.visibility = View.VISIBLE*/

        holder.tvTitle.text= list[position]
        if(checkedPosition == position){
            holder.ivChecked.visibility=View.VISIBLE
        }else{
            holder.ivChecked.visibility=View.GONE
        }
       // holder.ivImage.setImageDrawable(context.resources.getDrawable(list[position].drawableId))

        holder.itemView.setOnClickListener {
            checkedPosition = position
            holder.ivChecked.visibility=View.VISIBLE
            notifyDataSetChanged();

            rvClickListner.clickPos(list[position],position)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class MyViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
       val tvTitle: TextView = itemview.findViewById(R.id.tvTitle)
       val ivChecked: ImageView = itemview.findViewById(R.id.ivChecked)

    }

}