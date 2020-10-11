package com.smart.school.school.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.RecyclerView
import com.asiacove.surf.app.widget.CustomViewBulletinHolder
import com.smart.school.R
import com.smart.school.dialog.CDialog
import com.smart.school.dialog.CDialogReturnItem
import com.smart.school.school.BusRoutine
import com.smart.school.school.item.BulletinItem
import kotlinx.android.synthetic.main.row_bulletin_list.view.*


class BulletinListAdapter(var mContext : Context, var mList : ArrayList<BulletinItem> ): RecyclerView.Adapter<BulletinListAdapter.BulletinListVH>() {
    val evenColor = Color.parseColor("#FFFFFF")
    val oddColor = Color.parseColor("#D8F1CB")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BulletinListVH {
        val view = View.inflate(mContext, R.layout.row_bulletin_list, null)
        return BulletinListVH(view)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: BulletinListVH, position: Int) {
        holder.bind(mList[position])
        if(position % 2 ==0){
            holder.itemView.ll_no.setBackgroundColor(evenColor)
            holder.itemView.ll_title.setBackgroundColor(evenColor)
            holder.itemView.ll_reg_date.setBackgroundColor(evenColor)
        } else {
            holder.itemView.ll_no.setBackgroundColor(oddColor)
            holder.itemView.ll_title.setBackgroundColor(oddColor)
            holder.itemView.ll_reg_date.setBackgroundColor(oddColor)
        }
    }

    inner class BulletinListVH(itemView : View) : CustomViewBulletinHolder(itemView), View.OnClickListener, CDialog.CDialogListener {

        private var mTvNo = itemView.tv_no!!
        private var mTvTitle = itemView.tv_title!!
        private val mTvRegDate = itemView.tv_reg_date!!
        private var mSelected : String?=null


        override fun bind(item: BulletinItem) {
            mTvNo.text = item.no
            mTvTitle.text = item.title
            mTvRegDate.text = item.reg_date
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