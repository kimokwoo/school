package com.smart.school.Main.activity;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Outline;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpClientStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.smart.school.BuildConfig;
import com.smart.school.R;
import com.smart.school.app.config.AppController;
import com.smart.school.app.config.Config;
import com.smart.school.app.config.iConfig;
import com.smart.school.network.APIService;
import com.smart.school.school.item.CustomPictureItem;
import com.smart.school.util.BitmapUtil;

import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



public class BaseActivity extends AppCompatActivity implements Response.Listener, Response.ErrorListener {

    Context mContext;
    public AbstractHttpClient mHttpClient;
    public RequestQueue mQueue;

    protected Uri mImageCaptureUri;
    protected File mImageFile;

    protected Config config = Config.getInstance();

    private Fragment mCurrFragment;

    private TextView mTv_Title;

    private String strTag;
    private String strMode = "";

    public static ArrayList<AppCompatActivity> activities = new ArrayList<>();

    protected Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(iConfig.SERVER_URL_REAL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();


    protected Retrofit retrofit3 = new Retrofit.Builder()
            .baseUrl(iConfig.SERVER_URL_REAL1)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    protected APIService apiService = retrofit.create(APIService.class);
    protected APIService apiService3 = retrofit3.create(APIService.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mHttpClient = new DefaultHttpClient();

    }

//    @Override
//    protected void attachBaseContext(Context newBase) {
//        super.attachBaseContext(LocaleManager.Companion.setLocale(newBase));
//    }

    public void addHttpQueue(Context context, String url) {
        // we hold a reference to the HttpClient in order to be able to get/set cookies
        mContext = context;

        mQueue = Volley.newRequestQueue(context, new HttpClientStack(mHttpClient));
        mQueue.add(createRequest(url));
    }

    public void addHttpQueue(Context context, String url, String tag) {
        // we hold a reference to the HttpClient in order to be able to get/set cookies
        mContext = context;
        strTag = tag;

        mQueue = Volley.newRequestQueue(context, new HttpClientStack(mHttpClient));
        mQueue.add(createRequest(url, strTag));
    }

    public void addHttpQueue(Context context, String url, final Map<String, String> map, String tag) {
        // we hold a reference to the HttpClient in order to be able to get/set cookies
        mContext = context;
        strTag = tag;

        mQueue = Volley.newRequestQueue(context, new HttpClientStack(mHttpClient));
        mQueue.add(createPostRequest(url, map, strTag));
    }

    public StringRequest createRequest(String url) {

        return new StringRequest(Request.Method.GET,
                url,
                this,
                this);
    }

    public StringRequest createRequest(String url, final String tag) {

        return new StringRequest(Request.Method.GET,
                url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        onCustomResponse(response, tag);
                    }
                },
                this);
    }

    public StringRequest createPostRequest(String url, final Map<String, String> map, final String tag) {

        return new StringRequest(Request.Method.POST,
                url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        onPosResponse(response, tag);
                    }
                },
                BaseActivity.this) {

            @Override
            protected Map<String, String> getParams() {
                return map;
            }
        };
    }

    public void sendData(String url, final String flag) {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        onResponse(response);
                        progressDialog.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        progressDialog.dismiss();
                    }
                });
        // Adding request to request queue
        req.setRetryPolicy(new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        AppController.getInstance().addToRequestQueue(req, flag);
    }

    public void sendDataPost(String url, final String flag, final HashMap<String, String> map) {

        StringRequest str = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        onPosResponse(s, flag);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        onErrorResponse(volleyError);
                    }
                }) {
            @Override
            protected HashMap<String, String> getParams() {
                return map;
            }
        };
        // Adding request to request queue
        str.setRetryPolicy(new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(str, flag);
    }


    protected OkHttpClient getRequestHeader() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .build();

        return client;
    }


    protected void setToolbarTitle(String str){
        mTv_Title.setText(Html.fromHtml(str));
    }

    protected void setToolbarTitle(int resId){
        mTv_Title.setText(resId);
    }


    protected void onBackClick(){

    }

    protected void onCompleteClick(){

    }

    protected void onCancelClick(){

    }

    public Map<String, File> getImageMap2(ArrayList<CustomPictureItem> arrUploadImgFile) {
        Map<String, File> imageMap = new HashMap<>();
        if (arrUploadImgFile.size() > 0) {
            for (int i = 0; i < arrUploadImgFile.size(); i++) {
                String filePath = arrUploadImgFile.get(i).getFilePath();

                if (!TextUtils.isEmpty(filePath)) {
                    if (TextUtils.isEmpty(arrUploadImgFile.get(i).getPhotoUrl())) {
                        File file = new File(filePath);
                        double bytes = file.length();
                        double imgKilobytes = bytes / 1024;
                        if (imgKilobytes > 150) {

                            int exifOrientation = BitmapUtil.GetExifOrientation(filePath);

                            Bitmap orgImage = BitmapFactory.decodeFile(filePath);

                            int height = orgImage.getHeight() * 500 / orgImage.getWidth();
                            Bitmap resize = Bitmap.createScaledBitmap(orgImage, 500, height, true);
                            Bitmap exifBitmap = BitmapUtil.GetRotatedBitmap(resize, exifOrientation);

                            String[] fileName = arrUploadImgFile.get(i).getFileName().split("\\.");
                            String resizeImgName = fileName[0] + "_resize_500x" + height + ".png";
                            String path = BitmapUtil.saveBitmapToPng(exifBitmap, resizeImgName);

                            arrUploadImgFile.get(i).setFile(new File(path));
                        }

                        if(!TextUtils.isEmpty(arrUploadImgFile.get(i).getFile().getPath())) {
                            if (!arrUploadImgFile.get(i).getFile().getPath().startsWith("http://")) {
                                imageMap.put(iConfig.PARAM_KEY_UP_FILE + "[" + i + "]", new File(arrUploadImgFile.get(i).getFile().getPath()));
                            }
                        }
                    }
                }
            }
        } else {
//            imageMap.put(iConfig.PARAM_KEY_UP_FILE + "[]", new File(""));
        }
        return imageMap;
    }

    public Map<String, File> getImageMap3(ArrayList<CustomPictureItem> arrUploadImgFile) {
        Map<String, File> imageMap = new HashMap<>();
        if (arrUploadImgFile.size() > 0) {
            for (int i = 0; i < arrUploadImgFile.size(); i++) {
                String filePath = arrUploadImgFile.get(i).getFilePath();

                if (!TextUtils.isEmpty(filePath)) {
                    if (TextUtils.isEmpty(arrUploadImgFile.get(i).getPhotoUrl())) {
                        File file = new File(filePath);
                        double bytes = file.length();
                        double imgKilobytes = bytes / 1024;
                        if (imgKilobytes > 150) {

                            int exifOrientation = BitmapUtil.GetExifOrientation(filePath);

                            Bitmap orgImage = BitmapFactory.decodeFile(filePath);

                            int height = orgImage.getHeight() * 1400 / orgImage.getWidth();
                            Bitmap resize = Bitmap.createScaledBitmap(orgImage, 1400, height, true);
                            Bitmap exifBitmap = BitmapUtil.GetRotatedBitmap(resize, exifOrientation);

                            String[] fileName = arrUploadImgFile.get(i).getFileName().split("\\.");
                            String resizeImgName = fileName[0] + "_resize_1400px" + height + ".jpg";
                            String path = BitmapUtil.saveBitmapToJpeg3(exifBitmap, resizeImgName);

                            arrUploadImgFile.get(i).setFile(new File(path));
                        }

                        if(!TextUtils.isEmpty(arrUploadImgFile.get(i).getFile().getPath())) {
                            if (!arrUploadImgFile.get(i).getFile().getPath().startsWith("http://")) {
                                imageMap.put(iConfig.PARAM_KEY_UP_FILE + "[" + i + "]", new File(arrUploadImgFile.get(i).getFile().getPath()));
                            }
                        }
                    }
                }
            }
        } else {
//            imageMap.put(iConfig.PARAM_KEY_UP_FILE + "[]", new File(""));
        }
        return imageMap;
    }

    public void setTransaction(Fragment fragment, String tag) {
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.login_container, fragment, tag);
//            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            ft.addToBackStack(tag);
            //화면이 다시 보여진 후, Fragment 화면을 갱신하고자 할때
            ft.commitAllowingStateLoss();
        }
    }

    public void switchFragment(Fragment fragment) {
        switchFragment(fragment, false);
    }

    public void switchFragment(Fragment fragment, boolean addBackStack) {
        try {
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction ft = manager.beginTransaction();
            ft.replace(R.id.layout_content, fragment);
            if (addBackStack)
                ft.addToBackStack(null);
            ft.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void switchFragment(Fragment fragment, boolean addBackStack, boolean isShow) {
        try {
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction ft = manager.beginTransaction();
            ft.add(R.id.layout_content, fragment);
            if (addBackStack)
                ft.addToBackStack(null);
            if (isShow) {
                ft.show(fragment);
            } else {
                ft.hide(fragment);
            }
            ft.show(fragment);

            ft.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResponse(Object response) {
    }

    public void onCustomResponse(Object object) {

    }

    public void onCustomResponse(String response) {

    }

    public void onCustomResponse(String response, String tag) {

    }

    public void onPosResponse(String response, String tag) {

    }

    @Override
    public void onErrorResponse(VolleyError error) {
    }

    public void checkPermission(String[] permissions, int request) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            boolean checkPermission = hasPermissions(this, permissions);
            // 이 권한을 필요한 이유를 설명해야하는가?
            if (!checkPermission) {
                requestPermissions(permissions, request);
            }
        }
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                /*
                 * 권한이 설정이 되었는지 여부 check 하는 method, 즉 어제 권한설정이 되었어도 오늘 사용자가 권한을 꺼놓았을 수도 있으니깐 Run 하기 전에 Check하는 메소드다.
                 * check 되어있으면 PackageManager.PERMISSION_GRANTED 를 Return한다. 아닐 경우 PERMISSION_DENIED 를 Return 한다 .
                 */
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                } else if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_DENIED) {
                    return true;
                }
            }
        }
        return true;
    }

    public int getFirstPageLen(int itemMaxLen, double maxPageLen, double firstPageChildLen) {
        return (int) (itemMaxLen - (firstPageChildLen * Math.floor((maxPageLen - 1d))));
    }


    public Fragment getCurrFragment() {
        return mCurrFragment;
    }





    public void setShopEvaluateCurrentStatus(TextView textView, String sortName) {

        textView.setText(String.format("[%1$s] [%2$s]", sortName));
    }

    protected boolean isSDCARDMounted() {
        String status = Environment.getExternalStorageState();

        return status.equals(Environment.MEDIA_MOUNTED);
    }



    public void setActivities(){
        activities = new ArrayList<>();
    }

    public ArrayList<AppCompatActivity> getActivities(){
        return activities;
    }


    public void setAnimSlidUp(View view, int height){
        TranslateAnimation animation = new TranslateAnimation(
                0,
                0,
                height,
                0
        );
        animation.setDuration(2000);
        animation.setFillAfter(true);
        view.startAnimation(animation);
    }

    //Outline 없애기
    protected void setOutline(View view){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            ViewOutlineProvider outlineProvider = new ViewOutlineProvider() {
                @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void getOutline(View view, Outline outline) {
                    int fabSize = view.getHeight();
                    //int fabSize = getResources().getDimensionPixelSize(R.dimen.fab_big);
                    outline.setOval(0, 0, 0, 0);
                }
            };
            view.setOutlineProvider(outlineProvider);
        }
    }

    protected Uri getPhotoUri(File file){
        return FileProvider.getUriForFile(BaseActivity.this, BuildConfig.APPLICATION_ID + ".fileprovider", file);
    }

    protected void goGoogleMap(String lat, String lng){
        Uri uri = Uri.parse("geo:" + config.getLat(this) + "," + config.getLng(this) +
                "?z=17&q=" + lat + "," + lng);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, uri);
        mapIntent.setPackage("com.google.android.apps.maps");
        if (mapIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(mapIntent);
        }
    }

    // This method  converts String to RequestBody
    protected RequestBody toRequestBody (String value) {
        return RequestBody.create(MediaType.parse("text/plain"), value);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == iConfig.REQUEST_CHECK_SETTINGS){
            switch(resultCode){
                case RESULT_OK: {
                    Toast.makeText(this, "위치 사용 가능", Toast.LENGTH_SHORT).show();
                }
                break;

                case RESULT_CANCELED: {
                }
                break;
            }
        }
    }
}
