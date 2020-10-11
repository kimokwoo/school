package com.smart.school.school

import android.os.Bundle
import com.smart.school.Main.activity.BaseActivity
import com.smart.school.R
import kotlinx.android.synthetic.main.activity_restaurent_cctv.*

class TeacherTime : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teacher_time)
        bt_back.setOnClickListener {
            finish()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}