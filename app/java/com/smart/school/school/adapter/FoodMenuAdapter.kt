package com.smart.school.school.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.RecyclerView
import com.asiacove.surf.app.widget.CustomViewFoodMenuHolder
import com.smart.school.R
import com.smart.school.dialog.CDialog
import com.smart.school.dialog.CDialogReturnItem
import com.smart.school.school.BusRoutine
import com.smart.school.school.item.FoodMenuItem
import kotlinx.android.synthetic.main.row_food_menu.view.*


class FoodMenuAdapter(var mContext : Context, var mList : ArrayList<FoodMenuItem> ): RecyclerView.Adapter<FoodMenuAdapter.FoodMenuVH>() {
    val evenColor = Color.parseColor("#FFFFFF")
    val oddColor = Color.parseColor("#D8F1CB")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodMenuVH {
        val view = View.inflate(mContext, R.layout.row_food_menu, null)
        return FoodMenuVH(view)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: FoodMenuVH, position: Int) {
        holder.bind(mList[position])
        if(position % 2 ==0){
            holder.itemView.ll_food_name.setBackgroundColor(evenColor)
            holder.itemView.ll_cal.setBackgroundColor(evenColor)
            holder.itemView.ll_allergy.setBackgroundColor(evenColor)
        } else {
            holder.itemView.ll_food_name.setBackgroundColor(oddColor)
            holder.itemView.ll_cal.setBackgroundColor(oddColor)
            holder.itemView.ll_allergy.setBackgroundColor(oddColor)
        }
    }

    inner class FoodMenuVH(itemView : View) : CustomViewFoodMenuHolder(itemView), View.OnClickListener, CDialog.CDialogListener {

        private var mTvFoodName = itemView.tv_food_name!!
        private var mTvCal = itemView.tv_cal!!
        private val mTvAllergy = itemView.tv_allergy!!
        private var mSelected : String?=null


        override fun bind(item: FoodMenuItem) {
            mTvFoodName.text = item.food_name
            mTvCal.text = item.cal
            mTvAllergy.text = item.allergy
            mSelected = item.no

            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
//            val manager: FragmentManager =v.
//            val dialog: CDialog = CDialog.newInstance(CDialog.Flag.FLAG_RADIOBUTTON2, "구분", "")
//            dialog.show(manager, iConfig.DIALOG_TAG_LIST)
        }

        override fun onDialogClick(dialog: DialogFragment) {
            val item: CDialogReturnItem = (dialog as CDialog).getReturn()
            when (item.getFlag()) {
                CDialog.Flag.FLAG_RADIOBUTTON2 -> {
                    moveActivity(item.getNo())
                }
            }
        }

        private fun moveActivity(status :String) {
            val mIntent = Intent(itemView.context, BusRoutine::class.java)
            mIntent.putExtra("bsno", mSelected)
            mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            mContext.startActivity(mIntent)
        }
    }
}