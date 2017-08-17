package nikhilbhutani.github.com.googlemapsnavigation.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nikhilbhutani on 17/08/17.
 */

public class DirectionApiResponse {

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("routes")
    @Expose
    private List<Routes> routes = new ArrayList<>();

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Routes> getRoutes() {
        return routes;
    }

    public void setRoutes(List<Routes> routes) {
        this.routes = routes;
    }
}
