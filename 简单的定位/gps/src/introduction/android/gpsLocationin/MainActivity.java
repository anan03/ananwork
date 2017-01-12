package introduction.android.gpsLocationin;

/*
 * 本工程GPSLocation的功能是使用GPS实时定位,实时显示手机的经纬度
 */
import introduction.android.gpsLocation.R;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.LocationListener;
import android.location.LocationManager; //
import android.location.Location; //
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	private Button btn_listen;
	private TextView tv_01, tv_02;
	LocationManager lm; //
	Location loc; //

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		btn_listen = (Button) findViewById(R.id.btn_listen);
		tv_01 = (TextView) findViewById(R.id.tv_01);
		tv_02 = (TextView) findViewById(R.id.tv_02);
		// lm = (LocationManager)
		// this.getSystemService(Context.LOCATION_SERVICE); //
		// // 检测GPS状态（是否开启）
		// if (!lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) { // 若未打开GPS
		// Toast.makeText(MainActivity.this, "请开启GPS服务", Toast.LENGTH_LONG)
		// .show();
		// Intent myintent = new Intent(
		// Settings.ACTION_LOCATION_SOURCE_SETTINGS);
		// startActivity(myintent); // 运行手机的设置程序
		// }
		//

		//
		// btn_listen.setOnClickListener(new OnClickListener() {
		// @Override
		// public void onClick(View v) {
		// lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,
		// new MyLocationListener());
		// }
		// });
		openGPSSettings();
		getLocation();
	}

	private void openGPSSettings() {
		LocationManager alm = (LocationManager) this
				.getSystemService(Context.LOCATION_SERVICE);
		if (alm.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)) {
			Toast.makeText(this, "GPS模块正常", Toast.LENGTH_SHORT).show();
			return;
		}
		Toast.makeText(this, "请开启GPS！", Toast.LENGTH_SHORT).show();
		Intent intent = new Intent(Settings.ACTION_SECURITY_SETTINGS);
		startActivityForResult(intent, 0); // 此为设置完成后返回到获取界面
	}

	private void getLocation() {
		// 获取位置管理服务
		LocationManager locationManager;
		String serviceName = Context.LOCATION_SERVICE;
		locationManager = (LocationManager) this.getSystemService(serviceName);
		// 查找到服务信息
		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_FINE); // 高精度
		criteria.setAltitudeRequired(false);
		criteria.setBearingRequired(false);
		criteria.setCostAllowed(true);
		criteria.setPowerRequirement(Criteria.POWER_LOW); // 低功耗
		String provider = locationManager.getBestProvider(criteria, true); // 获取GPS信息
		Location location = locationManager.getLastKnownLocation(provider); // 通过GPS获取位置
		updateToNewLocation(location);
		// 设置监听*器，自动更新的最小时间为间隔N秒(1秒为1*1000，这样写主要为了方便)或最小位移变化超过N米
		locationManager.requestLocationUpdates(provider, 100 * 1000, 500,
				new MyLocationListener());
	}

	private void updateToNewLocation(Location location) {
		if (location != null) {
			double latitude = location.getLatitude();
			double longitude = location.getLongitude();
			tv_01.setText("经度：" + latitude + "纬度" + latitude);
		} else {
			tv_01.setText("无法获取");
		}
	}

	class MyLocationListener implements LocationListener { // 位置监听器，作为方法参数
		@Override
		public void onLocationChanged(Location loc) {
			// // TODO Auto-generated method stub
			// tv_01.setText("经度：" + loc.getLongitude());
			// tv_02.setText("纬度：" + loc.getLatitude());

		}

		@Override
		public void onProviderDisabled(String provider) {
			// 当provider被用户关闭时调用
			Log.i("GpsLocation", "provider被关闭！");
		}

		@Override
		public void onProviderEnabled(String provider) {
			// 当provider被用户开启后调用
			Log.i("GpsLocation", "provider被开启！");
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// 当provider的状态在OUT_OF_SERVICE、TEMPORARILY_UNAVAILABLE和AVAILABLE之间发生变化时调用
			Log.i("GpsLocation", "provider状态发生改变！");
		}
	}
}