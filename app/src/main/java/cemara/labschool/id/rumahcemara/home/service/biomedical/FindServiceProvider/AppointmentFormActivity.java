package cemara.labschool.id.rumahcemara.home.service.biomedical.FindServiceProvider;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.pm.LabeledIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.github.angads25.toggle.LabeledSwitch;
import com.github.angads25.toggle.interfaces.OnToggledListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import cemara.labschool.id.rumahcemara.R;

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
        setContentView(R.layout.appointment_form_activity);
        ButterKnife.bind(this);
        setToolbar();
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
}
