package com.blockent.map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.icu.text.AlphabeticIndex;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 구글맵 사용하겠다는 코드작성.
        SupportMapFragment mapFragment =
                (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

        // 내 위치가 지도의 중심이 되도록.

        LatLng myLocation = new LatLng(37.5428428, 126.6772096);

//        googleMap.moveCamera(CameraUpdateFactory.newLatLng( myLocation ));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 17));

        googleMap.addMarker( new MarkerOptions().position(myLocation)
                .title("연희직업전문학교")).setTag(0);
        googleMap.addMarker( new MarkerOptions().position(
           new LatLng(37.5438428, 126.6772096)
        ).title("마커2")).setTag(1);  ;
        googleMap.addMarker( new MarkerOptions().position(
           new LatLng(37.5428428, 126.6762096)
        ).title("마커3")).setTag(2);

        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        googleMap.setOnMarkerClickListener(this);
    }

    @Override
    public boolean onMarkerClick(@NonNull Marker marker) {

        int index = (int) marker.getTag();

        if(index == 0){
            Toast.makeText(this, "GOOD~~~~~", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "Hello~~~~", Toast.LENGTH_SHORT).show();
        }

        return false;
    }
}










