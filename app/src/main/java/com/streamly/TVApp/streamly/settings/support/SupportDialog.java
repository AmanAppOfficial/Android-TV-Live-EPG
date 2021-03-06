package com.streamly.TVApp.streamly.settings.support;

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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.streamly.TVApp.streamly.R;
import com.streamly.TVApp.streamly.local_storage.TokenStorage;
import com.streamly.TVApp.streamly.log_in.LogInActivity;
import com.streamly.TVApp.streamly.retrofit_clients.ApiClient;
import com.streamly.TVApp.streamly.retrofit_clients.ApiInterface;
import com.streamly.TVApp.streamly.utilities.CommonUtility;
import com.streamly.TVApp.streamly.utilities.ConstantUtility;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SupportDialog extends Dialog {
    private static final String TAG=SupportDialog.class.getSimpleName();
    private Context context;
    private Button btnOk;
    private RecyclerView rvSupport;
    private ProgressBar progressBar;
    private List<SupportTextType> list=new ArrayList<>();
    private TextView messageText;

    public SupportDialog(@NonNull Context context) {
        super(context);
        this.context=context;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_support);
        CommonUtility.checkInternetConnection(context);
        initViews();

        if (ConstantUtility.isNetworkConnected) {
            getSupportDetails();
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

    private void initViews() {
        btnOk=findViewById(R.id.btn_ok);
        rvSupport=findViewById(R.id.rv_support);
        progressBar=findViewById(R.id.progress_bar);
        messageText=findViewById(R.id.message_text);
    }

    private void getSupportDetails() {
        progressBar.setVisibility(View.VISIBLE);
        ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);
        Call<SupportModel> call=apiInterface.getSupportNumbers();
        call.enqueue(new Callback<SupportModel>() {
            @Override
            public void onResponse(Call<SupportModel> call, Response<SupportModel> response) {
                try {
                    if (response.isSuccessful() && response.body() != null) {

                     Data datalist= response.body().getData();
                     list.add(new SupportTextType(datalist.getEmail()));

                        messageText.setText(response.body().getMessage());

                        SupportAdpater supportAdpater=new SupportAdpater(context,list);
                        rvSupport.setLayoutManager(new LinearLayoutManager(context, RecyclerView.VERTICAL,false));
                        rvSupport.setAdapter(supportAdpater);
                        btnOk.setVisibility(View.VISIBLE);

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
                Log.e(TAG,"support_numbers "+response.code()+"");
                progressBar.setVisibility(View.INVISIBLE);
                btnOk.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<SupportModel> call, Throwable t) {
                Log.e(TAG, "Throwables  "+t);
                progressBar.setVisibility(View.INVISIBLE);
                btnOk.setVisibility(View.VISIBLE);
            }
        });
    }
}
