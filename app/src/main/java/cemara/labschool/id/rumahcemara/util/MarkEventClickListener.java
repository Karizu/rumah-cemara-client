package cemara.labschool.id.rumahcemara.util;

import android.view.View;
import android.widget.Toast;

import com.rezkyatinnov.kyandroid.localdata.LocalData;
import com.rezkyatinnov.kyandroid.reztrofit.ErrorResponse;
import com.rezkyatinnov.kyandroid.reztrofit.RestCallback;

import cemara.labschool.id.rumahcemara.api.ListHelper;
import cemara.labschool.id.rumahcemara.model.ApiResponse;
import cemara.labschool.id.rumahcemara.model.Event;
import cemara.labschool.id.rumahcemara.model.User;
import cemara.labschool.id.rumahcemara.util.dialog.Loading;
import io.realm.Realm;
import okhttp3.Headers;

public class MarkEventClickListener implements View.OnClickListener {
    private  Event event;
    private  cemara.labschool.id.rumahcemara.util.event.model.Event eventUtil;

    public MarkEventClickListener(Event event)
    {
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

        String eventId,eventDate;
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
                        Loading.hide(v.getContext());
                        if (body != null && body.isStatus()) {

                            Toast.makeText(v.getContext(), "Event Marked", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(v.getContext(), body.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailed(ErrorResponse error) {
                        Loading.hide(v.getContext());
                        Toast.makeText(v.getContext(),"Event Marked Failed", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCanceled() {
                        Loading.hide(v.getContext());
                    }
                });

    }
}



