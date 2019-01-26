package cemara.labschool.id.rumahcemara.auth.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
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
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;

public class SignUpActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private final int REQEUST_CAMERA = 1, REQUEST_GALLERY = 2;
    private Context mContext;
    private List<String> treatmentId;
    private ArrayList<String> dataTreatment;
    private File profileImage;


    @BindView(R.id.profilePictureImageView)
    ImageView profilePicture;

    @BindView(R.id.genderSpinner)
    Spinner genderSpinner;

    @BindView(R.id.type_treatment_spinner)
    Spinner typeTreatmentSpinner;

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
                    }

                    // Creating adapter for spinner
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(mContext, R.layout.spinner_text, dataTreatment);

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

    @OnClick(R.id.email_sign_in_button)
    public void doSignUp(){
        Bitmap bitmap = BitmapFactory.decodeFile(profileImage.getAbsolutePath());

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 20, byteArrayOutputStream);

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("username", email.getText().toString())
                .addFormDataPart("email", email.getText().toString())
                .addFormDataPart("birth_date", birthDate.getText().toString())
                .addFormDataPart("fullname", name.getText().toString())
                .addFormDataPart("password", password.getText().toString())
                .addFormDataPart("gender", genderSpinner.getSelectedItem().toString())
                .addFormDataPart("treatment_id", treatmentId.get(typeTreatmentSpinner.getSelectedItemPosition()))
                .addFormDataPart("picture", "photo.jpeg", RequestBody.create(MediaType.parse("image/jpeg"), byteArrayOutputStream.toByteArray()))
                .build();

        AuthHelper.register(requestBody, new RestCallback<ApiResponse>() {
            @Override
            public void onSuccess(Headers headers, ApiResponse body) {
                Intent intent = new Intent(mContext, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
            }

            @Override
            public void onFailed(ErrorResponse error) {

            }

            @Override
            public void onCanceled() {

            }
        });
    }

    @OnClick(R.id.birthDateTextInputEditText)
    public void setBirthDate(){
        new DatePickerDialog(this, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
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
}
