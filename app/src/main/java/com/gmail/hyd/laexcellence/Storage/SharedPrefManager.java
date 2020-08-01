package com.gmail.hyd.laexcellence.Storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.gmail.hyd.laexcellence.Models.Login.Login;


public class SharedPrefManager {

    private static final String SHARED_PREF_NAME = "my_shared_name";

    private static SharedPrefManager mInstance;
    private Context mCtx;


    private SharedPrefManager(Context mCtx) {
        this.mCtx = mCtx;
    }

    public static synchronized SharedPrefManager get_mInstance(Context mCtx) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(mCtx);

        }
        return mInstance;
    }

    public void saveUser(Login login) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("sid", login.getData().getAdmissionsId());
        editor.putString("id", login.getData().getId());
        editor.putString("name", login.getData().getName());
        editor.putString("mobile", login.getData().getMobile());
        editor.putString("image", login.getData().getPic());
        editor.putString("batch", login.getData().getBatch());
        editor.putBoolean("status", login.getSuccess());
        editor.putString("password", login.getData().getPassword());
        editor.putString("email", login.getData().getEmail());

        editor.apply();
        editor.commit();

    }


    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("status", false) == true;

    }

    public String getAdmissionId() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString("sid", "");

    }


    public String getSid() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString("id", "");

    }

    public String getImage() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString("image", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT3Ehdqrk7hounfPw7CG3wax5PEIzCN0pdC1QJo2Ro7vj_PsOBx1w");

    }

    public String getName() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString("name", "");

    }

    public String getMobile() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString("mobile", "");

    }

    public String getBatch() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString("batch", "");

    }

    public String getPassword() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString("password", "");

    }

    public String getEmail() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString("email", "");

    }


    //this method will save the device token to shared preferences
    public boolean saveDeviceToken(String token) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("token", token);
        editor.apply();
        return true;
    }

    //this method will fetch the device token from shared preferences
    public String getDeviceToken() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString("token", null);
    }


  /*  public Login getUser(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
          return new Login(
                  sharedPreferences.getString("email",null),
                  sharedPreferences.getString("password",null),
                  sharedPreferences.getString("uid",null),
                  sharedPreferences.getBoolean("status",false)

          );
    }

    */

    public void clear() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
