package com.streamly.TVApp.streamly.retrofit_clients;

import com.streamly.TVApp.streamly.QRCode.QrModel;
import com.streamly.TVApp.streamly.epg.ChannellistModel;
import com.streamly.TVApp.streamly.epg.channelgenre.ChannelGenreModel;
import com.streamly.TVApp.streamly.playervideo.drm.DrmKeyModel;
import com.streamly.TVApp.streamly.playervideo.geofence.GeofenceModel;
import com.streamly.TVApp.streamly.playervideo.ip.IpModel;
import com.streamly.TVApp.streamly.playervideo.zipcode.OutstationLocationModel;
import com.streamly.TVApp.streamly.settings.privacy.PrivacyPolicyModel;
import com.streamly.TVApp.streamly.settings.profile.profilemodel.ProfileModel;
import com.streamly.TVApp.streamly.settings.registerdevice.ConnectedDeviceModel;
import com.streamly.TVApp.streamly.settings.support.SupportModel;
import com.streamly.TVApp.streamly.termsofuses.TermsModel;
import com.streamly.TVApp.streamly.utilities.NetworkKeys;
import com.streamly.TVApp.streamly.log_in.LogInModel;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    @Headers("Accept: application/json")
    @POST("/api/auth/login")
    Call<LogInModel> postLogInCall(@Query(NetworkKeys.KEY_EMAIL) String user_email, @Query(NetworkKeys.KEY_PASS) String user_password, @Query(NetworkKeys.KEY_DEVICE_ID) String deviceid);

    @Headers(NetworkKeys.KEY_CONTENT_TYPE)
    @POST("/api/auth/logout")
    Call<LogInModel> postLogOutCall(@Header(NetworkKeys.KEY_AUTH) String authorization_token);

    @Headers(NetworkKeys.KEY_CONTENT_TYPE)
    @GET("/api/auth/profile")
    Call<ProfileModel> getUserProfile(@Header(NetworkKeys.KEY_AUTH) String authorization_token);

    @Headers(NetworkKeys.KEY_CONTENT_TYPE)
    @GET("/api/auth/connecteddevice")
    Call<ConnectedDeviceModel> getConnectedDevice(@Header(NetworkKeys.KEY_AUTH) String authorization_token);

    @Headers(NetworkKeys.KEY_CONTENT_TYPE)
    @GET(NetworkKeys.KEY_SUPPORT)
    Call<SupportModel> getSupportNumbers();

    @Headers(NetworkKeys.KEY_CONTENT_TYPE)
    @GET("/api/auth/privacypol")
    Call<PrivacyPolicyModel> getPoliciesText(@Header(NetworkKeys.KEY_AUTH) String authorization_token);

    @Headers("Accept: application/json")
    @GET("/api/auth/channellist")
    Call<ChannellistModel> getChannelList(@Header(NetworkKeys.KEY_AUTH) String authorization_token);

//    @Headers("User-Agent: iphone")
    @GET("/api/auth/channellist")
    Observable<ChannellistModel> getChannelList2(@Header(NetworkKeys.KEY_AUTH) String authorization_token);


    /*@Headers("Accept: application/json")
    @GET("/api/auth/channeldayschdeule")//api/auth/channeldayschdeule
    Call<ChannelScheduleModel> gotScheduleList(@Header(NetworkKeys.KEY_AUTH) String authorization_token);
*/

//    @Headers("User-Agent: iphone")
    @GET("/api/auth/channeldayschdeule")//api/auth/channeldayschdeule
    Observable<com.streamly.TVApp.streamly.channelschedule.ChannelScheduleModel> getScheduleList(@Header(NetworkKeys.KEY_AUTH) String authorization_token);

    /*@Headers("Accept: application/json")
    @GET("channeldayschdeule")//api/auth/channeldayschdeule
    Observable<com.streamly.tv.channelschedule.ChannelScheduleModel> getScheduleList(@Header(NetworkKeys.KEY_AUTH) String authorization_token);*/


    @Headers("Accept: application/json")
    @GET("/api/auth/channelgenre")//api/auth/channeldayschdeule
    Observable<com.streamly.TVApp.streamly.epg.channelgenre.ChannelGenreModel> getChannelGenre2(@Header(NetworkKeys.KEY_AUTH) String authorization_token);

    @Headers("Accept: application/json")
    @GET("/api/auth/channelgenre")
    Call<ChannelGenreModel> getChannelGenre(@Header(NetworkKeys.KEY_AUTH) String authorization_token);


    @Headers("Accept: application/json")
    @GET("/api/auth/termofuse")
    Call<TermsModel> getTermofuse();


    @Headers("Accept: application/json")
    @GET("/api/auth/getdrmkey")
    Call<DrmKeyModel> getDrmKey(@Header(NetworkKeys.KEY_AUTH) String authorization_token);

    @Headers("Accept: application/json")
    @GET("/api/qrcode")
    Call<QrModel> getQrCode();

//    http://ip-api.com/json/23.119.88.145
//    http://ip-api.com/json/['your ip']

    @Headers("Accept: application/json")
    @GET("json/{your_ip}")
    Call<IpModel> getLatLong(@Path("your_ip") String your_ip);

//    https://streamlytv.com/api/auth/geofence"

    @Headers("Accept: application/json")
    @POST("/api/auth/geofence")
    @FormUrlEncoded
    Call<GeofenceModel> postGeofenceCall(@Header(NetworkKeys.KEY_AUTH) String authorization_token,@FieldMap Map<String,Double> params);


    @Headers("Accept: application/json")
    @POST("/api/auth/outsidelocationplay")
    @FormUrlEncoded
    Call<OutstationLocationModel> postOutstationlocationplay(@Header(NetworkKeys.KEY_AUTH) String authorization_token, @FieldMap Map<String,String> params);


}
