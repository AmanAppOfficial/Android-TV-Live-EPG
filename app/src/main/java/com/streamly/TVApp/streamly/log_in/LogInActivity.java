package com.streamly.TVApp.streamly.log_in;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.fragment.app.FragmentActivity;

import com.google.gson.Gson;
import com.streamly.TVApp.streamly.MainActivity;
import com.streamly.TVApp.streamly.QRCode.QRCodeActivity;
import com.streamly.TVApp.streamly.local_storage.TokenStorage;
import com.streamly.TVApp.streamly.retrofit_clients.ApiClient;
import com.streamly.TVApp.streamly.retrofit_clients.ApiInterface;
import com.streamly.TVApp.streamly.settings.support.SupportDialog;
import com.streamly.TVApp.streamly.termsofuses.TermsOfUses;
import com.streamly.TVApp.streamly.utilities.CommonUtility;
import com.streamly.TVApp.streamly.utilities.ConstantUtility;
import com.streamly.TVApp.streamly.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LogInActivity extends FragmentActivity {
    private static final String TAG = LogInActivity.class.getSimpleName();
    private ProgressDialog dialog;
    private  String uniqueId;
    private EditText edUserName,edPassword;
    private TextView txtNeedHelp;
    private Button btnSignIn,btnRegisterView,btnTermsUses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CommonUtility.checkInternetConnection(LogInActivity.this);
        setContentView(R.layout.activity_log_in);
        initViews();
         uniqueId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);


        dialog= CommonUtility.custom_loader(LogInActivity.this);
        TokenStorage.clearSharedToken(LogInActivity.this);

        txtNeedHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SupportDialog supportDialog=new SupportDialog(LogInActivity.this);
                supportDialog.getWindow().setBackgroundDrawableResource(R.drawable.white_transparent);
                supportDialog.show();

            }
        });

        edUserName.setText("arun@gmail.com");
//        edUserName.setText("sahilkashyap64@gmail.com");
        edPassword.setText("123456789");
       btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ConstantUtility.isNetworkConnected){
                    if (edUserName.getText().toString().matches("")){
                        CommonUtility.showToast(LogInActivity.this,ConstantUtility.FILL_EMAIL);

                    }else if (edUserName.getText().toString().matches(ConstantUtility.EMAIL_REGEX)==false){
                        CommonUtility.showToast(LogInActivity.this,ConstantUtility.WRONG_EMAIL);

                    } else if (edPassword.getText().toString().matches("")){
                        CommonUtility.showToast(LogInActivity.this, ConstantUtility.FILL_PASSW);

                    }else {
                        signInCall();
                    }

                }else {
                    CommonUtility.showToast(LogInActivity.this,ConstantUtility.CHECK_INTERNET_CONNECTION);
                }

            }
        });

        btnRegisterView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(LogInActivity.this, QRCodeActivity.class));

                /*Toast.makeText(LogInActivity.this, "Please go to "+ ApiConfig.BASE_URL
                        +" for creating new account", Toast.LENGTH_SHORT).show();*/
            }
        });


        btnTermsUses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TermsOfUses terms=new TermsOfUses(LogInActivity.this);
                terms.show();
            }
        });
    }


    private void initViews() {
        edUserName=findViewById(R.id.ed_user_name);
        edPassword=findViewById(R.id.ed_password);
        txtNeedHelp=findViewById(R.id.txt_need_help);
        btnSignIn=findViewById(R.id.btn_sign_in);
        btnRegisterView=findViewById(R.id.btn_register_view);
        btnTermsUses=findViewById(R.id.btn_terms_uses);
    }

    private void signInCall() {
        dialog.show();
        ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);
        Call<LogInModel> call=apiInterface.postLogInCall(edUserName.getText().toString(),
                                                        edPassword.getText().toString(),uniqueId);
        call.enqueue(new Callback<LogInModel>() {
            @Override
            public void onResponse(Call<LogInModel> call, Response<LogInModel> response) {
                try {
                    Log.e(TAG, "resCode "+response.code());
                if (response.isSuccessful() && response.body()!=null){
                    String access_token=response.body().getAccessToken();
                    Long userId=response.body().getUser().getId();

                    TokenStorage.savedSharedToken(LogInActivity.this, ConstantUtility.USER_ID,userId.toString());
                    TokenStorage.savedSharedToken(LogInActivity.this, ConstantUtility.AUTH_TOKEN,access_token);
                    CommonUtility.showToast(LogInActivity.this,response.body().getMessage());
                    startActivity(new Intent(LogInActivity.this, MainActivity.class));
                    finish();

                    edUserName.setText("");
                    edPassword.setText("");

                }else if (response.code()==401){
                    LogInModel logInModel=new Gson().fromJson(response.errorBody().string(),LogInModel.class);
                    CommonUtility.showToast(LogInActivity.this,logInModel.getMessage());
                } else if (response.code()==429){
//                    CommonUtility.showToast(LogInActivity.this,response.body().getMessage());
                    CommonUtility.showToast(LogInActivity.this,ConstantUtility.MAX_USER_LOGIN);
                }
                else if (response.code()==500){
                    CommonUtility.showToast(LogInActivity.this,ConstantUtility.SERVER_ISSUE);
                }

              }catch (Exception e){
                    e.printStackTrace();
                    Log.e(TAG,"Exception: "+e.toString());
                } dialog.dismiss();
                Log.e(TAG,"ResponCode "+response.code()+"");
            }
            @Override
            public void onFailure(Call<LogInModel> call, Throwable t) {
                Log.e(TAG,"failure: "+t.getMessage());
                Log.e(TAG,"failRequest: "+call.request());
                CommonUtility.showToast(LogInActivity.this,ConstantUtility.SERVER_ISSUE);
                dialog.dismiss();
            }
        });
    }
}
