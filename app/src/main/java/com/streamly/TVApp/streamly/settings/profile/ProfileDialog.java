package com.streamly.TVApp.streamly.settings.profile;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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
import com.streamly.TVApp.streamly.settings.profile.profilemodel.ProfileModel;
import com.streamly.TVApp.streamly.utilities.CommonUtility;
import com.streamly.TVApp.streamly.utilities.ConstantUtility;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileDialog extends Dialog {
    private final String TAG=ProfileDialog.class.getSimpleName();
    private Button btnOk;
    private Context context;
    private String auth_token;
    private TextView userName,userEmail,buildDate;
    private ProgressBar progressBar;

    public ProfileDialog(@NonNull Context context) {
        super(context);
        this.context=context;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_profile);
        CommonUtility.checkInternetConnection(context);

        initViews();
        auth_token = TokenStorage.readSharedToken(context, ConstantUtility.AUTH_TOKEN, null);
        if (ConstantUtility.isNetworkConnected) {
            getUserProfile();
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

    public static int getColorWithAlpha(int color, float ratio) {
        int newColor = 0;
        int alpha = Math.round(Color.alpha(color) * ratio);
        int r = Color.red(color);
        int g = Color.green(color);
        int b = Color.blue(color);
        newColor = Color.argb(alpha, r, g, b);
        return newColor;
    }

    private void initViews() {
//        findViewById(R.id.fram_layout).setBackgroundColor(0x00000000);
//        Log.e(TAG, "opacityValue "+findViewById(R.id.frame_layout).getBackground().getOpacity());

        btnOk=findViewById(R.id.btn_ok);
        userName=findViewById(R.id.text_name);
        userEmail=findViewById(R.id.text_email);
        buildDate=findViewById(R.id.text_updated_date);
        progressBar=findViewById(R.id.progress_bar);
        findViewById(R.id.layout_name).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void getUserProfile() {
        progressBar.setVisibility(View.VISIBLE);
        ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);
        Call<ProfileModel> call=apiInterface.getUserProfile("Bearer "+auth_token);

        call.enqueue(new Callback<ProfileModel>() {
            @Override
            public void onResponse(Call<ProfileModel> call, Response<ProfileModel> response) {
                try{

                    if (response.isSuccessful() && response.body()!=null){
                        userName.setText(response.body().getData().getName());
                        userEmail.setText(response.body().getData().getEmail());
                        buildDate.setText(response.body().getData().getUpdatedAt());

                    }else if (response.code()==401){
                        CommonUtility.showToast(context,response.body().getMessage());
                        TokenStorage.clearSharedToken(context);
                        context.startActivity(new Intent(context, LogInActivity.class));
                        ((Activity)context).finish();
                    } else if (response.code()==404){
                        TokenStorage.clearSharedToken(context);
                        CommonUtility.showToast(context,ConstantUtility.SESSION_EXPIRED);
                        context.startActivity(new Intent(context, LogInActivity.class));
                        ((Activity)context).finish();
                    }
                    else if (response.code()==500){
                        CommonUtility.showToast(context,ConstantUtility.SERVER_ISSUE);
                    }

                }catch (Exception ex){
                    ex.printStackTrace();
                    Log.e(TAG, "Exception "+ex);
                }
                Log.e(TAG,"profileResponCode "+response.code()+"");
                progressBar.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onFailure(Call<ProfileModel> call, Throwable t) {
                Log.e(TAG,"failure: "+call+" "+t.getLocalizedMessage());
                CommonUtility.showToast(context,ConstantUtility.SERVER_ISSUE);
                progressBar.setVisibility(View.INVISIBLE);
            }
        });

    }

}
