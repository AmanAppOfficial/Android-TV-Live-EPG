package com.streamly.TVApp.streamly.playervideo;

import android.content.Intent;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.text.format.Formatter;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.drm.DefaultDrmSessionManager;
import com.google.android.exoplayer2.drm.DrmSessionManager;
import com.google.android.exoplayer2.drm.ExoMediaCrypto;
import com.google.android.exoplayer2.drm.FrameworkMediaDrm;
import com.google.android.exoplayer2.drm.HttpMediaDrmCallback;
import com.google.android.exoplayer2.drm.MediaDrmCallback;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.source.dash.DashMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.upstream.HttpDataSource;
import com.google.android.exoplayer2.util.Util;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.streamly.TVApp.streamly.R;
import com.streamly.TVApp.streamly.local_storage.TokenStorage;
import com.streamly.TVApp.streamly.log_in.LogInActivity;
import com.streamly.TVApp.streamly.playervideo.drm.DrmKeyModel;
import com.streamly.TVApp.streamly.playervideo.drm.DrmViewModel;
import com.streamly.TVApp.streamly.playervideo.geofence.GeofenceModel;
import com.streamly.TVApp.streamly.playervideo.geofence.GeofenceViewModel;
import com.streamly.TVApp.streamly.playervideo.ip.IpModel;
import com.streamly.TVApp.streamly.playervideo.ip.IpViewModel;
import com.streamly.TVApp.streamly.playervideo.zipcode.ZipCodeDialog;
import com.streamly.TVApp.streamly.retrofit_clients.ApiResponse;
import com.streamly.TVApp.streamly.utilities.CommonUtility;
import com.streamly.TVApp.streamly.utilities.ConstantUtility;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerActivity extends FragmentActivity {
    private static final String TAG=PlayerActivity.class.getSimpleName();
    String authToken,uniqueId,userId;
    SimpleExoPlayer player;
    PlayerView playerView;
    private FirebaseAnalytics mFirebaseAnalytics;
    Bundle params;
    Handler handler = new Handler();
    final int delay = 15000; //milliseconds
    Runnable runnable;


    @Override
    protected void onStart() {
        super.onStart();
    }

    private HttpDataSource.Factory getDefaultDataSourceFactory() {
        return new DefaultHttpDataSourceFactory(Util.getUserAgent(this, "sample-player"));
    }

    private HttpMediaDrmCallback createMediaDrmCallback(String licenseUrl, String[] keyRequestPropertiesArray) {
        HttpDataSource.Factory licenseDataSourceFactory = getDefaultDataSourceFactory();
        HttpMediaDrmCallback drmCallback = new HttpMediaDrmCallback(licenseUrl, licenseDataSourceFactory);
        if (keyRequestPropertiesArray != null) {
            for (int i = 0; i < keyRequestPropertiesArray.length - 1; i += 2) {
                drmCallback.setKeyRequestProperty(keyRequestPropertiesArray[i],
                        keyRequestPropertiesArray[i + 1]);
            }
        }
        return drmCallback;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        // Create player
        player = new SimpleExoPlayer.Builder(this).build();
        playerView = findViewById(R.id.playerView);
        authToken= TokenStorage.readSharedToken(PlayerActivity.this, ConstantUtility.AUTH_TOKEN, null);
        userId= TokenStorage.readSharedToken(PlayerActivity.this, ConstantUtility.USER_ID, null);
        uniqueId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        playerView.setUseController(false);

        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        params = new Bundle();
        String channelName=getIntent().getStringExtra("channel_name");
        params.putString("call_sign", channelName+"_Android");
        params.putString("inside_geofence", "false");
        mFirebaseAnalytics.setUserId(userId);

         getLatLong();
    }

    private void getLatLong() {
        WifiManager wm = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        String ip = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());
        Log.e(TAG,"IP_Address: "+ip);
//        157.42.78.63


        IpViewModel ipViewModel=new ViewModelProvider(this).get(IpViewModel.class);
        ipViewModel.getLatLong("57.42.78.63",PlayerActivity.this).observe(this, new Observer<ApiResponse>() {
            @Override
            public void onChanged(ApiResponse apiResponse) {
                IpModel ipModel= (IpModel) apiResponse.getApiResponse();
                switch (apiResponse.getResponseCode()){
                    case 200:
                        Log.e(TAG,"ipGetStatus= "+ipModel.getStatus());
                        if (ipModel.getStatus().equals("fail")){
                            CommonUtility.showToast(PlayerActivity.this,ConstantUtility.IPNOTVALID);
                            Log.e(TAG,"emulator IP is not valid for getting coordinate");
                            finish();
                        }else {
                            Double dbLat=ipModel.getLat();
                            Double dblon=ipModel.getLon();
                            postGeofenceCall(dbLat,dblon);
                        }
                        break;
                    case 401: CommonUtility.showToast(PlayerActivity.this,ipModel.getMessage());
                    break;
                    case 403: CommonUtility.showToast(PlayerActivity.this,ConstantUtility.SERVER_ISSUE);
                        break;
                    case 404: CommonUtility.showToast(PlayerActivity.this,ConstantUtility.SERVER_ISSUE);
                        break;
                    case 500: CommonUtility.showToast(PlayerActivity.this,ConstantUtility.SERVER_ISSUE);
                    break;
                }

            }
        });

    }


    private void postGeofenceCall(Double dbLat, Double dblon) {
        Map<String, Double> map=new HashMap<>();
        map.put("latitude",dbLat);
        map.put("longitude",dblon);
//        map.put("latitude",33.88768366489836);
//        map.put("longitude",-17.90419095752048);

        GeofenceViewModel viewModel= new ViewModelProvider(this).get(GeofenceViewModel.class);
        viewModel.getData(map,PlayerActivity.this).observe(this, new Observer<ApiResponse>() {
            @Override
            public void onChanged(ApiResponse apiResponse) {
                GeofenceModel geofenceModel= (GeofenceModel) apiResponse.getApiResponse();
                switch (apiResponse.getResponseCode()){
                    case 200:  getDrmKey();
                    break;
                    case 401:  TokenStorage.clearSharedToken(PlayerActivity.this);
                        CommonUtility.showToast(PlayerActivity.this,geofenceModel.getMessage());
                        startActivity(new Intent(PlayerActivity.this, LogInActivity.class));
                        finish();
                        break;
                    case 403: String popMessage="You are away from your home zip code:  click here to continue viewing.";
                        ZipCodeDialog zipCodeDialog=new ZipCodeDialog(PlayerActivity.this, popMessage,uniqueId, dbLat+"",dblon+"",authToken);
                        zipCodeDialog.getWindow().setBackgroundDrawableResource(R.drawable.white_transparent);
                        zipCodeDialog.show();
                        getDrmKey();
                        break;
                    default: CommonUtility.showToast(PlayerActivity.this,ConstantUtility.SERVER_ISSUE);
                }
            }
        });

    }

    private void getDrmKey() {
        DrmViewModel viewModel=new ViewModelProvider(this).get(DrmViewModel.class);
        viewModel.getData(PlayerActivity.this).observe(this, new Observer<ApiResponse>() {
            @Override
            public void onChanged(ApiResponse apiResponse) {
                DrmKeyModel drmKeyModel= (DrmKeyModel) apiResponse.getApiResponse();
                switch (apiResponse.getResponseCode()){
                    case 200: String drmKey=drmKeyModel.getData();
                        String wvUrl= drmKeyModel.getWidevinelicense();
                        initializePlayer(drmKey,wvUrl);
                        break;
                    case 401:
                        TokenStorage.clearSharedToken(PlayerActivity.this);
                        CommonUtility.showToast(PlayerActivity.this,drmKeyModel.getMessage());
                        startActivity(new Intent(PlayerActivity.this, LogInActivity.class));
                        finish();
                        break;
                    case 404:  TokenStorage.clearSharedToken(PlayerActivity.this);
                        CommonUtility.showToast(PlayerActivity.this,ConstantUtility.SESSION_EXPIRED);
                        startActivity(new Intent(PlayerActivity.this, LogInActivity.class));
                        finish();
                        break;
                    default: CommonUtility.showToast(PlayerActivity.this,ConstantUtility.SERVER_ISSUE);
                        break;
                }
            }
        });

    }


    private void initializePlayer(String drmKey, String wvUrl) {
        String streamUrl=getIntent().getStringExtra("from");

        // URL of the KeyOS MultiKey License API for Widevine
//        String wvUrl = "https://wv-keyos.licensekeyserver.com/";
//        String wvURL1="http://127.0.0.1:8000/api/generic?drm-type=widevine";
//        String wvURL1="https://streamlytv.com/api/generic?drm-type=widevine";
//        String wvUrl1="https://demo.keyos.com/roman/logging-proxy/generic-neovix.php?drm-type=widevine";
//        String streamUrl2="https://channelstore.streamlytv.com/streamly/AETVP.stream/manifest.mpd";
//        Uri dashUri = Uri.parse("https://path/to/your/stream.mpd");

        Uri dashUri = Uri.parse(streamUrl.trim());
//        Uri dashUri = Uri.parse("https://cdn.streamlytv.com/espn/index.m3u8");
//        Uri dashUri = Uri.parse("https://dash.akamaized.net/envivio/EnvivioDash3/manifest.mpd");

        Log.e(TAG, "1) license_server: "+ wvUrl+"\n"
                +"2) DRM_key: "+drmKey+ "\n"
                +"3) Stream_URL: "+streamUrl+ "\n"
                + "4) dash_uri: "+dashUri);

        // Identifies the Widevine DRM
        UUID widevineUuid = UUID.fromString("edef8ba9-79d6-4ace-a3c8-27dcd51d21ed");

        // Security token required to acquire a license. Base64 encoded Authentication XML
        String[] keyRequestProperties = new String[] { "x-keyos-authorization", drmKey };
//        String[] keyRequestProperties = new String[] { "customdata", "PD94bWwgdmVyc2lvbj0iMS4wIj8+CjxLZXlPU0F1dGhlbnRpY2F0aW9uWE1MPjxEYXRhPjxXaWRldmluZVBvbGljeSBmbF9DYW5QZXJzaXN0PSJmYWxzZSIgZmxfQ2FuUGxheT0idHJ1ZSIvPjxXaWRldmluZUNvbnRlbnRLZXlTcGVjIFRyYWNrVHlwZT0iSEQiPjxTZWN1cml0eUxldmVsPjE8L1NlY3VyaXR5TGV2ZWw+PC9XaWRldmluZUNvbnRlbnRLZXlTcGVjPjxGYWlyUGxheVBvbGljeSBwZXJzaXN0ZW50PSJmYWxzZSIvPjxMaWNlbnNlIHR5cGU9InNpbXBsZSIvPjxHZW5lcmF0aW9uVGltZT4yMDIwLTEwLTE5IDE0OjUxOjU1LjAwMDwvR2VuZXJhdGlvblRpbWU+PEV4cGlyYXRpb25UaW1lPjIwMjAtMTAtMTkgMTU6MDY6NTUuMDAwPC9FeHBpcmF0aW9uVGltZT48VW5pcXVlSWQ+ZjEzNGMxNjBlYjU1ZTE5ZTJjMjUxODM1YjZlYjdjZDY8L1VuaXF1ZUlkPjxSU0FQdWJLZXlJZD45NzU0ZTBjZmRkYzY4MmMyMTIxNmRlM2ViNGQxMTg4NTwvUlNBUHViS2V5SWQ+PC9EYXRhPjxTaWduYXR1cmU+dS9YQVgxNnNxU2FvUU43UW9FKzY3TlNuTzY0UXFhbUVBOUFsYnNVSlozOWdUT1l3NEpDTW9md3NaYkFQWWVJaS9DKy9SMkJweHJwVEpuWS9ZRGx2dERoSFluYlNwYy9wMXNKcUxOdmREaE4yejEwS2wyT0hKekE1OTdxU0Urcnd2d3BHd09FSm1iVEFidm8zK3FTQVdsTnQ0dWJoMTg0M1VQU3FYMDVZNFEzdlJLWXdzSzR5SUxRdFJuTStENDJsWXcvcTlpc0pUR0pGVWRqY1hJVnh3QWdPRGpDNEdWQVIycXM1VXhHY1cyT0c0THd6ZWpkeUkwMENrK1FYZHpNcTVab3pUZ243a2ZJaGpPS0JETUx0L2VrNnhNYTZ2VjRTQlZEdWxkYnZCR1BjZW9CVzNYWDc1WFY4VjZxdUxJM29GM3FTM3FZQzUvNkJiRmNuQ0k2YjFRPT08L1NpZ25hdHVyZT48L0tleU9TQXV0aGVudGljYXRpb25YTUw+Cg=="};

        // Create the DRM session manager and set required request parameters.
        MediaDrmCallback mediaDrmCallback = createMediaDrmCallback(wvUrl, keyRequestProperties);
        DrmSessionManager<ExoMediaCrypto> drmSessionManager = new DefaultDrmSessionManager.Builder()
                .setUuidAndExoMediaDrmProvider(widevineUuid, FrameworkMediaDrm.DEFAULT_PROVIDER)
                .build(mediaDrmCallback);




        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(PlayerActivity.this,
                Util.getUserAgent(this, "StreamlyTV"), null);
        //todo HLs for the testing purpose (m3u8)
//        HlsMediaSource mediaSource =new HlsMediaSource.Factory(dataSourceFactory)
//                .setDrmSessionManager(drmSessionManager)
//                .createMediaSource(dashUri);

        //todo DASH for the testing purpose (mpd)
        MediaSource mediaSource = new DashMediaSource.Factory(dataSourceFactory)
                .setDrmSessionManager(drmSessionManager)
                .createMediaSource(dashUri);

//        DashMediaSource mediaSource = new DashMediaSource(dashUri, dataSourceFactory,
//                new DefaultDashChunkSource.Factory(dataSourceFactory),
//                null, null);

        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelector trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));

       player = ExoPlayerFactory.newSimpleInstance(this, trackSelector);




