package nikhilbhutani.github.com.googlemapsnavigation.interfaces;

import nikhilbhutani.github.com.googlemapsnavigation.model.PlacesApiResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by nikhilbhutani on 27/06/17.
 */

public interface MyApiRequestInterface {

    @GET("api/place/textsearch/json")
    Call<PlacesApiResponse> getJson(@Query("query") String query, @Query("key") String key);
}
