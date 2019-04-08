package cemara.labschool.id.rumahcemara.auth.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.rezkyatinnov.kyandroid.reztrofit.ErrorResponse;
import com.rezkyatinnov.kyandroid.reztrofit.RestCallback;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cemara.labschool.id.rumahcemara.R;
import cemara.labschool.id.rumahcemara.api.ApiInterface;
import cemara.labschool.id.rumahcemara.api.AuthHelper;
import cemara.labschool.id.rumahcemara.api.TreatmentHelper;
import cemara.labschool.id.rumahcemara.auth.activity.LoginActivity;
import cemara.labschool.id.rumahcemara.model.ApiResponse;
import cemara.labschool.id.rumahcemara.model.Treatment;
import cemara.labschool.id.rumahcemara.util.dialog.Loading;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;

public class SignUpActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private final int REQEUST_CAMERA = 1, REQUEST_GALLERY = 2;
    private final int MY_PERMISSIONS_REQUEST_CAMERA = 100;
    private Context mContext;
    private List<String> treatmentId;
    private ArrayList<String> dataTreatment;
    private List<String> institutionId;
    private ArrayList<String> dataInstitution;
    private File profileImage;
    private Calendar calendar;
    private Dialog dialog;


    @BindView(R.id.profilePictureImageView)
    ImageView profilePicture;

    @BindView(R.id.genderSpinner)
    Spinner genderSpinner;

    @BindView(R.id.type_treatment_spinner)
    Spinner typeTreatmentSpinner;

    @BindView(R.id.type_institution_spinner)
    Spinner typeInstitutionSpinner;

    @BindView(R.id.nameTextInputEditText)
    TextInputEditText name;

    @BindView(R.id.birthDateTextInputEditText)
    TextInputEditText birthDate;

    @BindView(R.id.emailTextInputEditText)
    TextInputEditText email;

    @BindView(R.id.passwordTextInputEditText)
    TextInputEditText password;

    final Calendar myCalendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener date = (datePicker, year, month, day) -> {
        myCalendar.set(Calendar.YEAR, year);
        myCalendar.set(Calendar.MONTH, month);
        myCalendar.set(Calendar.DAY_OF_MONTH, day);
        updateLabel();
    };
    private void updateLabel() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        birthDate.setText(sdf.format(myCalendar.getTime()));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);
        mContext = this;

        ConnectivityManager conMgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if ( conMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED
                || conMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

        } else {
            showDialogAlert(R.layout.dialog_bad_connection);
            TextView ok = dialog.findViewById(R.id.appointment_ok);
            ok.setOnClickListener(view -> onBackPressed());
        }

        genderSpinner.setOnItemSelectedListener(this);
        typeTreatmentSpinner.setOnItemSelectedListener(this);
        typeInstitutionSpinner.setOnItemSelectedListener(this);

        settingGenderSpinner();
        settingTypeTreatmentSpinner();
        settingTypeInstitutionSpinner();

        calendar = Calendar.getInstance();
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

    @Override
    protected void onStart() {
        super.onStart();
        checkPermissionGrant();
    }

    final DatePickerDialog.OnDateSetListener updateDate = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, monthOfYear);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }
    };

    private void settingGenderSpinner() {
        // Spinner Drop down elements
        List<String> gender = new ArrayList<String>();
        gender.add("Laki-laki");
        gender.add("Perempuan");
        gender.add("Lainnya");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, gender);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        genderSpinner.setAdapter(dataAdapter);
    }

    private void settingTypeTreatmentSpinner() {
        // Spinner Drop down elements

        TreatmentHelper.getAllTreatment(new RestCallback<ApiResponse<List<Treatment>>>() {
            @Override
            public void onSuccess(Headers headers, ApiResponse<List<Treatment>> body) {
                if (body != null && body.isStatus()){
                    dataTreatment = new ArrayList();
                    treatmentId = new ArrayList();
                    List<Treatment> data = body.getData();
                    for (int i = 0; i < data.size(); i++){
                        dataTreatment.add(data.get(i).getName());
                        treatmentId.add(data.get(i).getId());
                        Log.d("Treatment"+" "+i, data.get(i).getName());
                    }

                    // Creating adapter for spinner
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(mContext, R.layout.support_simple_spinner_dropdown_item, dataTreatment);

                    // Drop down layout style - list view with radio button
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    // attaching data adapter to spinner
                    typeTreatmentSpinner.setAdapter(dataAdapter);
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

    private void settingTypeInstitutionSpinner() {
        // Spinner Drop down elements

        TreatmentHelper.getAllInstitution(new RestCallback<ApiResponse<List<Treatment>>>() {
            @Override
            public void onSuccess(Headers headers, ApiResponse<List<Treatment>> body) {
                if (body != null && body.isStatus()){
                    dataInstitution = new ArrayList();
                    institutionId = new ArrayList();
                    List<Treatment> data = body.getData();
                    for (int i = 0; i < data.size(); i++){
                        dataInstitution.add(data.get(i).getName());
                        institutionId.add(data.get(i).getId());
                        Log.d("Treatment"+" "+i, data.get(i).getName());
                    }

                    // Creating adapter for spinner
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(mContext, R.layout.support_simple_spinner_dropdown_item, dataInstitution);

                    // Drop down layout style - list view with radio button
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    // attaching data adapter to spinner
                    typeInstitutionSpinner.setAdapter(dataAdapter);
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

    private void validationData(){
        if (email.getText().toString().equals("")){
            email.setError("Email is required");
        }
        if (birthDate.getText().toString().equals("")){
            birthDate.setError("Birth date is required");
        }
        if (name.getText().toString().equals("")){
            name.setError("Name is required");
        }
        if (password.getText().toString().equals("")){
            password.setError("Password is required");
        }
    }

    @OnClick(R.id.email_sign_in_button)
    public void doSignUp(){
        if (email.getText().toString().equals("")
                || birthDate.getText().toString().equals("")
                || name.getText().toString().equals("")
                || password.getText().toString().equals("")
                || dataTreatment.size() == 0) {
            Toast.makeText(mContext, "Please fill all data", Toast.LENGTH_LONG).show();
        } else {
            Loading.show(SignUpActivity.this);
            RequestBody requestBody;

            if (profileImage != null) {
                Bitmap bitmap = BitmapFactory.decodeFile(profileImage.getAbsolutePath());

                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 20, byteArrayOutputStream);

                requestBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("username", email.getText().toString())
                        .addFormDataPart("email", email.getText().toString())
                        .addFormDataPart("birth_date", birthDate.getText().toString())
                        .addFormDataPart("fullname", name.getText().toString())
                        .addFormDataPart("password", password.getText().toString())
                        .addFormDataPart("gender", genderSpinner.getSelectedItem().toString())
                        .addFormDataPart("treatment_id", treatmentId.get(typeTreatmentSpinner.getSelectedItemPosition()))
                        .addFormDataPart("group_id", institutionId.get(typeInstitutionSpinner.getSelectedItemPosition()))
                        .addFormDataPart("type", "client")
                        .addFormDataPart("picture", "photo.jpeg", RequestBody.create(MediaType.parse("image/jpeg"), byteArrayOutputStream.toByteArray()))
                        .build();
            } else {
                requestBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("username", email.getText().toString())
                        .addFormDataPart("email", email.getText().toString())
                        .addFormDataPart("birth_date", birthDate.getText().toString())
                        .addFormDataPart("fullname", name.getText().toString())
                        .addFormDataPart("password", password.getText().toString())
                        .addFormDataPart("gender", genderSpinner.getSelectedItem().toString())
                        .addFormDataPart("treatment_id", treatmentId.get(typeTreatmentSpinner.getSelectedItemPosition()))
                        .addFormDataPart("group_id", institutionId.get(typeInstitutionSpinner.getSelectedItemPosition()))
                        .addFormDataPart("type", "client")
                        .build();
            }

            AuthHelper.register(requestBody, new RestCallback<ApiResponse>() {
                @Override
                public void onSuccess(Headers headers, ApiResponse body) {
                    Loading.hide(getApplicationContext());
                    Toast.makeText(mContext, "Thanks for signing up! Please login first", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(mContext, LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }

                @Override
                public void onFailed(ErrorResponse error) {
                    Loading.hide(getApplicationContext());
                    Log.d("Error Register", error.getDescription());
                    Toast.makeText(mContext, error.getHttpStatus().getReasonPhrase(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCanceled() {
                    Loading.hide(getApplicationContext());
                }
            });
        }
    }

    @OnClick(R.id.birthDateTextInputEditText)
    public void setBirthDate(){
        DatePickerDialog dialog = new DatePickerDialog(this, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH));
        dialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        dialog.show();
    }

    @OnClick(R.id.sign_in)
    public void toSignIn(){
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }

    @OnClick(R.id.profilePictureImageView)
    public void setProfilePicture() {
        selectImage();
    }

    private void selectImage() {
        final CharSequence[] options = {"Take Photo", "Choose From Gallery","Cancel"};
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        builder.setTitle("Select Option");
        builder.setItems(options, (dialog, item) -> {
            if (options[item].equals("Take Photo")) {
                EasyImage.openCamera(this, REQEUST_CAMERA);
            } else if (options[item].equals("Choose From Gallery")) {
                EasyImage.openGallery(this, REQUEST_GALLERY);
            } else if (options[item].equals("Cancel")) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        EasyImage.handleActivityResult(requestCode, resultCode, data, this, new DefaultCallback() {
            @Override
            public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
                //Some error handling
            }

            @Override
            public void onImagePicked(File imageFile, EasyImage.ImageSource source, int type) {
                Glide.with(mContext)
                        .load(imageFile)
                        .apply(RequestOptions.circleCropTransform())
                        .into(profilePicture);
                profileImage = imageFile;
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void checkPermissionGrant(){
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.CAMERA)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA},
                        MY_PERMISSIONS_REQUEST_CAMERA);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CAMERA: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
}
