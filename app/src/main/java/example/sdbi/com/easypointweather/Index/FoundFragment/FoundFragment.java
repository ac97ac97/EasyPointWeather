package example.sdbi.com.easypointweather.Index.FoundFragment;

import example.sdbi.com.easypointweather.R;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;

import java.util.ArrayList;
import java.util.List;

public class FoundFragment extends Fragment {
	private LocationClient mlocation;
	private TextView showPermission;
	private MapView mapView;
	private BaiduMap baiduMap;
	private boolean isFirstLocate;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mlocation = new LocationClient(getActivity().getApplication());
		mlocation.registerLocationListener(new MyLocationListener());
		SDKInitializer.initialize(getActivity().getApplicationContext());
		View view = inflater.inflate(R.layout.found_fragment, container, false);
		showPermission = view.findViewById(R.id.position_text_view);
		mapView=view.findViewById(R.id.mapView);
		baiduMap=mapView.getMap();
		baiduMap.setMyLocationEnabled(true);
		List<String> permissionList = new ArrayList<>();
		if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
			permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
		}
		if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
			permissionList.add(Manifest.permission.READ_PHONE_STATE);
		}
		if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
			permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
		}
		if (!permissionList.isEmpty()){
			String [] permissions=permissionList.toArray(new String[permissionList.size()]);
			ActivityCompat.requestPermissions(getActivity(),permissions,1);
		}else {
			requestLocation();
		}
		return view;
	}
	private void navegateTo(BDLocation location){
		if (isFirstLocate){
			LatLng latLng=new LatLng(location.getLatitude(),location.getLongitude());
			MapStatusUpdate update= MapStatusUpdateFactory.newLatLng(latLng);
			baiduMap.animateMapStatus(update);
			update=MapStatusUpdateFactory.zoomTo(16f);
			baiduMap.animateMapStatus(update);
			isFirstLocate=false;
			MyLocationData.Builder builder = new MyLocationData.Builder();
			builder.latitude(location.getLatitude());
			builder.longitude(location.getLongitude());
			MyLocationData myLocationData=builder.build();
			baiduMap.setMyLocationData(myLocationData);
		}

	}
	private void requestLocation(){
		initLocation();
		mlocation.start();
	}
	private void initLocation(){
		LocationClientOption option = new LocationClientOption();
		option.setScanSpan(1000);
//		option.setLocationMode(LocationClientOption.LocationMode.Device_Sensors);//使用传感器 gps进行定位 前提 手机必须手动开启gps功能和室外使用
		option.setIsNeedAddress(true);
		mlocation.setLocOption(option);
	}

	@Override
	public void onResume() {
		super.onResume();
		mapView.onResume();
	}

	@Override
	public void onPause() {
		super.onPause();
		mapView.onPause();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		mlocation.stop();
		mapView.onDestroy();
		baiduMap.setMyLocationEnabled(false);
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		switch (requestCode){
			case 1:
				if (grantResults.length>0){
					for (int result:grantResults){
						if (result != PackageManager.COMPONENT_ENABLED_STATE_DEFAULT){
							Toast.makeText(getActivity(),"必须同意所有权限才能继续向下操作",Toast.LENGTH_SHORT).show();
							getActivity().finish();
							return;
						}
					}
					requestLocation();
				}else {
					Toast.makeText(getActivity(),"请求失败",Toast.LENGTH_SHORT).show();
					getActivity().finish();
				}
				break;
			default:
		}

	}
	private class MyLocationListener implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation bdLocation) {
            if (bdLocation.getLocType()==BDLocation.TypeGpsLocation||bdLocation.getLocType()==BDLocation.TypeNetWorkLocation){
				navegateTo(bdLocation);
			}
			StringBuilder currentPosition = new StringBuilder();
			currentPosition.append("经度 :").append(bdLocation.getLatitude()).append("\n");
			currentPosition.append("纬度 :").append(bdLocation.getLongitude()).append("\n");
			currentPosition.append("国家 :").append(bdLocation.getCountry()).append("\n");
			currentPosition.append("省份 :").append(bdLocation.getProvince()).append("\n");
			currentPosition.append("城市 :").append(bdLocation.getCity()).append("\n");
			currentPosition.append("区/市 :").append(bdLocation.getDistrict()).append("\n");
			currentPosition.append("街道 :").append(bdLocation.getStreet()).append("\n");
			currentPosition.append("定位方式 :");
			if (bdLocation.getLocType() == BDLocation.TypeNetWorkLocation){

				currentPosition.append("网络");

			}else if (bdLocation.getLocType() == BDLocation.TypeGpsLocation){
				currentPosition.append("GPS");
			}
			showPermission.setText(currentPosition);
		}
	}

}
