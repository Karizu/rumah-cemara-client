package cemara.labschool.id.rumahcemara.home.service.biomedical.FindOutreachWorker;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.rezkyatinnov.kyandroid.reztrofit.ErrorResponse;
import com.rezkyatinnov.kyandroid.reztrofit.RestCallback;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import cemara.labschool.id.rumahcemara.R;
import cemara.labschool.id.rumahcemara.api.Api;
import cemara.labschool.id.rumahcemara.api.AppointmentHelper;
import cemara.labschool.id.rumahcemara.home.service.biomedical.FindOutreachWorker.adapter.AdapterListOutreachNearMe;
import cemara.labschool.id.rumahcemara.model.ApiResponse;
import cemara.labschool.id.rumahcemara.model.NearestOutreachModel;
import cemara.labschool.id.rumahcemara.model.response.OutreachLocationDataResponse;
import cemara.labschool.id.rumahcemara.model.response.OutreachNearMeResponse;
import cemara.labschool.id.rumahcemara.util.nearest.adapter.NearestAdapter;
import cemara.labschool.id.rumahcemara.util.nearest.modal.Nearest;
import okhttp3.Headers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FindOutreachWorkerActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private static final String TAG = "FindOutreachWorkerActivity";
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_img)
    ImageView toolbarImg;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    List<Nearest> nearestList = new ArrayList<>();
    NearestAdapter nearestAdapter;
    double longitude, latitude;
    private List<NearestOutreachModel> articleModels;
    private RecyclerView.Adapter adapter;
    private Context activity;
    private LinearLayoutManager layoutManager;
    String sBearerToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_outreach_worker);

        ButterKnife.bind(this);
        setToolbar();

        activity = getApplicationContext();
        layoutManager = new LinearLayoutManager(activity,
                LinearLayout.HORIZONTAL,
                false);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(layoutManager);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
        autocompleteFragment.setHint("Search Outreach Worker Name or Location");
        EditText editTextAutocomplete = ((EditText) Objects.requireNonNull(autocompleteFragment.getView()).findViewById(R.id.place_autocomplete_search_input));
        editTextAutocomplete.setTextSize(14.0f);
        editTextAutocomplete.setPadding(0, 1, 1, 1);
        editTextAutocomplete.setTextColor(getResources().getColor(R.color.White));
        editTextAutocomplete.setGravity(Gravity.CENTER_VERTICAL | Gravity.START);

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                Log.i(TAG, "Place: " + place.getName());
                Toast toast = Toast.makeText(getApplicationContext(), String.valueOf(place.getAddress()), Toast.LENGTH_SHORT);
                toast.show();

            }

            @SuppressLint("LongLogTag")
            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Toast toast = Toast.makeText(getApplicationContext(), String.valueOf(status), Toast.LENGTH_SHORT);
                toast.show();
                Log.i(TAG, "An error occurred: " + status);
            }
        });

//        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
//        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        latitude = -6.893870;
        longitude = 107.631200;

        sBearerToken = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwOlwvXC8zNy43Mi4xNzIuMTQ0XC9ydW1haC1jZW1hcmEtYXBpXC9wdWJsaWNcL2FwaVwvbG9naW4iLCJpYXQiOjE1NDg0MDI2NDUsImV4cCI6MTU4MDAyNTA0NSwibmJmIjoxNTQ4NDAyNjQ1LCJqdGkiOiI1ZkRLaUViVlJwcDE3d05EIiwic3ViIjoiNTIwMmU1ZDMtYTVkZi00NWQzLWIwMmQtNGJlNWUxMTQwNjgzIiwicHJ2IjoiZjZiNzE1NDlkYjhjMmM0MmI3NTgyN2FhNDRmMDJiN2VlNTI5ZDI0ZCJ9.o2m4rKGdv1jNiV4nhNBSHXKMcOBNaYEuFOshfLdLM50";
        System.out.println(longitude + "    " + latitude);

//        getListNearest();
        populateData();
    }

    private void populateData() {

        AppointmentHelper.getListOutreach(latitude, longitude, new RestCallback<ApiResponse<List<OutreachNearMeResponse>>>() {
            @Override
            public void onSuccess(Headers headers, ApiResponse<List<OutreachNearMeResponse>> body) {
                if (body != null && body.isStatus()) {
                    List<OutreachNearMeResponse> res = body.getData();
                    System.out.println("Response: "+body.getData());
                    articleModels = new ArrayList<>();
                    for (int i = 0; i < res.size(); i++) {
                        OutreachNearMeResponse article = res.get(i);
                        articleModels.add(new NearestOutreachModel(article.getId(),
                                article.getUser_id(),
                                article.getUser().getProfile().getPicture(),
                                article.getUser().getProfile().getFullname(),
                                article.getDescription(),
                                article.getUser().getProfile().getAddress(),
                                article.getUser().getProfile().getCity(),
                                article.getUser().getProfile().getPhoneNumber(),
                                article.getUser()));
                    }

                    adapter = new AdapterListOutreachNearMe(articleModels, activity);
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailed(ErrorResponse error) {
                Log.i("response", "Response Failed");
            }

            @Override
            public void onCanceled() {
                Log.i("response", "Response Failed");
            }
        });
    }

    private void getListNearest() {
        nearestList.add(new Nearest(R.drawable.select_dp, "Jika Tester", "2 km", "1"));
        nearestList.add(new Nearest(R.drawable.select_dp, "dan", "4 km", "2"));
        nearestList.add(new Nearest(R.drawable.select_dp, "Hanya Tester", "1 km", "3"));
        nearestAdapter = new NearestAdapter(getApplicationContext(), nearestList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(nearestAdapter);
        nearestAdapter.notifyDataSetChanged();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng indo = new LatLng(-2.5489, 118.0149);
        mMap.addMarker(new MarkerOptions().position(indo).title("Marker in Indonesia").icon(BitmapDescriptorFactory.fromResource(R.drawable.pin_map)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(indo));
    }

    public void setToolbar() {
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        toolbar.setNavigationIcon(R.drawable.icon_back);
        toolbarTitle.setText(R.string.find_outreach_worker);
        toolbarImg.setImageResource(R.drawable.icon_biomedical_white);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //What to do on back clicked
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {

        int count = getFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            super.onBackPressed();
            //additional code
        } else {
            getFragmentManager().popBackStack();
        }

    }
}
