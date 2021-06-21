// Copyright 2020 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.mobilisepakistan.pdma;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.common.collect.RowSortedTable;
import com.google.common.collect.TreeBasedTable;


public class MapsMarkerActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    String latt="";
    String longg="";
    String title="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        Intent intent = getIntent();
        latt=intent.getStringExtra("latt");
        longg=intent.getStringExtra("longg");
        title=intent.getStringExtra("title");
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera.
     * In this case, we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device.
     * This method will only be triggered once the user has installed
     Google Play services and returned to the app.
     */

    @Override
    public void onMapReady(GoogleMap googleMap) {



        RowSortedTable<String, String, String> weightedGraph = TreeBasedTable.create();
        weightedGraph.put("Swat", "matta", "Chupiral");
        weightedGraph.put("Swat", "matta", "Sinpora");


        mMap = googleMap;
        // Add a marker in Sydney and move the camera

        LatLng TutorialsPoint = new LatLng(Double.parseDouble(latt),Double.parseDouble(longg));
        mMap.addMarker(new
                MarkerOptions().position(TutorialsPoint).title(title));


        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.getUiSettings().setZoomGesturesEnabled(true);
      //  mMap.animateCamera(CameraUpdateFactory.zoomTo(12));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(TutorialsPoint,12));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}
