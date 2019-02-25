package cemara.labschool.id.rumahcemara.api;

import java.util.List;

import cemara.labschool.id.rumahcemara.model.ApiResponse;
import cemara.labschool.id.rumahcemara.model.Article;
import cemara.labschool.id.rumahcemara.model.Chat;
import cemara.labschool.id.rumahcemara.model.Event;
import cemara.labschool.id.rumahcemara.model.GenerateToken;
import cemara.labschool.id.rumahcemara.model.HistoryList;
import cemara.labschool.id.rumahcemara.model.ListReminder;
import cemara.labschool.id.rumahcemara.model.ListSaved;
import cemara.labschool.id.rumahcemara.model.Token;
import cemara.labschool.id.rumahcemara.model.response.GeneralDataResponse;
import cemara.labschool.id.rumahcemara.model.response.HistoryListResponse;
import cemara.labschool.id.rumahcemara.model.response.OutreachLocationDataResponse;
import cemara.labschool.id.rumahcemara.model.response.OutreachNearMeResponse;
import cemara.labschool.id.rumahcemara.model.response.ProviderNearMeResponse;
import okhttp3.ResponseBody;
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
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

//    String BASE_URL = "http://37.72.172.144/rumah-cemara-api/public/api/";

    String BASE_URL = "http://68.183.226.23/rumah-cemara-api/public/api/";

    @GET("userLocation/nearMe")
    Call<ApiResponse<List<OutreachNearMeResponse>>> getOutreachListNearMe(@Query("lat") Double latitude, @Query("long") Double longitude, @Query("radius") int radius);

    @GET("groupLocation/nearMe")
    Call<ApiResponse<List<ProviderNearMeResponse>>> getProviderListNearMe(@Query("lat") Double latitude, @Query("long") Double longitude, @Query("radius") int radius);

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

    @POST("serviceTransaction")
    Call<ApiResponse> createBiomedicalAppointmentOutreach(@Body RequestBody appointment);

    @POST("topic")
    Call<ApiResponse> createNewTopic(@Body RequestBody newTopicRequest);

    @GET("topic")
    Call<ApiResponse<List<Topic>>> getAllTopic();

    @GET("topic/{topic_id}")
    Call<ApiResponse<Topic>> getTopicDetail(@Path("topic_id") String topicId);

    @GET("serviceTransaction/myAppointment")
    Call<ApiResponse<List<GeneralDataResponse>>> getMyAppointmentList (@Query("user_id") String userId);

    @POST("message")
    Call<ApiResponse> sendMessage(@Body Chat chat);

    @GET("generateToken")
    Call<ApiResponse<Token>> generateToken(@Query("user_id") String userId);

    @GET("serviceTransaction/reportStatus")
    Call<ApiResponse<List<GeneralDataResponse>>> getMyReport (@Query("user_id") String userId);

    @POST("forgot")
    @FormUrlEncoded
    Call<ApiResponse> postForgotPassword (@Field("email") String email);

    @POST("recover")
    @FormUrlEncoded
    Call<ApiResponse> postRecoverPassword (@Field("email") String email, @Field("number") String number, @Field("password") String password);

    /***************** News API *********************/
    @GET("news")
    Call<ApiResponse<List<News>>> getNews();

    @GET("news")
    Call<ApiResponse<List<News>>> getNewsWithCategory(@Query("news_category_id") String newsCategoryId);
    @GET("newsCategories")
    Call<ApiResponse<List<News>>> getNewsCategories();

    @GET("news/{news_id}")
    Call<ApiResponse<News>> getNewsDetail(@Path("news_id") String id);

    /***************** Article API *********************/
    @GET("article")
    Call<ApiResponse<List<Article>>> getArticle();

    @GET("article")
    Call<ApiResponse<List<Article>>> getArticleWithCategory(@Query("article_category_id") String articleCategoryId);

    @GET("article/{article_id}")
    Call<ApiResponse<Article>> getArticleDetail(@Path("article_id") String id);

    @GET("articleCategories")
    Call<ApiResponse<List<Article>>> getArticleCategories();

    /***************** Event API *********************/
    @GET("event")
    Call<ApiResponse<List<Event>>> getEvent();

    @GET("event")
    Call<ApiResponse<List<Event>>> getEventWithCategory(@Query("event_category_id") String eventCategoryId);

    @GET("event/{event_id}")
    Call<ApiResponse<Event>> getEventDetail(@Path("event_id") String id);

    @GET("eventCategories")
    Call<ApiResponse<List<Event>>> getEventCategories();

    /***************** List API *********************/
    @GET("userList")
    Call<ApiResponse<List<ListReminder>>> getListReminder(@Query("user_id") String userId, @Query("type") String type);
    @GET("userList")
    Call<ApiResponse<List<ListSaved>>> getListSaved(@Query("user_id") String userId, @Query("type") String type);

    /***************** List Appointment History API *********************/
    @GET("serviceTransaction/myHistory")
    Call<ApiResponse<List<HistoryListResponse>>> getHistoryAppointment(@Query("user_id") String userId);

    @POST("rating")
    Call<ApiResponse> createRating(@Body RequestBody rating);
    @FormUrlEncoded
    @POST("userList")
    Call<ApiResponse> postCreateUserList(@Field("user_id") String userId, @Field("type") String type,@Field("type_id") String typeId,@Field("datetime") String datetime);

}
