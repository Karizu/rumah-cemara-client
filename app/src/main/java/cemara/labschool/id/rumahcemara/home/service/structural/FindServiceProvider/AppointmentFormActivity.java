package cemara.labschool.id.rumahcemara.home.service.structural.FindServiceProvider;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.angads25.toggle.LabeledSwitch;
import com.rezkyatinnov.kyandroid.localdata.LocalData;
import com.rezkyatinnov.kyandroid.reztrofit.ErrorResponse;
import com.rezkyatinnov.kyandroid.reztrofit.RestCallback;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
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
import io.realm.Realm;
import okhttp3.Headers;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class AppointmentFormActivity extends AppCompatActivity {


    @BindView(R.id.thistoolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_img)
    ImageView toolbarImg;
    @BindView(R.id.appointment_switch)
    LabeledSwitch apponintmentSwitch;
    @BindView(R.id.appointment_worker_name)
    EditText appointmentWorkerName;
    @BindView(R.id.description_material)
    EditText descriptionMaterial;
    EditText changeTo;
    Dialog dialog;
    String user_id, groupId, workerId;
    String typeProvider = "provider";
    String serviceTypeId = "17c00365-4987-5f1e-925b-2119fbe5ff8c";
    String startDate = "2019-01-26";
    String endDate = "2019-01-28";
    EditText dateStart, dateEnd;
    final Calendar myCalendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener date = (datePicker, year, month, day) -> {
        myCalendar.set(Calendar.YEAR, year);
        myCalendar.set(Calendar.MONTH, month);
        myCalendar.set(Calendar.DAY_OF_MONTH, day);
        updateLabel(changeTo);
    };
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

        populateData();

        apponintmentSwitch.setOnToggledListener((labeledSwitch, isOn) -> {
            if(isOn){
                appointmentWorkerName.setEnabled(false);
            }else {
                appointmentWorkerName.setEnabled(true);
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

    @OnClick(R.id.btn_send_appointment_form)
    void createAppointment(){

        startDate = ((EditText)findViewById(R.id.appointment_date_start)).getText().toString();
        endDate = ((EditText)findViewById(R.id.appointment_date_end)).getText().toString();
        RequestBody requestBody;
        requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("group_id", groupId)
                .addFormDataPart("user_id", user_id)
                .addFormDataPart("provider_id", groupId)
                .addFormDataPart("service_type_id", serviceTypeId)
                .addFormDataPart("start_date", startDate)
                .addFormDataPart("end_date", endDate)
                .addFormDataPart("description", descriptionMaterial.getText().toString())
                .addFormDataPart("type_provider", typeProvider)
                .build();

        AppointmentHelper.createBiomedicalAppointmentOutreach(requestBody, new RestCallback<ApiResponse>() {
            @Override
            public void onSuccess(Headers headers, ApiResponse body) {
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
                Log.d(error.toString(), "Error");
                showDialogAlert(R.layout.dialog_appointment_failed);
                TextView retry = dialog.findViewById(R.id.appointment_retry);
                TextView ok = dialog.findViewById(R.id.appointment_ok);
                retry.setOnClickListener(view -> dialog.findViewById(R.id.btn_send_appointment).callOnClick());
                ok.setOnClickListener(view -> dialog.dismiss());
            }

            @Override
            public void onCanceled() {

            }
        });
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
//    boolean i = false;
//    @OnClick(R.id.btn_send_appointment)
//    public void sendAppointment(){
//        if(i){
//            showDialogAlert(R.layout.dialog_appointment_success);
//            TextView gomylist = dialog.findViewById(R.id.appointment_gotomylist);
//            TextView ok = dialog.findViewById(R.id.appointment_ok);
//            gomylist.setOnClickListener(view -> {
//                Intent intent = new Intent(this, MainActivity.class);
//                intent.putExtra("frag", "mylistfragment");
//                startActivity(intent);
//            });
//            ok.setOnClickListener(view -> finish());
//            i = false;
//        }else{
//            showDialogAlert(R.layout.dialog_appointment_failed);
//            TextView retry = dialog.findViewById(R.id.appointment_retry);
//            TextView ok = dialog.findViewById(R.id.appointment_ok);
//            retry.setOnClickListener(view -> dialog.findViewById(R.id.btn_send_appointment).callOnClick());
//            ok.setOnClickListener(view -> dialog.dismiss());
//            i = true;
//        }
//    }

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
        toolbarTitle.setText("Appointment Form");
        toolbarImg.setImageResource(R.drawable.icon_structural_white);
        toolbar.setNavigationOnClickListener(v -> {
            //What to do on back clicked
            onBackPressed();
        });
    }

}
