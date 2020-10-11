package com.smart.school.school

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.DatePicker
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.smart.school.Main.activity.BaseActivity
import com.smart.school.R
import com.smart.school.app.config.iConfig
import com.smart.school.dialog.CDialog
import com.smart.school.dialog.CDialogReturnItem
import com.smart.school.school.adapter.FoodReviewAdapter
import com.smart.school.school.item.FoodMenuItem
import com.smart.school.school.item.FoodResponse
import com.smart.school.school.item.SchoolListItem
import com.smart.school.school.item.ValMessage
import com.smart.school.util.NetworkUtils
import com.smart.school.util.helper.ToastHelper
import kotlinx.android.synthetic.main.activity_bulletin_list.bt_back
import kotlinx.android.synthetic.main.activity_bulletin_list.recyclerView
import kotlinx.android.synthetic.main.activity_diet_review.*
import kotlinx.android.synthetic.main.activity_today_diet.*
import kotlinx.android.synthetic.main.activity_today_diet.ll_lunchdinner
import kotlinx.android.synthetic.main.activity_today_diet.ll_menudate
import kotlinx.android.synthetic.main.activity_today_diet.tv_lunchdinner
import kotlinx.android.synthetic.main.activity_today_diet.tv_menudate
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

class FoodReview : BaseActivity(), Callback<FoodResponse>, View.OnClickListener, CDialog.CDialogListener {

    var calendar:Calendar = Calendar.getInstance()
    private var mArrFoodMenu : ArrayList<FoodMenuItem> = ArrayList()
    private var mLunchDinner = "0"
    private var mMenuDate = timeGenerator()
    private var mStudentNo: String? = null
    private var mRemark:String? = null
    private var mIsSaved:Boolean = false
    private var setArrFoodMenu: String = ""
    private var setRemark: String? = null
    private var mIsChanged:Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diet_review)
        mStudentNo = config.getLoginInfo(this).user_no
        initView()
        intent.extras?.run {
            mLunchDinner = getString("lunchdinner", "0")
            mMenuDate = getString("menudate","")
        }

        bt_back.setOnClickListener(this)
        ll_menudate.setOnClickListener(this)
        ll_lunchdinner.setOnClickListener(this)
        ll_save.setOnClickListener(this)
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
            val call: Call<FoodResponse> = apiService.getFoodList(mMenuDate,mLunchDinner,mStudentNo)
            call.enqueue(this)
            Log.d("comm", "request " + call.request())
        }else{
            ToastHelper.showToast(this, getString(R.string.s17))
        }
    }

    private fun initList(){
        if (mArrFoodMenu.size > 0) {
            checkIsSaved()
            recyclerView.visibility = View.VISIBLE
            recyclerView.adapter = FoodReviewAdapter(this, mArrFoodMenu,mIsSaved)
            ll_remark.visibility = View.VISIBLE
            diet_review.setText(mRemark)
            if(mIsSaved){
                diet_review.isEnabled = false
                ll_save.visibility = View.INVISIBLE
            } else {
                diet_review.isEnabled = true
                ll_save.visibility = View.VISIBLE
            }

        }else{
            recyclerView.visibility = View.GONE
            ll_remark.visibility = View.GONE
        }
    }

    private fun checkIsSaved(){
        if(mRemark!=null && mRemark !="") {
            mIsSaved = true
        } else {
            var review_status =""
            for(i in 0..mArrFoodMenu.size-1){
                review_status = mArrFoodMenu.get(i).review_status
                if( review_status !=null && review_status !=""){
                    mIsSaved = true
                    break
                }
            }
        }

    }

    override fun onFailure(call: Call<FoodResponse>, t: Throwable) {
        t.printStackTrace()
    }
    override fun onResponse(call: Call<FoodResponse>, response: Response<FoodResponse>) {
        if(response.isSuccessful){
            response.body()?.apply {
                mArrFoodMenu = this.menu_info
                mRemark = this.remark
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
                if(mIsChanged){
                    updateData()
                } else {
                    Toast.makeText(this,"한가지 이상 선택하셔야 합니다.",Toast.LENGTH_SHORT).show();
                }
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

    fun setMenuReview(no:String, value:String) {
        mIsChanged = true
        for(i in 0..mArrFoodMenu.size-1){
            if(mArrFoodMenu[i].no == no){
                var goodNo = mArrFoodMenu[i].good_no.toInt()
                var badNo = mArrFoodMenu[i].bad_no.toInt()
                if(value =="1"){
                    if(mArrFoodMenu[i].review_status =="1" || mArrFoodMenu[i].review_status=="2"){
                        goodNo ++
                        badNo --
                    } else {
                        goodNo ++
                    }
                } else if(value =="2"){
                    if(mArrFoodMenu[i].review_status =="1" || mArrFoodMenu[i].review_status=="2"){
                        goodNo --
                        badNo ++
                    } else {
                        badNo ++
                    }
                }
                mArrFoodMenu[i].good_no = goodNo.toString()
                mArrFoodMenu[i].bad_no = badNo.toString()
                mArrFoodMenu[i].review_status = value
                //Toast.makeText(this,"$no/$value/$goodNo/$badNo",Toast.LENGTH_LONG).show()
                break
            }
        }
    }

    private fun updateData(){
        setRemark = diet_review.text.toString()
        setArrFoodMenu=""
        for(i in 0..mArrFoodMenu.size-1){
            var reviewStatus = if(mArrFoodMenu[i].review_status=="")  "0" else mArrFoodMenu[i].review_status
            setArrFoodMenu +=mArrFoodMenu[i].no+"/"+mArrFoodMenu[i].good_no+"/"+mArrFoodMenu[i].bad_no+"/"+reviewStatus
            if(i != mArrFoodMenu.size-1){
                setArrFoodMenu +=","
            }
        }
        //Toast.makeText(this,setRemark+"/*/"+setArrFoodMenu,Toast.LENGTH_LONG).show()
        if (NetworkUtils.isConnected(this)) {
            val call = apiService.updateMenuReview(mStudentNo, mMenuDate, mLunchDinner, setRemark, setArrFoodMenu) //mobile_login.php파일을 실행하여 결과를 가져온다.
            Log.d("comm", "" + call.request())
            call.enqueue(object : Callback<ValMessage> {
                override fun onResponse(call: Call<ValMessage>, response: Response<ValMessage>) {
                    val value = response.body()!!.value
                    val message = response.body()!!.message
                    if(value=="1"){
                        Toast.makeText(this@FoodReview, message, Toast.LENGTH_SHORT).show()
                        refreshActivity();
                    } else {
                        Toast.makeText(this@FoodReview, message, Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<ValMessage>, t: Throwable) {
                    t.printStackTrace()
                    Toast.makeText(this@FoodReview, getString(R.string.s28), Toast.LENGTH_SHORT).show()
                }
            })
        } else {
            Toast.makeText(this, getString(R.string.s17), Toast.LENGTH_SHORT).show()
        }
    }

    private fun refreshActivity(){
        val intent = intent
        intent.putExtra("lunchdinner", mLunchDinner)
        intent.putExtra("menudate", mMenuDate)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }
}
