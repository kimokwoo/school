package com.smart.school.school.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.smart.school.Main.activity.OnFragmentReplaceListener;
import com.smart.school.R;
import com.smart.school.app.config.iConfig;

/**
 * Created by art on 2016-04-21.
 */
public class  LoginSelectFragment extends BaseFragment implements View.OnClickListener{

    private OnFragmentReplaceListener   mListener;

    private AppCompatActivity           mAct;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.fragment_login_select, container, false);

        view.findViewById(R.id.btn_login_select_sign_up).setOnClickListener(this);
        view.findViewById(R.id.btn_login_select_login).setOnClickListener(this);

        ImageView iv_back = view.findViewById(R.id.iv_login_select_back);
        iv_back.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                mAct.finish();
            }
        });
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof AppCompatActivity){
            mAct = (AppCompatActivity)context;
        }

        if(context instanceof OnFragmentReplaceListener){
            mListener = (OnFragmentReplaceListener)context;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_login_select_sign_up:
            {
                mListener.onFragmentReplace(iConfig.TAG_SIGN_UP);
            }
                break;

            case R.id.btn_login_select_login:
            {
                mListener.onFragmentReplace(iConfig.TAG_LOGIN);
            }
            break;

        }
    }
}
