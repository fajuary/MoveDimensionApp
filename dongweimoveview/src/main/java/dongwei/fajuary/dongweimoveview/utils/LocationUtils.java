package dongwei.fajuary.dongweimoveview.utils;

/**
 * Created by Le on 2017/7/12.
 */

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import java.util.List;

/**
 * 位置信息的Utils
 *
 * @author zsl
 */
public class LocationUtils {

    // 纬度
    public static double latitude = 0.0;
    // 经度
    public static double longitude = 0.0;
    private static Context ctx;
    private static LocationManager locationManager;

    /**
     * 初始化位置信息
     *
     * @param context
     */
    public static void initLocation(final Context context) {
        ctx = context;
        /**
         * 获取位置服务
         */
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        /**
         * 获取所有的Location Provider,并在Logcat中输出
         */
        List<String> providerLists = locationManager.getAllProviders();
        for (String p : providerLists) {
            Log.e("providers", p);
        }
        /**
         * 分别输出不同Location Provider是否可用
         */
        try {
            /**
             * 分别获取GPS坐标和WiFi坐标
             */
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                return;
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        Location mGPSLocation = locationManager
                .getLastKnownLocation(LocationManager.GPS_PROVIDER);// GPS
        Location mWiFiLocation = locationManager
                .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);// NetWork
        Criteria criteria = new Criteria();// 根据当前设备情况自动选择Location Provider
        criteria.setAccuracy(Criteria.ACCURACY_FINE);// 最大精度
        criteria.setAltitudeRequired(false);// 不要求海拔信息
        criteria.setBearingRequired(false);// 不要求方位精度
        criteria.setCostAllowed(true);// 允许付费
        criteria.setPowerRequirement(Criteria.POWER_LOW);// 对电量的要求
        Location mCriteriaLocation = locationManager
                .getLastKnownLocation(locationManager.getBestProvider(
                        criteria, true));
        if (mGPSLocation != null) {
            latitude = mGPSLocation.getLatitude(); // 经度
            longitude = mGPSLocation.getLongitude(); // 纬度
            Log.e("GPSLocation:", mGPSLocation.getLongitude() + "," + mGPSLocation.getLatitude());
        }
        if (mWiFiLocation != null) {
            latitude = mWiFiLocation.getLatitude(); // 经度
            longitude = mWiFiLocation.getLongitude(); // 纬度
            Log.e("WiFiLocation:", mWiFiLocation.getLongitude() + "," + mWiFiLocation.getLatitude());
        }
        if (mCriteriaLocation != null) {
            latitude = mCriteriaLocation.getLatitude(); // 经度
            longitude = mCriteriaLocation.getLongitude(); // 纬度
            Log.e("AutoProviderLocation:", mCriteriaLocation.getLongitude() + "," + mCriteriaLocation.getLatitude());

        }
        /**
         * 注册位置更新事件(参数中也可用NETWORK_PROVIDER)
         */
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, new TestListener());
    }

    static class TestListener implements LocationListener {

        // 当坐标改变时触发此函数，如果Provider传进相同的坐标，它就不会被触发
        @Override
        public void onLocationChanged(Location location) {
            // TODO Auto-generated method stub
//            latitude = location.getLatitude(); // 经度
//            longitude = location.getLongitude(); // 纬度
//           Log.e("debug",location.getLatitude() + ":" + location.getLongitude());
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            // TODO Auto-generated method stub
        }

        // Provider被enable时触发此函数，比如GPS被打开
        @Override
        public void onProviderEnabled(String provider) {
            // TODO Auto-generated method stub
        }

        // Provider被disable时触发此函数，比如GPS被关闭
        @Override
        public void onProviderDisabled(String provider) {
            // TODO Auto-generated method stub
//            ToastHelper.showTextToast(ctx,"定位已关闭，将无法获取准确定位信息");
//            Intent intent = new Intent();
//            intent.setAction(Constants.LOCATION);
//            ctx.sendBroadcast(intent);
        }
    }
}
