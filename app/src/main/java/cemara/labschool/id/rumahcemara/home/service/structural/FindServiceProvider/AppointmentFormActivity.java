package cemara.labschool.id.rumahcemara.home.service.structural.FindServiceProvider;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.angads25.toggle.LabeledSwitch;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.rezkyatinnov.kyandroid.localdata.LocalData;
import com.rezkyatinnov.kyandroid.reztrofit.ErrorResponse;
import com.rezkyatinnov.kyandroid.reztrofit.RestCallback;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cemara.labschool.id.rumahcemara.MainActivity;
import cemara.labschool.id.rumahcemara.R;
import cemara.labschool.id.rumahcemara.api.AppointmentHelper;
import cemara.labschool.id.rumahcemara.home.service.biomedical.FindOutreachWorker.config.CircleTransform;
import cemara.labschool.id.rumahcemara.model.ApiResponse;
import cemara.labschool.id.rumahcemara.model.User;
import cemara.labschool.id.rumahcemara.model.response.OutreachNearMeResponse;
import cemara.labschool.id.rumahcemara.util.dialog.Loading;
import io.realm.Realm;
import okhttp3.Headers;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class AppointmentFormActivity extends AppCompatActivity implements LocationListener {


    @BindView(R.id.thistoolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_img)
    ImageView toolbarImg;
    @BindView(R.id.appointment_switch)
    LabeledSwitch apponintmentSwitch;
    @BindView(R.id.appointment_worker_name)
    Spinner appointmentWorkerName;
    @BindView(R.id.description_material)
    EditText descriptionMaterial;
    EditText changeTo;
    Dialog dialog;
    String user_id, groupId, workerId, selectedName, valueName;
    Context appContext;
    String typeProvider = "provider";
    String serviceTypeId = "b1cd92a3-2f47-5776-aa60-31c58c9f5291";
    String startDate = "2019-01-26";
    String endDate = "2019-01-28";
    int flag = 0;
    EditText dateStart, dateEnd;
    ArrayList<String> listValue;
    double latitude, longitude;
    final Calendar myCalendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener date = (datePicker, year, month, day) -> {
        myCalendar.set(Calendar.YEAR, year);
        myCalendar.set(Calendar.MONTH, month);
        myCalendar.set(Calendar.DAY_OF_MONTH, day);
        updateLabel(changeTo);
    };
    private boolean validate = false;
    private LocationManager locationManager;
    private int TAG_CODE_PERMISSION_LOCATION;
    private FusedLocationProviderClient fusedLocationClient;
    private void updateLabel(EditText date) {
        String myFormat = "MM/dd/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        date.setText(sdf.format(myCalendar.getTime()));
        changeTo = null;
    }
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_service_appointment_form_activity);
        ButterKnife.bind(this);
        setToolbar();

        Realm realm = LocalData.getRealm();
        User user = realm.where(User.class).findFirst();
        user_id = user.getId();

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

                        initSpinnerDosen(latitude, longitude);
                    }
                });


        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);

        appContext = this;

        populateData();

        appointmentWorkerName.setEnabled(false);

        apponintmentSwitch.setOnToggledListener((labeledSwitch, isOn) -> {
            if(isOn){
                appointmentWorkerName.setEnabled(true);
                flag = 1;
            }else {
                appointmentWorkerName.setEnabled(false);
                flag = 0;
            }
        });

        appointmentWorkerName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedName = parent.getItemAtPosition(position).toString();
                valueName = listValue.get(position);
