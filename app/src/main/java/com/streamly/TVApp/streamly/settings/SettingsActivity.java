package com.streamly.TVApp.streamly.settings;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.streamly.TVApp.streamly.MainActivity;
import com.streamly.TVApp.streamly.R;
import com.streamly.TVApp.streamly.local_storage.TokenStorage;
import com.streamly.TVApp.streamly.log_in.LogInActivity;
import com.streamly.TVApp.streamly.retrofit_clients.ApiClient;
import com.streamly.TVApp.streamly.retrofit_clients.ApiInterface;
import com.streamly.TVApp.streamly.settings.privacy.PrivacyDialog;
import com.streamly.TVApp.streamly.settings.profile.profilemodel.ProfileModel;
import com.streamly.TVApp.streamly.settings.registerdevice.RegisterDeviceDialog;
import com.streamly.TVApp.streamly.settings.signout.SignOutDialog;
import com.streamly.TVApp.streamly.settings.support.SupportDialog;
import com.streamly.TVApp.streamly.utilities.CommonUtility;
import com.streamly.TVApp.streamly.utilities.ConstantUtility;
import com.vlstr.blurdialog.BlurDialog;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SettingsActivity extends FragmentActivity implements View.OnClickListener {
    private static final String TAG = SettingsActivity.class.getSimpleName();
    private TextView textChannels;
    private LinearLayout account,registerDevice,support,privacy,signOut;

    //#neovifyissueresolved28sep
    private Dialog pDialog;
    private Button btnOk;
    private TextView userName, userEmail, buildDate;
    private ProgressBar progressBar;
    private BlurDialog blurView;
    private String auth_token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CommonUtility.checkInternetConnection(this);
        setContentView(R.layout.activity_settings);
        initViews();

        initListeners();

//        account.clearFocus();
        account.requestFocus();
    }

    private void initViews(){
        textChannels=findViewById(R.id.text_channels);
        account=findViewById(R.id.account);
        registerDevice=findViewById(R.id.register_device);
        support=findViewById(R.id.support);
        privacy=findViewById(R.id.privacy);
        signOut=findViewById(R.id.sign_out);

    }

    private void initListeners() {
        account.setOnClickListener(this);
        registerDevice.setOnClickListener(this);
        support.setOnClickListener(this);
        privacy.setOnClickListener(this);
        signOut.setOnClickListener(this);
        textChannels.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.account:
                viewFocusAccount(view);
                break;

            case R.id.register_device:
                RegisterDeviceDialog registerDeviceDialog = new RegisterDeviceDialog(this);
                registerDeviceDialog.getWindow().setBackgroundDrawableResource(R.drawable.white_transparent);
                registerDeviceDialog.show();
                break;

            case R.id.support:
                SupportDialog supportDialog = new SupportDialog(this);
                supportDialog.getWindow().setBackgroundDrawableResource(R.drawable.white_transparent);
                supportDialog.show();
                break;

            case R.id.privacy:
                PrivacyDialog privacyDialog = new PrivacyDialog(this);
                privacyDialog.getWindow().setBackgroundDrawableResource(R.drawable.white_transparent);
                privacyDialog.show();
                break;

            case R.id.sign_out:
                SignOutDialog signOutDialog = new SignOutDialog(this);
                signOutDialog.getWindow().setBackgroundDrawableResource(R.drawable.white_transparent);
                signOutDialog.show();
                break;

            case R.id.text_channels:
                startActivity(new Intent(SettingsActivity.this, MainActivity.class));
                finish();
                break;
            //handle multiple view click events
        }
    }

    private void viewFocusAccount(View view) {
        try {
            // Check if no view has focus:
            view = this.getCurrentFocus();
            if (view != null) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
            DisplayMetrics metrics = getResources().getDisplayMetrics();
            int screenWidth = (int) (metrics.widthPixels * 0.90);
            pDialog = new Dialog(SettingsActivity.this);
            pDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            pDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                    pDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
            pDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            pDialog.setContentView(R.layout.dialog_profile_new);
            pDialog.getWindow().setLayout(screenWidth, RecyclerView.LayoutParams.WRAP_CONTENT);
            blurView = pDialog.findViewById(R.id.blurView);
            blurView.create(getWindow().getDecorView(), 20);
//                    blurView.setBackgroundColor(getResources().getColor(R.color.shaded_color));
//            blurView.setBackground(getResources().getDrawable(R.drawable.layout_focus));
            blurView.setBackground(getResources().getDrawable(R.drawable.layout_focus2));
            blurView.show();

            btnOk = pDialog.findViewById(R.id.btn_ok);
            userName = pDialog.findViewById(R.id.text_name);
            userEmail = pDialog.findViewById(R.id.text_email);
            buildDate = pDialog.findViewById(R.id.text_updated_date);
            progressBar = pDialog.findViewById(R.id.progress_bar);

            CommonUtility.checkInternetConnection(SettingsActivity.this);
            auth_token = TokenStorage.readSharedToken(SettingsActivity.this, ConstantUtility.AUTH_TOKEN, null);
            if (ConstantUtility.isNetworkConnected) {
                getUserProfile();
            } else {
                CommonUtility.showToast(SettingsActivity.this, ConstantUtility.CHECK_INTERNET_CONNECTION);
            }

            btnOk.setOnClickListener(v -> pDialog.dismiss());

            pDialog.setCancelable(true);
            pDialog.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //#neovifyissueresolved28sep
    private void getUserProfile() {
        progressBar.setVisibility(View.VISIBLE);
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ProfileModel> call = apiInterface.getUserProfile("Bearer " + auth_token);
        call.enqueue(new Callback<ProfileModel>() {
            @Override
            public void onResponse(Call<ProfileModel> call, Response<ProfileModel> response) {
                try {

                    if (response.isSuccessful() && response.body() != null) {
                        userName.setText(response.body().getData().getName());
                        userEmail.setText(response.body().getData().getEmail());
                        buildDate.setText(response.body().getData().getUpdatedAt());

                    } else if (response.code() == 401) {
                        CommonUtility.showToast(SettingsActivity.this, response.body().getMessage());
                        TokenStorage.clearSharedToken(SettingsActivity.this);
                        SettingsActivity.this.startActivity(new Intent(SettingsActivity.this, LogInActivity.class));
                        SettingsActivity.this.finish();
                    } else if (response.code() == 404) {
                        TokenStorage.clearSharedToken(SettingsActivity.this);
                        CommonUtility.showToast(SettingsActivity.this, ConstantUtility.SESSION_EXPIRED);
                        SettingsActivity.this.startActivity(new Intent(SettingsActivity.this, LogInActivity.class));
                        SettingsActivity.this.finish();
                    } else if (response.code() == 500) {
                        CommonUtility.showToast(SettingsActivity.this, ConstantUtility.SERVER_ISSUE);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    Log.e(TAG, "Exception " + ex);
                }
                Log.e(TAG, "profileResponCode " + response.code() + "");
                progressBar.setVisibility(View.INVISIBLE);
            }
            @Override
            public void onFailure(Call<ProfileModel> call, Throwable t) {
                Log.e(TAG, "failure: " + call + " " + t.getLocalizedMessage());
                CommonUtility.showToast(SettingsActivity.this, ConstantUtility.SERVER_ISSUE);
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }

}
