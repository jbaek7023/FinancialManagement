package cs.android.ddtc.mvp.views;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.GoogleMap.OnMyLocationChangeListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import cs.android.ddtc.mvp.R;

public class LocationActivity extends FragmentActivity implements
		OnMyLocationChangeListener {

	public String uID;
	public String acct;
	public double latitude;
	public double longitude;

	GoogleMap mGoogleMap;
	LatLng loc = new LatLng(37.57778, 126.979187); // 위치 좌표 설정
	CameraPosition cp = new CameraPosition.Builder().target((loc)).zoom(7)
			.build();
	MarkerOptions marker = new MarkerOptions().position(loc); // 구글맵에 기본마커 표시
	Marker m;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_location);
		Intent intent = getIntent();
		Bundle extras = intent.getExtras();
		uID = extras.getString("uid");
		acct = extras.getString("acctname");
		latitude = extras.getDouble("lat");
		longitude = extras.getDouble("long");
		Log.d("lat2", Double.toString(latitude));
		Log.d("log2", Double.toString(longitude));
		

		mGoogleMap = ((SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.location)).getMap();
		// 화면에 구글맵 표시
		mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cp)); // 지정위치로
																				// 이동
		mGoogleMap.addMarker(marker); // 지정위치에 마커 추가

		// Enabling MyLocation Layer of Google Map
		mGoogleMap.setMyLocationEnabled(true);
		// Setting event handler for location change
		mGoogleMap.setOnMyLocationChangeListener(this);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.location, menu);
		return true;
	}

	@Override
	public void onMyLocationChange(Location location) {
		// TODO Auto-generated method stub
		// 현재 위도
		Log.d("lat3", Double.toString(latitude));
		Log.d("log3", Double.toString(longitude));
		double latitude1 = latitude;
		// 현재 경도
		double longitude1 = longitude;


		// latLng변수에 현재 위도, 경도를 저장
		loc = new LatLng(latitude, longitude);

		// 현재 위치로 구글맵 이동
		// mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
		// 확대 및 축소(Zoom)
		// mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(17));

		// 마커,타이틀, 스니핏 표시
		if (m != null) {
			m.remove(); // 기존마커지우기
		}
		cp = new CameraPosition.Builder().target((loc)).zoom(16).build();
		mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cp));
		marker = new MarkerOptions().position(loc).title("Current location")
				.snippet("Click this!");
		m = mGoogleMap.addMarker(marker);

		// 마커의 타이틀,스니핏을 클릭했을 때 호출됨
		mGoogleMap
				.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {

					@Override
					public void onInfoWindowClick(Marker arg0) {

						// TODO Auto-generated method stub

						AlertDialog.Builder alert = new AlertDialog.Builder(
								LocationActivity.this);
						alert.setTitle("Do you want to go back to history view?");
						alert.setIcon(R.drawable.ic_launcher);
						// positive버튼클릭시 처리할 이벤트 객체 생성
						DialogInterface.OnClickListener positiveClick = new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// 데이터베이스 입력작업 등등 실행
								// Toast.makeText(GoogleMapActivity.this,
								// "입력작업실행", Toast.LENGTH_LONG).show();
								Intent intent = new Intent(getBaseContext(),
										HistoryActivity.class);
								Bundle extras = new Bundle();
								extras.putString("uid", uID);
								extras.putString("acctname", acct);
								extras.putDouble("lat", loc.latitude);
								extras.putDouble("long", loc.longitude);
								intent.putExtras(extras);
								startActivity(intent);
							}
						};
						alert.setPositiveButton("Confirm", positiveClick);
						alert.setNegativeButton("Cancel", null);
						alert.show();
					}

				});

		// 마커를 클릭했을 때 호출됨
		mGoogleMap.setOnMarkerClickListener(new OnMarkerClickListener() {

			@Override
			public boolean onMarkerClick(Marker arg0) {

				// TODO Auto-generated method stub
				// Toast.makeText(GoogleMapActivity.this, "마커가 클릭됨",
				// Toast.LENGTH_LONG).show();
				return false;

			}

		});

	}

}