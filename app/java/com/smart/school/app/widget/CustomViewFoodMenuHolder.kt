package com.asiacove.surf.app.widget

import android.view.View
import com.smart.school.school.item.FoodMenuItem

abstract class CustomViewFoodMenuHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {

    abstract fun bind(item: FoodMenuItem)
}