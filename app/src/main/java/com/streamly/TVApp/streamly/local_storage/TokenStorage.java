package com.streamly.TVApp.streamly.local_storage;

import android.content.Context;
import android.content.SharedPreferences;
import com.streamly.TVApp.streamly.utilities.ConstantUtility;


public class TokenStorage {

    private TokenStorage() {
        throw new IllegalStateException("Utility class");
    }

    private final static String FILE_NAME = ConstantUtility.PREF_DATA;

    public static String readSharedToken(Context context, String access_token, String access_token_Value){
        SharedPreferences sharedPreferences=context.getSharedPreferences(FILE_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(access_token,access_token_Value);
    }

    public static void savedSharedToken(Context context,String access_token ,String access_token_Value){
        SharedPreferences sharedPreferences=context.getSharedPreferences(FILE_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(access_token,access_token_Value);
        editor.apply();
    }

    public static void clearSharedToken(Context context){
        SharedPreferences sharedPreferences=context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.clear();
        editor.apply();

    }
}
