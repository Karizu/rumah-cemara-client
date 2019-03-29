package cemara.labschool.id.rumahcemara.util;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.rezkyatinnov.kyandroid.localdata.LocalData;
import com.rezkyatinnov.kyandroid.reztrofit.ErrorResponse;
import com.rezkyatinnov.kyandroid.reztrofit.RestCallback;

import java.util.List;

import cemara.labschool.id.rumahcemara.api.ListHelper;
import cemara.labschool.id.rumahcemara.model.ApiResponse;
import cemara.labschool.id.rumahcemara.model.Event;
import cemara.labschool.id.rumahcemara.model.ListReminder;
import cemara.labschool.id.rumahcemara.model.User;
import cemara.labschool.id.rumahcemara.util.dialog.Loading;
import io.realm.Realm;
import okhttp3.Headers;

public class MarkEventClickListener implements View.OnClickListener {
    private Event event;
    private Context context;
    private String eventId,eventDate;
    private int flag1 = 1;
    private  cemara.labschool.id.rumahcemara.util.event.model.Event eventUtil;

    public MarkEventClickListener(Context context, Event event)
    {
        this.context=context;
        this.event=event;
        this.eventUtil=null;
    }
    public MarkEventClickListener(cemara.labschool.id.rumahcemara.util.event.model.Event event)
    {
        this.eventUtil=event;
        this.event=null;
    }

    @Override
    public void onClick(View v) {
        // Use mView here if needed
        Loading.show(v.getContext());
        Realm realm = LocalData.getRealm();
        User user = realm.where(User.class).findFirst();

        if(eventUtil!=null){
            eventId=eventUtil.getEventId();
            eventDate=eventUtil.getDateCreated();
        } else {
            eventId=event.getId();
            eventDate=event.getCreatedAt();
        }

        checkBookmark(eventId, eventDate);

    }

    private void checkBookmark(String eventId, String eventDate){
        ListHelper.getListReminder(new RestCallback<ApiResponse<List<ListReminder>>>() {
            @Override
            public void onSuccess(Headers headers, ApiResponse<List<ListReminder>> body) {
                Loading.hide(context);
                if (body != null && body.isStatus()) {
                    List<cemara.labschool.id.rumahcemara.model.ListReminder> res = body.getData();
                    Log.d("Saved Size",String.valueOf(res.size()));
                    if (res.size() != 0) {
                        for (int i = 0; i < res.size(); i++) {
                            if (res.get(i).getEvent()!=null){
                                if(res.get(i).getEvent().getId().equals(eventId)){
                                    flag1 = 0;
                                    Log.d("aaa", "33");
                                }
                            }
                        }
                    } else {
                        flag1 = 1;
                        Log.d("aaa", "55");
                    }

                    if (flag1 != 0) {
                        createBookmark();
                    } else {
                        Toast.makeText(context, "Event sudah ada dalam list anda", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailed(ErrorResponse error) {
                Loading.hide(context);
            }

            @Override
            public void onCanceled() {

            }
        });
    }

    private void createBookmark(){
        Realm realm = LocalData.getRealm();
        User user = realm.where(User.class).findFirst();

        if(eventUtil!=null){
            eventId=eventUtil.getEventId();
            eventDate=eventUtil.getDateCreated();
        } else {
            eventId=event.getId();
            eventDate=event.getCreatedAt();
        }

        ListHelper.postCreateUserList(
                user.getId(),
                "Event",
                eventId,
                eventDate,
                new RestCallback<ApiResponse>() {
                    @Override
                    public void onSuccess(Headers headers, ApiResponse body) {
                        Loading.hide(context);
                        if (body != null && body.isStatus()) {

                            Toast.makeText(context, "Event Marked", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, body.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailed(ErrorResponse error) {
                        Loading.hide(context);
                        Toast.makeText(context,"Event Marked Failed", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCanceled() {
                        Loading.hide(context);
                    }
                });
    }
}



