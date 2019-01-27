package cemara.labschool.id.rumahcemara.api;

import com.rezkyatinnov.kyandroid.reztrofit.RestCallback;
import com.rezkyatinnov.kyandroid.reztrofit.Reztrofit;

import java.util.List;

import cemara.labschool.id.rumahcemara.model.ApiResponse;
import cemara.labschool.id.rumahcemara.model.News;
import cemara.labschool.id.rumahcemara.model.Treatment;

public class NewsHelper {

    public static  String CAMPAIGN_CATEGORY_ID="cac6430c-19aa-46c9-9805-7e8d9e35b723";
    public static  String CAPACITY_CATEGORY_ID="263b8057-e1be-4c3f-b550-b0549ea91517";
    public static  String TRAINING_CATEGORY_ID="993df927-7e72-555f-9abb-74d695ac172f";
    public static void getNews(RestCallback<ApiResponse<List<News>>> callback){
        Reztrofit<ApiInterface> service = Reztrofit.getInstance();
        service.getEndpoint().getNews().enqueue(callback);
    }

    public static void getNewsWithCategory(String newsCategoryId,RestCallback<ApiResponse<List<News>>> callback){
        Reztrofit<ApiInterface> service = Reztrofit.getInstance();
        service.getEndpoint().getNewsWithCategory(newsCategoryId).enqueue(callback);
    }
    public static void getNewsCampaign(RestCallback<ApiResponse<List<News>>> callback){
        getNewsWithCategory(CAMPAIGN_CATEGORY_ID,callback);
    }

    public static void getNewsCapacity(RestCallback<ApiResponse<List<News>>> callback){
        getNewsWithCategory(CAPACITY_CATEGORY_ID,callback);
    }

    public static void getNewsTraining(RestCallback<ApiResponse<List<News>>> callback){
        getNewsWithCategory(TRAINING_CATEGORY_ID,callback);
    }

    public static void getNewsDetail(String id,RestCallback<ApiResponse<News>> callback){
        Reztrofit<ApiInterface> service = Reztrofit.getInstance();
        service.getEndpoint().getNewsDetail(id).enqueue(callback);
    }

}
