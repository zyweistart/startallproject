package com.discover.app;

import java.io.IOException;
import java.util.List;

import android.app.Activity;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
/**
 * 主界面
 * @author Start
 */
public class MainActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_main);
       
       //获取到LocationManager对象
       LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
       //创建一个Criteria对象
       Criteria criteria = new Criteria();
       //设置粗略精确度
       criteria.setAccuracy(Criteria.ACCURACY_COARSE);
       //设置是否需要返回海拔信息
       criteria.setAltitudeRequired(false);
       //设置是否需要返回方位信息
       criteria.setBearingRequired(false);
       //设置是否允许付费服务
       criteria.setCostAllowed(true);
       //设置电量消耗等级
       criteria.setPowerRequirement(Criteria.POWER_HIGH);
       //设置是否需要返回速度信息
       criteria.setSpeedRequired(false);

       //根据设置的Criteria对象，获取最符合此标准的provider对象
       String currentProvider = locationManager.getBestProvider(criteria, true);
       Log.d("Location", "currentProvider: " + currentProvider);
       //根据当前provider对象获取最后一次位置信息
       Location currentLocation = locationManager.getLastKnownLocation(currentProvider);
       //如果位置信息为null，则请求更新位置信息
       if(currentLocation == null){
           locationManager.requestLocationUpdates(currentProvider, 0, 0, locationListener);
       }
       //直到获得最后一次位置信息为止，如果未获得最后一次位置信息，则显示默认经纬度
       //每隔10秒获取一次位置信息
       while(true){
           currentLocation = locationManager.getLastKnownLocation(currentProvider);
           if(currentLocation != null){
               Log.d("Location", "Latitude: " + currentLocation.getLatitude());
               Log.d("Location", "location: " + currentLocation.getLongitude());
               break;
           }else{
               Log.d("Location", "Latitude: " + 0);
               Log.d("Location", "location: " + 0);
           }
           try {
               Thread.sleep(10000);
           } catch (InterruptedException e) {
                Log.e("Location", e.getMessage());
           }
       }
       //解析地址并显示
       Geocoder geoCoder = new Geocoder(this);
       try {
           int latitude = (int) currentLocation.getLatitude();
           int longitude = (int) currentLocation.getLongitude();
           List<Address> list = geoCoder.getFromLocation(latitude, longitude, 5);
           for(int i=0; i<list.size(); i++){
               Address address = list.get(i); 
               Toast.makeText(MainActivity.this, address.getCountryName() + address.getAdminArea() + address.getFeatureName(), Toast.LENGTH_LONG).show();
           }
       } catch (IOException e) {
           Toast.makeText(MainActivity.this,e.getMessage(), Toast.LENGTH_LONG).show();
       }
       
    }
    
    //创建位置监听器
    private LocationListener locationListener = new LocationListener(){
        //位置发生改变时调用
        @Override
        public void onLocationChanged(Location location) {
            Log.d("Location", "onLocationChanged");
            Log.d("Location", "onLocationChanged Latitude" + location.getLatitude());
            Log.d("Location", "onLocationChanged location" + location.getLongitude());
        }

        //provider失效时调用
        @Override
        public void onProviderDisabled(String provider) {
            Log.d("Location", "onProviderDisabled");
        }

        //provider启用时调用
        @Override
        public void onProviderEnabled(String provider) {
            Log.d("Location", "onProviderEnabled");
        }

        //状态改变时调用
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            Log.d("Location", "onStatusChanged");
        }
    };
}