package cemara.labschool.id.rumahcemara.api;

import java.util.List;

import cemara.labschool.id.rumahcemara.model.ApiResponse;
import cemara.labschool.id.rumahcemara.model.response.OutreachLocationDataResponse;
import cemara.labschool.id.rumahcemara.model.response.OutreachNearMeResponse;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    String BASE_URL = "http://37.72.172.144/rumah-cemara-api/public/api/";

    @FormUrlEncoded
    @POST("login")
    Call<ResponseBody> loginRequest(@Field("username") String username,
                                    @Field("password") String password);

    @GET("userLocation/nearMe")
    Call<ApiResponse<List<OutreachNearMeResponse>>> getOutreachListNearMe(@Query("lat") Double latitude, @Query("long") Double longitude, @Query("radius") int radius);

    @GET("userLocation/nearMe")
    Call<ApiResponse<List<OutreachNearMeResponse>>> getOutreachListNearMes(@Query("lat") Double latitude, @Query("long") Double longitude, @Query("radius") int radius, @Header("Authorization") String token);

}
