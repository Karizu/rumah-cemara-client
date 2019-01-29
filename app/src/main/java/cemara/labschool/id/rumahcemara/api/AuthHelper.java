package cemara.labschool.id.rumahcemara.api;

import com.rezkyatinnov.kyandroid.reztrofit.RestCallback;
import com.rezkyatinnov.kyandroid.reztrofit.Reztrofit;

import cemara.labschool.id.rumahcemara.model.ApiResponse;
import cemara.labschool.id.rumahcemara.model.LoginRequest;
import cemara.labschool.id.rumahcemara.model.News;
import cemara.labschool.id.rumahcemara.model.Profile;
import cemara.labschool.id.rumahcemara.model.Token;
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

    public static void registerUserDevice(RequestBody userDeviceRequest, RestCallback<ApiResponse> callback){
        Reztrofit<ApiInterface> service = Reztrofit.getInstance();
        service.getEndpoint().postUserDevice(userDeviceRequest).enqueue(callback);
    }

    public static void updateProfile(RequestBody profile, RestCallback<ApiResponse> callback){
        Reztrofit<ApiInterface> service = Reztrofit.getInstance();
        service.getEndpoint().updateProfile(profile).enqueue(callback);
    }

    public static void myProfile(RestCallback<ApiResponse<User>> callback){
        Reztrofit<ApiInterface> service = Reztrofit.getInstance();
        service.getEndpoint().myProfile().enqueue(callback);
    }

    public static void generateToken(String userId, RestCallback<ApiResponse<Token>> callback){
        Reztrofit<ApiInterface> service = Reztrofit.getInstance();
        service.getEndpoint().generateToken(userId).enqueue(callback);
    }

    public static void forgotPassword(String email, RestCallback<ApiResponse> callback){
        Reztrofit<ApiInterface> service = Reztrofit.getInstance();
        service.getEndpoint().postForgotPassword(email).enqueue(callback);
    }

    public static void recoverPassword(String email, String number, String password, RestCallback<ApiResponse> callback){
        Reztrofit<ApiInterface> service = Reztrofit.getInstance();
        service.getEndpoint().postRecoverPassword(email, number, password).enqueue(callback);
    }


}
