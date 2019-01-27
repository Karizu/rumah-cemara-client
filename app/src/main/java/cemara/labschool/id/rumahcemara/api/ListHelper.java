package cemara.labschool.id.rumahcemara.api;

import android.util.Log;

import com.rezkyatinnov.kyandroid.localdata.LocalData;
import com.rezkyatinnov.kyandroid.reztrofit.RestCallback;
import com.rezkyatinnov.kyandroid.reztrofit.Reztrofit;

import java.util.List;

import cemara.labschool.id.rumahcemara.model.ApiResponse;
import cemara.labschool.id.rumahcemara.model.Event;
import cemara.labschool.id.rumahcemara.model.ListReminder;
import cemara.labschool.id.rumahcemara.model.User;
import cemara.labschool.id.rumahcemara.model.response.GeneralDataResponse;
import io.realm.Realm;

public class ListHelper {

    public static void getUserList(String type,String userId,RestCallback<ApiResponse<List<ListReminder>>> callback){
        Reztrofit<ApiInterface> service = Reztrofit.getInstance();
        service.getEndpoint().getUserList(userId,type).enqueue(callback);
    }

    public static void getListReminder(RestCallback<ApiResponse<List<ListReminder>>> callback)
    {
        Realm realm = LocalData.getRealm();

        User user = realm.where(User.class).findFirst();
        Log.d("API Reminder",user.getId());
        getUserList("Reminder", user.getId(),callback);
    }
    public static void getListSaved(RestCallback<ApiResponse<List<ListReminder>>> callback)
    {
        Realm realm = LocalData.getRealm();

        User user = realm.where(User.class).findFirst();
        getUserList("Saved", user.getId(),callback);
    }
}
