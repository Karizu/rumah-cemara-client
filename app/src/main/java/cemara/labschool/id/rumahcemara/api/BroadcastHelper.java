package cemara.labschool.id.rumahcemara.api;

import com.rezkyatinnov.kyandroid.reztrofit.RestCallback;
import com.rezkyatinnov.kyandroid.reztrofit.Reztrofit;

import java.util.List;

import cemara.labschool.id.rumahcemara.model.ApiResponse;
import cemara.labschool.id.rumahcemara.model.Article;
import cemara.labschool.id.rumahcemara.model.Broadcast;
import cemara.labschool.id.rumahcemara.model.CategoryModel;

public class BroadcastHelper {

    public static  String CAMPAIGN_CATEGORY_ID="3a11c4b3-cb8a-4dc8-970f-33f5b6132844";
    public static  String CAPACITY_CATEGORY_ID="2784d424-99a3-4078-b32f-a0f63b984c86";
    public static  String TRAINING_CATEGORY_ID="2750251b-eb91-5e02-a055-52e57983f49e";
    public static void getArticle(RestCallback<ApiResponse<List<Article>>> callback){
        Reztrofit<ApiInterface> service = Reztrofit.getInstance();
        service.getEndpoint().getArticle(1).enqueue(callback);
    }

    public static void getArticleWithCategory(String newsCategoryId,RestCallback<ApiResponse<List<Broadcast>>> callback){
        Reztrofit<ApiInterface> service = Reztrofit.getInstance();
        service.getEndpoint().getBroadcastWithCategory(newsCategoryId, 1).enqueue(callback);
    }
    public static void getArticleCampaign(String id, RestCallback<ApiResponse<List<Broadcast>>> callback){
        getArticleWithCategory(id,callback);
    }

    public static void getArticleCapacity(RestCallback<ApiResponse<List<Broadcast>>> callback){
        getArticleWithCategory(CAPACITY_CATEGORY_ID,callback);
    }

    public static void getArticleTraining(RestCallback<ApiResponse<List<Broadcast>>> callback){
        getArticleWithCategory(TRAINING_CATEGORY_ID,callback);
    }

    public static void getBroadcastDetail(String id,RestCallback<ApiResponse<Broadcast>> callback){
        Reztrofit<ApiInterface> service = Reztrofit.getInstance();
        service.getEndpoint().getBroadcastDetail(id).enqueue(callback);
    }
    public static void getBroadcastCategories(RestCallback<ApiResponse<List<CategoryModel>>> callback){
        Reztrofit<ApiInterface> service = Reztrofit.getInstance();
        service.getEndpoint().getBroadcastCategories().enqueue(callback);
    }

}
