package cemara.labschool.id.rumahcemara.mylist.fragment;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.rezkyatinnov.kyandroid.localdata.LocalData;
import com.rezkyatinnov.kyandroid.reztrofit.ErrorResponse;
import com.rezkyatinnov.kyandroid.reztrofit.RestCallback;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.ButterKnife;
import butterknife.OnClick;
import cemara.labschool.id.rumahcemara.R;
import cemara.labschool.id.rumahcemara.api.AppointmentHelper;
import cemara.labschool.id.rumahcemara.home.service.asktheexpert.ChatExpertActivity;
import cemara.labschool.id.rumahcemara.model.ApiResponse;
import cemara.labschool.id.rumahcemara.model.User;
import cemara.labschool.id.rumahcemara.model.response.GeneralDataResponse;
import cemara.labschool.id.rumahcemara.mylist.fragment.Adapter.MyListAppointmentAdapter;
import cemara.labschool.id.rumahcemara.mylist.fragment.activity.ChatAppointmentActivity;
import cemara.labschool.id.rumahcemara.util.dialog.Loading;
import io.realm.Realm;
import okhttp3.Headers;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyListAppointment extends Fragment {

    private Context activity;

    private RecyclerView recyclerView;

    private List<GeneralDataResponse> appointmentList;

    private RecyclerView.Adapter adapter;
    private LinearLayoutManager layoutManager;

    private User user;


    public MyListAppointment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_list_appointment_fragment, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        activity = getActivity();
        layoutManager = new LinearLayoutManager(activity,
                LinearLayout.VERTICAL,
                false);

        recyclerView = view.findViewById(R.id.myAppointmentList);
        recyclerView.setLayoutManager(layoutManager);

        Realm realm = LocalData.getRealm();

        user = realm.where(User.class).findFirst();

        populateData();

    }

    private void populateData() {
        AppointmentHelper.getMyAppointment(user.getId(), new RestCallback<ApiResponse<List<GeneralDataResponse>>>() {
            @Override
            public void onSuccess(Headers headers, ApiResponse<List<GeneralDataResponse>> body) {
                try {
                    if (body != null && body.isStatus()) {
                        List<GeneralDataResponse> res = body.getData();
                        appointmentList = new ArrayList<>();

                        for (int i = 0; i < res.size(); i++) {
                            GeneralDataResponse data = res.get(i);
                            appointmentList.add(data);
                        }

                        adapter = new MyListAppointmentAdapter(appointmentList, activity);
                        recyclerView.setAdapter(adapter);
                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onFailed(ErrorResponse error) {

            }

            @Override
            public void onCanceled() {

            }
        });
    }
}
