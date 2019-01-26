package cemara.labschool.id.rumahcemara.api;

import com.rezkyatinnov.kyandroid.reztrofit.RestCallback;
import com.rezkyatinnov.kyandroid.reztrofit.Reztrofit;

import cemara.labschool.id.rumahcemara.model.ApiResponse;
import cemara.labschool.id.rumahcemara.model.LoginRequest;
import cemara.labschool.id.rumahcemara.model.User;
import okhttp3.RequestBody;

public class AuthHelper {

    public static void register(RequestBody registerRequest, RestCallback<ApiResponse> callback){
        Reztrofit<ApiInterface> service = Reztrofit.getInstance();
        service.getEndpoint().postRegister(registerRequest).enqueue(callback);
    }

    public static void login(LoginRequest loginRequest, RestCallback<ApiResponse<User>> callback){
        Reztrofit<ApiInterface> service = Reztrofit.getInstance();
        service.getEndpoint().postLogin(loginRequest).enqueue(callback);
    }

}
