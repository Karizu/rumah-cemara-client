package cemara.labschool.id.rumahcemara.api;

import com.rezkyatinnov.kyandroid.reztrofit.RestCallback;
import com.rezkyatinnov.kyandroid.reztrofit.Reztrofit;

import java.util.List;

import cemara.labschool.id.rumahcemara.model.ApiResponse;
import cemara.labschool.id.rumahcemara.model.Topic;
import okhttp3.RequestBody;

public class AskTheExpertHelper {

    public static void createTopic(RequestBody createTopicRequest, RestCallback<ApiResponse> callback){
        Reztrofit<ApiInterface> service = Reztrofit.getInstance();
        service.getEndpoint().createNewTopic(createTopicRequest).enqueue(callback);
    }

    public static void getAllTopic(RestCallback<ApiResponse<List<Topic>>> callback){
        Reztrofit<ApiInterface> service = Reztrofit.getInstance();
        service.getEndpoint().getAllTopic().enqueue(callback);
    }

    public static void getTopicDetail(String topicId, RestCallback<ApiResponse<Topic>> callback){
        Reztrofit<ApiInterface> service = Reztrofit.getInstance();
        service.getEndpoint().getTopicDetail(topicId).enqueue(callback);
    }
}
