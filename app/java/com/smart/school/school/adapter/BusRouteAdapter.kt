package com.smart.school.school.adapter

import android.content.Context
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.RecyclerView
import com.asiacove.surf.app.widget.CustomViewSchoolHolder
import com.smart.school.R
import com.smart.school.dialog.CDialog
import com.smart.school.dialog.CDialogReturnItem
import com.smart.school.school.BusRoutine
import com.smart.school.school.item.SchoolListItem
import kotlinx.android.synthetic.main.row_bus_route_list.view.*


class BusRouteAdapter(var mContext : Context, var mList : ArrayList<SchoolListItem>,
                      var mFirst:String, var mLast:String) : RecyclerView.Adapter<BusRouteAdapter.BusRouteVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BusRouteVH {
        val view = View.inflate(mContext, R.layout.row_bus_route_list, null)
        return BusRouteVH(view)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: BusRouteVH, position: Int) {
        holder.bind(mList[position])
    }

    inner class BusRouteVH(itemView : View) : CustomViewSchoolHolder(itemView), View.OnClickListener, CDialog.CDialogListener {

        private var mIvCar = itemView.iv_car!!
        private var mIvRoute = itemView.iv_route!!
        private val mTvBusStop = itemView.tv_bus_stop!!
        private val mTvParr = itemView.tv_parr!!
        private val mTvEarr = itemView.tv_earr!!
        private var mSelected : String?=null


        override fun bind(item: SchoolListItem) {

            mTvBusStop.text = item.busstop
            mTvParr.text = item.parr
            mTvEarr.text = item.earr
            mSelected = item.no
            if(item.no == mFirst) {
                mIvRoute.setImageResource(R.drawable.route_start)
            } else if(item.no == mLast) {
                mIvRoute.setImageResource(R.drawable.route_end)
            } else {
                mIvRoute.setImageResource(R.drawable.route_mid)
            }

            if (item.ison == "1") {
                mIvCar.visibility = View.VISIBLE
            } else {
                mIvCar.visibility = View.INVISIBLE
            }

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