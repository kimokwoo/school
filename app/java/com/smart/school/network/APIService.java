package com.smart.school.network;

import com.google.gson.JsonObject;
import com.smart.school.adapter.item.BrandResponse;
import com.smart.school.adapter.item.CodeResponse;
import com.smart.school.adapter.item.CommItem;
import com.smart.school.adapter.item.CountryListResponse;
import com.smart.school.adapter.item.MustBuyItem;
import com.smart.school.adapter.item.MyPageItem;
import com.smart.school.adapter.item.RsvStatusServiceResponse;
import com.smart.school.adapter.item.VersionItem;
import com.smart.school.app.config.iConfig;
import com.smart.school.dialog.adapter.item.CodeItem;
import com.smart.school.school.item.BulletinItem;
import com.smart.school.school.item.FoodMenuItem;
import com.smart.school.school.item.FoodResponse;
import com.smart.school.school.item.LoginResponse;
import com.smart.school.school.item.MyInfoResponse;
import com.smart.school.school.item.SchoolListItem;
import com.smart.school.school.item.ValMessage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by tae kwon on 2017-08-02.
 * Retrofit 과 OkHttp 는 Square社(https://square.github.io/)에서 만든 오픈라이브러리 입니다.
 * Retrofit : REST 통신을 위한 클라이언트 라이브러리
 * OkHttp :  HTTP & HTTP/2 통신 클라이언트 라이브러리
 * Rxandroid : 안드로이드에서 Observer 패턴, Iterator 패턴을 사용 할 수 있게 하는 라이브러리
 */

public interface APIService {

    // Get 방식, 파라메터는 @Query("파라메터명")으로 보낼 수 있습니다.
    // Bean 객체를 생성하지 않고 JsonObject 로 받을 수 있습니다.
    @GET(iConfig.URL_LOGIN2)
    Call<JsonObject> Project(@Query("email") String email);

    // Get 방식, 주소가 고정되지 않는 상황에서는 @Path 를 통해 주소를 다이나믹하게 넣을 수 있습니다.
     @GET("/data/app/surf/{project_id}")
     Call<JsonObject> apiList(@Path("project_id") String project_id);

    @GET(iConfig.URL_GET_VERSION)
    Call<VersionItem> getVersion();



    @GET(iConfig.URL_GET_MY_INFO)
    Call<MyInfoResponse> getMyInfo(
            @Query(iConfig.USER_NO) String user_no
    );

    @GET(iConfig.URL_UPDATE_MY_INFO)
    Call<ValMessage> updateMyInfo(
            @Query("user_no") String user_no,
            @Query("bus_no") String bus_no,
            @Query("busstop_no") String busstop_no,
            @Query("allergy_info") String allergy_info
    );

    @GET(iConfig.URL_UPDATE_MENU_REVIEW)
    Call<ValMessage> updateMenuReview(
            @Query("student_no") String student_no,
            @Query("menudate") String menudate,
            @Query("lunchdinner") String lunchdinner,
            @Query("remark") String remark,
            @Query("arrfoodmenu") String arrfoodmenu
    );

    @GET(iConfig.URL_UPDATE_RECOMMEND_REVIEW)
    Call<ValMessage> updateRecommendReview(
            @Query("student_no") String student_no,
            @Query("food_no") String no,
            @Query("review_status") String review_status
    );

    @GET(iConfig.URL_ADD_RECOMMEND_FOOD)
    Call<ValMessage> addRecommendFood(
            @Query("student_no") String student_no,
            @Query("food_name") String food_name
    );

    @GET(iConfig.URL_MUST_BUY_CATEGORY)
    Call<ArrayList<MustBuyItem>> mustBuyCategory(
            @Query(iConfig.PARAM_KEY_CITY_NO) String city_no,
            @Query(iConfig.PARAM_KEY_LANG) String lang
    );

    @GET(iConfig.URL_BRAND_LIST)
    Call<BrandResponse> brandGoods(
            @Query(iConfig.PARAM_KEY_LANG) String lang,
            @Query(iConfig.PARAM_KEY_PARENT_TYPE) String parent_type,
            @Query(iConfig.PARAM_KEY_PARENT_NO) String parent_no,
            @Query(iConfig.PARAM_KEY_KEY_LANG) String key_lang,
            @Query(iConfig.PARAM_KEY_KEY) String key,
            @Query(iConfig.PARAM_KEY_CATEGORY) String category,
            @Query(iConfig.PARAM_KEY_SORT_TYPE) String sort_type,
            @Query(iConfig.PARAM_KEY_PER_PAGE) String per_page,
            @Query(iConfig.PARAM_KEY_PAGE) String page,
            @Query(iConfig.PARAM_KEY_MEMBER_NO) String member_no
    );

    @GET(iConfig.URL_BRAND_LIST)
    Call<BrandResponse> brandGoods2(
            @QueryMap Map<String, String> options
    );



    @GET(iConfig.URL_GET_CODE)
    Call<CodeResponse> getCode(
            @Query(iConfig.PARAM_KEY_PARENT_NO) String parent_no,
            @Query(iConfig.PARAM_KEY_LANG) String lang
    );

    @GET(iConfig.URL_SET_DELETE)
    Call<CodeItem> setDeleteTour(
            @Query(iConfig.PARAM_KEY_USER_NO) String user_no,
            @Query(iConfig.PARAM_KEY_TOUR_NO) String tour_no
    );


    @GET(iConfig.URL_SET_SURF_SHOPPING_TIP_NOW_DELETE)
    Call<CodeItem> setSurfShoppingTipNowDelete(
            @Query(iConfig.PARAM_KEY_USER_NO) String user_no,
            @Query(iConfig.PARAM_KEY_TIP_NO) String tip_no
    );

    @GET(iConfig.URL_SET_SURF_SHOPPING_TIP_COMMENT_DELETE)
    Call<CodeItem> setSurfShoppingTipNowCommentDelete(
            @Query(iConfig.PARAM_KEY_USER_NO) String user_no,
            @Query(iConfig.PARAM_KEY_NO) String no
    );

    @GET(iConfig.URL_SET_SHOPPING_TIP_NOW_LIKE)
    Call<CodeItem> setShoppingTipNowLike(
            @Query(iConfig.PARAM_KEY_USER_NO) String user_no,
            @Query(iConfig.PARAM_KEY_TIP_NO) String tip_no,
            @Query(iConfig.PARAM_KEY_FLAG) String flag
    );

    @GET(iConfig.URL_SET_SHOPPING_TIP_FILE_DEL)
    Call<CodeItem> setShoppingTipNowFileDel(
            @Query(iConfig.PARAM_KEY_FILE_NO) String file_no
    );

    @GET(iConfig.URL_SET_SURF_USER_TOUR_FILE_DELETE)
    Call<CodeItem> setSurfUserTourFileDel(
            @Query(iConfig.PARAM_KEY_FILE_NO) String file_no
    );


    @GET(iConfig.URL_MY_PAGE)
    Call<MyPageItem> getMyPage(
            @Query(iConfig.PARAM_KEY_USER_NO) String user_no,
            @Query(iConfig.PARAM_KEY_LANG) String lang
    );

    @GET(iConfig.URL_SET_SURF_POI_EVAL_DELETE)
    Call<CodeItem> setSurfPoiEvalDelete(
            @Query(iConfig.PARAM_KEY_USER_NO) String user_no,
            @Query("eval_no") String tip_no
    );

    @GET(iConfig.URL_SET_SURF_POI_EVAL_FILE_DEL)
    Call<CodeItem> setSurfPoiEvalFileDel(
            @Query(iConfig.PARAM_KEY_FILE_NO) String file_no
    );



    @GET(iConfig.URL_SET_RESERVATION_SHOPPING_GOODS_PROC)
    Call<CodeItem> setReservationShoppingGoodsProc(
            @Query(iConfig.PARAM_KEY_RSV_NO) String rsv_no,
            @Query(iConfig.PARAM_KEY_USER_NO) String user_no,
            @Query(iConfig.PARAM_KEY_TOUR_NO) String tour_no,
            @Query(iConfig.PARAM_KEY_POI_NO) String poi_no,
            @Query(iConfig.PARAM_KEY_GOODS_NO) String goods_no,
            @Query(iConfig.PARAM_KEY_CURRENCY) String currency,
            @Query(iConfig.PARAM_KEY_RESERVE_PRICE) String reserve_price,
            @Query(iConfig.PARAM_KEY_AMOUNT) String amount,
            @Query(iConfig.PARAM_KEY_MODE) String mode
    );



    @GET(iConfig.URL_GET_RESERVATION_STATUS_SERVICE)
    Call<RsvStatusServiceResponse> getReservationStatusService(
            @Query(iConfig.PARAM_KEY_LANG) String lang,
            @Query(iConfig.PARAM_KEY_USER_NO) String user_no
    );



    @GET(iConfig.Url_PUSH_DEVICE_REG)
    Call<CodeItem> pushDeviceReg(
            @Query(iConfig.PARAM_KEY_DEVICE_TOKEN) String device_token,
            @Query(iConfig.PARAM_KEY_DEVICE_FLAG) String device_flag,
            @Query(iConfig.PARAM_KEY_UUID) String uuid,
            @Query(iConfig.PARAM_KEY_LANG) String lang,
            @Query(iConfig.PARAM_KEY_USER_FLAG) String user_flag,
            @Query(iConfig.PARAM_KEY_USER_NO) String user_no
    );



    @GET(iConfig.URL_LOGOUT)
    Call<CodeItem> logout(
    );

    @GET(iConfig.URL_COUNTRY_LIST)
    Call<CountryListResponse> countryList(
            @Query(iConfig.PARAM_KEY_LANG) String lang
    );




    @GET(iConfig.URL_GET_RECOMMEND_REST_DETAIL_SERVICE)
    Call<ArrayList<CommItem>> getRecommendRestDetailService(
            @Query(iConfig.PARAM_KEY_POI_NO) String poi_no,
            @Query(iConfig.PARAM_KEY_LANG) String lang,
            @Query(iConfig.PARAM_KEY_CITY_LANG) String city_lang
    );

    @GET(iConfig.URL_GET_TELEPHONE_API2)
    Call<ArrayList<CommItem>> getTelephoneApi2(
            @Query(iConfig.PARAM_KEY_LANG) String lang
    );



    @GET(iConfig.URL_GET_BUS_ROUTE_INFO)
    Call<SchoolListItem> getBusRouteInfo(
            @Query("bsno") String mBsNo);

    @GET(iConfig.URL_GET_BULLETIN_LIST)
    Call<ArrayList<BulletinItem>> getBulletinList();

    @GET(iConfig.URL_GET_FOOD_LIST)
    Call<FoodResponse> getFoodList(
            @Query("menudate") String menudate,
            @Query("lunchdinner") String lunchdinner,
            @Query("student_no") String student_no);

    @GET(iConfig.URL_GET_RECOMMEND_FOOD)
    Call<ArrayList<FoodMenuItem>> getRecommendFood(
            @Query("student_no") String student_no);
    /** URL encoding 하여 보냅니다.
     *  POST 방식, 파라메터는 @Field("파라메터명") 으로 보낼 수 있습니다.
     */
    @FormUrlEncoded
    @POST(iConfig.URL_LOGIN)
    Call<LoginResponse> login(
            @FieldMap HashMap<String, String> params
    );

    @FormUrlEncoded
    @POST(iConfig.URL_SIGN_UP)
    Call<CodeItem> signUp(
            @FieldMap HashMap<String, String> params
    );

    //URL encoding 하여 보냅니다.
    //POST 방식, 파라메터는 @Field("파라메터명") 으로 보낼 수 있습니다.
    @FormUrlEncoded
    @POST(iConfig.URL_SET_TOUR_REG)
    Call<CodeItem> setTourSave(
            @FieldMap HashMap<String, String> params
    );

    @FormUrlEncoded
    @POST(iConfig.URL_SET_SURF_SHOPPING_TIP_NOW_COMMENT_SAVE)
    Call<CodeItem> setSurfShoppingTipNowCommentSave(
            @FieldMap HashMap<String, String> params
    );

    @FormUrlEncoded
    @POST(iConfig.URL_SET_SURF_SHOPPING_TIP_NOW_NOTIFY_SAVE)
    Call<CodeItem> setSurfShoppingTipNowNotifySave(
            @FieldMap HashMap<String, String> params
    );

    @FormUrlEncoded
    @Multipart
    @POST(iConfig.URL_SET_PRICE_REPORT)
    Call<CodeItem> setPriceReport(
            @FieldMap Map<String, RequestBody> params,
            @Part Map<String, RequestBody> file
    );

    @FormUrlEncoded
    @POST(iConfig.URL_SET_SURF_USER_TOUR_GOODS_SAVE)
    Call<CodeItem> setSurfUserTourGoodsSave(
            @FieldMap HashMap<String, String> params
    );

    @FormUrlEncoded
    @POST(iConfig.URL_SET_SURF_USER_TOUR_GOODS_DELETE)
    Call<CodeItem> setSurfUserTourGoodsDelete(
            @FieldMap HashMap<String, String> params
    );

    @FormUrlEncoded
    @POST(iConfig.URL_SET_TOUR_GOODS)
    Call<CodeItem> setTourGoods(
            @FieldMap HashMap<String, String> params
    );

    @FormUrlEncoded
    @POST(iConfig.URL_SET_TOUR_SHOP)
    Call<CodeItem> setTourShop(
            @FieldMap HashMap<String, String> params
    );

    @FormUrlEncoded
    @POST(iConfig.URL_SET_TOUR_BRAND)
    Call<CodeItem> setTourBrand(
            @FieldMap HashMap<String, String> params
    );

    @FormUrlEncoded
    @POST(iConfig.URL_SET_SURF_USER_PUBLIC_COUPON_SAVE_DELETE)
    Call<CodeItem> setSurfUserPublicCoupon(
            @FieldMap HashMap<String, String> params
    );

    @FormUrlEncoded
    @POST(iConfig.URL_SET_SURF_RESERVATION_STATUS_10)
    Call<CodeItem> setSurfReservationStatus10(
            @FieldMap HashMap<String, String> params
    );

    @FormUrlEncoded
    @POST(iConfig.URL_GET_SMS_CERTIFICATION)
    Call<CodeItem> getSmsCertification(
            @FieldMap HashMap<String, String> params
    );

    @FormUrlEncoded
    @POST(iConfig.URL_GET_SMS_CERTIFICATION_NO)
    Call<CodeItem> getSmsCertificationNo(
            @FieldMap HashMap<String, String> params
    );

    @FormUrlEncoded
    @POST(iConfig.URL_SET_SURF_RESERVATION_STATUS_CANCEL)
    Call<CodeItem> setSurfReservationStatusCancel(
            @FieldMap HashMap<String, String> params
    );

    @FormUrlEncoded
    @POST(iConfig.URL_SET_SURF_RESERVATION_STATUS_DELETE)
    Call<CodeItem> setSurfReservationStatusDelete(
            @FieldMap HashMap<String, String> params
    );

    @FormUrlEncoded
    @POST(iConfig.URL_SET_RESERVATION_SHOPPING_MAKE_RNO)
    Call<CodeItem> setReservationShoppingMakeRno(
            @FieldMap HashMap<String, String> params
    );

    @FormUrlEncoded
    @POST(iConfig.URL_SET_RESERVATION_SHOPPING_UPDATE_RNO)
    Call<CodeItem> setReservationShoppingUpdateRno(
            @FieldMap HashMap<String, String> params
    );

    @FormUrlEncoded
    @POST(iConfig.URL_SURF_USER_UPDATE)
    Call<CodeItem> surfUserUpdate(
            @FieldMap HashMap<String, String> params
    );

    @FormUrlEncoded
    @POST(iConfig.URL_CHANGE_PW)
    Call<CodeItem> changePw(
            @FieldMap HashMap<String, String> params
    );

    @FormUrlEncoded
    @POST(iConfig.URL_SET_SURF_USER_MENU_ORDER)
    Call<CodeItem> setSurfUserMenuOrder(
            @FieldMap HashMap<String, String> params
    );

    @FormUrlEncoded
    @POST(iConfig.URL_SET_SURF_USER_MENU_UPDATE)
    Call<CodeItem> setSurfUserMenuUpdate(
            @FieldMap HashMap<String, String> params
    );

    @FormUrlEncoded
    @POST(iConfig.URL_SET_SURF_USER_MENU_DELETE)
    Call<CodeItem> setSurfUserMenuDelete(
            @FieldMap HashMap<String, String> params
    );

    @FormUrlEncoded
    @POST(iConfig.URL_SET_SURF_USER_MENU_AMOUNT)
    Call<CodeItem> setSurfUserMenuAmount(
            @FieldMap HashMap<String, String> params
    );

    @FormUrlEncoded
    @POST(iConfig.URL_MEMBER_LEAVE)
    Call<CodeItem> setSurfUserDelete(
            @FieldMap HashMap<String, String> params
    );

    @Multipart
    @POST(iConfig.URL_SET_SURF_SHOPPING_TIP_NOW_SAVE)
    Call<CodeItem> setSurfShoppingTipNowSave(
            @PartMap Map<String, RequestBody> params,
            @Part List<MultipartBody.Part> file
    );

    @Multipart
    @POST(iConfig.URL_SET_SURF_POI_EVAL_SAVE)
    Call<CodeItem> setSurfPoiEvalSave(
            @PartMap Map<String, RequestBody> params,
            @Part List<MultipartBody.Part> file
    );

    @Multipart
    @POST(iConfig.URL_SET_PRICE_REPORT)
    Call<CodeItem> setPriceReport(
            @PartMap Map<String, RequestBody> params,
            @Part List<MultipartBody.Part> file
    );

    @Multipart
    @POST(iConfig.URL_SET_SURF_USER_TOUR_GOODS_SAVE)
    Call<CodeItem> setSurfUserTourGoodsSave(
            @PartMap Map<String, RequestBody> params,
            @Part List<MultipartBody.Part> file
    );

    @Multipart
    @POST(iConfig.URL_SURF_IMAGE_UPLOAD)
    Call<CodeItem> surfProfilePhoto(
            @PartMap Map<String, RequestBody> params,
            @Part List<MultipartBody.Part> file
    );
}
