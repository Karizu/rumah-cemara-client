package cemara.labschool.id.rumahcemara.home.service.structural.FindOutreachWorker;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
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
import cemara.labschool.id.rumahcemara.home.service.structural.FindOutreachWorker.adapter.AdapterListOutreachNearMe;
import cemara.labschool.id.rumahcemara.model.ApiResponse;
import cemara.labschool.id.rumahcemara.model.NearestOutreachModel;
import cemara.labschool.id.rumahcemara.model.response.OutreachNearMeResponse;
import cemara.labschool.id.rumahcemara.util.dialog.Loading;
import cemara.labschool.id.rumahcemara.util.nearest.adapter.NearestAdapter;
import cemara.labschool.id.rumahcemara.util.nearest.adapter.adapter.nearest.search.counseling.NearestSearchResultAdapterApi;
import cemara.labschool.id.rumahcemara.util.nearest.modal.Nearest;
import okhttp3.Headers;

public class FindOutreachWorkerActivity extends AppCompatActivity implements OnMapReadyCallback, SearchView.OnQueryTextListener {
    private GoogleMap mMap, mOutreach;
    private static final String TAG = "FindOutreachWorkerActivity";
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

    List<Nearest> nearestList = new ArrayList<>();
    List<Nearest> nearestSearchList = new ArrayList<>();
    NearestAdapter nearestAdapter;
    NearestSearchResultAdapterApi nearestSearchAdapter;


    double longitude, latitude;
    private List<NearestOutreachModel> articleModels;
    private AdapterListOutreachNearMe adapter;
    private Context activity;
    private LinearLayoutManager layoutManager;
    private Dialog dialog;

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

        SearchView searchView = findViewById(R.id.search_view);
        searchView.setQueryHint("Seacrh Outreach Worker Name or Location");

        int id = searchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        EditText searchEditText = (EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchEditText.setTextColor(getResources().getColor(R.color.place_autocomplete_search_hint));
        searchEditText.setHintTextColor(getResources().getColor(R.color.place_autocomplete_search_hint));

        searchView.setOnQueryTextListener(this);

        searchView.setOnClickListener(v -> {

                    getListNearestSearch();
                    searchView.setIconified(false);
                }
        );

//        setupAutocomplete();
        sheetBehavior = BottomSheetBehavior.from(layoutBottomSheet);
        sheetBehavior.setHideable(true);//Important to add
        sheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

        Bundle bundle = getIntent().getBundleExtra("myData");   //<< get Bundle from Intent

        latitude = Double.parseDouble(bundle.getString("latitude"));
        longitude = Double.parseDouble(bundle.getString("longitude"));

//        latitude = -6.893870;
//        longitude = 107.631200;

        bottomSheetExpand();
//        getListNearest();
        populateData();
    }

    private void populateData() {
        Loading.show(this);
        AppointmentHelper.getListOutreach(latitude, longitude, new RestCallback<ApiResponse<List<OutreachNearMeResponse>>>() {
            @Override
            public void onSuccess(Headers headers, ApiResponse<List<OutreachNearMeResponse>> body) {
                Loading.hide(getApplicationContext());
                if (body != null && body.isStatus()) {
                    List<OutreachNearMeResponse> res = body.getData();
                    if (res.size() != 0){
                        System.out.println("Response: " + body.getData());
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
                                    article.getDistance(),
                                    article.getUser(),
                                    article.getGroup()));
                        }

                        adapter = new AdapterListOutreachNearMe(articleModels, activity);
                        recyclerView.setAdapter(adapter);
                    } else {
                        showDialogAlert(R.layout.dialog_appointment_out_of_range_outreach);
                        TextView ok = dialog.findViewById(R.id.appointment_ok);
                        ok.setOnClickListener(view -> onBackPressed());
                    }
                } else {
                    showDialogAlert(R.layout.dialog_appointment_out_of_range_outreach);
                    TextView ok = dialog.findViewById(R.id.appointment_ok);
                    ok.setOnClickListener(view -> onBackPressed());
                }
            }

