package cemara.labschool.id.rumahcemara.auth.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cemara.labschool.id.rumahcemara.R;
import cemara.labschool.id.rumahcemara.auth.activity.LoginActivity;

public class SignUpActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    @BindView(R.id.genderSpinner) Spinner genderSpinner;
    @BindView(R.id.type_treatment_spinner) Spinner typeTreatmentSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);
        // Spinner click listener
        genderSpinner.setOnItemSelectedListener(this);
        typeTreatmentSpinner.setOnItemSelectedListener(this);

       settingGenderSpinner();
       settingTypeTreatmentSpinner();
    }

    private void settingGenderSpinner() {
        // Spinner Drop down elements
        List<String> gender = new ArrayList<String>();
        gender.add("Laki-laki");
        gender.add("Perempuan");
        gender.add("Lainnya");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.spinner_text, gender);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        genderSpinner.setAdapter(dataAdapter);
    }
    private void settingTypeTreatmentSpinner() {
        // Spinner Drop down elements
        List<String> typeTreatment = new ArrayList<String>();
        typeTreatment.add("Text1");
        typeTreatment.add("Text2");
        typeTreatment.add("Text3");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.spinner_text, typeTreatment);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        typeTreatmentSpinner.setAdapter(dataAdapter);
    }

    @OnClick(R.id.sign_in)
    public void toSignIn(){
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        String item = adapterView.getItemAtPosition(position).toString();
        Toast.makeText(adapterView.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
