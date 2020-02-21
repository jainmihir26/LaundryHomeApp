package com.example.stet.Activities;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.stet.Adapters.PlaceAutoSuggestAdapter;
import com.example.stet.Helper.SharedPreferencesConfig;
import com.example.stet.Helper.Urls;
import com.example.stet.R;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Thread.sleep;


public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback{


    int MAX_ADDRESS_ALLOWED = 1;
    String mapsUrl="http://192.168.1.208:8000/apis/add_address/";

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Toast.makeText(this, "Map is Ready", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onMapReady: map is ready");
        mMap = googleMap;

        if (mLocationPermissionsGranted) {
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
    private EditText mMainAdrees;
    private EditText mApartmentName;
    private EditText mLandmark;
    private EditText mBuildingName;
    private EditText mArea;
    private Button mSaveButton;
    private AutoCompleteTextView autoCompleteTextView;
    String from_activity;


    //vars
    private Boolean mLocationPermissionsGranted = false;
    private GoogleMap mMap;
    private Button mGps;
    String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION};


    private FusedLocationProviderClient mFusedLocationProviderClient;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

//        checkConnection();


        from_activity = getIntent().getStringExtra("from_activity");
        mGps = findViewById(R.id.detectLocationBt);
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


        autoCompleteTextView=findViewById(R.id.autocomplete);
        autoCompleteTextView.setAdapter(new PlaceAutoSuggestAdapter(MapsActivity.this,android.R.layout.simple_list_item_1));
        autoCompleteTextView.setImeActionLabel("Custom Text",KeyEvent.KEYCODE_ENTER);
        Log.d(TAG, "onCreate: "+" heyyyy");


        autoCompleteTextView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || event.getAction() == KeyEvent.ACTION_DOWN
                        || event.getAction() == KeyEvent.KEYCODE_ENTER || event.getAction() == KeyEvent.KEYCODE_BACK) {

                    Toast.makeText(MapsActivity.this, "Geolocating.", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "onEditorAction: Yo I am in.");
                    try {
                        geoLocateForInputSearch();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }else {
                    Toast.makeText(MapsActivity.this, "In else//////", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "onEditorAction: IME search failed.");
                }
                return false;
            }
        });
        getLocationPermission();

        saveButtonClicked();
    }




    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    private void init() {
        Log.d(TAG, "init: initializing.");

        mGps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: gps icon clicked");
                getDeviceLocation();
            }
        });
    }

    // from the search string move the camera to that location.
    private void geoLocateForInputSearch() throws InterruptedException {

        Log.d(TAG, "geoLocateForInputSearch: geolocaitng");

//        String searchString = mSearchText.getText().toString();
        String searchString = autoCompleteTextView.getText().toString();

        Log.i(TAG, "geoLocateForInputSearch: searchString"+searchString);

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
            String locality=address.getLocality();

            if(!locality.equals("Pune")){

                autoCompleteTextView.setError("Currently we are not available here.");
                autoCompleteTextView.requestFocus(1);
                sleep(2000);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        autoCompleteTextView.getText().clear();
                    }
                },2000);

                return;
            }

            moveCamera1(new LatLng(address.getLatitude(), address.getLongitude()), DEFAULT_ZOOM, address.getAddressLine(0));

            mMainAdrees.setText(address.getAddressLine(0));
        }
    }

    private void geoLocateForCurrentLocation(double currentLatitude, double currentLongitude) throws InterruptedException {
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
            String locality=address.getLocality();
            mMainAdrees.setText(address.getAddressLine(0));
            if(!("Pune").equals(locality)){
                AlertDialog.Builder builder=new AlertDialog.Builder(MapsActivity.this);
                builder.setMessage("Sorry! Right now we are not available here.").setNegativeButton("OK",null);
                AlertDialog alertDialog=builder.create();
                alertDialog.show();
                mMainAdrees.getText().clear();
                return;
            }

            moveCamera1(new LatLng(currentLatitude, currentLongitude), DEFAULT_ZOOM, "My Location");

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
                                    displayPromptForEnablingGPS(MapsActivity.this);

                                }
                            } else {
                                Log.d(TAG, "onComplete: Current location is null");
                                Toast.makeText(MapsActivity.this, "Unable to get Current location.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    private void onTheLocation() {
        LocationRequest mLocationRequest = LocationRequest.create();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);

        SettingsClient client = LocationServices.getSettingsClient(this);
        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());

        task.addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                Toast.makeText(MapsActivity.this, "addOnSuccessListener", Toast.LENGTH_SHORT).show();
                // All location settings are satisfied. The client can initialize
                // location requests here.
                // ...
            }
        });

        task.addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MapsActivity.this, "addOnFailureListener", Toast.LENGTH_SHORT).show();
                if (e instanceof ResolvableApiException) {
                    // Location settings are not satisfied, but this can be fixed
                    // by showing the user a dialog.
                    try {
                        // Show the dialog by calling startResolutionForResult(),
                        // and check the result in onActivityResult().
                        ResolvableApiException resolvable = (ResolvableApiException) e;
                        resolvable.startResolutionForResult(MapsActivity.this,
                                2001);
                    } catch (IntentSender.SendIntentException sendEx) {
                        // Ignore the error.
                    }
                }
            }
        });
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
    public  void displayPromptForEnablingGPS(
            final Activity activity)
    {
        final AlertDialog.Builder builder =
                new AlertDialog.Builder(activity);
        final String action = Settings.ACTION_LOCATION_SOURCE_SETTINGS;
        final String message = "Enable either GPS or any other location"
                + " service to find current location.  Click OK to go to"
                + " location services settings to let you do so.";

        builder.setMessage(message)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface d, int id) {
                                activity.startActivity(new Intent(action));
                                getDeviceLocation();
                                d.dismiss();
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface d, int id) {
                                d.cancel();
                            }
                        });
        builder.create().show();
    }


    private void initMap() {
        Log.d(TAG, "initMap: initializing map");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        assert mapFragment != null;
        mapFragment.getMapAsync(MapsActivity.this);
    }

    private void getLocationPermission() {
        Log.d(TAG, "getLocationPermission: getting location permissions");


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
                    autoCompleteTextView.setError("Please enter your Main Address");
                    autoCompleteTextView.requestFocus();
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
                    mArea.setError("Enter this field");
                    mArea.requestFocus();
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


                SharedPreferencesConfig sharedPreferencesConfig = new SharedPreferencesConfig(MapsActivity.this);
                sharedPreferencesConfig.write_address(mainAddress+" "+aptNumber+" "+landmark+" "+buildingName+" "+area);


                if(from_activity.equals("VerifyOtpActivity")){
                    Intent intent=new Intent(MapsActivity.this,MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }else{
                    Intent intent=new Intent(MapsActivity.this,OrderDetails.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }

//                startActivity(new Intent(MapsActivity.this,HomeActivity.class));
//                startActivity(new Intent(MapsActivity.this,HomeActivity.class));
            }
        });
    }

    private void sendAddressToDatabase(final String mainAddress, final String aptNumber, final String landmark, final String buildingName, final String area) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.mapsUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "onResponse: "+response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onErrorResponse: ",error );
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                SharedPreferencesConfig sharedPreferencesConfig = new SharedPreferencesConfig(getApplicationContext());

                JSONObject jsonObject=new JSONObject();
                try {
                    jsonObject.put("main_address",mainAddress);
                    jsonObject.put("apt_number",aptNumber);
                    jsonObject.put("landmark",landmark);
                    jsonObject.put("building_name",buildingName);
                    jsonObject.put("area",area);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                params.put("token",sharedPreferencesConfig.read_token());
                params.put("address",jsonObject.toString());
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }





}