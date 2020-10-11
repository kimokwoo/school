package com.smart.school.school

import android.os.Bundle
import android.webkit.WebSettings
import android.webkit.WebViewClient
import com.smart.school.Main.activity.BaseActivity
import com.smart.school.R
import kotlinx.android.synthetic.main.activity_restaurent_cctv.*


class RestaurantCctv : BaseActivity(){

    private var mWebSettings //웹뷰세팅
            : WebSettings? = null
    private var mWebSettings2 //웹뷰세팅
            : WebSettings? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurent_cctv)
        bt_back.setOnClickListener {
            finish()
        }


        wv_cctv1.setWebViewClient(WebViewClient()) // 클릭시 새창 안뜨게

        mWebSettings = wv_cctv1.getSettings() //세부 세팅 등록
        mWebSettings!!.setJavaScriptEnabled(true) // 웹페이지 자바스클비트 허용 여부
        mWebSettings!!.setSupportMultipleWindows(false) // 새창 띄우기 허용 여부
        mWebSettings!!.setJavaScriptCanOpenWindowsAutomatically(false) // 자바스크립트 새창 띄우기(멀티뷰) 허용 여부
        mWebSettings!!.setLoadWithOverviewMode(true) // 메타태그 허용 여부
        mWebSettings!!.setUseWideViewPort(true) // 화면 사이즈 맞추기 허용 여부
        mWebSettings!!.setSupportZoom(false) // 화면 줌 허용 여부
        mWebSettings!!.setBuiltInZoomControls(false) // 화면 확대 축소 허용 여부
        mWebSettings!!.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN) // 컨텐츠 사이즈 맞추기
        mWebSettings!!.setCacheMode(WebSettings.LOAD_NO_CACHE) // 브라우저 캐시 허용 여부
        mWebSettings!!.setDomStorageEnabled(true) // 로컬저장소 허용 여부
        wv_cctv1.loadUrl("https://livecam-pro.com/fileadmin/templates/assets/_html/livecamX.040.html")

        wv_cctv2.setWebViewClient(WebViewClient()) // 클릭시 새창 안뜨게

        mWebSettings2 = wv_cctv2.getSettings() //세부 세팅 등록
        mWebSettings2!!.setJavaScriptEnabled(true) // 웹페이지 자바스클비트 허용 여부
        mWebSettings2!!.setSupportMultipleWindows(false) // 새창 띄우기 허용 여부
        mWebSettings2!!.setJavaScriptCanOpenWindowsAutomatically(false) // 자바스크립트 새창 띄우기(멀티뷰) 허용 여부
        mWebSettings2!!.setLoadWithOverviewMode(true) // 메타태그 허용 여부
        mWebSettings2!!.setUseWideViewPort(true) // 화면 사이즈 맞추기 허용 여부
        mWebSettings2!!.setSupportZoom(false) // 화면 줌 허용 여부
        mWebSettings2!!.setBuiltInZoomControls(false) // 화면 확대 축소 허용 여부
        mWebSettings2!!.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN) // 컨텐츠 사이즈 맞추기
        mWebSettings2!!.setCacheMode(WebSettings.LOAD_NO_CACHE) // 브라우저 캐시 허용 여부
        mWebSettings2!!.setDomStorageEnabled(true) // 로컬저장소 허용 여부
        wv_cctv2.loadUrl("https://www.youtube.com/watch?v=mRe-514tGMg")// 웹뷰에 표시할 웹사이트 주소, 웹뷰 시작
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}