////////////////////////////////////////////////////////////////////////////////////////////////////
        //todo for the DRM protected streaming
        // Create a data source factory.
//        DataSource.Factory dataSourceFactory = getDefaultDataSourceFactory();
        // Create a DASH media source pointing to a DASH manifest uri.
//        MediaSource mediaSource = new DashMediaSource.Factory(dataSourceFactory)
//                .setDrmSessionManager(drmSessionManager)
//                .createMediaSource(dashUri);
/////////////////////////////////////////////////////////////////////////////////////////////////////
        // Attach it to the view
        playerView.setPlayer(player);

        // Preapre the player with content
        player.prepare(mediaSource);

        player.addListener(new Player.EventListener() {
            @Override
            public void onTimelineChanged(Timeline timeline, int reason) {

            }

            @Override
            public void onTimelineChanged(Timeline timeline, @Nullable Object manifest, int reason) {

            }

            @Override
            public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

            }

            @Override
            public void onLoadingChanged(boolean isLoading) {
                /*if (isLoading){
                    mFirebaseAnalytics.logEvent("video_play", params);
                }*/
            }

            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                switch (playbackState) {
                    case ExoPlayer.STATE_IDLE:
                        break;

                    case ExoPlayer.STATE_READY:
                        break;

                    case ExoPlayer.STATE_BUFFERING:
                        break;

                    case ExoPlayer.STATE_ENDED:
                        break;
                }


            }

            @Override
            public void onPlaybackSuppressionReasonChanged(int playbackSuppressionReason) {

            }

            @Override
            public void onIsPlayingChanged(boolean isPlaying) {

                if (isPlaying){
                    mFirebaseAnalytics.logEvent("video_play", params);

                    runnable = new Runnable() {
                        @Override
                        public void run() {
                            //do something
                            params.putString("livestream_seconds_watched", "15");
                            mFirebaseAnalytics.logEvent("video_livestream_duration",params);
                            handler.postDelayed(this, delay);
                        }
                    };
                    handler.postDelayed(runnable, delay);
                }

            }

            @Override
            public void onRepeatModeChanged(int repeatMode) {

            }

            @Override
            public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

            }

            @Override
            public void onPlayerError(ExoPlaybackException error) {
                Log.e(TAG,"error_msg"+error.toString());
                params.putString("error_color", "red");
                params.putString("error_msg", error.getSourceException().toString());
                mFirebaseAnalytics.logEvent("player_error", params);
//                Log.e(TAG,"error_msg2 "+error.getSourceException());

            }

            @Override
            public void onPositionDiscontinuity(int reason) {

            }

            @Override
            public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

            }

            @Override
            public void onSeekProcessed() {

            }
        });

        player.setPlayWhenReady(true);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        handler.removeCallbacks(runnable);
        player.stop();
        Log.e(TAG,"back_press_called");
        finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
        handler.removeCallbacks(runnable);
        Log.e(TAG,"on_stop_called");
        player.stop();
    }
}