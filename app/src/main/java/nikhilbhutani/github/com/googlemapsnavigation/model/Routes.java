package nikhilbhutani.github.com.googlemapsnavigation.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nikhilbhutani on 17/08/17.
 */

public class Routes {

    @SerializedName("summary")
    @Expose
    private String summary;

    @SerializedName("legs")
    @Expose
    private List<Legs> legs = new ArrayList<>();

    @SerializedName("overview_polyline")
    @Expose
    private OverviewPolyline overviewPolyline;

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public List<Legs> getLegs() {
        return legs;
    }

    public void setLegs(List<Legs> legs) {
        this.legs = legs;
    }

    public OverviewPolyline getOverviewPolyline() {
        return overviewPolyline;
    }

    public void setOverviewPolyline(OverviewPolyline overviewPolyline) {
        this.overviewPolyline = overviewPolyline;
    }
}
