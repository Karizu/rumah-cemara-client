package cemara.labschool.id.rumahcemara.home.service.structural;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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

public class StructuralViolationActivity extends AppCompatActivity {

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
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.structural_violation_activity);
        ButterKnife.bind(this);
        setToolbar();
        mContext = this;
        Realm realm = LocalData.getRealm();
        Profile profile = realm.where(Profile.class).findFirst();
        userId = profile.getUserId();
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    public void setToolbar() {
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        toolbar.setNavigationIcon(R.drawable.icon_back);
        toolbarTitle.setText("Structural-Violation Report");
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

        if (description.getText().toString().equals("") || attachment == null){
            Loading.hide(getApplicationContext());
            Toast.makeText(mContext, "Silahkan lengkapi formulir", Toast.LENGTH_SHORT).show();
        } else {
            Bitmap bitmap = BitmapFactory.decodeFile(attachment.getAbsolutePath());

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 10, byteArrayOutputStream);

            requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("group_id", "752637ba-6ec2-5092-b796-c971a1c45d53")
                    .addFormDataPart("user_id", userId)
                    .addFormDataPart("provider_id", "752637ba-6ec2-5092-b796-c971a1c45d53")
                    .addFormDataPart("service_type_id", "259d7e03-c7a7-5706-bc4d-12ddb924b6ca")
                    .addFormDataPart("description", description.getText().toString())
                    .addFormDataPart("type_provider", "provider")
                    .addFormDataPart("attachment", "photo.jpeg", RequestBody.create(MediaType.parse("image/jpeg"), byteArrayOutputStream.toByteArray()))
                    .build();

            AppointmentHelper.createBiomedicalAppointmentOutreach(requestBody, new RestCallback<ApiResponse>() {
                @Override
                public void onSuccess(Headers headers, ApiResponse body) {
                    Loading.hide(mContext);
                    showDialogAlert(R.layout.dialog_appointment_success);
                    TextView gomylist = dialog.findViewById(R.id.appointment_gotomylist);
                    TextView ok = dialog.findViewById(R.id.appointment_ok);
                    gomylist.setVisibility(View.GONE);
                    ok.setOnClickListener(view -> {
                        Intent intent = new Intent(StructuralViolationActivity.this, MainActivity.class);
                        intent.putExtra("frag", "homeFragment");
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    });
                }

                @Override
                public void onFailed(ErrorResponse error) {
                    Loading.hide(mContext);
                    showDialogAlert(R.layout.dialog_appointment_failed);
                    TextView retry = dialog.findViewById(R.id.appointment_retry);
                    TextView ok = dialog.findViewById(R.id.appointment_ok);
                    retry.setOnClickListener(view -> send());
                    ok.setOnClickListener(view -> dialog.dismiss());
                }

                @Override
                public void onCanceled() {
                    Loading.hide(mContext);
                }
            });
        }
    }

    private void showDialogAlert(int layout) {
        dialog = new Dialog(this);
        //SET TITLE
        dialog.setTitle("");

        //set content
        dialog.setContentView(layout);
        dialog.setCanceledOnTouchOutside(false);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(Objects.requireNonNull(dialog.getWindow()).getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }
}
