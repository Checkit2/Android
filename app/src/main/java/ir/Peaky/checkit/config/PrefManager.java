package ir.Peaky.checkit.config;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefManager {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;
    // shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "setting";

    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";

    public PrefManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setFirstTimeLaunch(boolean isFirstTime){
        editor.putBoolean(IS_FIRST_TIME_LAUNCH,isFirstTime);
        editor.commit();
    }
    public boolean isFirstTimeLaunch(){
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH,false);
    }

    public void setLogin(boolean login) {
        editor.putBoolean("login", login);
        editor.commit();
    }

    public boolean isLogin() {
        return pref.getBoolean("login", false);
    }


    public void setPhoneNumber(String phone){
        editor.putString("phone",phone);
        editor.commit();
    }
    public String getPhoneNumber(){
        return pref.getString("phone","null");
    }
    public void setUserId(int userId){
        editor.putInt("user_id",userId);
        editor.commit();
    }
    public int getUserId(){
        return pref.getInt("user_id",404);
    }
}
