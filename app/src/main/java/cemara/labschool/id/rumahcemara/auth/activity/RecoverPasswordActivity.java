package cemara.labschool.id.rumahcemara.auth.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Toast;

import com.rezkyatinnov.kyandroid.reztrofit.ErrorResponse;
import com.rezkyatinnov.kyandroid.reztrofit.RestCallback;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cemara.labschool.id.rumahcemara.R;
import cemara.labschool.id.rumahcemara.api.AuthHelper;
import cemara.labschool.id.rumahcemara.model.ApiResponse;
import cemara.labschool.id.rumahcemara.util.dialog.Loading;
import okhttp3.Headers;

public class RecoverPasswordActivity extends AppCompatActivity {

    private Context mContext;

    @BindView(R.id.emailTextInputEditText)
    TextInputEditText email;

    @BindView(R.id.numberTextInputEditText)
    TextInputEditText number;

    @BindView(R.id.passwordTextInputEditText)
    TextInputEditText newPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recover_password);
        ButterKnife.bind(this);
        mContext = this;
    }

    private void validationData(){
        if (email.getText().toString().equals("")){
            email.setError("Email is required");
        }
        if (number.getText().toString().equals("")){
            number.setError("Number is required");
        }
        if (newPassword.getText().toString().equals("")){
            newPassword.setError("New password is required");
        }
    }

    @OnClick(R.id.recover_btn)
    public void doRecover() {
        if (email.getText().toString().equals("")
        || number.getText().toString().equals("")
        || newPassword.getText().toString().equals("")) {
            Toast.makeText(mContext, "Please fill all data", Toast.LENGTH_LONG).show();
        } else {
            Loading.show(mContext);
            AuthHelper.recoverPassword(email.getText().toString(), number.getText().toString(),
                    newPassword.getText().toString(), new RestCallback<ApiResponse>() {
                        @Override
                        public void onSuccess(Headers headers, ApiResponse body) {
                            Loading.hide(mContext);
                            Toast.makeText(mContext, "Recover Password Success", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(mContext, LoginActivity.class);
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
