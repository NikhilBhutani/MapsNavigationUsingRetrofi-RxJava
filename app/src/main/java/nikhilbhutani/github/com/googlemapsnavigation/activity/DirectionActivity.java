package nikhilbhutani.github.com.googlemapsnavigation.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import nikhilbhutani.github.com.googlemapsnavigation.R;
import nikhilbhutani.github.com.googlemapsnavigation.interfaces.DirectionsApiRequestInterface;
import nikhilbhutani.github.com.googlemapsnavigation.model.DirectionApiResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by nikhilbhutani on 04/07/17.
 */

public class DirectionActivity extends AppCompatActivity implements OnMapReadyCallback{

    String baseUrl = "https://maps.googleapis.com/maps/";
    Polyline line;
    private GoogleMap mMap;
    ArrayList<LatLng> MarkerPoints;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_directions);
        MarkerPoints = new ArrayList<>();


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);




    }

    private void requestRetrofitAndGetResponse(String driving) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        if(line!=null){
            line.remove();
        }

        DirectionsApiRequestInterface directionsApiRequestInterface = retrofit.create(DirectionsApiRequestInterface.class);


        Call<DirectionApiResponse> directionsApiRequestInterfaceCall = directionsApiRequestInterface.getDirectionJson("Janakpuri","Noida", "API KEY HERE");

        directionsApiRequestInterfaceCall.enqueue(new Callback<DirectionApiResponse>() {
            @Override
            public void onResponse(Call<DirectionApiResponse> call, Response<DirectionApiResponse> response) {

                Log.i("JSON RESPONSE", " "+ new GsonBuilder().setPrettyPrinting().create().toJson(response.body()));

                List<LatLng> latLngs =  decodePoly(response.body().getRoutes().get(0).getOverviewPolyline().getPoints());

                line = mMap.addPolyline(new PolylineOptions()
                .addAll(latLngs)
                .width(20)
                .color(Color.RED)
                .geodesic(true));


            }

            @Override
            public void onFailure(Call<DirectionApiResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }


    private List<LatLng> decodePoly(String encoded) {
        List<LatLng> poly = new ArrayList<LatLng>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng( (((double) lat / 1E5)),
                    (((double) lng / 1E5) ));
            poly.add(p);
        }

        return poly;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng Model_Town = new LatLng(28.7158727, 77.1910738);
        mMap.addMarker(new MarkerOptions().position(Model_Town).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(Model_Town));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(11));

        // Setting onclick event listener for the map
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng point) {

                // clearing map and generating new marker points if user clicks on map more than two times
                if (MarkerPoints.size() > 1) {
                    mMap.clear();
                    MarkerPoints.clear();
                    MarkerPoints = new ArrayList<>();
                 //   ShowDistanceDuration.setText("");
                }

                // Adding new item to the ArrayList
                MarkerPoints.add(point);

                // Creating MarkerOptions
                MarkerOptions options = new MarkerOptions();

                // Setting the position of the marker
                options.position(point);

                /**
                 * For the start location, the color of marker is GREEN and
                 * for the end location, the color of marker is RED.
                 */
                if (MarkerPoints.size() == 1) {
                    options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                } else if (MarkerPoints.size() == 2) {
                    options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                }


                // Add new marker to the Google Map Android API V2
                mMap.addMarker(options);

                // Checks, whether start and end locations are captured
//                if (MarkerPoints.size() >= 2) {
//                    origin = MarkerPoints.get(0);
//                    dest = MarkerPoints.get(1);
//                }

            }
        });

        requestRetrofitAndGetResponse("driving");

    }
}
