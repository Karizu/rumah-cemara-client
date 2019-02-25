package cemara.labschool.id.rumahcemara.home.service.structural;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.rezkyatinnov.kyandroid.localdata.LocalData;
import com.rezkyatinnov.kyandroid.reztrofit.ErrorResponse;
import com.rezkyatinnov.kyandroid.reztrofit.RestCallback;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cemara.labschool.id.rumahcemara.MainActivity;
import cemara.labschool.id.rumahcemara.R;
import cemara.labschool.id.rumahcemara.api.AppointmentHelper;
import cemara.labschool.id.rumahcemara.auth.activity.LoginActivity;
import cemara.labschool.id.rumahcemara.model.ApiResponse;
import cemara.labschool.id.rumahcemara.model.Profile;
import cemara.labschool.id.rumahcemara.util.dialog.Loading;
import io.realm.Realm;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;

public class StructuralLegalAidActivity extends AppCompatActivity {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_img)
    ImageView toolbarImg;

    @BindView(R.id.edit_dsc_material)
    EditText description;

    @BindView(R.id.attached_file_doc)
    LinearLayout attachContainer;

    @BindView(R.id.fileName)
    TextView fileName;

    private File attachment;
    private Context mContext;
    private final int REQEUST_DOC = 1, REQUEST_GALLERY = 2;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.structural_legal_aid_activity);
        ButterKnife.bind(this);
        setToolbar();
        mContext = this;
        Realm realm = LocalData.getRealm();
        Profile profile = realm.where(Profile.class).findFirst();
        userId = profile.getUserId();

    }
    public void setToolbar() {
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        toolbar.setNavigationIcon(R.drawable.icon_back);
        toolbarTitle.setText("Structural-Legal Aid");
        toolbarImg.setImageResource(R.drawable.icon_structural_white);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //What to do on back clicked
                onBackPressed();
            }
        });
    }

    @OnClick(R.id.select_atached)
    public void selectFile(){
        final CharSequence[] options = {"Choose File", "Choose From Gallery","Cancel"};
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        builder.setTitle("Select Option");
        builder.setItems(options, (dialog, item) -> {
            if (options[item].equals("Choose File")) {
                EasyImage.openDocuments(this, REQEUST_DOC);
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
                attachment = imageFile;
                fileName.setText(imageFile.getName());
                attachContainer.setVisibility(View.VISIBLE);
            }
        });
    }

    @OnClick(R.id.remove_attachment)
    public void removeAttachment(){
        attachContainer.setVisibility(View.INVISIBLE);
        attachment = null;
    }

    @OnClick(R.id.btn_submit)
    public void send(){
        Loading.show(mContext);
        RequestBody requestBody;

        if (description.getText().toString().equals("")){
            Loading.hide(getApplicationContext());
            description.setError("This field is required");
        } else {
            if (attachment != null) {
                Bitmap bitmap = BitmapFactory.decodeFile(attachment.getAbsolutePath());

                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 10, byteArrayOutputStream);

                requestBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("group_id", "5431993f-239b-5349-b275-f587a9f7fdfc")
                        .addFormDataPart("user_id", userId)
                        .addFormDataPart("provider_id", "5431993f-239b-5349-b275-f587a9f7fdfc")
                        .addFormDataPart("service_type_id", "b0a7739e-49b2-59d2-a822-2ba758dea4cb")
                        .addFormDataPart("description", description.getText().toString())
                        .addFormDataPart("type_provider", "provider")
                        .addFormDataPart("attachment", "photo.jpeg", RequestBody.create(MediaType.parse("image/jpeg"), byteArrayOutputStream.toByteArray()))
                        .build();
            } else {
                requestBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("group_id", "5431993f-239b-5349-b275-f587a9f7fdfc")
                        .addFormDataPart("user_id", userId)
                        .addFormDataPart("provider_id", "5431993f-239b-5349-b275-f587a9f7fdfc")
                        .addFormDataPart("service_type_id", "b0a7739e-49b2-59d2-a822-2ba758dea4cb")
                        .addFormDataPart("description", description.getText().toString())
                        .addFormDataPart("type_provider", "provider")
                        .build();
            }

            AppointmentHelper.createBiomedicalAppointmentOutreach(requestBody, new RestCallback<ApiResponse>() {
                @Override
                public void onSuccess(Headers headers, ApiResponse body) {
                    Intent intent = new Intent(mContext, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }

                @Override
                public void onFailed(ErrorResponse error) {
                    Loading.hide(mContext);
                }

                @Override
                public void onCanceled() {
                    Loading.hide(mContext);
                }
            });
        }
    }
}
