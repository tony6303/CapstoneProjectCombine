package com.example.myapplication;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.InfoWindow;
import com.naver.maps.map.overlay.Marker;

import java.util.ArrayList;

//import com.naver.*;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        //지도를 출력할 프래그먼트 영역 인식
        MapFragment mapFragment = (MapFragment)getSupportFragmentManager().findFragmentById(R.id.map);

        if(mapFragment == null){
            mapFragment = MapFragment.newInstance();
            getSupportFragmentManager().beginTransaction().add(R.id.map,mapFragment).commit();
        }

        //지도 사용이 준비되면 onMapReady 콜백 메소드 호출
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
       naverMap.setMapType(NaverMap.MapType.Basic);
        //심볼 크기
        naverMap.setSymbolScale(1.5f);

        ArrayList<Double> latitude = new ArrayList<>();
        ArrayList<Double> longitude = new ArrayList<>();

        //농장 위도 경도 삽입
        latitude.add(35.348257); //양산 물금 주말농장
        longitude.add(128.958056);
        latitude.add(35.344592); //생생주말농장
        longitude.add(128.640034);
        latitude.add(35.352292); //새길주말농장
        longitude.add(129.251317);
        latitude.add(35.593575); //주말농원소풍
        longitude.add(129.234187);
        latitude.add(35.440554); //sk주말농장
        longitude.add(128.728143);
        latitude.add(35.306854); //행복한 주말농장
        longitude.add(128.661707);

        //위도경도 삽입
        LatLng latlng0 = new LatLng(latitude.get(0),longitude.get(0));
        LatLng latlng1 = new LatLng(latitude.get(1),longitude.get(1));
        LatLng latlng2 = new LatLng(latitude.get(2),longitude.get(2));
        LatLng latlng3 = new LatLng(latitude.get(3),longitude.get(3));
        LatLng latlng4 = new LatLng(latitude.get(4),longitude.get(4));
        LatLng latlng5 = new LatLng(latitude.get(5),longitude.get(5));

        //지도 중심
        CameraUpdate cameraUpdate0 = CameraUpdate.scrollTo(latlng0) ;
        naverMap.moveCamera(cameraUpdate0);

        //농장 마커
        Marker marker0 = new Marker();
        marker0.setPosition(latlng0);
        marker0.setMap(naverMap);
        marker0.setCaptionText("양산 물금 주말농장");
        marker0.setCaptionColor(Color.RED);
        marker0.setCaptionHaloColor(Color.YELLOW);
        marker0.setCaptionTextSize(10);
        InfoWindow infoWindow0 = new InfoWindow();
        infoWindow0.setAdapter(new InfoWindow.DefaultTextAdapter(this) {
            @NonNull
            @Override
            public CharSequence getText(@NonNull InfoWindow infoWindow) {
               return "010-3047-1977";
            }
        });
        infoWindow0.open(marker0);

        Marker marker1 = new Marker();
        marker1.setPosition(latlng1);
        marker1.setMap(naverMap);
        marker1.setCaptionText("생생 주말농장");
        marker1.setCaptionColor(Color.RED);
        marker1.setCaptionHaloColor(Color.YELLOW);
        marker1.setCaptionTextSize(10);
        InfoWindow infoWindow1 = new InfoWindow();
        infoWindow1.setAdapter(new InfoWindow.DefaultTextAdapter(this) {
            @NonNull
            @Override
            public CharSequence getText(@NonNull InfoWindow infoWindow) {
                return "080-777-3737";
            }
        });
        infoWindow1.open(marker1);

        Marker marker2 = new Marker();
        marker2.setPosition(latlng2);
        marker2.setMap(naverMap);
        marker2.setCaptionText("새길 주말농장");
        marker2.setCaptionColor(Color.RED);
        marker2.setCaptionHaloColor(Color.YELLOW);
        marker2.setCaptionTextSize(10);
        InfoWindow infoWindow2 = new InfoWindow();
        infoWindow2.setAdapter(new InfoWindow.DefaultTextAdapter(this) {
            @NonNull
            @Override
            public CharSequence getText(@NonNull InfoWindow infoWindow) {
                return "전화번호 미등록";
            }
        });
        infoWindow2.open(marker2);

        Marker marker3 = new Marker();
        marker3.setPosition(latlng3);
        marker3.setMap(naverMap);
        marker3.setCaptionText("주말농원소풍");
        marker3.setCaptionColor(Color.RED);
        marker3.setCaptionHaloColor(Color.YELLOW);
        marker3.setCaptionTextSize(10);
        InfoWindow infoWindow3 = new InfoWindow();
        infoWindow3.setAdapter(new InfoWindow.DefaultTextAdapter(this) {
            @NonNull
            @Override
            public CharSequence getText(@NonNull InfoWindow infoWindow) {
                return "052-244-3570";
            }
        });
        infoWindow3.open(marker3);

        Marker marker4 = new Marker();
        marker4.setPosition(latlng4);
        marker4.setMap(naverMap);
        marker4.setCaptionText("sk 주말농장");
        marker4.setCaptionColor(Color.RED);
        marker4.setCaptionHaloColor(Color.YELLOW);
        marker4.setCaptionTextSize(10);
        InfoWindow infoWindow4 = new InfoWindow();
        infoWindow4.setAdapter(new InfoWindow.DefaultTextAdapter(this) {
            @NonNull
            @Override
            public CharSequence getText(@NonNull InfoWindow infoWindow) {
                return "전화번호 미등록";
            }
        });
        infoWindow4.open(marker4);

        Marker marker5 = new Marker();
        marker5.setPosition(latlng5);
        marker5.setMap(naverMap);
        marker5.setCaptionText("행복한 주말농장");
        marker5.setCaptionColor(Color.RED);
        marker5.setCaptionHaloColor(Color.YELLOW);
        marker5.setCaptionTextSize(10);
        InfoWindow infoWindow5 = new InfoWindow();
        infoWindow5.setAdapter(new InfoWindow.DefaultTextAdapter(this) {
            @NonNull
            @Override
            public CharSequence getText(@NonNull InfoWindow infoWindow) {
                return "전화번호 미등록";
            }
        });
        infoWindow5.open(marker5);

    }
}
