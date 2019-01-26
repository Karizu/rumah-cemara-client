package cemara.labschool.id.rumahcemara.api;

import java.util.List;

import cemara.labschool.id.rumahcemara.model.ApiResponse;
import cemara.labschool.id.rumahcemara.model.response.OutreachLocationDataResponse;
import cemara.labschool.id.rumahcemara.model.response.OutreachNearMeResponse;
import okhttp3.ResponseBody;
import cemara.labschool.id.rumahcemara.model.ApiResponse;
import cemara.labschool.id.rumahcemara.model.LoginRequest;
import cemara.labschool.id.rumahcemara.model.Treatment;
import cemara.labschool.id.rumahcemara.model.User;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    String BASE_URL = "http://37.72.172.144/rumah-cemara-api/public/api/";

    @GET("userLocation/nearMe")
    Call<ApiResponse<List<OutreachNearMeResponse>>> getOutreachListNearMe(@Query("lat") Double latitude, @Query("long") Double longitude, @Query("radius") int radius);

    @POST("register")
    Call<ApiResponse> postRegister(@Body RequestBody registerRequest);

    @POST("login")
    Call<ApiResponse<User>> postLogin(@Body LoginRequest loginRequest);

    @GET("treatment")
    Call<ApiResponse<List<Treatment>>> getAllTreatment();
}
