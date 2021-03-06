package com.streamly.TVApp.streamly.playervideo.zipcode;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.streamly.TVApp.streamly.R;
import com.streamly.TVApp.streamly.local_storage.TokenStorage;
import com.streamly.TVApp.streamly.log_in.LogInActivity;
import com.streamly.TVApp.streamly.retrofit_clients.ApiClient;
import com.streamly.TVApp.streamly.retrofit_clients.ApiInterface;
import com.streamly.TVApp.streamly.utilities.CommonUtility;
import com.streamly.TVApp.streamly.utilities.ConstantUtility;
import com.streamly.TVApp.streamly.utilities.NetworkKeys;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ZipCodeDialog extends Dialog {
    private final String TAG= ZipCodeDialog.class.getSimpleName();
    private Context context;
    private Button btnContinue;
    private ProgressBar progressBar;
    private TextView poptext;
    String popMessage,uniqueId,dbLat,dblon,authToken;


    public ZipCodeDialog(@NonNull Context context,String popMessage, String uniqueId,
                         String dbLat, String dblon,String authToken){
        super(context);
        this.context=context;
        this.popMessage=popMessage;
        this.uniqueId=uniqueId;
        this.dbLat=dbLat;
        this.dblon=dblon;
        this.authToken=authToken;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_zip_code);
        CommonUtility.checkInternetConnection(context);
        initViews();

        poptext.setText(popMessage);
        if (ConstantUtility.isNetworkConnected) {
//            getSupportDetails();
        }else {
            CommonUtility.showToast(context,ConstantUtility.CHECK_INTERNET_CONNECTION);
        }


        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                outStationLocationPlayCall();
                cancel();
            }
        });



    }

    private void outStationLocationPlayCall() {
        Map<String, String> map=new HashMap<>();
        map.put("latitude",dbLat);
        map.put("longitude",dblon);
        map.put("deviceid",uniqueId);
//        map.put("latitude","33.88768366489836");
//        map.put("longitude","-17.90419095752048");
//        map.put("deviceid",uniqueId);

        ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);
        Call<OutstationLocationModel> call=apiInterface.postOutstationlocationplay(NetworkKeys.KEY_BEARER+authToken,map);
        call.enqueue(new Callback<OutstationLocationModel>() {
            @Override
            public void onResponse(Call<OutstationLocationModel> call, Response<OutstationLocationModel> response) {
                try{
                    Log.e("ZipCode: ",response.body()+"");
                    if (response.isSuccessful() && response.body()!=null && response.body().getSuccess()==true){
                        CommonUtility.showToast(context,response.body().getMessage());

                    }else if (response.code()==401){
                        CommonUtility.showToast(context,response.body().getMessage());
                        TokenStorage.clearSharedToken(context);
                        context.startActivity(new Intent(context, LogInActivity.class));
                        ((Activity)context).finish();
                    }
                    else if (response.code()==403 || response.body().getSuccess()==false){
                        CommonUtility.showToast(context,ConstantUtility.SERVER_ISSUE);
                    }
                    else if (response.code()==500){
                        CommonUtility.showToast(context,ConstantUtility.SERVER_ISSUE);
                    }

                }catch (Exception ex){
                    ex.printStackTrace();
                    Log.e(TAG, "Exception "+ex);
                }
            }

            @Override
            public void onFailure(Call<OutstationLocationModel> call, Throwable t) {
                CommonUtility.showToast(context,ConstantUtility.SERVER_ISSUE);

            }
        });
    }

    private void initViews() {
        btnContinue=findViewById(R.id.btn_continue);
        progressBar=findViewById(R.id.progress_bar);
        poptext=findViewById(R.id.poptext);
    }
}
