package cemara.labschool.id.rumahcemara.home.service.biomedical;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cemara.labschool.id.rumahcemara.R;
import cemara.labschool.id.rumahcemara.home.service.biomedical.FindOutreachWorker.FindOutreachWorkerActivity;
import cemara.labschool.id.rumahcemara.home.service.biomedical.FindServiceProvider.FindServiceProviderActivity;

public class BiomedicalAppointmentActivity extends AppCompatActivity implements LocationListener {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.tvLatitude)
    TextView setLatitude;
    @BindView(R.id.tvLongitude)
    TextView setLongitude;
    @BindView(R.id.toolbar_img)
    ImageView toolbarImg;
    Double latitude, longitude;
    private LocationManager locationManager;
    private LocationRequest mLocationRequest;
    int TAG_CODE_PERMISSION_LOCATION;
    private FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.biomedical_appointment_activity);
        ButterKnife.bind(this);
        setToolbar();
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            Log.i("fuck", "need permissions....");
            ActivityCompat.requestPermissions(this, new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION,},
                    TAG_CODE_PERMISSION_LOCATION);
        }

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, location -> {
                    // Got last known location. In some rare situations this can be null.
                    if (location != null) {
                        // Logic to handle location object
                        latitude = location.getLatitude();
                        longitude = location.getLongitude();
                    }
                });

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
    }

    @OnClick(R.id.find_service_provider)
    public void toFindServiceProvider(){
        Bundle bundle = new Bundle();
        bundle.putString("latitude", String.valueOf(latitude));
        bundle.putString("longitude", String.valueOf(longitude));
        if (latitude != null){
            Intent intent = new Intent(this, FindServiceProviderActivity.class);
            intent.putExtra("myData",bundle);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Searching Location", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.find_outreach_worker)
    public void toFindOutreachWorker(){
        Bundle bundle = new Bundle();
        bundle.putString("latitude", String.valueOf(latitude));
        bundle.putString("longitude", String.valueOf(longitude));
        if (latitude != null){
            Intent intent = new Intent(this, FindOutreachWorkerActivity.class);
            intent.putExtra("myData",bundle);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Searching Location", Toast.LENGTH_SHORT).show();
        }
    }

    public void setToolbar() {
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        toolbar.setNavigationIcon(R.drawable.icon_back);
        toolbarTitle.setText("Biomedical Appointment");
        toolbarImg.setImageResource(R.drawable.icon_biomedical_white);
        toolbar.setNavigationOnClickListener(v -> {
            //What to do on back clicked
            onBackPressed();
        });
    }

    @Override
    public void onLocationChanged(Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        setLatitude.setText(String.valueOf(latitude));
        setLongitude.setText(String.valueOf(longitude));
        Log.d("Latitude", String.valueOf(latitude));
        Log.d("Longitude", String.valueOf(longitude));
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d("Latitude","status");
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d("Latitude","enabled");
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.d("Latitude","disable");
    }
}
