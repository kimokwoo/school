package com.smart.school.school

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.smart.school.Main.activity.BaseActivity
import com.smart.school.R
import com.smart.school.app.config.iConfig
import com.smart.school.dialog.CDialog
import com.smart.school.dialog.CDialogReturnItem
import com.smart.school.school.adapter.BulletinListAdapter
import com.smart.school.school.item.BulletinItem
import com.smart.school.school.item.SchoolListItem
import com.smart.school.util.NetworkUtils
import com.smart.school.util.helper.ToastHelper
import kotlinx.android.synthetic.main.activity_bulletin_list.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BulletinList : BaseActivity(), Callback<ArrayList<BulletinItem>>, View.OnClickListener, CDialog.CDialogListener {

    private var mArrBulletinList : ArrayList<BulletinItem> = ArrayList()
    private var mArrRouteInfo : ArrayList<SchoolListItem> = ArrayList()
    private var mArrBusSchedule : ArrayList<SchoolListItem> = ArrayList()
    private var mBsNo = "1"
    private var mRequestManager : RequestManager? = null
    private var mBusStop =""
    private var mFirst = ""
    private var mLast = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bulletin_list)

        initView()

        bt_back.setOnClickListener(this)

        loadData()
    }

    private fun initView(){
        mRequestManager = Glide.with(this)
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL,
                false)


    }

    private fun loadData(){
        if(NetworkUtils.isConnected(this)){
            val call: Call<ArrayList<BulletinItem>> = apiService.getBulletinList()
            call.enqueue(this)
            Log.d("comm", "request " + call.request())
        }else{
            ToastHelper.showToast(this, getString(R.string.s17))
        }
    }

    private fun initList(arr : ArrayList<BulletinItem>){
        if (arr.size > 0) {
            recyclerView.visibility = View.VISIBLE
            recyclerView.adapter = BulletinListAdapter(this, arr)
        }else{
            recyclerView.visibility = View.GONE
        }
    }

    override fun onFailure(call: Call<ArrayList<BulletinItem>>, t: Throwable) {
        t.printStackTrace()
    }
    override fun onResponse(call: Call<ArrayList<BulletinItem>>, response: Response<ArrayList<BulletinItem>>) {
        if(response.isSuccessful){
            response.body()?.apply {
                mArrBulletinList = this
                initList(mArrBulletinList)
            }
        }
    }

    override fun onClick(v: View?) {
        when(v){
            bt_back -> {
                finish()
            }

            tv_bus_menu1 -> {
                val dialog: CDialog = CDialog.newInstance(CDialog.Flag.FLAG_LIST2, "구분", mArrBusSchedule)
                dialog.show(supportFragmentManager, iConfig.DIALOG_TAG_LIST)
            }
        }
    }

    override fun onDialogClick(dialog: DialogFragment) {
        val item: CDialogReturnItem = (dialog as CDialog).getReturn()
        when (item.getFlag()) {
            CDialog.Flag.FLAG_LIST2 -> {
                mBsNo = item.getNo()
                tv_bus_menu1.setText(item.getName())
                moveActivity()
            }
        }
    }

    private fun moveActivity() {
        val intent = intent
        intent.putExtra("bsno", mBsNo)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }


}
