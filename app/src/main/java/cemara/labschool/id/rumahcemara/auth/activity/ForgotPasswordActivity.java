package cemara.labschool.id.rumahcemara.auth.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.rezkyatinnov.kyandroid.reztrofit.ErrorResponse;
import com.rezkyatinnov.kyandroid.reztrofit.RestCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cemara.labschool.id.rumahcemara.R;
import cemara.labschool.id.rumahcemara.api.AuthHelper;
import cemara.labschool.id.rumahcemara.model.ApiResponse;
import cemara.labschool.id.rumahcemara.util.dialog.Loading;
import okhttp3.Headers;

public class ForgotPasswordActivity extends AppCompatActivity {

    private Context mContext;

    @BindView(R.id.email)
    AutoCompleteTextView email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        ButterKnife.bind(this);
        mContext = this;
    }

    @OnClick(R.id.btn_submit)
    public void doForgotPassword() {
        if (email.getText().toString().equals("")){
            email.setError("Email is required");
        } else {
            Loading.show(mContext);
            AuthHelper.forgotPassword(email.getText().toString(), new RestCallback<ApiResponse>() {
                @Override
                public void onSuccess(Headers headers, ApiResponse body) {
                    Loading.hide(mContext);
                    Toast.makeText(mContext, "Please Check your email to recover the password", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(mContext, RecoverPasswordActivity.class);
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
