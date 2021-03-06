package com.streamly.TVApp.streamly;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.jakewharton.threetenabp.AndroidThreeTen;
import com.streamly.TVApp.streamly.epg.ChannellistModel;
import com.streamly.TVApp.streamly.epg.Datum;
import com.streamly.TVApp.streamly.epg.GenereAdapter;
import com.streamly.TVApp.streamly.epg.comedy.ComedyFragment;
import com.streamly.TVApp.streamly.epg.family.FamilyFragment;
import com.streamly.TVApp.streamly.epg.kids.KidsFragment;
import com.streamly.TVApp.streamly.epg.movies.MoviesFragment;
import com.streamly.TVApp.streamly.epg.news.NewsFragment;
import com.streamly.TVApp.streamly.epg.others.OtherFragment;
import com.streamly.TVApp.streamly.epg.sports.SportFragment;
import com.streamly.TVApp.streamly.local_storage.TokenStorage;
import com.streamly.TVApp.streamly.log_in.LogInActivity;
import com.streamly.TVApp.streamly.retrofit_clients.ApiClient;
import com.streamly.TVApp.streamly.retrofit_clients.ApiInterface;
import com.streamly.TVApp.streamly.search_channels.SearchActivity;
import com.streamly.TVApp.streamly.search_channels.SearchAdapter;
import com.streamly.TVApp.streamly.search_channels.SearchModel;
import com.streamly.TVApp.streamly.settings.SettingsActivity;
import com.streamly.TVApp.streamly.utilities.CommonUtility;
import com.streamly.TVApp.streamly.utilities.ConstantUtility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends FragmentActivity implements View.OnClickListener {
    private static final String TAG=MainActivity.class.getSimpleName();
    String authToken;
    private LinearLayout imageSearch;
//    private TextView textAll,textOthers,textFamily,textComedy,
//            textSports,textNews,textMovies,textKids,settingText,goBackText;
private TextView settingText,goBackText;

    private FirebaseAnalytics mFirebaseAnalytics;
    private RecyclerView recyclerView;

    List<String> generList = new ArrayList<String>();
    Map<String , List<Datum>> dataList = new HashMap<String , List<Datum>>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidThreeTen.init(this);
        setContentView(R.layout.activity_main);
        initViews();
        authToken = TokenStorage.readSharedToken(MainActivity.this, ConstantUtility.AUTH_TOKEN, null);

         Log.e(TAG,"authToken "+authToken);

        FirebaseCrashlytics.getInstance().log("Test Crash in the Main Activity");
        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "id");
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "EPG Fragment");
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "EPG Channels");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);



        getChannelList();



    }

    public void fragment(String type , List<Datum> dataListm){

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new KidsFragment(MainActivity.this , dataListm))
                    .commit();


    }

    private void getChannelList() {

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ChannellistModel> call = apiInterface.getChannelList("Bearer " + authToken);
        call.enqueue(new Callback<ChannellistModel>() {
            @Override
            public void onResponse(Call<ChannellistModel> call, Response<ChannellistModel> response) {
                try {

                    if (response.isSuccessful() && response.body() != null) {

                        List<Datum> datumList = response.body().getData();

                        for (int i = 0; i < datumList.size(); i++) {

//                            String channelTitle = datumList.get(i).getCallSign();
//                            String channelLogo = datumList.get(i).getLogo();
//                            String videoUrl = datumList.get(i).getUrl();

                            if(!generList.contains(datumList.get(i).getGenrename().get(0)))
                            generList.add(datumList.get(i).getGenrename().get(0));


                        }



                        dataList.put("All" , datumList);
                        for(String s : generList){
                            Log.e("doneok" , s);

                            List<Datum> tempList = new ArrayList<Datum>();
                            for(int i=0 ; i<datumList.size() ; i++){
                                if(datumList.get(i).getGenrename().get(0).equals(s)){
                                    Log.e("done" , "here");
                                    tempList.add(datumList.get(i));
                                }
                            }
                            dataList.put(s , tempList);
                        }


                        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false));
                        GenereAdapter adapter = new GenereAdapter(MainActivity.this, dataList);
                        recyclerView.setAdapter(adapter);


                        fragment("all" , datumList);



                    } else if (response.code() == 401) {
                        CommonUtility.showToast(MainActivity.this, response.body().getMessage());

                    } else if (response.code() == 404) {
                        TokenStorage.clearSharedToken(MainActivity.this);
                        CommonUtility.showToast(MainActivity.this, ConstantUtility.SESSION_EXPIRED);


                    } else if (response.code() == 500) {
                        CommonUtility.showToast(MainActivity.this, ConstantUtility.SERVER_ISSUE);

                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    Log.e(TAG, "Exception " + ex);
                }
            }

            @Override
            public void onFailure(Call<ChannellistModel> call, Throwable t) {
                Log.e(TAG, "failure: " + call + " " + t.getLocalizedMessage());
                CommonUtility.showToast(MainActivity.this, ConstantUtility.SERVER_ISSUE);
            }
        });
    }



    private void initViews() {
        imageSearch=findViewById(R.id.image_search);
        recyclerView = findViewById(R.id.main_recycler_view);
//        textAll=findViewById(R.id.text_all);
//        textOthers=findViewById(R.id.text_others);
//        textFamily=findViewById(R.id.text_family);
//        textComedy=findViewById(R.id.text_comedy);
//        textSports=findViewById(R.id.text_sports);
//        textNews=findViewById(R.id.text_news);
//        textMovies=findViewById(R.id.text_movies);
//        textKids=findViewById(R.id.text_kids);
        settingText=findViewById(R.id.setting_text);
        goBackText=findViewById(R.id.go_back_text);
        imageSearch.setFocusable(true);
        imageSearch.setFocusableInTouchMode(true);
//        textAll.setFocusable(true);
//        textAll.setFocusableInTouchMode(true);
//        textOthers.setFocusable(true);
//        textOthers.setFocusableInTouchMode(true);
//        textFamily.setFocusable(true);
//        textFamily.setFocusableInTouchMode(true);
//        textComedy.setFocusable(true);
//        textComedy.setFocusableInTouchMode(true);
//        textSports.setFocusable(true);
//        textSports.setFocusableInTouchMode(true);
//        textNews.setFocusable(true);
//        textNews.setFocusableInTouchMode(true);
//        textMovies.setFocusable(true);
//        textMovies.setFocusableInTouchMode(true);
//        textKids.setFocusable(true);
//        textKids.setFocusableInTouchMode(true);
        settingText.setFocusable(true);
        settingText.setFocusableInTouchMode(true);
        goBackText.setFocusable(true);
        goBackText.setFocusableInTouchMode(true);


        imageSearch.setOnClickListener(this);
//        textAll.setOnClickListener(this);
//        textOthers.setOnClickListener(this);
//        textFamily.setOnClickListener(this);
//        textComedy.setOnClickListener(this);
//        textSports.setOnClickListener(this);
//        textNews.setOnClickListener(this);
//        textMovies.setOnClickListener(this);
//        textKids.setOnClickListener(this);
        settingText.setOnClickListener(this);
        goBackText.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.image_search:
                startActivity(new Intent(MainActivity.this, SearchActivity.class));
                break;

//            case R.id.text_all:
//                getSupportFragmentManager().beginTransaction()
//                        .replace(R.id.fragment_container, new EpgFragment(MainActivity.this))
//                        .commit();
//                break;
//
//            case R.id.text_others:
//                getSupportFragmentManager().beginTransaction()
//                        .replace(R.id.fragment_container, new OtherFragment(MainActivity.this))
//                        .commit();
//
//                break;
//
//            case R.id.text_family:
//                getSupportFragmentManager().beginTransaction()
//                        .replace(R.id.fragment_container, new FamilyFragment(MainActivity.this))
//                        .commit();
//                break;
//
//            case R.id.text_sports:
//                getSupportFragmentManager().beginTransaction()
//                        .replace(R.id.fragment_container, new SportFragment(MainActivity.this))
//                        .commit();
//                break;
//
//            case R.id.text_news:
//                getSupportFragmentManager().beginTransaction()
//                        .replace(R.id.fragment_container, new NewsFragment(MainActivity.this))
//                        .commit();
//                break;
//
//            case R.id.text_comedy:
//                getSupportFragmentManager().beginTransaction()
//                        .replace(R.id.fragment_container, new ComedyFragment(MainActivity.this))
//                        .commit();
//                break;
//
//            case R.id.text_movies:
//                getSupportFragmentManager().beginTransaction()
//                        .replace(R.id.fragment_container, new MoviesFragment(MainActivity.this))
//                        .commit();
//                break;
//
//            case R.id.text_kids:
//                getSupportFragmentManager().beginTransaction()
//                        .replace(R.id.fragment_container, new KidsFragment(MainActivity.this))
//                        .commit();
//                break;

            case R.id.setting_text:
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                finish();
                break;

            case R.id.go_back_text:
               finish();
                break;
        }
    }
}




