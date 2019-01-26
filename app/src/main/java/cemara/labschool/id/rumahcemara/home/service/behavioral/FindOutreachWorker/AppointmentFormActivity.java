package cemara.labschool.id.rumahcemara.home.service.behavioral.FindOutreachWorker;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cemara.labschool.id.rumahcemara.R;
import cemara.labschool.id.rumahcemara.util.location.SetLocationActivity;

public class AppointmentFormActivity extends AppCompatActivity {


    @BindView(R.id.thistoolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_img)
    ImageView toolbarImg;
    @BindView(R.id.appointment_material_description)
    EditText descriptionMaterial;
    @BindView(R.id.appointment_location)
    AutoCompleteTextView appointmentLocation;
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
        setContentView(R.layout.find_outreach_appointment_form_activity);
        ButterKnife.bind(this);
        setToolbar();
        descriptionMaterial.setOnTouchListener((v, event) -> {
            if (descriptionMaterial.hasFocus()) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                switch (event.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_SCROLL:
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        return true;
                }
            }
            return false;
        });

        // Get the string array
        String[] testArray = getResources().getStringArray(R.array.test_array);
        // Create the adapter and set it to the AutoCompleteTextView
        ArrayAdapter<String> adapterLocation =
                new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, testArray);
        appointmentLocation.setAdapter(adapterLocation);
    }


    @OnClick(R.id.appointment_date_start)
    public void openCalenderDateStart() {
        changeTo = findViewById(R.id.appointment_date_start);
        new DatePickerDialog(this, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    @OnClick(R.id.appointment_date_end)
    public void openCalenderDateEnd() {
        changeTo = findViewById(R.id.appointment_date_end);
        new DatePickerDialog(this, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }
    @OnClick(R.id.appointment_getlocation)
    public void getLocation(){
        Intent intent = new Intent(this, SetLocationActivity.class);
        startActivityForResult(intent,1);
    }
    public void setToolbar() {
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        toolbar.setNavigationIcon(R.drawable.icon_back);
        toolbarTitle.setText("Appointment Form");
        toolbarImg.setImageResource(R.drawable.icon_biomedical_white);
        toolbar.setNavigationOnClickListener(v -> {
            //What to do on back clicked
            onBackPressed();
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                String result=data.getStringExtra("result");
                Toast.makeText(this, String.valueOf(result), Toast.LENGTH_SHORT).show();
                appointmentLocation.setText(result);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(this, "Cancel", Toast.LENGTH_SHORT).show();
                //Write your code if there's no result
            }
        }
    }//onActivityResult
}
