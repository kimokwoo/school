package com.smart.school.school.adapter

import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.asiacove.surf.app.widget.CustomViewFoodMenuHolder
import com.smart.school.R
import com.smart.school.school.FoodReview
import com.smart.school.school.item.FoodMenuItem
import kotlinx.android.synthetic.main.row_food_menu.view.ll_food_name
import kotlinx.android.synthetic.main.row_food_menu.view.tv_food_name
import kotlinx.android.synthetic.main.row_food_review.view.*


class FoodReviewAdapter(var mContext : Context, var mList : ArrayList<FoodMenuItem>, var isSaved:Boolean ): RecyclerView.Adapter<FoodReviewAdapter.FoodReviewVH>() {
    val evenColor = Color.parseColor("#FFFFFF")
    val oddColor = Color.parseColor("#D8F1CB")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodReviewVH {
        val view = View.inflate(mContext, R.layout.row_food_review, null)
        return FoodReviewVH(view)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: FoodReviewVH, position: Int) {
        holder.bind(mList[position])
        if(isSaved){
            holder.itemView.rb_good.isEnabled = false
            holder.itemView.rb_bad.isEnabled = false
        } else {
            holder.itemView.rb_good.isEnabled = true
            holder.itemView.rb_bad.isEnabled = true
        }
        if(position % 2 ==0){
            holder.itemView.ll_food_name.setBackgroundColor(evenColor)
            holder.itemView.ll_good.setBackgroundColor(evenColor)
            holder.itemView.ll_bad.setBackgroundColor(evenColor)
        } else {
            holder.itemView.ll_food_name.setBackgroundColor(oddColor)
            holder.itemView.ll_good.setBackgroundColor(oddColor)
            holder.itemView.ll_bad.setBackgroundColor(oddColor)
        }
    }

    inner class FoodReviewVH(itemView : View) : CustomViewFoodMenuHolder(itemView), View.OnClickListener {

        private var mTvFoodName = itemView.tv_food_name!!
        private var mRbGood = itemView.rb_good!!
        private val mRbBad = itemView.rb_bad!!
        private var mSelected : String?=null


        override fun bind(item: FoodMenuItem) {
            mTvFoodName.text = item.food_name
            if(item.review_status =="1"){
                mRbGood.isChecked = true
            } else {
                mRbGood.isChecked = false
            }
            if(item.review_status =="2"){
                mRbBad.isChecked = true
            } else {
                mRbBad.isChecked = false
            }
            mSelected = item.no

            if(!mRbGood.isChecked){
                mRbGood.setOnClickListener(this)
            }

            if(!mRbBad.isChecked){
                mRbBad.setOnClickListener(this)
            }
        }

        override fun onClick(v: View?) {
            when(v){
                mRbGood -> {
                    mRbGood.isChecked = true
                    mRbBad.isChecked = false
                    (mContext as FoodReview).setMenuReview(mSelected!!,"1")
                }

                mRbBad -> {
                    mRbGood.isChecked = false
                    mRbBad.isChecked = true
                    (mContext as FoodReview).setMenuReview(mSelected!!,"2")
                }
            }
        }

    }
}