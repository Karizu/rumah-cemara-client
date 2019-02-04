package cemara.labschool.id.rumahcemara.mylist.fragment.activity;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.rezkyatinnov.kyandroid.localdata.LocalData;
import com.rezkyatinnov.kyandroid.reztrofit.ErrorResponse;
import com.rezkyatinnov.kyandroid.reztrofit.RestCallback;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cemara.labschool.id.rumahcemara.MainActivity;
import cemara.labschool.id.rumahcemara.R;
import cemara.labschool.id.rumahcemara.api.ApiInterface;
import cemara.labschool.id.rumahcemara.api.AppointmentHelper;
import cemara.labschool.id.rumahcemara.home.service.biomedical.FindOutreachWorker.config.CircleTransform;
import cemara.labschool.id.rumahcemara.model.ApiResponse;
import cemara.labschool.id.rumahcemara.model.User;
import cemara.labschool.id.rumahcemara.util.dialog.Loading;
import io.realm.Realm;
import okhttp3.Headers;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class DetailHistoryAppointment extends AppCompatActivity {

    @BindView(R.id.ic_close)
    ImageView imgClose;
    @BindView(R.id.ratingBar)
    RatingBar rating;
    @BindView(R.id.txt_komentar)
    EditText komentar;
    @BindView(R.id.btn_rate)
    Button btnRate;

    String ratingValue, id, providerId, user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_list_appointment_history_dialog);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        ButterKnife.bind(this);

        Realm realm = LocalData.getRealm();
        User user = realm.where(User.class).findFirst();
        user_id = user.getId();

        populateData();

        rating.setOnRatingBarChangeListener((ratingBar1, v, b) -> {
            switch ((int) ratingBar1.getRating()) {
                case 1:
                    ratingValue = "1";
                    break;
                case 2:
                    ratingValue = "2";
                    break;
                case 3:
                    ratingValue = "3";
                    break;
                case 4:
                    ratingValue = "4";
                    break;
                case 5:
                    ratingValue = "5";
                    break;
                default:
            }
        });

        imgClose.setOnClickListener(view -> onBackPressed());
    }

    private void populateData() {
        Bundle bundle = getIntent().getBundleExtra("myData");   //<< get Bundle from Intent

        id = bundle.getString("id");
        String imgUrl = bundle.getString("srcImg");
        String fullName = bundle.getString("name");
        String Date = bundle.getString("date");
        String phoneNumber = bundle.getString("phone");
        String serviceNamed = bundle.getString("serviceName");
        providerId = bundle.getString("providerId");

        TextView phone = findViewById(R.id.tvPhone);
        TextView name = findViewById(R.id.tvName);
        TextView date = findViewById(R.id.tvDate);
        TextView serviceName = findViewById(R.id.tvServiceName);
        ImageView imgProfile = findViewById(R.id.img_user);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.ENGLISH);
        try {
            java.util.Date dates = sdf.parse(Date);
            String formated = new SimpleDateFormat("EEEE, dd MMM yyyy").format(dates);
            date.setText(formated);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        name.setText(fullName);
        Picasso.get().load(imgUrl).transform(new CircleTransform()).into(imgProfile);
//        Glide.with(this).applyDefaultRequestOptions(new RequestOptions().placeholder(R.drawable.select_dp)).load(imgUrl).into(imageProfile);
        serviceName.setText(serviceNamed);
        phone.setText(phoneNumber);
    }

    @OnClick(R.id.btn_rate)
    void createRateAppointment() {
        Loading.show(this);
        if (komentar.getText().toString().isEmpty()) {

            Loading.hide(this);
            Toast.makeText(DetailHistoryAppointment.this, "Please fill in feedback text box", Toast.LENGTH_LONG).show();

        } else {

            RequestBody requestBody;
            requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("service_transaction_id", id)
                    .addFormDataPart("user_id", user_id)
                    .addFormDataPart("provider_id", providerId)
                    .addFormDataPart("type", "provider")
                    .addFormDataPart("rating", ratingValue)
                    .addFormDataPart("description", komentar.getText().toString())
                    .build();

            AppointmentHelper.createAppointmentRating(requestBody, new RestCallback<ApiResponse>() {
                @Override
                public void onSuccess(Headers headers, ApiResponse body) {
                    Loading.hide(getApplicationContext());
                    Toast.makeText(DetailHistoryAppointment.this, "Thank you for sharing your feedback", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(DetailHistoryAppointment.this, MainActivity.class);
                    intent.putExtra("frag", "homeFragment");
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }

                @Override
                public void onFailed(ErrorResponse error) {
                    Loading.hide(getApplicationContext());
                    Log.d("onFailed", error.getMessage());
                }

                @Override
                public void onCanceled() {
                    Loading.hide(getApplicationContext());
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
