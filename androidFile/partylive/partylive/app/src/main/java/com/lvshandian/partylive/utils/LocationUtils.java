package com.lvshandian.partylive.utils;

import android.content.Context;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;


import com.lvshandian.partylive.interf.ResultListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by gjj on 2016/12/20.
 */

public class LocationUtils {
    private static LocationUtils utils;

    private LocationUtils() {
    }

    public static LocationUtils newInstance() {
        if (utils == null) {
            synchronized (LocationUtils.class) {
                if (utils == null) {
                    utils = new LocationUtils();
                }
            }
        }
        return utils;
    }

    /**
     * 获取定位权限并获取定位
     *
     * @author sll
     * @time 2016/12/21 10:20
     */
    public void getLocation(Context context, final LocationManager mManager, final LocationListener listener) {
        PermisionUtils.newInstance().checkLocationPermission(context, new PermisionUtils.OnPermissionGrantedLintener() {
            @Override
            public void permissionGranted() {
                location(mManager, listener);
            }
        });
    }

    /**
     * 定位工具，可以得到一个Location对象，进而得到经纬度
     *
     * @param mManager
     * @param listener
     */
    public void location(LocationManager mManager, LocationListener listener) {
        Criteria criteria = new Criteria();// 生成位置提供者的条件
        criteria.setPowerRequirement(Criteria.POWER_MEDIUM);// 电量
        criteria.setCostAllowed(true);// 是否允许话费
        String provider = mManager.getBestProvider(criteria, true);
        long minTime = 30 * 1000;// 获取位置的最短时间限制
        float minDistance = 0;// 获取位置的最短距离限制
        mManager.requestLocationUpdates(provider, minTime, minDistance, listener);
    }


    /**
     * 定位到城市
     *
     * @param context  上下文
     * @param mManager LocationManager
     * @param listener 定位结果回调
     */
    public void locationCity(final Context context, LocationManager mManager, final ResultListener listener) {
        Criteria criteria = new Criteria();// 生成位置提供者的条件
        criteria.setPowerRequirement(Criteria.POWER_MEDIUM);// 电量
        criteria.setCostAllowed(true);// 是否允许话费
        String provider = mManager.getBestProvider(criteria, true);
        long minTime = 30 * 1000;// 获取位置的最短时间限制
        float minDistance = 0;// 获取位置的最短距离限制
        mManager.requestLocationUpdates(provider, minTime, minDistance, new BaseLocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                double latitude = location.getLatitude();// 纬度
                double longitude = location.getLongitude();// 经度
                float accuracy = location.getAccuracy();// 精确度
                double altitude = location.getAltitude();// 海拔

                getCity(latitude, longitude, context, listener);
            }
        });
    }

    /**
     * 根据Location对象获取城市
     *
     * @param location
     */
    public void getCityByLocation(Context context, Location location, ResultListener listener) {
        Geocoder gc = new Geocoder(context, Locale.getDefault());
        double latitude = location.getLatitude();// 纬度
        double longitude = location.getLongitude();// 经度
        List<Address> locationList = null;
        try {
            locationList = gc.getFromLocation(latitude, longitude, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (locationList != null && locationList.size() > 0) {
            Address address = locationList.get(0);//得到Address实例
            String countryName = address.getCountryName();//得到国家名称，比如：中国
            String locality = address.getLocality();//得到城市名称，比如：北京市
            if (listener != null) {
                listener.onSucess(locality);
            }
            for (int i = 0; address.getAddressLine(i) != null; i++) {
                String addressLine = address.getAddressLine(i);//得到周边信息，包括街道等，i=0，得到街道名称

            }
        } else if (listener != null) {
            listener.onFaild();
        }
    }

    private void getCity(double latitude, double longitude, Context context, ResultListener listener) {
        Geocoder gc = new Geocoder(context, Locale.getDefault());
        List<Address> locationList = null;
        try {
            locationList = gc.getFromLocation(latitude, longitude, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (locationList != null && locationList.size() > 0) {
            Address address = locationList.get(0);//得到Address实例
            String countryName = address.getCountryName();//得到国家名称，比如：中国
            String locality = address.getLocality();//得到城市名称，比如：北京市
            if (listener != null) {
                listener.onSucess(locality);
            }
            for (int i = 0; address.getAddressLine(i) != null; i++) {
                String addressLine = address.getAddressLine(i);//得到周边信息，包括街道等，i=0，得到街道名称

            }
        } else if (listener != null) {
            listener.onFaild();
        }
    }
}
