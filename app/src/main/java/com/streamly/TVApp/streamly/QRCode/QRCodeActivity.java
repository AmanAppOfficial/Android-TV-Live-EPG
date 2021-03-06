package com.streamly.TVApp.streamly.QRCode;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.streamly.TVApp.streamly.R;
import com.streamly.TVApp.streamly.log_in.LogInActivity;
import com.streamly.TVApp.streamly.retrofit_clients.ApiClient;
import com.streamly.TVApp.streamly.retrofit_clients.ApiInterface;
import com.streamly.TVApp.streamly.utilities.CommonUtility;
import com.streamly.TVApp.streamly.utilities.ConstantUtility;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QRCodeActivity extends FragmentActivity {
    private static final String TAG = QRCodeActivity.class.getSimpleName();
    private ProgressBar progressBar;
    private TextView tvLink;
    private ImageView imgQrCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_q_r_code);
        CommonUtility.checkInternetConnection(QRCodeActivity.this);
        initViews();
        getQRCode();

    }

    private void initViews(){
        progressBar=findViewById(R.id.progress_bar);
        tvLink=findViewById(R.id.tv_link);
        imgQrCode=findViewById(R.id.img_qr_code);

    }

    private void getQRCode() {
        progressBar.setVisibility(View.VISIBLE);
        ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);
        Call<QrModel> call=apiInterface.getQrCode();
        call.enqueue(new Callback<QrModel>() {
            @Override
            public void onResponse(Call<QrModel> call, Response<QrModel> response) {
            try {

                if (response.isSuccessful() && response.body() != null) {
                    String registerLink=response.body().getMessage();
                    String qrImage=response.body().getData();
                    setQRImage(registerLink,qrImage);

                } else if (response.code() == 401) {
                    CommonUtility.showToast(QRCodeActivity.this, response.body().getMessage());
                    startActivity(new Intent(QRCodeActivity.this, LogInActivity.class));
                    finish();
                }else if (response.code()==404){
                    CommonUtility.showToast(QRCodeActivity.this,ConstantUtility.SESSION_EXPIRED);
                    startActivity(new Intent(QRCodeActivity.this, LogInActivity.class));
                    finish();
                } else if (response.code() == 500) {
                    CommonUtility.showToast(QRCodeActivity.this, ConstantUtility.SERVER_ISSUE);
                }

            }catch (Exception e){
                e.printStackTrace();
                Log.e(TAG,"Exception: "+e.toString());
            }   progressBar.setVisibility(View.INVISIBLE);
                Log.e(TAG,"ResponCode "+response.code()+"");
            }

            @Override
            public void onFailure(Call<QrModel> call, Throwable t) {
                Log.e(TAG,"failure " +t);
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void setQRImage(String register_URL,String qrCode_Link) {

        tvLink.setText(register_URL.trim());
        Glide.with(QRCodeActivity.this).load(qrCode_Link).into(imgQrCode);
    }
}