package cemara.labschool.id.rumahcemara.util.location;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
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

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cemara.labschool.id.rumahcemara.R;

public class SetLocationActivity extends AppCompatActivity implements OnMapReadyCallback {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_img)
    ImageView toolbarImg;
    @BindView(R.id.location_address)
    TextView locationAddress;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_location_activity);
        ButterKnife.bind(this);
        setToolbar();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
        setupAutocomplete();

    }
    @OnClick(R.id.btn_set_location)
    public void setThisLocation(){
        Intent returnIntent = new Intent();
        returnIntent.putExtra("result",locationAddress.getText().toString());
        setResult(Activity.RESULT_OK,returnIntent);
        finish();
    }
    private void setupAutocomplete() {
        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
        autocompleteFragment.setHint("Search Location");
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
                Toast toast = Toast.makeText(getApplicationContext(), String.valueOf(place.getAddress()), Toast.LENGTH_SHORT);
                toast.show();
                locationAddress.setText(String.valueOf(place.getAddress()));
            }

            @SuppressLint("LongLogTag")
            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Toast toast = Toast.makeText(getApplicationContext(), String.valueOf(status), Toast.LENGTH_SHORT);
                toast.show();
            }
        });
//        autocompleteFragment.getView().findViewById(R.id.place_autocomplete_clear_button)
//                .setOnClickListener(view -> {
//                    // example : way to access view from PlaceAutoCompleteFragment
//                    // ((EditText) autocompleteFragment.getView()
//                    // .findViewById(R.id.place_autocomplete_search_input)).setText("");
//                    editTextAutocomplete.setText("");
//                });
    }

    public void setToolbar() {
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        toolbar.setNavigationIcon(R.drawable.icon_back);
        toolbarTitle.setText("Set Your Location");
        toolbarImg.setVisibility(View.GONE);
        toolbar.setNavigationOnClickListener(v -> {
            //What to do on back clicked
            onBackPressed();
        });
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng indo = new LatLng(-2.5489, 118.0149);
        mMap.addMarker(new MarkerOptions().position(indo).title("Marker in Indonesia").icon(BitmapDescriptorFactory.fromResource(R.drawable.pin_map)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(indo));
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        Toast.makeText(this, "hasCapture", Toast.LENGTH_SHORT).show();
    }

}
