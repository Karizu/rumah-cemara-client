package cemara.labschool.id.rumahcemara.home.service.biomedical.FindOutreachWorker;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.rezkyatinnov.kyandroid.reztrofit.ErrorResponse;
import com.rezkyatinnov.kyandroid.reztrofit.RestCallback;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cemara.labschool.id.rumahcemara.R;
import cemara.labschool.id.rumahcemara.api.AppointmentHelper;
import cemara.labschool.id.rumahcemara.model.ApiResponse;
import okhttp3.Headers;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class AppointmentFormOutreachActivity extends AppCompatActivity {

    @BindView(R.id.thistoolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_img)
    ImageView toolbarImg;
    @BindView(R.id.description_material)
    EditText descriptionMaterial;
    final Calendar myCalendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener date = (datePicker, year, month, day) -> {
        myCalendar.set(Calendar.YEAR, year);
        myCalendar.set(Calendar.MONTH, month);
        myCalendar.set(Calendar.DAY_OF_MONTH, day);
    };
    private EditText updateLabel(EditText date) {
        String myFormat = "yyyy/MM/dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        date.setText(sdf.format(myCalendar.getTime()));
        return date;
    }


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_form_outreach);

        ButterKnife.bind(this);
        setToolbar();

//        populateData();

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

//    private void populateData(){
//        Bundle bundle = getIntent().getBundleExtra("myData");   //<< get Bundle from Intent
//
//        String id = bundle.getString("id");
//        user_id = bundle.getString("user_id");
//        String imgUrl = bundle.getString("imgUrl");
//        String fullName = bundle.getString("fullname");
//        String address = bundle.getString("address");
//        String phoneNumber = bundle.getString("phone");
//        groupId = bundle.getString("group_id");
//        workerId = bundle.getString("worker_id");
//
//        TextView tvName = findViewById(R.id.appointment_name);
//        ImageView imageProfile = findViewById(R.id.imgProfile);
//        TextView tvAddress = findViewById(R.id.appointment_address);
//        TextView tvPhoneNumber = findViewById(R.id.appointment_telp);
//
//        tvName.setText(fullName);
//        Glide.with(this).applyDefaultRequestOptions(new RequestOptions().placeholder(R.drawable.select_dp)).load(imgUrl).into(imageProfile);
//        tvAddress.setText(address);
//        tvPhoneNumber.setText(phoneNumber);
//    }

    @OnClick(R.id.appointment_date_start)
    public void openCalenderDateStart(){
        new DatePickerDialog(this, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
        updateLabel(findViewById(R.id.appointment_date_start));
    }
    @OnClick(R.id.appointment_date_end)
    public void openCalenderDateEnd(){
        new DatePickerDialog(this, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
        updateLabel(findViewById(R.id.appointment_date_end));
    }
    public void setToolbar() {
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        toolbar.setNavigationIcon(R.drawable.icon_back);
        toolbarTitle.setText("Biomedical");
        toolbarImg.setImageResource(R.drawable.icon_biomedical_white);
        toolbar.setNavigationOnClickListener(v -> {
            //What to do on back clicked
            onBackPressed();
        });
    }
}
