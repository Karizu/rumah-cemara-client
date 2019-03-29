package cemara.labschool.id.rumahcemara.api;

import com.rezkyatinnov.kyandroid.reztrofit.RestCallback;
import com.rezkyatinnov.kyandroid.reztrofit.Reztrofit;

import java.util.List;

import cemara.labschool.id.rumahcemara.model.ApiResponse;
import cemara.labschool.id.rumahcemara.model.CategoryModel;
import cemara.labschool.id.rumahcemara.model.Event;

public class EventHelper {

    public static  String CAMPAIGN_CATEGORY_ID="026334b8-47cf-5274-a523-82c9b69cf93e";
    public static  String CAPACITY_CATEGORY_ID="e79c0889-a2aa-5327-868d-e6d91c85ecf4";
    public static  String TRAINING_CATEGORY_ID="2750251b-eb91-5e02-a055-52e57983f49e";
    public static void getEvent(RestCallback<ApiResponse<List<Event>>> callback){
        Reztrofit<ApiInterface> service = Reztrofit.getInstance();
        service.getEndpoint().getEvent(1).enqueue(callback);
    }

    public static void getEventWithCategory(String newsCategoryId,RestCallback<ApiResponse<List<Event>>> callback){
        Reztrofit<ApiInterface> service = Reztrofit.getInstance();
        service.getEndpoint().getEventWithCategory(newsCategoryId, 1).enqueue(callback);
    }
    public static void getEventCampaign(String id, RestCallback<ApiResponse<List<Event>>> callback){
        getEventWithCategory(id,callback);
    }

    public static void getEventCapacity(RestCallback<ApiResponse<List<Event>>> callback){
        getEventWithCategory(CAPACITY_CATEGORY_ID,callback);
    }

    public static void getEventTraining(RestCallback<ApiResponse<List<Event>>> callback){
        getEventWithCategory(TRAINING_CATEGORY_ID,callback);
    }

    public static void getEventDetail(String id,RestCallback<ApiResponse<Event>> callback){
        Reztrofit<ApiInterface> service = Reztrofit.getInstance();
        service.getEndpoint().getEventDetail(id).enqueue(callback);
    }
    public static void getEventCategory(RestCallback<ApiResponse<List<CategoryModel>>> callback){
        Reztrofit<ApiInterface> service = Reztrofit.getInstance();
        service.getEndpoint().getEventCategories().enqueue(callback);
    }

}
