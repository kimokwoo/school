package com.asiacove.surf.app.widget

import android.view.View
import com.smart.school.school.item.BulletinItem

abstract class CustomViewBulletinHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {
    abstract fun bind(item: BulletinItem)
}