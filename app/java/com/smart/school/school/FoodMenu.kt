package com.smart.school.school

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.smart.school.Main.activity.BaseActivity
import com.smart.school.R
import com.smart.school.app.config.iConfig
import com.smart.school.dialog.CDialog
import com.smart.school.dialog.CDialogReturnItem
import com.smart.school.school.adapter.FoodMenuAdapter
import com.smart.school.school.item.FoodMenuItem
import com.smart.school.school.item.FoodResponse
import com.smart.school.school.item.SchoolListItem
import com.smart.school.util.NetworkUtils
import com.smart.school.util.helper.ToastHelper
import kotlinx.android.synthetic.main.activity_bulletin_list.bt_back
import kotlinx.android.synthetic.main.activity_bulletin_list.recyclerView
import kotlinx.android.synthetic.main.activity_today_diet.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

class FoodMenu : BaseActivity(), Callback<FoodResponse>, View.OnClickListener, CDialog.CDialogListener {

    var calendar:Calendar = Calendar.getInstance()
    private var mArrFoodMenu : ArrayList<FoodMenuItem> = ArrayList()
    private var mLunchDinner = "0"
    private var mMenuDate = timeGenerator()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_today_diet)

        initView()
        intent.extras?.run {
            mLunchDinner = getString("lunchdinner", "0")
            mMenuDate = getString("menudate","")
        }

        bt_back.setOnClickListener(this)
        ll_menudate.setOnClickListener(this)
        ll_lunchdinner.setOnClickListener(this)
        if(mLunchDinner=="0"){
            tv_lunchdinner.setText("점심")
        } else {
            tv_lunchdinner.setText("저녁")
        }
        tv_menudate.setText(mMenuDate)
        loadData()
    }

    private fun initView(){
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL,
                false)

    }

    private fun loadData(){
        if(NetworkUtils.isConnected(this)){
            val call: Call<FoodResponse> = apiService.getFoodList(mMenuDate,mLunchDinner,"1")
            call.enqueue(this)
            Log.d("comm", "request " + call.request())
        }else{
            ToastHelper.showToast(this, getString(R.string.s17))
        }
    }

    private fun initList(arr : ArrayList<FoodMenuItem>){
        if (arr.size > 0) {
            recyclerView.visibility = View.VISIBLE
            recyclerView.adapter = FoodMenuAdapter(this, arr)
        }else{
            recyclerView.visibility = View.GONE
        }
    }

    override fun onFailure(call: Call<FoodResponse>, t: Throwable) {
        t.printStackTrace()
    }
    override fun onResponse(call: Call<FoodResponse>, response: Response<FoodResponse>) {
        if(response.isSuccessful){
            response.body()?.apply {
                mArrFoodMenu = this.menu_info
                initList(mArrFoodMenu)
            }
        }
    }

    override fun onClick(v: View?) {
        when(v){
            bt_back -> {
                finish()
            }

            ll_lunchdinner -> {
                val arrlunchdinner:ArrayList<SchoolListItem> = ArrayList()
                arrlunchdinner.add(SchoolListItem("0","점심"))
                arrlunchdinner.add(SchoolListItem("1","저녁"))
                val dialog: CDialog = CDialog.newInstance(CDialog.Flag.FLAG_LIST2, "구분", arrlunchdinner)
                dialog.show(supportFragmentManager, iConfig.DIALOG_TAG_LIST)
            }

            ll_menudate -> {
                var splitMenudate = mMenuDate.split("-")
                var year = splitMenudate[0].toInt()
                var month = splitMenudate[1].toInt() -1
                var day = splitMenudate[2].toInt()
                var date_listener = object : DatePickerDialog.OnDateSetListener {
                    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                        mMenuDate = timeGenerator1(year,month,dayOfMonth)
                        tv_menudate.setText(mMenuDate)
                    }
                }
                var builder = DatePickerDialog(this,date_listener,year,month,day)
                builder.show()
            }
        }
    }

    override fun onDialogClick(dialog: DialogFragment) {
        val item: CDialogReturnItem = (dialog as CDialog).getReturn()
        when (item.getFlag()) {
            CDialog.Flag.FLAG_LIST2 -> {
                mLunchDinner = item.getNo()
                tv_lunchdinner.setText(item.getName())
                moveActivity()
            }
        }
    }

    private fun moveActivity() {
        val intent = intent
        intent.putExtra("lunchdinner", mLunchDinner)
        intent.putExtra("menudate", mMenuDate)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }

    private fun timeGenerator() :String{
        val yearStr = calendar.get(Calendar.YEAR).toString()
        var monthStr = (calendar.get(Calendar.MONTH) + 1).toString()
        var dateStr = calendar.get(Calendar.DATE).toString()
        if (monthStr.toInt() < 10) {
            monthStr = "0$monthStr"
        }
        if (dateStr.toInt() < 10) {
            dateStr = "0$dateStr"
        }
        return "${yearStr}-${monthStr}-${dateStr}"
    }

    private fun timeGenerator1(year:Int, month:Int, day:Int) :String{
        val yearStr = year.toString()
        var monthStr = (month+1).toString()
        var dateStr = day.toString()
        if (monthStr.toInt() < 10) {
            monthStr = "0$monthStr"
        }
        if (dateStr.toInt() < 10) {
            dateStr = "0$dateStr"
        }
        return "${yearStr}-${monthStr}-${dateStr}"
    }
}
