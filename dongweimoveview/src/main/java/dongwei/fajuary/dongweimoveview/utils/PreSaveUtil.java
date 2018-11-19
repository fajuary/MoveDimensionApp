package dongwei.fajuary.dongweimoveview.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 作者：${神游风云（fajuary）} on 2017/8/17 17:32
 * 邮箱：fajuary@foxmail.com
 */

public class PreSaveUtil {
    //名字
    public static final String NAME_PREFERENCE = "dongweimove";
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    public void onDestroy() {
        this.mSharedPreferences = null;
        this.mEditor = null;
    }

    public PreSaveUtil(Context c) {
        this.mSharedPreferences = c.getSharedPreferences(NAME_PREFERENCE, 0);
        this.mEditor = this.mSharedPreferences.edit();
    }

    public void setLong(String key, long l) {
        this.mEditor.putLong(key, l);
        this.mEditor.commit();
    }

    public long getLong(String key, long defaultlong) {
        return this.mSharedPreferences.getLong(key, defaultlong);
    }

    public void setBoolean(String key, boolean value) {
        this.mEditor.putBoolean(key, value);
        this.mEditor.commit();
    }

    public boolean getBoolean(String key, Boolean defaultboolean) {
        return this.mSharedPreferences.getBoolean(key, defaultboolean.booleanValue());
    }

    public void setInt(String key, int value) {
        this.mEditor.putInt(key, value);
        this.mEditor.commit();
    }
    public void setFloat(String key, float value) {
        this.mEditor.putFloat(key, value);
        this.mEditor.commit();
    }
    public float getFloat(String key, float defaultlong) {
        return this.mSharedPreferences.getFloat(key, defaultlong);
    }


    public int getInt(String key, int defaultInt) {
        return this.mSharedPreferences.getInt(key, defaultInt);
    }

    public String getString(String key) {
        return this.mSharedPreferences.getString(key, "");
    }

    public void setString(String key, String value) {
        this.mEditor.putString(key, value);
        this.mEditor.commit();
    }

    public void remove(String key) {
        this.mEditor.remove(key);
        this.mEditor.commit();
    }
}
