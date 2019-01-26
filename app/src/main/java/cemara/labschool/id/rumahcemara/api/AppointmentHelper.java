package cemara.labschool.id.rumahcemara.api;

import com.rezkyatinnov.kyandroid.reztrofit.RestCallback;
import com.rezkyatinnov.kyandroid.reztrofit.Reztrofit;

import java.util.List;

import cemara.labschool.id.rumahcemara.model.ApiResponse;
import cemara.labschool.id.rumahcemara.model.response.OutreachNearMeResponse;
import cemara.labschool.id.rumahcemara.model.response.ProviderNearMeResponse;
import okhttp3.RequestBody;

public class AppointmentHelper {

    public static void getListOutreach(Double latitude, Double longitude, RestCallback<ApiResponse<List<OutreachNearMeResponse>>> callback){
        Reztrofit<ApiInterface> service = Reztrofit.getInstance();
        service.getEndpoint().getOutreachListNearMe(latitude, longitude, 30).enqueue(callback);
    }

    public static void getListProvider(Double latitude, Double longitude, RestCallback<ApiResponse<List<ProviderNearMeResponse>>> callback){
        Reztrofit<ApiInterface> service = Reztrofit.getInstance();
        service.getEndpoint().getProviderListNearMe(latitude, longitude, 30).enqueue(callback);
    }

    public static void createBiomedicalAppointmentOutreach(RequestBody appointment, RestCallback<ApiResponse> callback){
        Reztrofit<ApiInterface> service = Reztrofit.getInstance();
        service.getEndpoint().createBiomedicalAppointmentOutreach(appointment).enqueue(callback);
    }


}
