package com.streamly.TVApp.streamly.search_channels;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.streamly.TVApp.streamly.R;
import com.streamly.TVApp.streamly.epg.ChannellistModel;
import com.streamly.TVApp.streamly.epg.Datum;
import com.streamly.TVApp.streamly.local_storage.TokenStorage;
import com.streamly.TVApp.streamly.log_in.LogInActivity;
import com.streamly.TVApp.streamly.receivers.NetworkStateReceiver;
import com.streamly.TVApp.streamly.retrofit_clients.ApiClient;
import com.streamly.TVApp.streamly.retrofit_clients.ApiInterface;
import com.streamly.TVApp.streamly.utilities.CommonUtility;
import com.streamly.TVApp.streamly.utilities.ConstantUtility;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends FragmentActivity implements NetworkStateReceiver.NetworkStateReceiverListener {
    private static final String TAG = SearchActivity.class.getSimpleName();
    private EditText edSearch;
    private ImageView imgVoiceSearch;
    private ProgressBar progressBar;
    private RecyclerView rvSearch;
    String authToken;
    private static final int REQUEST_CODE = 1234;

    List<SearchModel> searchList = new ArrayList<>();
    SearchAdapter searchAdapter;
    private LinearLayout llNoDataFound,layoutSearch,layoutInternet;
    NetworkStateReceiver networkStateReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initViews();
        edSearch.setEnabled(false);

        networkStateReceiver = new NetworkStateReceiver();
        networkStateReceiver.addListener(this);
        this.registerReceiver(networkStateReceiver, new IntentFilter(android.net.ConnectivityManager.CONNECTIVITY_ACTION));

        authToken = TokenStorage.readSharedToken(SearchActivity.this, ConstantUtility.AUTH_TOKEN, null);

        voiceSearch();

        edSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    imgVoiceSearch.setEnabled(false);
                    searchAdapter.getFilter().filter(edSearch.getText().toString());

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void initViews(){
        llNoDataFound = findViewById(R.id.llNoDataFound);
        edSearch = findViewById(R.id.ed_search);
        imgVoiceSearch = findViewById(R.id.img_voice_search);
        progressBar = findViewById(R.id.progress_bar);
        layoutSearch = findViewById(R.id.layout_search);
        rvSearch = findViewById(R.id.rv_search);
        layoutInternet = findViewById(R.id.layout_internet);
    }

    private void voiceSearch() {
        PackageManager pm = getPackageManager();
        List<ResolveInfo> activities = pm.queryIntentActivities(new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
        if (activities.size() == 0) {
            imgVoiceSearch.setEnabled(false);
            Toast.makeText(this, "Recognizer not present", Toast.LENGTH_SHORT).show();
//            speak.setText();
        }

        imgVoiceSearch.setOnClickListener(v -> startVoiceRecognitionActivity());
    }

    /**
     * Fire an intent to start the voice recognition activity.
     */
    private void startVoiceRecognitionActivity() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Voice searching...");
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            // Populate the wordsList with the String values the recognition engine thought it heard
            final ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (!matches.isEmpty()) {
                String Query = matches.get(0);
                edSearch.setText(Query);
                imgVoiceSearch.setEnabled(false);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    //#neovifyissueresolved28sep
    private void getChannelList() {
        layoutSearch.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ChannellistModel> call = apiInterface.getChannelList("Bearer " + authToken);
        call.enqueue(new Callback<ChannellistModel>() {
            @Override
            public void onResponse(Call<ChannellistModel> call, Response<ChannellistModel> response) {
                try {

                    if (response.isSuccessful() && response.body() != null) {

                        List<Datum> datumList = response.body().getData();

                        for (int i = 0; i < datumList.size(); i++) {

                            String channelTitle = datumList.get(i).getCallSign();
                            String channelLogo = datumList.get(i).getLogo();
                            String videoUrl = datumList.get(i).getUrl();

                            searchList.add(new SearchModel(channelTitle, channelLogo));
                        }
                        searchAdapter = new SearchAdapter(SearchActivity.this, searchList, SearchActivity.this);
                        rvSearch.setLayoutManager(new GridLayoutManager(SearchActivity.this, 4));
                        rvSearch.setAdapter(searchAdapter);

                        edSearch.setEnabled(true);

                    } else if (response.code() == 401) {
                        CommonUtility.showToast(SearchActivity.this, response.body().getMessage());
                        edSearch.setEnabled(false);

                    } else if (response.code() == 404) {
                        TokenStorage.clearSharedToken(SearchActivity.this);
                        CommonUtility.showToast(SearchActivity.this, ConstantUtility.SESSION_EXPIRED);
                        SearchActivity.this.startActivity(new Intent(SearchActivity.this, LogInActivity.class));
                        SearchActivity.this.finish();
                        edSearch.setEnabled(false);

                    } else if (response.code() == 500) {
                        CommonUtility.showToast(SearchActivity.this, ConstantUtility.SERVER_ISSUE);
                        edSearch.setEnabled(false);

                    }
                    progressBar.setVisibility(View.INVISIBLE);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    Log.e(TAG, "Exception " + ex);
                    edSearch.setEnabled(false);
                }
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<ChannellistModel> call, Throwable t) {
                Log.e(TAG, "failure: " + call + " " + t.getLocalizedMessage());
                CommonUtility.showToast(SearchActivity.this, ConstantUtility.SERVER_ISSUE);
                progressBar.setVisibility(View.INVISIBLE);
                rvSearch.setVisibility(View.GONE);
                layoutInternet.setVisibility(View.VISIBLE);
                edSearch.setEnabled(false);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        networkStateReceiver.removeListener(this);
        this.unregisterReceiver(networkStateReceiver);
    }

    @Override
    public void networkAvailable() {
        layoutInternet.setVisibility(View.INVISIBLE);
        getChannelList();

    }

    @Override
    public void networkUnavailable() {
        layoutSearch.setVisibility(View.INVISIBLE);
        layoutInternet.setVisibility(View.VISIBLE);

    }
}