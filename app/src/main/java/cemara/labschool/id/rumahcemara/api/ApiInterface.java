package cemara.labschool.id.rumahcemara.api;

import java.util.List;

import cemara.labschool.id.rumahcemara.model.ApiResponse;
import cemara.labschool.id.rumahcemara.model.LoginRequest;
import cemara.labschool.id.rumahcemara.model.News;
import cemara.labschool.id.rumahcemara.model.Profile;
import cemara.labschool.id.rumahcemara.model.Topic;
import cemara.labschool.id.rumahcemara.model.Treatment;
import cemara.labschool.id.rumahcemara.model.User;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    String BASE_URL = "http://37.72.172.144/rumah-cemara-api/public/api/";

    @POST("register")
    Call<ApiResponse> postRegister(@Body RequestBody registerRequest);

    @POST("login")
    Call<ApiResponse<User>> postLogin(@Body LoginRequest loginRequest);

    @GET("treatment")
    Call<ApiResponse<List<Treatment>>> getAllTreatment();

    @POST("userDevice")
    Call<ApiResponse> postUserDevice(@Body RequestBody userDeviceRequest);

    @POST("profile")
    Call<ApiResponse> updateProfile(@Body RequestBody profile);

    @GET("profile")
    Call<ApiResponse<User>> myProfile();

    @GET("news")
    Call<ApiResponse<List<News>>> getNews();

    @POST("topic")
    Call<ApiResponse> createNewTopic(@Body RequestBody newTopicRequest);

    @GET("topic")
    Call<ApiResponse<List<Topic>>> getAllTopic();

    @GET("topic/{topic_id}")
    Call<ApiResponse<Topic>> getTopicDetail(@Path("topic_id") String topicId);

}
