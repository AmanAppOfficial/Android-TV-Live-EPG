package com.streamly.TVApp.streamly.settings.privacy;

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
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PrivacyDialog extends Dialog {
    private static final String TAG= PrivacyDialog.class.getSimpleName();
    private Context context;
    private Button btnOk;
    private TextView tvPrivacy;
    private String auth_token;
    private ProgressBar progressBar;

    public PrivacyDialog(@NonNull Context context) {
        super(context);
        this.context=context;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_privacy);
        CommonUtility.checkInternetConnection(context);
        initViews();
        auth_token = TokenStorage.readSharedToken(context, ConstantUtility.AUTH_TOKEN, null);

        if (ConstantUtility.isNetworkConnected) {
            getPrivacyPolicy();
        }else {
            CommonUtility.showToast(context,ConstantUtility.CHECK_INTERNET_CONNECTION);
        }

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });
    }

    private void getPrivacyPolicy() {
        progressBar.setVisibility(View.VISIBLE);
        ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);
        Call<PrivacyPolicyModel> call=apiInterface.getPoliciesText(NetworkKeys.KEY_BEARER +auth_token);
        call.enqueue(new Callback<PrivacyPolicyModel>() {
            @Override
            public void onResponse(Call<PrivacyPolicyModel> call, Response<PrivacyPolicyModel> response) {
                try {
                    if (response.isSuccessful() && response.body() != null) {
                        String policyData= response.body().getData();
                        tvPrivacy.setText(policyData);

                    } else if (response.code() == 401) {
                        CommonUtility.showToast(context, response.body().getMessage());
                        TokenStorage.clearSharedToken(context);
                        context.startActivity(new Intent(context, LogInActivity.class));
                        ((Activity) context).finish();
                    }else if (response.code()==404){

                        TokenStorage.clearSharedToken(context);
                        CommonUtility.showToast(context,ConstantUtility.SESSION_EXPIRED);
                        context.startActivity(new Intent(context, LogInActivity.class));
                        ((Activity)context).finish();
                    } else if (response.code() == 500) {
                        CommonUtility.showToast(context, ConstantUtility.SERVER_ISSUE);
                    }
                }catch(Exception ex){
                    ex.printStackTrace();
                    Log.e(TAG, "Exception "+ex);
                }
                Log.e(TAG,"connected Devices "+response.code()+"");
                progressBar.setVisibility(View.INVISIBLE);
                btnOk.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<PrivacyPolicyModel> call, Throwable t) {
                Log.e(TAG,"failure " +t);
                progressBar.setVisibility(View.INVISIBLE);
                btnOk.setVisibility(View.VISIBLE);

            }
        });

    }

    private void initViews() {
        btnOk=findViewById(R.id.btn_ok);
        tvPrivacy=findViewById(R.id.tv_privacy);
        progressBar=findViewById(R.id.progress_bar);
    }
}
