package cemara.labschool.id.rumahcemara.api;

import com.rezkyatinnov.kyandroid.reztrofit.RestCallback;
import com.rezkyatinnov.kyandroid.reztrofit.Reztrofit;

import java.util.List;

import cemara.labschool.id.rumahcemara.model.ApiResponse;
import cemara.labschool.id.rumahcemara.model.Article;
import cemara.labschool.id.rumahcemara.model.Chat;
import cemara.labschool.id.rumahcemara.model.Datum;
import cemara.labschool.id.rumahcemara.model.response.GeneralDataResponse;
import cemara.labschool.id.rumahcemara.model.response.OutreachNearMeResponse;
import cemara.labschool.id.rumahcemara.model.response.ProviderNearMeResponse;
import okhttp3.RequestBody;

public class AppointmentHelper {

    public static void getListOutreach(Double latitude, Double longitude, RestCallback<ApiResponse<List<OutreachNearMeResponse>>> callback){
        Reztrofit<ApiInterface> service = Reztrofit.getInstance();
        service.getEndpoint().getOutreachListNearMe(latitude, longitude, 30).enqueue(callback);
    }

    public static void getListProviderBiomedical(Double latitude, Double longitude, RestCallback<ApiResponse<List<ProviderNearMeResponse>>> callback){
        Reztrofit<ApiInterface> service = Reztrofit.getInstance();
        service.getEndpoint().getProviderListNearMe(latitude, longitude, 30, "74b991aa-cf71-5f8f-990c-742081b2f601").enqueue(callback);
    }

    public static void getListProviderBehavioral(Double latitude, Double longitude, RestCallback<ApiResponse<List<ProviderNearMeResponse>>> callback){
        Reztrofit<ApiInterface> service = Reztrofit.getInstance();
        service.getEndpoint().getProviderListNearMe(latitude, longitude, 30, "17c00365-4987-5f1e-925b-2119fbe5ff8a").enqueue(callback);
    }

    public static void getListProviderLegalCounseling(Double latitude, Double longitude, RestCallback<ApiResponse<List<ProviderNearMeResponse>>> callback){
        Reztrofit<ApiInterface> service = Reztrofit.getInstance();
        service.getEndpoint().getProviderListNearMe(latitude, longitude, 30, "b1cd92a3-2f47-5776-aa60-31c58c9f5291").enqueue(callback);
    }

    public static void createBiomedicalAppointmentOutreach(RequestBody appointment, RestCallback<ApiResponse> callback){
        Reztrofit<ApiInterface> service = Reztrofit.getInstance();
        service.getEndpoint().createBiomedicalAppointmentOutreach(appointment).enqueue(callback);
    }

    public static void getMyAppointment(String userId, RestCallback<ApiResponse<List<GeneralDataResponse>>> callback){
        Reztrofit<ApiInterface> service = Reztrofit.getInstance();
        service.getEndpoint().getMyAppointmentList(userId).enqueue(callback);
    }

    public static void sendMessage(Datum chat, RestCallback<ApiResponse> callback){
        Reztrofit<ApiInterface> service = Reztrofit.getInstance();
        service.getEndpoint().sendMessage(chat).enqueue(callback);
    }

    public static void getMyReport(String userId,RestCallback<ApiResponse<List<GeneralDataResponse>>> callback){
        Reztrofit<ApiInterface> service = Reztrofit.getInstance();
        service.getEndpoint().getMyReport(userId).enqueue(callback);
    }

    public static void createAppointmentRating(RequestBody rating, RestCallback<ApiResponse> callback){
        Reztrofit<ApiInterface> service = Reztrofit.getInstance();
        service.getEndpoint().createRating(rating).enqueue(callback);
    }

}
