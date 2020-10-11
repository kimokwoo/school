package com.smart.school.school.adapter

import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.asiacove.surf.app.widget.CustomViewFoodMenuHolder
import com.smart.school.R
import com.smart.school.school.RecommendFood
import com.smart.school.school.item.FoodMenuItem
import kotlinx.android.synthetic.main.row_food_menu.view.ll_food_name
import kotlinx.android.synthetic.main.row_food_menu.view.tv_food_name
import kotlinx.android.synthetic.main.row_food_review.view.*
import kotlinx.android.synthetic.main.row_food_review.view.ll_good
import kotlinx.android.synthetic.main.row_recommend_food.view.*


class RecommendFoodAdapter(var mContext : Context, var mList : ArrayList<FoodMenuItem> ): RecyclerView.Adapter<RecommendFoodAdapter.RecommendFoodVH>() {
    val evenColor = Color.parseColor("#FFFFFF")
    val oddColor = Color.parseColor("#D8F1CB")
    private val reviewImage = intArrayOf(R.drawable.heart_off,R.drawable.heart_on)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendFoodVH {
        val view = View.inflate(mContext, R.layout.row_recommend_food, null)
        return RecommendFoodVH(view)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: RecommendFoodVH, position: Int) {
        holder.bind(mList[position])
        if(position % 2 ==0){
            holder.itemView.ll_food_name.setBackgroundColor(evenColor)
            holder.itemView.ll_good.setBackgroundColor(evenColor)
            holder.itemView.ll_review.setBackgroundColor(evenColor)
        } else {
            holder.itemView.ll_food_name.setBackgroundColor(oddColor)
            holder.itemView.ll_good.setBackgroundColor(oddColor)
            holder.itemView.ll_review.setBackgroundColor(oddColor)
        }
    }

    inner class RecommendFoodVH(itemView : View) : CustomViewFoodMenuHolder(itemView) {
        private var mTvFoodName = itemView.tv_food_name!!
        private var mTvGood = itemView.tv_good!!
        private val mIvReview = itemView.iv_review!!
        private var mSelected : String?=null


        override fun bind(item: FoodMenuItem) {
            mTvFoodName.text = item.food_name
            if(item.review_status =="0"){
                mIvReview.setImageResource(reviewImage[0])
            } else {
                mIvReview.setImageResource(reviewImage[1])
            }
            mTvGood.text = item.good_no
            mSelected = item.no

            mIvReview.setOnClickListener{
                var setVal:String?=null
                if(item.review_status =="0"){
                    mIvReview.setImageResource(reviewImage[1])
                    setVal = "1"
                } else {
                    mIvReview.setImageResource(reviewImage[0])
                    setVal = "0"
                }
                (mContext as RecommendFood).setMenuReview(mSelected!!,setVal)
            }
        }

    }
}