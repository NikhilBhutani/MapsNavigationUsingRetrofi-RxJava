package nikhilbhutani.github.com.googlemapsnavigation.interfaces;

import nikhilbhutani.github.com.googlemapsnavigation.model.DirectionApiResponse;
import nikhilbhutani.github.com.googlemapsnavigation.model.PlacesApiResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by nikhilbhutani on 17/08/17.
 */

public interface DirectionsApiRequestInterface {

    @GET("api/directions/json")
    Call<DirectionApiResponse> getDirectionJson(@Query("origin") String origin, @Query("destination") String destination, @Query("key") String key);
}
