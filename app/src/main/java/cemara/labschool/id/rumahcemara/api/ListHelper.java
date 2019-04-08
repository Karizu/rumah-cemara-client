package cemara.labschool.id.rumahcemara.api;

import android.util.Log;

import com.rezkyatinnov.kyandroid.localdata.LocalData;
import com.rezkyatinnov.kyandroid.reztrofit.RestCallback;
import com.rezkyatinnov.kyandroid.reztrofit.Reztrofit;

import java.util.List;
import java.util.Objects;

import cemara.labschool.id.rumahcemara.model.ApiResponse;
import cemara.labschool.id.rumahcemara.model.ChatHistory;
import cemara.labschool.id.rumahcemara.model.Datum;
import cemara.labschool.id.rumahcemara.model.Event;
import cemara.labschool.id.rumahcemara.model.HistoryList;
import cemara.labschool.id.rumahcemara.model.ListReminder;
import cemara.labschool.id.rumahcemara.model.ListSaved;
import cemara.labschool.id.rumahcemara.model.User;
import cemara.labschool.id.rumahcemara.model.response.GeneralDataResponse;
import cemara.labschool.id.rumahcemara.model.response.HistoryListResponse;
import io.realm.Realm;

public class ListHelper {


    public static void getListReminder(RestCallback<ApiResponse<List<ListReminder>>> callback)
    {
        Realm realm = LocalData.getRealm();

        User user = realm.where(User.class).findFirst();
        Log.d("API Reminder",user.getId());
        Reztrofit<ApiInterface> service = Reztrofit.getInstance();
        service.getEndpoint().getListReminder(user.getId(),"Reminder").enqueue(callback);
    }
    public static void getListSaved(RestCallback<ApiResponse<List<ListSaved>>> callback)
    {
        Realm realm = LocalData.getRealm();

        User user = realm.where(User.class).findFirst();
        Reztrofit<ApiInterface> service = Reztrofit.getInstance();
        service.getEndpoint().getListSaved(user.getId(),"Saved").enqueue(callback);

    }

    public static void postCreateUserList(String userId,String type,String typeId, String datetime,RestCallback<ApiResponse> callback)
    {
        Reztrofit<ApiInterface> service = Reztrofit.getInstance();
        service.getEndpoint().postCreateUserList(userId,type,typeId,datetime).enqueue(callback);

    }

    public static void removeBookmark(String id, RestCallback<ApiResponse> callback)
    {
        Reztrofit<ApiInterface> service = Reztrofit.getInstance();
        service.getEndpoint().removeBookmark(id).enqueue(callback);

    }

    public static void getListAppointmentHistory(RestCallback<ApiResponse<List<HistoryListResponse>>> callback)
    {
        Realm realm = LocalData.getRealm();

        User user = realm.where(User.class).findFirst();
        Reztrofit<ApiInterface> service = Reztrofit.getInstance();
        service.getEndpoint().getHistoryAppointment(Objects.requireNonNull(user).getId()).enqueue(callback);

    }

    public static void getListAppointmentHistoryProvider(RestCallback<ApiResponse<List<HistoryList>>> callback)
    {
        Realm realm = LocalData.getRealm();

        User user = realm.where(User.class).findFirst();
        Reztrofit<ApiInterface> service = Reztrofit.getInstance();
        service.getEndpoint().getHistoryAppointmentProvider(Objects.requireNonNull(user).getId()).enqueue(callback);

    }

    public static void getChatHistory(String serviceId, RestCallback<ApiResponse<List<Datum>>> callback){
        Reztrofit<ApiInterface> service = Reztrofit.getInstance();
        service.getEndpoint().chatHistory(serviceId).enqueue(callback);
    }
}
