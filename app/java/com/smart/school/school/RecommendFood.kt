package com.smart.school.school

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.smart.school.Main.activity.BaseActivity
import com.smart.school.R
import com.smart.school.school.adapter.RecommendFoodAdapter
import com.smart.school.school.item.FoodMenuItem
import com.smart.school.school.item.ValMessage
import com.smart.school.util.NetworkUtils
import com.smart.school.util.helper.ToastHelper
import kotlinx.android.synthetic.main.activity_bulletin_list.bt_back
import kotlinx.android.synthetic.main.activity_bulletin_list.recyclerView
import kotlinx.android.synthetic.main.activity_diet_review.*
import kotlinx.android.synthetic.main.activity_diet_review.ll_save
import kotlinx.android.synthetic.main.activity_recommend_food.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

class RecommendFood : BaseActivity(), Callback<ArrayList<FoodMenuItem>>, View.OnClickListener {

    var calendar:Calendar = Calendar.getInstance()
    private var mArrFoodMenu : ArrayList<FoodMenuItem> = ArrayList()
    private var mStudentNo: String? = null
    private var setArrFoodMenu: String = ""
    private var setRemark: String? = null
    private var mIsChanged:Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recommend_food)
        mStudentNo = config.getLoginInfo(this).user_no
        initView()

        bt_back.setOnClickListener(this)
        ll_save.setOnClickListener(this)
        loadData()
    }

    private fun initView(){
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL,
                false)

    }

    private fun loadData(){
        if(NetworkUtils.isConnected(this)){
            val call: Call<ArrayList<FoodMenuItem>> = apiService.getRecommendFood(mStudentNo)
            call.enqueue(this)
            Log.d("comm", "request " + call.request())
        }else{
            ToastHelper.showToast(this, getString(R.string.s17))
        }
    }

    private fun initList(){
        if (mArrFoodMenu.size > 0) {
            recyclerView.visibility = View.VISIBLE
            recyclerView.adapter = RecommendFoodAdapter(this, mArrFoodMenu)
        }else{
            recyclerView.visibility = View.GONE
        }
    }

    override fun onFailure(call: Call<ArrayList<FoodMenuItem>>, t: Throwable) {
        t.printStackTrace()
    }
    override fun onResponse(call: Call<ArrayList<FoodMenuItem>>, response: Response<ArrayList<FoodMenuItem>>) {
        if(response.isSuccessful){
            response.body()?.apply {
                mArrFoodMenu = this
                initList()
            }
        }
    }

    override fun onClick(v: View?) {
        when(v){
            bt_back -> {
                finish()
            }

            ll_save -> {
                updateData()
            }
        }
    }


    fun setMenuReview(no:String, value:String) {
        if (NetworkUtils.isConnected(this)) {
            val call = apiService.updateRecommendReview(mStudentNo, no, value) //mobile_login.php파일을 실행하여 결과를 가져온다.
            Log.d("comm", "" + call.request())
            call.enqueue(object : Callback<ValMessage> {
                override fun onResponse(call: Call<ValMessage>, response: Response<ValMessage>) {
                    val value = response.body()!!.value
                    val message = response.body()!!.message
                    if(value=="1"){
                        refreshActivity()
                    } else {
                        Toast.makeText(this@RecommendFood, message, Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<ValMessage>, t: Throwable) {
                    t.printStackTrace()
                    Toast.makeText(this@RecommendFood, getString(R.string.s28), Toast.LENGTH_SHORT).show()
                }
            })
        } else {
            Toast.makeText(this, getString(R.string.s17), Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateData(){

        if (NetworkUtils.isConnected(this)) {
            val foodName = et_recommend_food.text.toString()
            val call = apiService.addRecommendFood(mStudentNo, foodName)
            Log.d("comm", "" + call.request())
            call.enqueue(object : Callback<ValMessage> {
                override fun onResponse(call: Call<ValMessage>, response: Response<ValMessage>) {
                    val value = response.body()!!.value
                    val message = response.body()!!.message
                    if(value=="1"){
                        Toast.makeText(this@RecommendFood, message, Toast.LENGTH_SHORT).show()
                        refreshActivity();
                    } else {
                        Toast.makeText(this@RecommendFood, message, Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<ValMessage>, t: Throwable) {
                    t.printStackTrace()
                    Toast.makeText(this@RecommendFood, getString(R.string.s28), Toast.LENGTH_SHORT).show()
                }
            })
        } else {
            Toast.makeText(this, getString(R.string.s17), Toast.LENGTH_SHORT).show()
        }
    }

    private fun refreshActivity(){
        val intent = intent
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }
}
