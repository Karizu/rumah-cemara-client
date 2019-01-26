package cemara.labschool.id.rumahcemara.options.fragment.activity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.rezkyatinnov.kyandroid.localdata.LocalData;
import com.rezkyatinnov.kyandroid.reztrofit.ErrorResponse;
import com.rezkyatinnov.kyandroid.reztrofit.RestCallback;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cemara.labschool.id.rumahcemara.R;
import cemara.labschool.id.rumahcemara.api.AuthHelper;
import cemara.labschool.id.rumahcemara.auth.activity.LoginActivity;
import cemara.labschool.id.rumahcemara.auth.activity.SignUpActivity;
import cemara.labschool.id.rumahcemara.model.ApiResponse;
import cemara.labschool.id.rumahcemara.model.Profile;
import cemara.labschool.id.rumahcemara.model.User;
import cemara.labschool.id.rumahcemara.util.dialog.Loading;
import io.realm.Realm;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;

public class EditAccountActivity extends AppCompatActivity {

    @BindView (R.id.toolbar)
    Toolbar toolbar;
    @BindView (R.id.text_toolbar_title)
    TextView toolbarTitle;

    @BindView(R.id.img_profile)
    ImageView imgProfile;

    @BindView(R.id.nama)
    EditText fullname;

    @BindView(R.id.phonenumber)
    EditText phoneNumber;

    @BindView(R.id.no_ktp)
    EditText identityNumber;

    @BindView(R.id.birthDate)
    EditText birthDate;

    @BindView(R.id.email)
    EditText email;

    @BindView(R.id.address)
    EditText address;

    @BindView(R.id.city)
    EditText city;

    private final int REQEUST_CAMERA = 1, REQUEST_GALLERY = 2;
    private Context mContext;
    private File profileImage;

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
        setContentView(R.layout.activity_edit_account);
        ButterKnife.bind(this);
        setToolbar();

        mContext = this;

        init();
    }

    private void init() {
        AuthHelper.myProfile(new RestCallback<ApiResponse<User>>() {
            @Override
            public void onSuccess(Headers headers, ApiResponse<User> body) {
                if (body.isStatus() && body.getData() != null) {
                    Glide.with(mContext)
                            .load(body.getData().getProfile().getPicture())
                            .apply(RequestOptions.circleCropTransform())
                            .into(imgProfile);

                    fullname.setText(body.getData().getProfile().getFullname());
                    email.setText(body.getData().getProfile().getEmail());
                    phoneNumber.setText(body.getData().getProfile().getPhoneNumber());
                    identityNumber.setText(body.getData().getProfile().getIdentityCard());
                    birthDate.setText(body.getData().getProfile().getBirthDate());
                    address.setText(body.getData().getProfile().getAddress());
                    city.setText(body.getData().getProfile().getCity());
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

    public void setToolbar() {
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        toolbar.setNavigationIcon(R.drawable.icon_back);
        toolbarTitle.setText("Edit Account");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //What to do on back clicked
                onBackPressed();
            }
        });
    }

    @OnClick(R.id.birthDate)
    public void setBirthDate(){
        new DatePickerDialog(this, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    @OnClick(R.id.img_profile)
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
                        .into(imgProfile);
                profileImage = imageFile;
            }
        });
    }

    @OnClick(R.id.save)
    public void doUpdateProfile(){
        RequestBody requestBody;
        Loading.show(EditAccountActivity.this);
        if (profileImage != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(profileImage.getAbsolutePath());

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 20, byteArrayOutputStream);

            requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("_method", "PUT")
                    .addFormDataPart("identity_card", identityNumber.getText().toString())
                    .addFormDataPart("email", email.getText().toString())
                    .addFormDataPart("birth_date", birthDate.getText().toString())
                    .addFormDataPart("fullname", fullname.getText().toString())
                    .addFormDataPart("address", address.getText().toString())
                    .addFormDataPart("city", city.getText().toString())
                    .addFormDataPart("phone_number", phoneNumber.getText().toString())
                    .addFormDataPart("picture", "photo.jpeg", RequestBody.create(MediaType.parse("image/jpeg"), byteArrayOutputStream.toByteArray()))
                    .build();
        } else {
            requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("_method", "PUT")
                    .addFormDataPart("identity_card", identityNumber.getText().toString())
                    .addFormDataPart("email", email.getText().toString())
                    .addFormDataPart("birth_date", birthDate.getText().toString())
                    .addFormDataPart("fullname", fullname.getText().toString())
                    .addFormDataPart("address", address.getText().toString())
                    .addFormDataPart("city", city.getText().toString())
                    .addFormDataPart("phone_number", phoneNumber.getText().toString())
                    .build();
        }

        AuthHelper.updateProfile(requestBody, new RestCallback<ApiResponse>() {
            @Override
            public void onSuccess(Headers headers, ApiResponse body) {
                finish();
            }

            @Override
            public void onFailed(ErrorResponse error) {

            }

            @Override
            public void onCanceled() {

            }
        });
    }
}
