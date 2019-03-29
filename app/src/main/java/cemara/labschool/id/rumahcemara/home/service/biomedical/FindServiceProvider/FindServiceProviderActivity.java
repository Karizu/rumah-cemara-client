package cemara.labschool.id.rumahcemara.home.service.biomedical.FindServiceProvider;

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
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
import cemara.labschool.id.rumahcemara.home.service.biomedical.FindServiceProvider.adapter.AdapterListProviderNearMe;
import cemara.labschool.id.rumahcemara.model.ApiResponse;
import cemara.labschool.id.rumahcemara.model.NearestProviderModel;
import cemara.labschool.id.rumahcemara.model.response.ProviderNearMeResponse;
import cemara.labschool.id.rumahcemara.util.dialog.Loading;
import cemara.labschool.id.rumahcemara.util.nearest.adapter.adapter.nearest.search.biomedical.NearestProviderSearchAdapter;
import okhttp3.Headers;

public class FindServiceProviderActivity extends AppCompatActivity implements OnMapReadyCallback, SearchView.OnQueryTextListener {
    private GoogleMap mOutreach;
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
    double longitude, latitude;
    private AdapterListProviderNearMe adapter;
    private List<NearestProviderModel> articleModels;
    private Dialog dialog;

    NearestProviderSearchAdapter nearestSearchAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_service_provider_activity);
        ButterKnife.bind(this);
        setToolbar();

        activity = getApplicationContext();
        LinearLayoutManager layoutManager = new LinearLayoutManager(activity,
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

//        int id = searchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        EditText searchEditText = searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
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

        bottomSheetExpand();
//        getListNearest();

        Bundle bundle = getIntent().getBundleExtra("myData");   //<< get Bundle from Intent
        latitude = Double.parseDouble(Objects.requireNonNull(bundle.getString("latitude")));
        longitude = Double.parseDouble(Objects.requireNonNull(bundle.getString("longitude")));

//        latitude = -6.893870;
//        longitude = 107.631200;

        populateData();
    }

    private void populateData() {
        Loading.show(this);
        AppointmentHelper.getListProviderBiomedical(latitude, longitude, new RestCallback<ApiResponse<List<ProviderNearMeResponse>>>() {
            @Override
            public void onSuccess(Headers headers, ApiResponse<List<ProviderNearMeResponse>> body) {
                Loading.hide(getApplicationContext());
                if (body != null && body.isStatus()) {
                    List<ProviderNearMeResponse> res = body.getData();
                    if (res.size() != 0){
                        System.out.println("Response Bio: " + body.getData());
                        articleModels = new ArrayList<>();
                        for (int i = 0; i < res.size(); i++) {
                            ProviderNearMeResponse article = res.get(i);
                            if (article.getGroup()!=null){
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
                        }

                        adapter = new AdapterListProviderNearMe(articleModels, activity);
                        recyclerView.setAdapter(adapter);
                    } else {
                        Log.d("Provider", "Masuk Else");
                        showDialogAlert(R.layout.dialog_appointment_out_of_range_provider);
                        TextView ok = dialog.findViewById(R.id.appointment_ok);
                        ok.setOnClickListener(view -> onBackPressed());
                    }
                } else {
                    showDialogAlert(R.layout.dialog_appointment_out_of_range_provider);
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

    private void getListNearestSearch() {
        if (sheetBehavior.getState() != BottomSheetBehavior.STATE_HALF_EXPANDED) {
            sheetBehavior.setState(BottomSheetBehavior.STATE_HALF_EXPANDED);
//             btnBottomSheet.setText("Close sheet");
        }
//         else {
//             sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
////             btnBottomSheet.setText("Expand sheet");
//         }
        AppointmentHelper.getListProviderBiomedical(latitude, longitude, new RestCallback<ApiResponse<List<ProviderNearMeResponse>>>() {
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
                                article.getGroup().getGroupProfile().getPicture(),
                                article.getGroup().getName(),
                                article.getDescription(),
                                article.getGroup().getGroupProfile().getAddress(),
                                article.getGroup().getGroupProfile().getAddress(),
                                article.getGroup().getGroupProfile().getPhone_number(),
                                article.getDistance(),
                                article.getUser(),
                                article.getGroup()));
                    }

                    nearestSearchAdapter = new NearestProviderSearchAdapter(articleModels, activity);
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
        mOutreach = googleMap;
        LatLng indo = new LatLng(latitude, longitude);
        googleMap.addMarker(new MarkerOptions().position(indo).title("Lokasi Anda").icon(BitmapDescriptorFactory.fromResource(R.drawable.pin_map)));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 15.0f));

        AppointmentHelper.getListProviderBiomedical(latitude, longitude, new RestCallback<ApiResponse<List<ProviderNearMeResponse>>>() {
            @Override
            public void onSuccess(Headers headers, ApiResponse<List<ProviderNearMeResponse>> body) {

                if (body != null && body.isStatus()) {
                    List<ProviderNearMeResponse> res = body.getData();
                    System.out.println("Response: " + body.getData());
                    Double lat, longi;
                    articleModels = new ArrayList<>();
                    for (int i = 0; i < res.size(); i++) {
                        ProviderNearMeResponse article = res.get(i);
                        lat = Double.valueOf(article.getLat());
                        longi = Double.valueOf(article.getLongitude());
                        Log.d("lat, longi", lat + " " + longi);
                        LatLng outreach = new LatLng(lat, longi);
                        if (article.getGroup()!=null){
                            mOutreach.addMarker(new MarkerOptions().position(outreach).title(article.getGroup().getName()).icon(BitmapDescriptorFactory.fromResource(R.drawable.pin_rs)));
                        }
                    }
                }
            }

            @Override
            public void onFailed(ErrorResponse error) {
                error.getDescription();
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
        toolbarTitle.setText(R.string.find_service_provider);
        toolbarImg.setImageResource(R.drawable.icon_biomedical_white);
        toolbar.setNavigationOnClickListener(v -> {
            //What to do on back clicked
            onBackPressed();
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
        List<NearestProviderModel> newWorker = new ArrayList<>();
        String newTextLowerCase = newText.toLowerCase();
        if (articleModels.size() > 0) {
            for (NearestProviderModel user : articleModels) {
                if (user.getName().toLowerCase().contains(newTextLowerCase)) {
                    newWorker.add(user);
                }
            }
        }
//        adapter.updateData(newWorker);
        nearestSearchAdapter.updateData(newWorker);
        return true;
    }

}
