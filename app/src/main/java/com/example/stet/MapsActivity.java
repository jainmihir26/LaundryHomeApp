package com.example.stet;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.maps.OnMapReadyCallback;


import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;






public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {


    int MAX_ADDRESS_ALLOWED = 3;
    String mapsUrl;

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Toast.makeText(this, "Map is Ready", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onMapReady: map is ready");
        mMap = googleMap;

        if (mLocationPermissionsGranted) {
//            getDeviceLocation();

            if (ActivityCompat.checkSelfPermission(this.getApplicationContext(),
                    FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this.getApplicationContext(),
                    COURSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }


            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(false);

            init();

        }
    }

    private static final String TAG = "MapsActivity";

    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final float DEFAULT_ZOOM = 15f;

    //widgets

    private EditText mSearchText;
    private EditText mMainAdrees;
    private EditText mApartmentName;
    private EditText mLandmark;
    private EditText mBuildingName;
    private EditText mArea;
    private Button mSaveButton;
    private Button mProceed;


    //vars
    private Boolean mLocationPermissionsGranted = false;
    private GoogleMap mMap;
    private ImageView mGps;


    private FusedLocationProviderClient mFusedLocationProviderClient;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        mSearchText = findViewById(R.id.map_inputTextId);
        mGps = findViewById(R.id.map_mGpsId);
        mMainAdrees = findViewById(R.id.map_mainAddressId);
        mApartmentName = findViewById(R.id.map_apartmentNameId);
        mLandmark = findViewById(R.id.map_landmarkId);
        mBuildingName = findViewById(R.id.map_businessOrBuildingNameId);
        mArea = findViewById(R.id.map_areaOrDistrictId);
        mSaveButton = findViewById(R.id.map_saveButton);

        mMainAdrees.setCursorVisible(false);
        mMainAdrees.setLongClickable(false);
        mMainAdrees.setClickable(false);
        mMainAdrees.setFocusable(false);
        mMainAdrees.setSelected(false);
        mMainAdrees.setKeyListener(null);
//        mProceed=findViewById(R.id.map_proceedButton);

        getLocationPermission();

        saveButtonClicked();

    }


    private void init() {
        Log.d(TAG, "init: initializing.");
//        Toast.makeText(MapsActivity.this, "In init method ........", Toast.LENGTH_SHORT).show();

        mSearchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || event.getAction() == KeyEvent.ACTION_DOWN
                        || event.getAction() == KeyEvent.KEYCODE_ENTER) {

                    Toast.makeText(MapsActivity.this, "Geolocating.", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "onEditorAction: Yo I am in.");
                    geoLocateForInputSearch();


                } else {
                    Toast.makeText(MapsActivity.this, "In else//////", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "onEditorAction: IME search failed.");
                }
                return false;
            }
        });

        mGps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: gps icon clicked");
                getDeviceLocation();
            }
        });

    }


    // from the search string move the camera to that location.
    private void geoLocateForInputSearch() {

        Log.d(TAG, "geoLocateForInputSearch: geolocaitng");

        String searchString = mSearchText.getText().toString();

        Geocoder geocoder = new Geocoder(MapsActivity.this);

        List<Address> list = new ArrayList<>();


        try {
            list = geocoder.getFromLocationName(searchString, 1);
//            list=geocoder.
        } catch (IOException e) {
            Log.e(TAG, "geoLocateForInputSearch: ", e);
        }
        if (list.size() > 0) {
            Address address;
            address = list.get(0);

            Toast.makeText(MapsActivity.this, "geoLocateForInputSearch: Location is  : " + address.toString(), Toast.LENGTH_SHORT).show();

            Log.d(TAG, "geoLocateForInputSearch: Location is  : " + address.toString());

            moveCamera1(new LatLng(address.getLatitude(), address.getLongitude()), DEFAULT_ZOOM, address.getAddressLine(0));

            mMainAdrees.setText(address.getAddressLine(0));
        }
    }

    private void geoLocateForCurrentLocation(double currentLatitude, double currentLongitude) {
        Geocoder geocoder = new Geocoder(MapsActivity.this);
        List<Address> list = new ArrayList<>();
        try {
            list = geocoder.getFromLocation(currentLatitude, currentLongitude, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (list.size() > 0) {
            Address address;
            address = list.get(0);
            Log.d(TAG, "geoLocateForCurrentLocation: " + address.toString() + " \naddress Lines" + address.getAddressLine(0));
            moveCamera1(new LatLng(currentLatitude, currentLongitude), DEFAULT_ZOOM, "My Location");
            mMainAdrees.setText(address.getAddressLine(0));
        }
    }


    private void getDeviceLocation() throws SecurityException {
        Log.d(TAG, "getDeviceLocation: getting device current location .");

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);


        if (mLocationPermissionsGranted) {
            final Task location = mFusedLocationProviderClient.getLastLocation();
            location.addOnCompleteListener(
                    new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "onComplete: Found location.");
                                Location currentLocation = (Location) task.getResult();
                                assert currentLocation != null;
                                try {
                                    moveCamera1(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()), DEFAULT_ZOOM, "My Location");
                                    geoLocateForCurrentLocation(currentLocation.getLatitude(), currentLocation.getLongitude());
                                } catch (Exception e) {
                                    Toast.makeText(MapsActivity.this, "Please turn on your mobile location or ", Toast.LENGTH_SHORT).show();
                                }

                            } else {
                                Log.d(TAG, "onComplete: Current location is null");
                                Toast.makeText(MapsActivity.this, "Unable to get Current location.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    private void moveCamera1(LatLng latLng, float zoom, String title) {
        Log.d(TAG, "moveCamera: Moving the camera to latitide : " + latLng.latitude + ", long: " + latLng.longitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));

        if (!title.equals("My Location") || true) {
            MarkerOptions options = new MarkerOptions()
                    .position(latLng)
                    .title(title);
            mMap.addMarker(options);
        }

    }

    private void initMap() {
        Log.d(TAG, "initMap: initializing map");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        assert mapFragment != null;
        mapFragment.getMapAsync(MapsActivity.this);
    }

    private void getLocationPermission() {
        Log.d(TAG, "getLocationPermission: getting location permissions");
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                    COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionsGranted = true;
                initMap(); // this line is for testing ..
            } else {
                ActivityCompat.requestPermissions(this,
                        permissions,
                        LOCATION_PERMISSION_REQUEST_CODE);
            }
        } else {
            ActivityCompat.requestPermissions(this,
                    permissions,
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult: called.");
        mLocationPermissionsGranted = false;

        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            mLocationPermissionsGranted = false;
                            Log.d(TAG, "onRequestPermissionsResult: permission failed");
                            return;
                        }
                    }
                    Log.d(TAG, "onRequestPermissionsResult: permission granted");
                    mLocationPermissionsGranted = true;
                    //initialize our map
                    initMap();
                }
            }
        }
    }


    private void saveButtonClicked() {
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: On button clicked. ");

                if (mMainAdrees.getText().toString().isEmpty()) {
                    mSearchText.setError("Please select your address");
                    mSearchText.requestFocus();
                    return;
                }

                if (mApartmentName.getText().toString().isEmpty()) {
                    mApartmentName.setError("Enter this field");
                    mApartmentName.requestFocus();
                    return;
                }
                if (mBuildingName.getText().toString().isEmpty()) {
                    mBuildingName.setError("Enter this field");
                    mBuildingName.requestFocus();
                    return;
                }
                if (mLandmark.getText().toString().isEmpty()) {
                    mLandmark.setError("Enter this field");
                    mLandmark.requestFocus();
                    return;
                }
                if (mArea.getText().toString().isEmpty()) {
                    mLandmark.setError("Enter this field");
                    mLandmark.requestFocus();
                    return;
                }
                Log.d(TAG, "onClick: save button clicked");
                String mainAddress = mMainAdrees.getText().toString();
                String aptNumber = mApartmentName.getText().toString();
                String landmark = mLandmark.getText().toString();
                String buildingName = mBuildingName.getText().toString();
                String area = mArea.getText().toString();
                Log.d(TAG, "onClick: " + " ininin.");
//                sendAddressToDatabase(mainAddress,aptNumber,landmark,buildingName,area);
                sendAddressToDatabase(mainAddress, aptNumber, landmark, buildingName, area);
                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(UserDetailsSharedPreferences.sharedPreferences, MODE_PRIVATE);
                SharedPreferences.Editor mEditor = sharedPreferences.edit();
                mEditor.putString(UserDetailsSharedPreferences.addressOfUser, mainAddress);
                mEditor.apply();
//                startActivity(new Intent(MapsActivity.this,HomeActivity.class));
            }
        });
    }

    private void sendAddressToDatabase(String mainAddress, String aptNumber, String landmark, String buildingName, String area) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, mapsUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }

}