//                requestDetailDosen(selectedName);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        descriptionMaterial.setOnTouchListener((v, event) -> {
            if (descriptionMaterial.hasFocus()) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                switch (event.getAction() & MotionEvent.ACTION_MASK){
                    case MotionEvent.ACTION_SCROLL:
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        return true;
                }
            }
            return false;
        });
    }

    private void initSpinnerDosen(Double latitude, Double longitude) {

        AppointmentHelper.getListOutreach(latitude, longitude, new RestCallback<ApiResponse<List<OutreachNearMeResponse>>>() {
            @Override
            public void onSuccess(Headers headers, ApiResponse<List<OutreachNearMeResponse>> body) {
                if (body != null && body.isStatus()) {
                    List<OutreachNearMeResponse> res = body.getData();
                    listValue = new ArrayList<String>();
                    ArrayList<String> listLabel = new ArrayList<String>();
                    for (int i = 0; i < res.size(); i++) {
                        listValue.add(res.get(i).getUser().getId());
                        listLabel.add(res.get(i).getUser().getProfile().getFullname());
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(appContext,
                            android.R.layout.simple_spinner_item, listLabel);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    appointmentWorkerName.setAdapter(adapter);
                } else {
                    Toast.makeText(appContext, "Connecting Failed", Toast.LENGTH_SHORT).show();
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

    private void populateData(){
        Bundle bundle = getIntent().getBundleExtra("myData");   //<< get Bundle from Intent

        String id = bundle.getString("id");
        String imgUrl = bundle.getString("imgUrl");
        String fullName = bundle.getString("fullname");
        String address = bundle.getString("address");
        String phoneNumber = bundle.getString("phone");
        String distance = bundle.getString("distance");
        groupId = bundle.getString("group_id");
        workerId = bundle.getString("worker_id");

        TextView tvName = findViewById(R.id.appointment_name);
        ImageView imageProfile = findViewById(R.id.img_profile);
        TextView tvAddress = findViewById(R.id.appointment_address);
        TextView tvPhoneNumber = findViewById(R.id.appointment_telp);
        TextView tvDistance = findViewById(R.id.appointment_distance);

        tvName.setText(fullName);
        Picasso.get().load(imgUrl).transform(new CircleTransform()).into(imageProfile);
//        Glide.with(this).applyDefaultRequestOptions(new RequestOptions().placeholder(R.drawable.select_dp)).load(imgUrl).into(imageProfile);
        tvAddress.setText(address);
        tvPhoneNumber.setText(phoneNumber);
        tvDistance.setText(distance);
    }

    private void validateField() {

        startDate = ((EditText) findViewById(R.id.appointment_date_start)).getText().toString();
        endDate = ((EditText) findViewById(R.id.appointment_date_end)).getText().toString();

        if (descriptionMaterial.getText().toString().length() == 0) {
            descriptionMaterial.setError("Description is required!");
        }
        if (startDate.length() == 0) {
            Toast.makeText(this, "Start Date is required", Toast.LENGTH_SHORT).show();
        }
        if (endDate.length() == 0) {
            Toast.makeText(this, "End Date is required", Toast.LENGTH_SHORT).show();
        }
        Loading.hide(getApplicationContext());
    }

    @OnClick(R.id.btn_send_appointment_form)
    void createAppointment() {
        Loading.show(this);
        validateField();

        if (descriptionMaterial.getText().toString().length() != 0 && startDate.length() != 0 && endDate.length() != 0) {
            Loading.show(this);
            if (flag == 1){
                valueName = listValue.get(appointmentWorkerName.getSelectedItemPosition());
            } else {
                valueName = "";
            }
            RequestBody requestBody;
            requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("group_id", groupId)
                    .addFormDataPart("user_id", user_id)
                    .addFormDataPart("provider_id", groupId)
                    .addFormDataPart("service_type_id", serviceTypeId)
                    .addFormDataPart("worker_id", valueName)
                    .addFormDataPart("start_date", startDate)
                    .addFormDataPart("end_date", endDate)
                    .addFormDataPart("description", descriptionMaterial.getText().toString())
                    .addFormDataPart("type_provider", typeProvider)
                    .build();

            AppointmentHelper.createBiomedicalAppointmentOutreach(requestBody, new RestCallback<ApiResponse>() {
                @Override
                public void onSuccess(Headers headers, ApiResponse body) {
                    Loading.hide(getApplicationContext());
                    Log.d("Create Success", "Create Appointment Success");
                    Toast.makeText(getApplicationContext(), "Create Appointment Success", Toast.LENGTH_LONG).show();
                    showDialogAlert(R.layout.dialog_appointment_success);
                    TextView gomylist = dialog.findViewById(R.id.appointment_gotomylist);
                    TextView ok = dialog.findViewById(R.id.appointment_ok);
                    gomylist.setOnClickListener(view -> {
                        Intent intent = new Intent(AppointmentFormActivity.this, MainActivity.class);
                        intent.putExtra("frag", "mylistfragment");
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    });
                    ok.setOnClickListener(view -> {
                        Intent intent = new Intent(AppointmentFormActivity.this, MainActivity.class);
                        intent.putExtra("frag", "homeFragment");
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    });
                }

                @Override
                public void onFailed(ErrorResponse error) {
                    Loading.hide(getApplicationContext());
                    Log.d(error.toString(), "Error");
                    showDialogAlert(R.layout.dialog_appointment_failed);
                    TextView retry = dialog.findViewById(R.id.appointment_retry);
                    TextView ok = dialog.findViewById(R.id.appointment_ok);
                    retry.setOnClickListener(view -> dialog.findViewById(R.id.btn_send_appointment).callOnClick());
                    ok.setOnClickListener(view -> dialog.dismiss());
                }

                @Override
                public void onCanceled() {
                    Loading.hide(getApplicationContext());
                }
            });
        }
    }

    @OnClick(R.id.appointment_date_start)
    public void openCalenderDateStart(){
        changeTo = findViewById(R.id.appointment_date_start);
         new DatePickerDialog(this, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }
    @OnClick(R.id.appointment_date_end)
    public void openCalenderDateEnd(){
        changeTo = findViewById(R.id.appointment_date_end);
        new DatePickerDialog(this, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void showDialogAlert(int layout) {
            dialog = new Dialog(this);
            //SET TITLE
            dialog.setTitle("Appointment Request");

            //set content
            dialog.setContentView(layout);
        dialog.setCanceledOnTouchOutside(false);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            lp.copyFrom(Objects.requireNonNull(dialog.getWindow()).getAttributes());
//        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
//        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            dialog.show();
            dialog.getWindow().setAttributes(lp);
    }

    public void setToolbar() {
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        toolbar.setNavigationIcon(R.drawable.icon_back);
        toolbarTitle.setText("Formulir");
        toolbarImg.setImageResource(R.drawable.icon_structural_white);
        toolbar.setNavigationOnClickListener(v -> {
            //What to do on back clicked
            onBackPressed();
        });
    }

    @Override
    public void onLocationChanged(Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
