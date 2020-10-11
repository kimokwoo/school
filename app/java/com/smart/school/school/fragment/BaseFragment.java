package com.smart.school.school.fragment;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.smart.school.R;
import com.smart.school.app.config.Config;
import com.smart.school.app.config.iConfig;
import com.smart.school.dialog.adapter.item.CodeItem;
import com.smart.school.network.APIService;
import com.smart.school.util.NetworkUtils;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class BaseFragment extends Fragment{

    private Activity activity;

    protected Config config = Config.getInstance();


    private String strMode = "";

    protected Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(iConfig.SERVER_URL_REAL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    protected Retrofit retrofit2 = new Retrofit.Builder()
            .baseUrl(iConfig.SERVER_URL_REAL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    protected Retrofit retrofit3 = new Retrofit.Builder()
            .baseUrl(iConfig.SERVER_URL_REAL1)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    protected APIService apiService = retrofit.create(APIService.class);
    protected APIService apiService2 = retrofit2.create(APIService.class);
    protected APIService apiService3 = retrofit3.create(APIService.class);

    void loadTourGoodsDelete(HashMap<String, String> hashMap){

        if(NetworkUtils.isConnected(activity)) {
            APIService service = retrofit.create(APIService.class);
            Call<CodeItem> call = service.setTourGoods(hashMap);
            call.enqueue(new Callback<CodeItem>() {
                @Override
                public void onResponse(@NonNull Call<CodeItem> call, @NonNull retrofit2.Response<CodeItem> response) {
                    onCustomResponse(call, response);
                }

                @Override
                public void onFailure(@NonNull Call<CodeItem> call, @NonNull Throwable t) {
                    onCustomFailure(call, t);
                }
            });
        }else{
            Toast.makeText(activity, getString(R.string.s17), Toast.LENGTH_SHORT).show();
        }
    }

    void loadWishShop(HashMap<String, String> hashMap, final String mode){

        if(NetworkUtils.isConnected(activity)) {
            APIService service = retrofit.create(APIService.class);
            Call<CodeItem> call = service.setTourShop(hashMap);
            call.enqueue(new Callback<CodeItem>() {
                @Override
                public void onResponse(@NonNull Call<CodeItem> call, @NonNull retrofit2.Response<CodeItem> response) {
                    onCustomResponse2(call, response, mode);
                }

                @Override
                public void onFailure(@NonNull Call<CodeItem> call, @NonNull Throwable t) {
                    onCustomFailure2(call, t);
                }
            });
        }else{
            Toast.makeText(activity, getString(R.string.s17), Toast.LENGTH_SHORT).show();
        }
    }

    void loadTourCouponDelete(HashMap<String, String> hashMap){

        if(NetworkUtils.isConnected(activity)) {
            APIService service = retrofit.create(APIService.class);
            Call<CodeItem> call = service.setSurfUserPublicCoupon(hashMap);
            call.enqueue(new Callback<CodeItem>() {
                @Override
                public void onResponse(@NonNull Call<CodeItem> call, @NonNull retrofit2.Response<CodeItem> response) {
                    onCustomResponse(call, response);
                }

                @Override
                public void onFailure(@NonNull Call<CodeItem> call, @NonNull Throwable t) {
                    onCustomFailure(call, t);
                }
            });
        }else{
            Toast.makeText(activity, getString(R.string.s17), Toast.LENGTH_SHORT).show();
        }
    }

    void loadTourDelete(String tour_no){

        if(NetworkUtils.isConnected(activity)) {
            APIService service = retrofit.create(APIService.class);
            Call<CodeItem> call = service.setDeleteTour(config.getLoginInfo(activity).getUser_no(), tour_no);
            call.enqueue(new Callback<CodeItem>() {
                @Override
                public void onResponse(@NonNull Call<CodeItem> call, @NonNull retrofit2.Response<CodeItem> response) {
                    onCustomResponse(call, response);
                }

                @Override
                public void onFailure(@NonNull Call<CodeItem> call, @NonNull Throwable t) {
                    onCustomFailure(call, t);
                }
            });
        }else{
            Toast.makeText(activity, getString(R.string.s17), Toast.LENGTH_SHORT).show();
        }
    }

    public void onCustomResponse(Call<CodeItem> call, retrofit2.Response<CodeItem> response) {

    }

    public void onCustomFailure(Call<CodeItem> call, Throwable t) {

    }

    public void onCustomResponse2(Call<CodeItem> call, retrofit2.Response<CodeItem> response, String isDelete) {

    }

    public void onCustomFailure2(Call<CodeItem> call, Throwable t) {

    }


    /*protected void setWishShop(SubListItem item){
        HashMap<String, String> tourShopMap = new HashMap<>();
        tourShopMap.put(iConfig.PARAM_KEY_NO_TOUR, "");
        tourShopMap.put(iConfig.PARAM_KEY_USER_NO, config.getLoginInfo(activity).getNo());
        tourShopMap.put(iConfig.PARAM_KEY_REGION, config.getCityNo(activity));
        tourShopMap.put(iConfig.PARAM_KEY_POI_NO, item.getNo());
        tourShopMap.put(iConfig.PARAM_KEY_GUBUN, "S");
        tourShopMap.put(iConfig.PARAM_KEY_LANG, new AppController().getLangCode(activity).toUpperCase());

        strMode = item.getIs_tour_shop().equals(iConfig.IS_TRUE) ? iConfig.IS_TRUE : "";
        tourShopMap.put(iConfig.PARAM_KEY_MODE, strMode);

        loadWishShop(tourShopMap, strMode);
    }*/



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = getActivity();
    }

    protected int getFirstPageLen(int itemMaxLen,double maxPageLen ,double firstPageChildLen){
        return (int) (itemMaxLen - (firstPageChildLen * Math.floor((maxPageLen - 1d))));
    }




}
