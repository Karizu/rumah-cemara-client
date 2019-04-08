package cemara.labschool.id.rumahcemara.api;

import com.rezkyatinnov.kyandroid.reztrofit.RestCallback;
import com.rezkyatinnov.kyandroid.reztrofit.Reztrofit;

import java.util.List;

import cemara.labschool.id.rumahcemara.model.ApiResponse;
import cemara.labschool.id.rumahcemara.model.Treatment;

public class TreatmentHelper {

    public static void getAllTreatment(RestCallback<ApiResponse<List<Treatment>>> callback){
        Reztrofit<ApiInterface> service = Reztrofit.getInstance();
        service.getEndpoint().getAllTreatment().enqueue(callback);
    }

    public static void getAllInstitution(RestCallback<ApiResponse<List<Treatment>>> callback){
        Reztrofit<ApiInterface> service = Reztrofit.getInstance();
        service.getEndpoint().getAllInstitution("Institution").enqueue(callback);
    }
}
