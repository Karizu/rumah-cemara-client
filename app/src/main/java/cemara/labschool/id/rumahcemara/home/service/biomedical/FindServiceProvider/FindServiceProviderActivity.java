package cemara.labschool.id.rumahcemara.home.service.biomedical.FindServiceProvider;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
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
import android.widget.RelativeLayout;
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
import cemara.labschool.id.rumahcemara.api.AppointmentHelper;
import cemara.labschool.id.rumahcemara.home.service.biomedical.FindOutreachWorker.adapter.AdapterListOutreachNearMe;
import cemara.labschool.id.rumahcemara.home.service.biomedical.FindServiceProvider.adapter.AdapterListProviderNearMe;
import cemara.labschool.id.rumahcemara.model.ApiResponse;
import cemara.labschool.id.rumahcemara.model.NearestOutreachModel;
import cemara.labschool.id.rumahcemara.model.NearestProviderModel;
import cemara.labschool.id.rumahcemara.model.response.OutreachNearMeResponse;
import cemara.labschool.id.rumahcemara.model.response.ProviderNearMeResponse;
import cemara.labschool.id.rumahcemara.util.nearest.adapter.NearestAdapter;
import cemara.labschool.id.rumahcemara.util.nearest.adapter.NearestSearchResultAdapter;
import cemara.labschool.id.rumahcemara.util.nearest.modal.Nearest;
import okhttp3.Headers;

public class FindServiceProviderActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap, mOutreach;
    private static final String TAG = "FindServiceProviderActivity";
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_img)
    ImageView toolbarImg;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.bottom_sheet)
    LinearLayout layoutBottomSheet;
    @BindView(R.id.result_recycler)
    RecyclerView resultRecyclerView;
    @BindView(R.id.btn_sheet_navigation)
    RelativeLayout btnSheetNavigation;
    @BindView(R.id.img_navigation)
    ImageView imgNavigation;

    BottomSheetBehavior sheetBehavior;
    private Context activity;
    private LinearLayoutManager layoutManager;
    double longitude, latitude;
    private RecyclerView.Adapter adapter;
    private List<NearestProviderModel> articleModels;

    List<Nearest> nearestList = new ArrayList<>();
    List<Nearest> nearestSearchList = new ArrayList<>();
    NearestAdapter nearestAdapter;
    NearestSearchResultAdapter nearestSearchAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_service_provider_activity);
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
        setupAutocomplete();
        sheetBehavior = BottomSheetBehavior.from(layoutBottomSheet);
        sheetBehavior.setHideable(true);//Important to add
        sheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

        bottomSheetExpand();
//        getListNearest();

        latitude = -6.893870;
        longitude = 107.631200;

        populateData();
    }

    private void populateData() {

        AppointmentHelper.getListProvider(latitude, longitude, new RestCallback<ApiResponse<List<ProviderNearMeResponse>>>() {
            @Override
            public void onSuccess(Headers headers, ApiResponse<List<ProviderNearMeResponse>> body) {
                if (body != null && body.isStatus()) {
                    List<ProviderNearMeResponse> res = body.getData();
                    System.out.println("Response: " + body.getData());
                    articleModels = new ArrayList<>();
                    for (int i = 0; i < res.size(); i++) {
                        ProviderNearMeResponse article = res.get(i);
                        articleModels.add(new NearestProviderModel(article.getId(),
                                article.getGroup().getId(),
                                article.getGroup().getGroupProfile().getGroup_id(),
                                article.getGroup().getName(),
                                article.getDescription(),
                                article.getGroup().getGroupProfile().getAddress(),
                                article.getGroup().getGroupProfile().getAddress(),
                                article.getGroup().getGroupProfile().getPhone_number(),
                                article.getDistance(),
                                article.getUser(),
                                article.getGroup()));
                    }

                    adapter = new AdapterListProviderNearMe(articleModels, activity);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailed(ErrorResponse error) {

            }

            @Override
            public void onCanceled() {

            }
        });

    }

    private void setupAutocomplete() {
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

                getListNearestSearch();
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

        autocompleteFragment.getView().findViewById(R.id.place_autocomplete_clear_button)
                .setOnClickListener(view -> {
                    // example : way to access view from PlaceAutoCompleteFragment
                    // ((EditText) autocompleteFragment.getView()
                    // .findViewById(R.id.place_autocomplete_search_input)).setText("");
                    editTextAutocomplete.setText("");
                    sheetBehavior.setHideable(true);//Important to add
                    sheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                });
    }

    private void bottomSheetExpand() {
        sheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View view, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_HIDDEN:
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED: {
                        imgNavigation.setImageResource(R.drawable.ic_arrow_bottom);
//                        btnBottomSheet.setText("Close Sheet");
                    }
                    break;
                    case BottomSheetBehavior.STATE_COLLAPSED: {
                        imgNavigation.setImageResource(R.drawable.ic_arrow_up);
//                        btnBottomSheet.setText("Expand Sheet");
                    }
                    break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                        break;
                    case BottomSheetBehavior.STATE_SETTLING:
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View view, float v) {

            }
        });
    }

    private void getListNearest() {
        nearestList.add(new Nearest(R.drawable.select_dp, "Test1", "2 km", "1"));
        nearestList.add(new Nearest(R.drawable.select_dp, "test2", "4 km", "2"));
        nearestList.add(new Nearest(R.drawable.select_dp, "teST3", "1 km", "3"));
        nearestAdapter = new NearestAdapter(getApplicationContext(), nearestList, "findserviceprovider");
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(nearestAdapter);
        nearestAdapter.notifyDataSetChanged();
    }

    private void getListNearestSearch() {
        if (sheetBehavior.getState() != BottomSheetBehavior.STATE_HALF_EXPANDED) {
            sheetBehavior.setState(BottomSheetBehavior.STATE_HALF_EXPANDED);
//             btnBottomSheet.setText("Close sheet");
        }
//         else {
//             sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
////             btnBottomSheet.setText("Expand sheet");
//         }
        nearestSearchList.clear();
        nearestSearchList.add(new Nearest(R.drawable.select_dp, "Searched", "2 km", "1a"));
        nearestSearchList.add(new Nearest(R.drawable.select_dp, "and", "4 km", "2a"));
        nearestSearchList.add(new Nearest(R.drawable.select_dp, "Found", "1 km", "3a"));
        nearestSearchAdapter = new NearestSearchResultAdapter(getApplicationContext(), nearestSearchList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        resultRecyclerView.setLayoutManager(layoutManager);
        resultRecyclerView.setItemAnimator(new DefaultItemAnimator());
        resultRecyclerView.setAdapter(nearestSearchAdapter);
        nearestSearchAdapter.notifyDataSetChanged();

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mOutreach = googleMap;
        LatLng indo = new LatLng(-2.5489, 118.0149);
        LatLng outreach = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions().position(indo).title("Marker in Indonesia").icon(BitmapDescriptorFactory.fromResource(R.drawable.pin_map)));
        mOutreach.addMarker(new MarkerOptions().position(outreach).title("Outreach").icon(BitmapDescriptorFactory.fromResource(R.drawable.pin_rs)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(indo));
    }

    public void setToolbar() {
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        toolbar.setNavigationIcon(R.drawable.icon_back);
        toolbarTitle.setText(R.string.find_service_provider);
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
