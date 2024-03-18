package com.example.rentalexpertz.Adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.rentalexpertz.Model.MenuModelBean
import com.example.rentalexpertz.R
import com.example.rentalexpertz.Utills.RvStatusClickListner


class DashAllLeadAdapter(var context: Activity, var list: ArrayList<MenuModelBean>, var rvClickListner: RvStatusClickListner) : RecyclerView.Adapter<DashAllLeadAdapter.MyViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder { // infalte the item Layout
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_home_allleads, parent, false)
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


        holder.tvUpPrice.text= list[position].title
       // holder.tvSubTitle.text= list[position].subTitle
        holder.tvUpServiceName.text= list[position].subTitle
  //      holder.ivImage.setImageDrawable(context.resources.getDrawable(list[position].drawableId))

      /*  if ("Retailer"=="Retailer"){
      //      holder.itemView.visibility=View.GONE
        }*/

        holder.itemView.setOnClickListener {
          /*  if (position==0){
                context.startActivity(
                    Intent(
                        context,
                        AddLeadActivity::class.java
                    )
                )
            }else if (position==13){
                context.startActivity(
                    Intent(
                        context,
                        SalesExecutiveActivity::class.java
                    )
                )
            }else{
                context.startActivity(
                    Intent(
                        context,
                        AllLeadActivity::class.java
                    ).putExtra("leadStatus", list[position].title.uppercase())
                )
            }*/

           rvClickListner.clickPos( list[position].title,list[position].indexId)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class MyViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
   //     val ivImage: ImageView = itemview.findViewById(R.id.ivImage)
       val tvUpPrice: TextView = itemview.findViewById(R.id.tvUpPrice)
       val tvUpServiceName: TextView = itemview.findViewById(R.id.tvUpServiceName)
    }

}