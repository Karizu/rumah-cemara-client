package cemara.labschool.id.rumahcemara.mylist.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.rezkyatinnov.kyandroid.reztrofit.ErrorResponse;
import com.rezkyatinnov.kyandroid.reztrofit.RestCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cemara.labschool.id.rumahcemara.Constants;
import cemara.labschool.id.rumahcemara.R;
import cemara.labschool.id.rumahcemara.api.ArticleHelper;
import cemara.labschool.id.rumahcemara.api.ListHelper;
import cemara.labschool.id.rumahcemara.model.ApiResponse;
import cemara.labschool.id.rumahcemara.model.Article;
import cemara.labschool.id.rumahcemara.model.Event;
import cemara.labschool.id.rumahcemara.model.response.GeneralDataResponse;
import cemara.labschool.id.rumahcemara.mylist.fragment.Adapter.MyListAppointmentAdapter;
import cemara.labschool.id.rumahcemara.util.article.model.adapter.ArticleAdapter;
import cemara.labschool.id.rumahcemara.util.dialog.Loading;
import cemara.labschool.id.rumahcemara.util.event.adapter.EventAdapter;
import cemara.labschool.id.rumahcemara.util.events.adapter.EventsAdapter;
import cemara.labschool.id.rumahcemara.util.events.model.Events;
import cemara.labschool.id.rumahcemara.util.helper.DateHelper;
import cemara.labschool.id.rumahcemara.util.list.reminder.adapter.ListReminderAdapter;
import cemara.labschool.id.rumahcemara.util.list.reminder.model.ListReminder;
import okhttp3.Headers;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyListReminder extends Fragment {

    @BindView(R.id.myReminderList)
    RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    ListReminderAdapter eventAdapter;


    private List<ListReminder> reminderList;
    private Context activity;
    private LinearLayoutManager layoutManager;

    public MyListReminder() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d("Reminder", "Starting");
        View rootView = inflater.inflate(R.layout.my_list_reminder_fragment, container, false);
        ButterKnife.bind(this, rootView);

        activity = getActivity();
        layoutManager = new LinearLayoutManager(activity,
                LinearLayout.VERTICAL,
                false);

        recyclerView.setLayoutManager(layoutManager);

        getReminderList();
        return rootView;
    }

    private void getReminderList() {
        Log.d("Reminder", "start");

        Loading.show(getContext());
        ListHelper.getListReminder(new RestCallback<ApiResponse<List<cemara.labschool.id.rumahcemara.model.ListReminder>>>() {
            @Override
            public void onSuccess(Headers headers, ApiResponse<List<cemara.labschool.id.rumahcemara.model.ListReminder>> body) {
                Loading.hide(getContext());
                if (body != null && body.isStatus()) {
                    List<cemara.labschool.id.rumahcemara.model.ListReminder> res = body.getData();
                    Log.d("Reminder Size", String.valueOf(res.size()));
                    reminderList = new ArrayList<>();
                    for (int i = 0; i < res.size(); i++) {
                        //reminderList.add(new cemara.labschool.id.rumahcemara.util.event.model.Event());
                        if (res.get(i).getEvent() != null) {
                            reminderList.add(new ListReminder(res.get(i).getEvent().getId(),res.get(i).getId(), res.get(i).getEvent().getTitle(), res.get(i).getEvent().getPlace(), res.get(i).getEvent().getIsBanner(), res.get(i).getEvent().getBanner()));

                        }
                    }
                    eventAdapter = new ListReminderAdapter(getContext(), reminderList);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(eventAdapter);
                    eventAdapter.notifyDataSetChanged();

                } else {
//                        loadingDialog.dismiss();
                    Toast.makeText(getContext(), body.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailed(ErrorResponse error) {
                Loading.hide(getContext());
                Toast.makeText(getContext(), "Gagal Ambil Data", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCanceled() {
                Loading.hide(getContext());
            }
        });
    }

}