            @Override
            public void onFailed(ErrorResponse error) {
                Loading.hide(getApplicationContext());
                showDialogAlert(R.layout.dialog_bad_connection);
                TextView ok = dialog.findViewById(R.id.appointment_ok);
                ok.setOnClickListener(view -> onBackPressed());
            }

            @Override
            public void onCanceled() {
                Loading.hide(getApplicationContext());
            }
        });

    }

    private void showDialogAlert(int layout) {
        dialog = new Dialog(this);
        //SET TITLE
        dialog.setTitle("");

        //set content
        dialog.setContentView(layout);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(Objects.requireNonNull(dialog.getWindow()).getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.show();
        dialog.getWindow().setAttributes(lp);
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
        nearestAdapter = new NearestAdapter(getApplicationContext(), nearestList, "findoutreachworker");
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

        AppointmentHelper.getListOutreach(latitude, longitude, new RestCallback<ApiResponse<List<OutreachNearMeResponse>>>() {
            @Override
            public void onSuccess(Headers headers, ApiResponse<List<OutreachNearMeResponse>> body) {
                if (body != null && body.isStatus()) {
                    List<OutreachNearMeResponse> res = body.getData();
                    System.out.println("Response List Search: " + body.getData());
                    articleModels = new ArrayList<>();
                    for (int i = 0; i < res.size(); i++) {
//                        OutreachLocationData outreachLocationData = res.get(i).getOutreachLocationData();
//                        workerModels.add(outreachLocationData);
                        OutreachNearMeResponse article = res.get(i);
                        articleModels.add(new NearestOutreachModel(article.getId(),
                                article.getUser_id(),
                                article.getUser().getProfile().getPicture(),
                                article.getUser().getProfile().getFullname(),
                                article.getDescription(),
                                article.getUser().getProfile().getAddress(),
                                article.getUser().getProfile().getCity(),
                                article.getUser().getProfile().getPhoneNumber(),
                                article.getDistance(),
                                article.getUser(),
                                article.getGroup()));
                    }

                    nearestSearchAdapter = new NearestSearchResultAdapterApi(articleModels, activity);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    resultRecyclerView.setLayoutManager(layoutManager);
                    resultRecyclerView.setItemAnimator(new DefaultItemAnimator());
                    resultRecyclerView.setAdapter(nearestSearchAdapter);
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

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mOutreach = googleMap;
        LatLng indo = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions().position(indo).title("Lokasi Anda").icon(BitmapDescriptorFactory.fromResource(R.drawable.pin_map)));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 15.0f));

        AppointmentHelper.getListOutreach(latitude, longitude, new RestCallback<ApiResponse<List<OutreachNearMeResponse>>>() {
            @Override
            public void onSuccess(Headers headers, ApiResponse<List<OutreachNearMeResponse>> body) {
                if (body != null && body.isStatus()) {
                    List<OutreachNearMeResponse> res = body.getData();
                    System.out.println("Response: " + body.getData());
                    Double lat, longi;
                    articleModels = new ArrayList<>();
                    for (int i = 0; i < res.size(); i++) {
                        OutreachNearMeResponse article = res.get(i);
                        lat = Double.valueOf(article.getLat());
                        longi = Double.valueOf(article.getLongitude());
                        Log.d("lat, longi", lat + " " + longi);
                        LatLng outreach = new LatLng(lat, longi);
                        mOutreach.addMarker(new MarkerOptions().position(outreach).title(article.getUser().getProfile().getFullname()).icon(BitmapDescriptorFactory.fromResource(R.drawable.pin_people)));
                    }
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

    public void setToolbar() {
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        toolbar.setNavigationIcon(R.drawable.icon_back);
        toolbarTitle.setText(R.string.find_outreach_worker);
        toolbarImg.setImageResource(R.drawable.icon_structural_white);
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

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        List<NearestOutreachModel> newWorker = new ArrayList<>();
        String newTextLowerCase = newText.toLowerCase();
        for (NearestOutreachModel user : articleModels) {
            if (user.getUser().getProfile().getFullname().toLowerCase().contains(newTextLowerCase)) {
                newWorker.add(user);
            }
        }

//        adapter.updateData(newWorker);
        nearestSearchAdapter.updateData(newWorker);
        return true;
    }
}
