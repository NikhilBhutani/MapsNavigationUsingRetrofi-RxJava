package nikhilbhutani.github.com.googlemapsnavigation.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import com.google.gson.GsonBuilder;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import nikhilbhutani.github.com.googlemapsnavigation.R;
import nikhilbhutani.github.com.googlemapsnavigation.interfaces.MyApiRequestInterface;
import nikhilbhutani.github.com.googlemapsnavigation.model.PlacesApiResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.searchPlace)
    EditText editText;

    String baseUrl = "https://maps.googleapis.com/maps/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();



        MyApiRequestInterface myApiRequestInterface = retrofit.create(MyApiRequestInterface.class);

        Call<PlacesApiResponse> myApiRequestInterfaceCall = myApiRequestInterface.getJson("janakpuri", "API KEY HERE");

        myApiRequestInterfaceCall.enqueue(new Callback<PlacesApiResponse>() {
            @Override
            public void onResponse(Call<PlacesApiResponse> call, Response<PlacesApiResponse> response) {

                PlacesApiResponse placesApiResponse = response.body();

                Log.i("JSON RESPONSE", " "+ new GsonBuilder().setPrettyPrinting().create().toJson(response.body()));

                String results = placesApiResponse.getStatus();


            }

            @Override
            public void onFailure(Call<PlacesApiResponse> call, Throwable t) {

                t.printStackTrace();

            }
        });

    }

    @OnClick(R.id.button)
    public void nextActivity(){
      startActivity(new Intent(MainActivity.this, DirectionActivity.class));
    }
}
