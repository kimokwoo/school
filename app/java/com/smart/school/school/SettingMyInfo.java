package com.smart.school.school;

import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.smart.school.Main.activity.BaseActivity;
import com.smart.school.R;
import com.smart.school.app.config.iConfig;
import com.smart.school.dialog.CDialog;
import com.smart.school.dialog.CDialogReturnItem;
import com.smart.school.school.item.MyInfoResponse;
import com.smart.school.school.item.SchoolListItem;
import com.smart.school.school.item.ValMessage;
import com.smart.school.util.NetworkUtils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingMyInfo extends BaseActivity implements View.OnClickListener, CDialog.CDialogListener{

    LinearLayout ll_bus_setting,ll_busstop_setting, ll_modify, ll_save, ll_cancel;
    LinearLayout[] ll_allergy = new LinearLayout[9];
    TextView tv_bus_setting, tv_busstop_setting;
    CheckBox[] checkAllergy = new CheckBox[18];
    ImageView bt_back;

    private int[] idLlAllergy = {R.id.ll_allergy0, R.id.ll_allergy1,R.id.ll_allergy2,R.id.ll_allergy3,
                            R.id.ll_allergy4,R.id.ll_allergy5,R.id.ll_allergy6,R.id.ll_allergy7,R.id.ll_allergy8};

    private String user_no, val_bus, val_busStop, val_allergy, setVal_bus, setVal_busStop, setVal_allergy;
    private String [] allergy_value= new String[18];
    private boolean isBus;

    private ArrayList<SchoolListItem> arrAllergyKind  = new ArrayList<>();
    private ArrayList<SchoolListItem> arrBusKind  = new ArrayList<>();
    private ArrayList<SchoolListItem> arrBusStopKind  = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_setting_myinfo);

        if(config.getActivities() == null){  //현재 열려있는 Activity가 비어있다면
            config.setActivities();  //Activity ArrayList를 생성하고
            config.getActivities().add(SettingMyInfo.this);  //intro 액티비티를 ArrayList에 추가
        }else {
            config.getActivities().add(SettingMyInfo.this);
        }
        user_no = config.getLoginInfo(this).getUser_no();

        initView();

    }

    private void initView() {
        tv_busstop_setting = findViewById(R.id.tv_busstop_setting);
        tv_bus_setting = findViewById(R.id.tv_bus_setting);
        bt_back = findViewById(R.id.bt_back);
        ll_bus_setting = findViewById(R.id.ll_bus_setting);
        ll_busstop_setting = findViewById(R.id.ll_busstop_setting);
        ll_modify = findViewById(R.id.ll_modify);
        ll_save = findViewById(R.id.ll_save);
        ll_cancel = findViewById(R.id.ll_cancel);
        ll_bus_setting.setOnClickListener(this);
        ll_busstop_setting.setOnClickListener(this);
        ll_modify.setOnClickListener(this);
        ll_save.setOnClickListener(this);
        ll_cancel.setOnClickListener(this);
        bt_back.setOnClickListener(this);

        loadData();
    }

    private void loadData() {
        if(NetworkUtils.isConnected(this)) {

            Call<MyInfoResponse> call = apiService.getMyInfo(user_no);  //mobile_login.php파일을 실행하여 결과를 가져온다.
            Log.d("comm", "" + call.request());
            call.enqueue(new Callback<MyInfoResponse>() {
                @Override
                public void onResponse(@NonNull Call<MyInfoResponse> call, @NonNull Response<MyInfoResponse> response) {

                    if (response.isSuccessful()) {
                        MyInfoResponse myInfoResponse = response.body();
                        if(myInfoResponse != null) {
                            arrAllergyKind = myInfoResponse.getAllergy_info();
                            arrBusKind = myInfoResponse.getBus_info();
                            arrBusStopKind = myInfoResponse.getBusstop_info();
                            val_bus = myInfoResponse.getVal_bus() !=null && !myInfoResponse.getVal_bus().equals("") ? myInfoResponse.getVal_bus():"0";
                            val_busStop = myInfoResponse.getVal_busstop() !=null && !myInfoResponse.getVal_busstop().equals("") ? myInfoResponse.getVal_busstop():"0";
                            val_allergy = myInfoResponse.getVal_allergy() !=null ? myInfoResponse.getVal_allergy():"";
                            setVal_bus = val_bus;
                            setVal_busStop = val_busStop;
                            setVal_allergy = val_allergy;

                            setValue();
                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<MyInfoResponse> call, @NonNull Throwable t) {
                    t.printStackTrace();
                }
            });
        }else{
            Toast.makeText(this, getString(R.string.s17), Toast.LENGTH_SHORT).show();
        }
    }

    private void setValue(){

        for(int i=0;i<arrBusKind.size();i++){
            if(val_bus.equals(arrBusKind.get(i).getNo())){
                tv_bus_setting.setText(arrBusKind.get(i).getName());
                break;
            }
        }

        for(int i=0;i<arrBusStopKind.size();i++){
            if(val_busStop.equals(arrBusStopKind.get(i).getNo())){
                tv_busstop_setting.setText(arrBusStopKind.get(i).getName());
                break;
            }
        }

        String[] arrVal_allergy = val_allergy.split(",");

        final int ht = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0, getResources().getDisplayMetrics());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ht, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.weight=1;

        params.setMargins(3,0,0,0);
        for(int i=0;i<9;i++){
            ll_allergy[i] = findViewById(idLlAllergy[i]);
            for(int k=0;k<2;k++){
                if(2*i+k<18){
                    checkAllergy[2*i+k] = new CheckBox(this);
                    checkAllergy[2*i+k].setText(arrAllergyKind.get(2*i+k).getName());
                    ll_allergy[i].addView(checkAllergy[2*i+k], params);
                    if(arrVal_allergy.length>0){
                        for(int j=0;j<arrVal_allergy.length;j++){
                            if(arrVal_allergy[j].equals(arrAllergyKind.get(2*i+k).getNo())){
                                checkAllergy[2*i+k].setChecked(true);
                                break;
                            }
                        }
                    }
                }

            }
        }

        readMode();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_bus_setting: {
                isBus = true;
                CDialog dialog = CDialog.newInstance(CDialog.Flag.FLAG_LIST2, "버스선택", arrBusKind);
                dialog.show(getSupportFragmentManager(), iConfig.DIALOG_TAG_LIST);
            }
            break;
            case R.id.ll_busstop_setting: {
                isBus = false;
                CDialog dialog = CDialog.newInstance(CDialog.Flag.FLAG_LIST2, "정류장선택", arrBusStopKind);
                dialog.show(getSupportFragmentManager(), iConfig.DIALOG_TAG_LIST);
            }
            break;
            case R.id.ll_modify: {
                editMode();
            }
            break;
            case R.id.ll_save: {
                updataData();
            }
            break;
            case R.id.ll_cancel: {
                restoreValue();
                readMode();
            }
            break;
            case R.id.bt_back: {
                finish();
            }
            break;
        }
    }

    @Override
    protected void onDestroy() {
//        unregisterReceiver();
        super.onDestroy();
    }

    private void readMode(){
        ll_bus_setting.setEnabled(false);
        ll_busstop_setting.setEnabled(false);
        ll_modify.setVisibility(View.VISIBLE);
        ll_save.setVisibility(View.GONE);
        ll_cancel.setVisibility(View.GONE);
    }

    private void editMode(){
        ll_bus_setting.setEnabled(true);
        ll_busstop_setting.setEnabled(true);
        ll_modify.setVisibility(View.GONE);
        ll_save.setVisibility(View.VISIBLE);
        ll_cancel.setVisibility(View.VISIBLE);
    }



    private void updataData(){
        setVal_allergy = "";
        for(int i=0;i<18;i++){
            if(checkAllergy[i].isChecked()){
                setVal_allergy += (i+1)+",";
            }
        }
        if(!setVal_allergy.equals("")){
            setVal_allergy.substring(0,setVal_allergy.length()-1);
        }
        //Toast.makeText(this,setVal_allergy+"/"+setVal_busStop+"/"+setVal_bus,Toast.LENGTH_LONG).show();
        config.getLoginInfo(this).setBus_no(setVal_bus);
        config.getLoginInfo(this).setBusstop_no(setVal_busStop);
        config.getLoginInfo(this).setAllergy_info(setVal_allergy);

        if(NetworkUtils.isConnected(this)) {

            Call<ValMessage> call = apiService.updateMyInfo(user_no,setVal_bus,setVal_busStop,setVal_allergy);  //mobile_login.php파일을 실행하여 결과를 가져온다.
            Log.d("comm", "" + call.request());
            call.enqueue(new Callback<ValMessage>() {
                @Override
                public void onResponse(@NonNull Call<ValMessage> call, @NonNull Response<ValMessage> response) {

                    String value = response.body().getValue();
                    String message = response.body().getMessage();

                    Toast.makeText(SettingMyInfo.this, message, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(@NonNull Call<ValMessage> call, @NonNull Throwable t) {
                    t.printStackTrace();
                }
            });
        }else{
            Toast.makeText(this, getString(R.string.s17), Toast.LENGTH_SHORT).show();
        }

        readMode();
    }

    private void restoreValue(){
        String[] arrVal_allergy = val_allergy.split(",");
        for(int i=0;i<arrBusKind.size();i++){
            if(val_bus.equals(arrBusKind.get(i).getNo())){
                tv_bus_setting.setText(arrBusKind.get(i).getName());
                break;
            }
        }
        setVal_bus = val_bus;

        for(int i=0;i<arrBusStopKind.size();i++){
            if(val_busStop.equals(arrBusStopKind.get(i).getNo())){
                tv_busstop_setting.setText(arrBusStopKind.get(i).getName());
                break;
            }
        }
        setVal_busStop = val_busStop;

        for(int i=0;i<18;i++){
            checkAllergy[i].setChecked(false);
            if(arrVal_allergy.length>0){
                for(int j=0;j<arrVal_allergy.length;j++){
                    if(arrVal_allergy[j].equals(arrAllergyKind.get(i).getNo())){
                        checkAllergy[i].setChecked(true);
                        break;
                    }
                }
            }
        }
    }

    @Override
    public void onDialogClick(DialogFragment dialog) {
        CDialogReturnItem item = ((CDialog)dialog).getReturn();
        switch (item.getFlag()){
            case FLAG_LIST2:
            {
                if(isBus){
                    setVal_bus     = item.getNo();
                    tv_bus_setting.setText(item.getName());
                } else {
                    setVal_busStop     = item.getNo();
                    tv_busstop_setting.setText(item.getName());
                }
            }
            break;
        }
    }

}
